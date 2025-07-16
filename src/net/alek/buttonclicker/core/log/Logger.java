package net.alek.buttonclicker.core.log;

import net.alek.buttonclicker.transfer.event.payload.LogPayload;
import net.alek.buttonclicker.transfer.event.type.SubscribeMethod;
import net.alek.buttonclicker.transfer.event.type.Event;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static File log = new File("Data/BC-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".log");

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";

    private static PrintStream terminalStream;
    private static PrintStream fileStream;
    private static final Object lock = new Object();

    static {
        Event.LOG.subscribe(SubscribeMethod.ASYNC, (LogPayload p) -> logWriter(p.message(), p.logType()));
        setupLogger();
    }

    private static void setupLogger() {
        try {
            fileStream = new PrintStream(new FileOutputStream(log, true), true);
            terminalStream = System.out;
            PrintStream originalOut = System.out;
            PrintStream originalErr = System.err;

            System.setOut(new PrintStream(new TeeOutputStream(originalOut, fileStream), true));
            System.setErr(new PrintStream(new TeeOutputStream(originalErr, fileStream), true));
        } catch (FileNotFoundException e) {
            System.err.println("Failed to setup logger: " + e.getMessage());
            System.exit(-1);
        }
    }

    private static String getCallerInfo() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            String className = element.getClassName();
            if (!className.equals(Logger.class.getName())) {
                String simpleClassName = className.substring(className.lastIndexOf('.') + 1);
                return simpleClassName + ":" + element.getLineNumber();
            }
        }
        return "UnknownCaller";
    }

    private static void logWriter(String toWrite, LogType type) {
        synchronized (lock) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String caller = getCallerInfo();
            String logMessage = timestamp + " " + type + "  " + caller + " - " + toWrite;

            fileStream.println(logMessage);
            fileStream.flush();

            String coloredMessage;
            switch (type) {
                case ERROR -> coloredMessage = RED + logMessage + RESET;
                case WARN -> coloredMessage = YELLOW + logMessage + RESET;
                case DEBUG -> coloredMessage = GREEN + logMessage + RESET;
                default -> coloredMessage = logMessage;
            }

            terminalStream.println(coloredMessage);
            terminalStream.flush();
        }
    }

    private static class TeeOutputStream extends OutputStream {
        private final OutputStream stream1;
        private final OutputStream stream2;

        public TeeOutputStream(OutputStream s1, OutputStream s2) {
            this.stream1 = s1;
            this.stream2 = s2;
        }

        @Override
        public void write(int b) throws IOException {
            stream1.write(b);
            stream2.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            stream1.write(b);
            stream2.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            stream1.write(b, off, len);
            stream2.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            stream1.flush();
            stream2.flush();
        }

        @Override
        public void close() throws IOException {
            stream1.close();
            stream2.close();
        }
    }
}