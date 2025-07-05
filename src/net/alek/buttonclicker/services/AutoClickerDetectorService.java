package net.alek.buttonclicker.services;

import net.alek.buttonclicker.utilities.write.WriteUtility;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AutoClickerDetectorService {
    private static final ArrayList<Long> clickTimes = new ArrayList<>();
    private static final ArrayList<Long> pressDurations = new ArrayList<>();
    private static final int MIN_CLICKS_FOR_DETECTION = 10;
    private static final int WINDOW_SIZE = 50;

    public AutoClickerDetectorService() {
        RenderService.buttonTop.addMouseListener(new MouseListener() {
            private long pressStartTime;

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    pressStartTime = System.currentTimeMillis();
                    RenderService.buttonTop.setLocation(30, 35);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    long pressDuration = System.currentTimeMillis() - pressStartTime;
                    pressDurations.add(pressDuration);
                    long currentTime = System.currentTimeMillis();
                    clickTimes.add(currentTime);

                    if (clickTimes.size() > MIN_CLICKS_FOR_DETECTION) {
                        DetectionResult result = detectAutoClicker();
                        LoggingService.Logger.debug(result.message);
                    }

                    RenderService.buttonTop.setLocation(30, 30);

                    if (Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")) {
                        WriteUtility.clicks1 += WriteUtility.clickPower1;
                        RenderService.clicksField.setText("Clicks: " + WriteUtility.clicks1);
                    } else if (Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")) {
                        WriteUtility.clicks2 += WriteUtility.clickPower2;
                        RenderService.clicksField.setText("Clicks: " + WriteUtility.clicks2);
                    } else if (Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")) {
                        WriteUtility.clicks3 += WriteUtility.clickPower3;
                        RenderService.clicksField.setText("Clicks: " + WriteUtility.clicks3);
                    }

                    AudioService.SFX.playSFX("click");
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private static DetectionResult detectAutoClicker() {
        List<Long> windowClickTimes = getSlidingWindow(clickTimes);
        List<Long> windowPressDurations = getSlidingWindow(pressDurations);

        List<Long> intervals = new ArrayList<>();
        for (int i = 1; i < windowClickTimes.size(); i++) {
            long interval = windowClickTimes.get(i) - windowClickTimes.get(i - 1);
            intervals.add(interval);
        }
        if (intervals.isEmpty()) return new DetectionResult(false, "Not enough data");

        double cps = windowClickTimes.size() / (getTotalTime(windowClickTimes) / 1000.0);

        // Existing stats
        double intervalStdDev = calculateStandardDeviation(intervals);
        double intervalMedian = calculateMedian(intervals);
        double intervalIQR = calculateIQR(intervals);
        double intervalEntropy = calculateEntropy(intervals);
        double durationStdDev = calculateStandardDeviation(windowPressDurations);

        // NEW STATS
        double burstiness = calculateBurstiness(intervals);
        double autocorrelation = calculateAutocorrelation(intervals);
        double skewness = calculateSkewness(intervals);
        double kurtosis = calculateKurtosis(intervals);
        double diRatio = calculateDurationIntervalRatio(windowPressDurations, intervals);
        int intervalRepeats = calculateIntervalRepetitions(intervals);
        double jitter = calculateJitter(intervals);

        // Normalize metrics for suspicion scoring (simple min-max clamping for demo)
        double normBurstiness = clamp(1.0 - burstiness, 0, 1); // low burstiness = suspicious
        double normAutocorr = clamp(autocorrelation, 0, 1);
        double normSkewness = clamp(Math.abs(skewness) / 10.0, 0, 1); // scale down
        double normKurtosis = clamp(kurtosis / 10.0, 0, 1);
        double normDiRatio = clamp(1.0 - diRatio, 0, 1); // low ratio suspicious
        double normIntervalRepeats = clamp(intervalRepeats / 10.0, 0, 1);
        double normJitter = clamp(1.0 - (jitter / 100.0), 0, 1); // low jitter suspicious

        // Weights for each metric - tune these as you want
        double wCPS = 0.25;
        double wIntervalStdDev = 0.8;
        double wEntropy = 1.0;
        double wDurationStdDev = 1.0;
        double wBurstiness = 1.0;
        double wAutocorrelation = 1.2;
        double wSkewness = 0.7;
        double wKurtosis = 0.7;
        double wDiRatio = 1.2;
        double wIntervalRepeats = 1.0;
        double wJitter = 1.0;

        double suspicionScore = 0.0;
        suspicionScore += wCPS * (cps / 100.0); // normalized roughly
        suspicionScore += wIntervalStdDev * (intervalStdDev < 5 ? 1 : 0);
        suspicionScore += wEntropy * (intervalEntropy < 0.5 ? 1 : 0);
        suspicionScore += wDurationStdDev * (durationStdDev < 5 ? 1 : 0);
        suspicionScore += wBurstiness * normBurstiness;
        suspicionScore += wAutocorrelation * normAutocorr;
        suspicionScore += wSkewness * normSkewness;
        suspicionScore += wKurtosis * normKurtosis;
        suspicionScore += wDiRatio * normDiRatio;
        suspicionScore += wIntervalRepeats * normIntervalRepeats;
        suspicionScore += wJitter * normJitter;

        boolean detected = suspicionScore > 3.5; // tune threshold to your liking

        String message = (detected ? "Auto clicker detected! 🔥 " : "No auto clicker detected. ✅ ") +
                String.format("Suspicion Score: %.2f | CPS: %.2f | IntervalStdDev: %.2f | Entropy: %.2f | DurationStdDev: %.2f\n", suspicionScore, cps, intervalStdDev, intervalEntropy, durationStdDev) +
                String.format("Burstiness: %.2f | Autocorr: %.2f | Skewness: %.2f | Kurtosis: %.2f | DI Ratio: %.2f | IntervalRepeats: %d | Jitter: %.2f",
                        burstiness, autocorrelation, skewness, kurtosis, diRatio, intervalRepeats, jitter);

        return new DetectionResult(detected, message);
    }

    // ======= NEW METRICS IMPLEMENTATION =======

    private static double calculateBurstiness(List<Long> intervals) {
        double mean = calculateMean(intervals);
        double stddev = calculateStandardDeviation(intervals);
        if (mean + stddev == 0) return 0;
        return (stddev - mean) / (stddev + mean);
    }

    private static double calculateAutocorrelation(List<Long> intervals) {
        if (intervals.size() < 2) return 0;
        double mean = calculateMean(intervals);
        double variance = 0;
        double covariance = 0;
        for (int i = 0; i < intervals.size() - 1; i++) {
            variance += Math.pow(intervals.get(i) - mean, 2);
            covariance += (intervals.get(i) - mean) * (intervals.get(i + 1) - mean);
        }
        if (variance == 0) return 0;
        return covariance / variance;
    }

    private static double calculateSkewness(List<Long> intervals) {
        double mean = calculateMean(intervals);
        double stddev = calculateStandardDeviation(intervals);
        if (stddev == 0) return 0;
        double skewSum = 0;
        for (long v : intervals) {
            skewSum += Math.pow((v - mean) / stddev, 3);
        }
        return skewSum / intervals.size();
    }

    private static double calculateKurtosis(List<Long> intervals) {
        double mean = calculateMean(intervals);
        double stddev = calculateStandardDeviation(intervals);
        if (stddev == 0) return 0;
        double kurtSum = 0;
        for (long v : intervals) {
            kurtSum += Math.pow((v - mean) / stddev, 4);
        }
        return kurtSum / intervals.size() - 3; // excess kurtosis
    }

    private static double calculateDurationIntervalRatio(List<Long> durations, List<Long> intervals) {
        if (durations.isEmpty() || intervals.isEmpty()) return 0;
        double avgDuration = calculateMean(durations);
        double avgInterval = calculateMean(intervals);
        if (avgInterval == 0) return 0;
        return avgDuration / avgInterval;
    }

    private static int calculateIntervalRepetitions(List<Long> intervals) {
        int repeats = 0;
        for (int i = 1; i < intervals.size(); i++) {
            if (Math.abs(intervals.get(i) - intervals.get(i - 1)) < 2) repeats++;
        }
        return repeats;
    }

    private static double calculateJitter(List<Long> intervals) {
        if (intervals.size() < 3) return 0;
        List<Long> deltas = new ArrayList<>();
        for (int i = 2; i < intervals.size(); i++) {
            deltas.add(Math.abs(intervals.get(i) - intervals.get(i - 1)));
        }
        return calculateStandardDeviation(deltas);
    }

    // ======= EXISTING UTILS =======

    private static List<Long> getSlidingWindow(List<Long> data) {
        if (data.size() <= WINDOW_SIZE) {
            return new ArrayList<>(data);
        }
        return data.subList(data.size() - WINDOW_SIZE, data.size());
    }

    private static long getTotalTime(List<Long> clickTimes) {
        return clickTimes.get(clickTimes.size() - 1) - clickTimes.get(0);
    }

    private static double calculateStandardDeviation(List<Long> values) {
        if (values.isEmpty()) return 0;
        double mean = calculateMean(values);
        double sumSquaredDifferences = 0.0;
        for (long value : values) {
            sumSquaredDifferences += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sumSquaredDifferences / values.size());
    }

    private static double calculateMean(List<Long> values) {
        if (values.isEmpty()) return 0;
        double sum = 0.0;
        for (long value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    private static double calculateMedian(List<Long> values) {
        if (values.isEmpty()) return 0;
        Collections.sort(values);
        int size = values.size();
        if (size % 2 == 0) {
            return (values.get(size / 2 - 1) + values.get(size / 2)) / 2.0;
        } else {
            return values.get(size / 2);
        }
    }

    private static double calculateIQR(List<Long> values) {
        if (values.isEmpty()) return 0;
        Collections.sort(values);
        int size = values.size();
        double q1 = calculateMedian(values.subList(0, size / 2));
        double q3 = calculateMedian(values.subList((size + 1) / 2, size));
        return q3 - q1;
    }

    private static double calculateEntropy(List<Long> values) {
        double entropy = 0.0;
        for (long value : values) {
            if (value > 0) {
                entropy += value * Math.log(value);
            }
        }
        return -entropy;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private static class DetectionResult {
        boolean detected;
        String message;

        DetectionResult(boolean detected, String message) {
            this.detected = detected;
            this.message = message;
        }
    }
}