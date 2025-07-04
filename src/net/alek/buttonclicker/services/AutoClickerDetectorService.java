package net.alek.buttonclicker.services;

import net.alek.buttonclicker.utilities.WriteUtility;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AutoClickerDetectorService {
    private static final ArrayList<Long> clickTimes = new ArrayList<>();
    private static final ArrayList<Long> pressDurations = new ArrayList<>();
    private static final int MIN_CLICKS_FOR_DETECTION = 3;
    private static final int WINDOW_SIZE = 50;

    public AutoClickerDetectorService() {
        RenderService.buttonTop.addMouseListener(new MouseListener() {
            private long pressStartTime;

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    pressStartTime = System.currentTimeMillis();

                    RenderService.buttonTop.setLocation(30, 35);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    long pressDuration = System.currentTimeMillis() - pressStartTime;
                    pressDurations.add(pressDuration);
                    long currentTime = System.currentTimeMillis();
                    clickTimes.add(currentTime);

                    if (clickTimes.size() > MIN_CLICKS_FOR_DETECTION) {
                        DetectionResult result = detectAutoClicker();
                        System.out.println(result.message);
                    }

                    RenderService.buttonTop.setLocation(30, 30);

                    if(Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")){
                        WriteUtility.clicks1 += WriteUtility.clickPower1;
                        RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks1);
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")){
                        WriteUtility.clicks2 += WriteUtility.clickPower2;
                        RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks2);
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")){
                        WriteUtility.clicks3 += WriteUtility.clickPower3;
                        RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks3);
                    }

                    AudioService.SFX.playSFX("click");
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
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

        double cps = windowClickTimes.size() / (getTotalTime(windowClickTimes) / 1000.0);

        double intervalStdDev = calculateStandardDeviation(intervals);
        double intervalMedian = calculateMedian(intervals);
        double intervalIQR = calculateIQR(intervals);
        double intervalEntropy = calculateEntropy(intervals);
        double durationStdDev = calculateStandardDeviation(windowPressDurations);

        double cpsThreshold = 50;
        double intervalStdDevThreshold = 5;
        double entropyThreshold = 0.5;
        double durationStdDevThreshold = 5;

        boolean unrealisticSpeed = cps > cpsThreshold;
        boolean highlyRegularIntervals = intervalStdDev < intervalStdDevThreshold && intervalEntropy < entropyThreshold;
        boolean regularDurations = durationStdDev < durationStdDevThreshold;

        String message = "No auto clicker detected.";
        if (unrealisticSpeed) {
            message = "Auto clicker detected: High CPS.";
        } else if (highlyRegularIntervals) {
            message = "Auto clicker detected: Highly regular click intervals.";
        } else if (regularDurations) {
            message = "Auto clicker detected: Regular press durations.";
        }

        message += String.format(" CPS: %.2f, Interval Std Dev: %.2f, Interval Median: %.2f, IQR: %.2f, Duration Std Dev: %.2f",
                cps, intervalStdDev, intervalMedian, intervalIQR, durationStdDev);

        return new DetectionResult(unrealisticSpeed || highlyRegularIntervals || regularDurations, message);
    }

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
        double mean = calculateMean(values);
        double sumSquaredDifferences = 0.0;
        for (long value : values) {
            sumSquaredDifferences += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sumSquaredDifferences / values.size());
    }

    private static double calculateMean(List<Long> values) {
        double sum = 0.0;
        for (long value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    private static double calculateMedian(List<Long> values) {
        Collections.sort(values);
        int size = values.size();
        if (size % 2 == 0) {
            return (values.get(size / 2 - 1) + values.get(size / 2)) / 2.0;
        } else {
            return values.get(size / 2);
        }
    }

    private static double calculateIQR(List<Long> values) {
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

    private static class DetectionResult {
        boolean detected;
        String message;

        DetectionResult(boolean detected, String message) {
            this.detected = detected;
            this.message = message;
        }
    }
}