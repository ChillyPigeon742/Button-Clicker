package net.alek.buttonclicker.services;

import net.alek.buttonclicker.core.log.Logger;
import net.alek.buttonclicker.ui.components.ATimer;
import net.alek.buttonclicker.ui.RenderService;
import net.alek.buttonclicker.read.ReadUtility;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class AutoClickerDetectorService {
    private static final Deque<Double> recentScores = new ArrayDeque<>();
    private static final Queue<Long> clickTimes = new ConcurrentLinkedQueue<>();
    private static final Queue<Long> pressDurations = new ConcurrentLinkedQueue<>();
    private static final int MIN_CLICKS_FOR_DETECTION = 15;
    private static final int WINDOW_SIZE = 50;
    private static final double DETECTION_THRESHOLD = 5.5;
    private static final int DATA_CLEANUP_INTERVAL_MINUTES = 5;
    private static final long DETECTION_COOLDOWN_MS = 10_000;
    private static final int MAX_DETECTIONS_BEFORE_PUNISH = 3;
    private static final int SCORE_HISTORY_SIZE = 10;
    private int detectionCount = 0;
    private long lastDetectionTime = 0;

    private static final ScheduledExecutorService cleanupScheduler = Executors.newSingleThreadScheduledExecutor();
    private static final ExecutorService detectionExecutor = Executors.newSingleThreadExecutor();
    private long pressStartTime;

    public AutoClickerDetectorService() {
        RenderService.buttonTop.addMouseListener(createMouseListener());
        scheduleDataCleanup();
    }

    private MouseListener createMouseListener() {
        return new MouseListener() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    pressStartTime = System.nanoTime();
                    RenderService.buttonTop.setLocation(30, 35);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    handleMouseRelease();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        };
    }

    private static void trimQueue(Queue<Long> queue) {
        while (queue.size() > AutoClickerDetectorService.WINDOW_SIZE) {
            queue.poll();
        }
    }

    private void scheduleDataCleanup() {
        cleanupScheduler.scheduleAtFixedRate(() -> {
            trimQueue(clickTimes);
            trimQueue(pressDurations);
        }, DATA_CLEANUP_INTERVAL_MINUTES, DATA_CLEANUP_INTERVAL_MINUTES, TimeUnit.MINUTES);
    }

    private void handleMouseRelease() {
        recordClickData();

        if (shouldCheckForAutoClicker()) {
            long now = System.currentTimeMillis();

            if (now - lastDetectionTime > DETECTION_COOLDOWN_MS) {
                detectionExecutor.submit(() -> {
                    DetectionResult result = detectAutoClicker();
                    Logger.Log.debug(result.message);

                    if (result.detected) {
                        lastDetectionTime = System.currentTimeMillis();

                        detectionCount++;

                        if (detectionCount >= MAX_DETECTIONS_BEFORE_PUNISH) {
                            detectionCount = 0;
                            SwingUtilities.invokeLater(this::handleAutoClickerPunishment);
                        } else {
                            SwingUtilities.invokeLater(this::handleAutoClickerWarning);
                        }
                    }
                });
            }
        }

        updateButtonState();
        updateClickCounter();
        AudioService.SFX.playSFX("click");

        pressStartTime = -1;
    }

    private void recordClickData() {
        if (pressStartTime > 0) {
            long pressDuration = System.nanoTime() - pressStartTime;
            pressDurations.add(pressDuration);
            clickTimes.add(System.nanoTime());
        }
    }

    private boolean shouldCheckForAutoClicker() {
        return clickTimes.size() > MIN_CLICKS_FOR_DETECTION;
    }

    private void updateButtonState() {
        RenderService.buttonTop.setLocation(30, 30);
    }

    private void updateClickCounter() {
        RenderService.clicksField.setText("Clicks: " + getCurrentClicks());
    }

    private BigInteger getCurrentClicks() {
        return (BigInteger) Objects.requireNonNull(ReadUtility.getCurrentSave()).get("clicks");
    }

    private void handleAutoClickerWarning() {
        // Example: show a gentle warning to the user
        JOptionPane.showMessageDialog(null,
                "Warning: Suspicious clicking behavior detected. Please slow down.",
                "AutoClicker Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void handleAutoClickerPunishment() {
        // TODO: punishment logic — e.g. disable clicking, reset score, etc.
        JOptionPane.showMessageDialog(null,
                "AutoClicker detected multiple times. Actions are being taken.",
                "AutoClicker Punishment", JOptionPane.ERROR_MESSAGE);

        resetDetectionData();
        // Example: disable the button for a cooldown period or kick user, etc.
        RenderService.buttonTop.setEnabled(false);

        ATimer timer = new ATimer();
        timer.setDelay((int) DETECTION_COOLDOWN_MS);
        timer.setTask(() -> RenderService.buttonTop.setEnabled(true));
        timer.start();
    }

    private void resetDetectionData() {
        clickTimes.clear();
        pressDurations.clear();
    }

    private static DetectionResult detectAutoClicker() {
        List<Long> windowClickTimes = getSlidingWindow(clickTimes);
        List<Long> rawDurations = getSlidingWindow(pressDurations);
        List<Long> windowPressDurations = rawDurations.stream()
                .map(d -> d / 1_000_000L)
                .collect(Collectors.toList());

        if (windowClickTimes.size() < 2) {
            return new DetectionResult(false, "Not enough data for detection");
        }

        List<Long> intervals = calculateIntervals(windowClickTimes);
        ClickMetrics metrics = calculateAllMetrics(windowClickTimes, windowPressDurations, intervals);
        double suspicionScore = calculateSuspicionScore(metrics);

        double dynamicThreshold = getDynamicThreshold();
        boolean detected = suspicionScore > dynamicThreshold;
        addRecentScore(suspicionScore);
        return new DetectionResult(detected, buildDetectionMessage(detected, suspicionScore, metrics));
    }

    private static List<Long> calculateIntervals(List<Long> clickTimes) {
        List<Long> intervals = new ArrayList<>();
        for (int i = 1; i < clickTimes.size(); i++) {
            intervals.add((clickTimes.get(i) - clickTimes.get(i - 1)) / 1_000_000L);
        }
        return intervals;
    }

    private static ClickMetrics calculateAllMetrics(List<Long> clickTimes, List<Long> pressDurations, List<Long> intervals) {
        ClickMetrics metrics = new ClickMetrics();

        metrics.cps = calculateCPS(clickTimes);
        metrics.intervalStdDev = calculateStandardDeviation(intervals);
        metrics.intervalMedian = calculateMedian(intervals);
        metrics.intervalIQR = calculateIQR(intervals);
        metrics.intervalEntropy = calculateEntropy(intervals);
        metrics.durationStdDev = calculateStandardDeviation(pressDurations);
        metrics.burstiness = calculateBurstiness(intervals);
        metrics.autocorrelation = calculateAutocorrelation(intervals);
        metrics.skewness = calculateSkewness(intervals);
        metrics.kurtosis = calculateKurtosis(intervals);
        metrics.diRatio = calculateDurationIntervalRatio(pressDurations, intervals);
        metrics.intervalRepeats = calculateIntervalRepetitions(intervals);
        metrics.jitter = calculateJitter(intervals);
        metrics.modeConsistency = calculateModeConsistency(intervals);
        metrics.acceleration = calculateClickAcceleration(intervals);

        return metrics;
    }

    private static double calculateSuspicionScore(ClickMetrics metrics) {
        double normCPS = clamp(metrics.cps / 30.0);
        double normIntervalStdDev = clamp(1.0 - (metrics.intervalStdDev / 50.0));
        double normEntropy = clamp(1.0 - metrics.intervalEntropy);
        double normDurationStdDev = clamp(1.0 - (metrics.durationStdDev / 50.0));
        double normBurstiness = clamp(1.0 - metrics.burstiness);
        double normAutocorr = clamp(metrics.autocorrelation);
        double normSkewness = clamp(Math.abs(metrics.skewness) / 10.0);
        double normKurtosis = clamp(metrics.kurtosis / 10.0);
        double normDiRatio = clamp(1.0 - metrics.diRatio);
        double normIntervalRepeats = clamp(metrics.intervalRepeats / 10.0);
        double normJitter = clamp(1.0 - (metrics.jitter / 100.0));
        double normMedianDeviation = clamp(1.0 - (metrics.intervalMedian / 100.0));
        double normIQR = clamp(1.0 - (metrics.intervalIQR / 100.0));
        double normModeConsistency = clamp(metrics.modeConsistency);
        double normAcceleration = clamp(Math.abs(metrics.acceleration) / 10.0);

        double[] weights = {1.0, 1.2, 0.8, 1.0, 1.1, 1.3, 0.7, 0.7, 1.2, 1.0, 1.1, 1.4, 1.3, 1.2, 0.9};
        double[] normalizedMetrics = {
                normCPS, normIntervalStdDev, normEntropy, normDurationStdDev,
                normBurstiness, normAutocorr, normSkewness, normKurtosis,
                normDiRatio, normIntervalRepeats, normJitter, normMedianDeviation,
                normIQR, normModeConsistency, normAcceleration
        };

        double score = 0.0;
        for (int i = 0; i < weights.length; i++) {
            score += weights[i] * normalizedMetrics[i];
        }
        score *= getVolatilityBoost();
        return score;
    }

    private static String buildDetectionMessage(boolean detected, double score, ClickMetrics metrics) {
        return (detected ? "Auto clicker detected" : "Normal click pattern") +
                String.format("\nScore: %.2f (Threshold: %.1f)", score, DETECTION_THRESHOLD) +
                "\n--- Timing ---" +
                String.format("\nCPS: %.2f | Median: %.2fms | IQR: %.2fms",
                        metrics.cps, metrics.intervalMedian, metrics.intervalIQR) +
                String.format("\nStdDev: %.2fms | Jitter: %.2fms | Mode: %.2f",
                        metrics.intervalStdDev, metrics.jitter, metrics.modeConsistency) +
                "\n--- Pattern ---" +
                String.format("\nBurstiness: %.2f | Autocorr: %.2f | Repeats: %d",
                        metrics.burstiness, metrics.autocorrelation, metrics.intervalRepeats) +
                String.format("\nEntropy: %.2f | DI Ratio: %.2f | Accel: %.2f",
                        metrics.intervalEntropy, metrics.diRatio, metrics.acceleration) +
                String.format("\nSkewness: %.2f | Kurtosis: %.2f",
                        metrics.skewness, metrics.kurtosis);
    }

    private static double getVolatilityBoost() {
        if (recentScores.size() < 2) return 1.0;
        double stddev = calculateStandardDeviationDouble(new ArrayList<>(recentScores));
        return 1.0 + (stddev * 0.1);
    }

    private static void addRecentScore(double score) {
        if (recentScores.size() >= SCORE_HISTORY_SIZE) {
            recentScores.pollFirst();
        }
        recentScores.addLast(score);
    }

    private static double getDynamicThreshold() {
        if (recentScores.isEmpty()) return DETECTION_THRESHOLD;
        double avg = recentScores.stream().mapToDouble(Double::doubleValue).average().orElse(DETECTION_THRESHOLD);
        double stdDev = calculateStandardDeviationDouble(new ArrayList<>(recentScores));
        return avg + Math.max(0.75, stdDev * 0.5);
    }

    private static double calculateCPS(List<Long> clickTimes) {
        if (clickTimes.size() < 2) return 0;
        long totalTime = clickTimes.get(clickTimes.size() - 1) - clickTimes.get(0);
        return totalTime > 0 ? (clickTimes.size() / (totalTime / 1_000_000_000.0)) : 0;
    }

    private static double calculateBurstiness(List<Long> intervals) {
        double mean = calculateMean(intervals);
        double stddev = calculateStandardDeviation(intervals);
        return (mean + stddev) != 0 ? (stddev - mean) / (stddev + mean) : 0;
    }

    private static double calculateAutocorrelation(List<Long> intervals) {
        if (intervals.size() < 2) return 0;
        double mean = calculateMean(intervals);
        double variance = 0, covariance = 0;

        for (int i = 0; i < intervals.size() - 1; i++) {
            double diff = intervals.get(i) - mean;
            variance += diff * diff;
            covariance += diff * (intervals.get(i + 1) - mean);
        }

        return variance != 0 ? covariance / variance : 0;
    }

    private static double calculateDurationIntervalRatio(List<Long> durations, List<Long> intervals) {
        if (durations.isEmpty() || intervals.isEmpty()) return 0;
        double avgDuration = calculateMean(durations);
        double avgInterval = calculateMean(intervals);
        return avgInterval != 0 ? avgDuration / avgInterval : 0;
    }

    private static int calculateIntervalRepetitions(List<Long> intervals) {
        int repeats = 0;
        for (int i = 1; i < intervals.size(); i++) {
            if (Math.abs(intervals.get(i) - intervals.get(i - 1)) < 2) repeats++;
        }
        return repeats;
    }

    private static double calculateJitter(List<Long> intervals) {
        List<Long> deltas = new ArrayList<>();
        for (int i = 2; i < intervals.size(); i++) {
            deltas.add(Math.abs(intervals.get(i) - intervals.get(i - 1)));
        }
        return calculateStandardDeviation(deltas);
    }

    private static double calculateSkewness(List<Long> intervals) {
        if (intervals.size() < 3) return 0;
        double mean = calculateMean(intervals);
        double stdDev = calculateStandardDeviation(intervals);
        if (stdDev == 0) return 0;

        double sum = 0;
        for (long value : intervals) {
            sum += Math.pow((value - mean) / stdDev, 3);
        }
        return sum / intervals.size();
    }

    private static double calculateKurtosis(List<Long> intervals) {
        if (intervals.size() < 4) return 0;
        double mean = calculateMean(intervals);
        double stdDev = calculateStandardDeviation(intervals);
        if (stdDev == 0) return 0;

        double sum = 0;
        for (long value : intervals) {
            sum += Math.pow((value - mean) / stdDev, 4);
        }
        return (sum / intervals.size()) - 3;
    }

    private static double calculateModeConsistency(List<Long> intervals) {
        if (intervals.isEmpty()) return 0;
        List<Long> rounded = intervals.stream().map(val -> Math.round(val / 10.0) * 10).toList();

        long mode = rounded.stream()
                .collect(Collectors.groupingBy(val -> val, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(0L);

        long modeCount = rounded.stream().filter(val -> val == mode).count();
        return (double) modeCount / rounded.size();
    }

    private static double calculateClickAcceleration(List<Long> intervals) {
        if (intervals.size() < 3) return 0;
        List<Double> speeds = new ArrayList<>();
        for (int i = 1; i < intervals.size(); i++) {
            speeds.add(1000.0 / intervals.get(i));
        }

        double accelerationSum = 0;
        for (int i = 1; i < speeds.size(); i++) {
            accelerationSum += speeds.get(i) - speeds.get(i - 1);
        }
        return accelerationSum / (speeds.size() - 1);
    }

    private static List<Long> getSlidingWindow(Queue<Long> queue) {
        int size = queue.size();
        int startIndex = Math.max(0, size - WINDOW_SIZE);

        List<Long> result = new ArrayList<>(WINDOW_SIZE);
        int i = 0;
        for (Long value : queue) {
            if (i >= startIndex) {
                result.add(value);
            }
            i++;
        }
        return result;
    }

    private static double calculateStandardDeviation(List<Long> values) {
        if (values.isEmpty()) return 0;
        double mean = calculateMean(values);
        double sum = 0;
        for (long value : values) {
            sum += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sum / values.size());
    }

    private static double calculateStandardDeviationDouble(List<Double> values) {
        if (values.isEmpty()) return 0;
        double mean = values.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double sum = 0;
        for (double value : values) {
            sum += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sum / values.size());
    }


    private static double calculateMean(List<Long> values) {
        return values.isEmpty() ? 0 : values.stream().mapToLong(Long::longValue).average().orElse(0);
    }

    private static double calculateMedian(List<Long> values) {
        if (values.isEmpty()) return 0;
        List<Long> sorted = new ArrayList<>(values);
        Collections.sort(sorted);
        int size = sorted.size();
        return size % 2 == 0 ?
                (sorted.get(size/2 - 1) + sorted.get(size/2)) / 2.0 :
                sorted.get(size/2);
    }

    private static double calculateIQR(List<Long> values) {
        if (values.isEmpty()) return 0;
        List<Long> sorted = new ArrayList<>(values);
        Collections.sort(sorted);
        int size = sorted.size();
        return calculateMedian(sorted.subList((size + 1)/2, size)) -
                calculateMedian(sorted.subList(0, size/2));
    }

    private static double calculateEntropy(List<Long> values) {
        if (values.isEmpty()) return 0;
        double sum = values.stream().mapToLong(Long::longValue).sum();
        if (sum == 0) return 0;

        double entropy = 0;
        for (long value : values) {
            if (sum <= 0) return 0;
            double p = value / sum;
            if (p > 0) entropy += p * Math.log(p);
        }
        return -entropy;
    }

    private static double clamp(double value) {
        return Math.max(0, Math.min(1, value));
    }

    public static void shutdown() {
        cleanupScheduler.shutdown();
        detectionExecutor.shutdown();

        try {
            if (!cleanupScheduler.awaitTermination(1, TimeUnit.SECONDS)) cleanupScheduler.shutdownNow();
            if (!detectionExecutor.awaitTermination(1, TimeUnit.SECONDS)) detectionExecutor.shutdownNow();
        } catch (InterruptedException e) {
            cleanupScheduler.shutdownNow();
            detectionExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private static class DetectionResult {
        final boolean detected;
        final String message;

        DetectionResult(boolean detected, String message) {
            this.detected = detected;
            this.message = message;
        }
    }

    private static class ClickMetrics {
        double cps;
        double intervalStdDev;
        double intervalMedian;
        double intervalIQR;
        double intervalEntropy;
        double durationStdDev;
        double burstiness;
        double autocorrelation;
        double skewness;
        double kurtosis;
        double diRatio;
        int intervalRepeats;
        double jitter;
        double modeConsistency;
        double acceleration;
    }
}