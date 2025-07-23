package net.alek.buttonclicker.audio;

import org.lwjgl.openal.AL11;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SoundClip {
    private final int bufferId;
    private final int sourceId;
    private final int sampleRate;
    private final float durationSeconds;

    private volatile boolean manuallyStopped = false;
    private volatile boolean ended = false;
    private Runnable onEnd;

    private static final int AL_SAMPLE_OFFSET = 0x1024;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.setName("SoundClip-PlaybackChecker");
        return t;
    });

    public SoundClip(int bufferId, int sourceId) {
        this.bufferId = bufferId;
        this.sourceId = sourceId;

        int bufferSize = AL11.alGetBufferi(bufferId, AL11.AL_SIZE);
        this.sampleRate = AL11.alGetBufferi(bufferId, AL11.AL_FREQUENCY);
        int channels = AL11.alGetBufferi(bufferId, AL11.AL_CHANNELS);
        int bitsPerSample = AL11.alGetBufferi(bufferId, AL11.AL_BITS);

        this.durationSeconds = (float) bufferSize / (channels * (bitsPerSample / 8f) * sampleRate);

        scheduler.scheduleAtFixedRate(this::checkPlayback, 1, 1, TimeUnit.SECONDS);
    }

    public int getBufferId() {
        return bufferId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public float getDuration() {
        return durationSeconds;
    }

    public float getPlaybackPosition() {
        int sampleOffset = AL11.alGetSourcei(sourceId, AL_SAMPLE_OFFSET);
        return (float) sampleOffset / sampleRate;
    }

    public void setOnEnd(Runnable onEnd) {
        this.onEnd = onEnd;
    }

    public void play(boolean loop) {
        manuallyStopped = false;
        ended = false;
        AL11.alSourcei(sourceId, AL11.AL_LOOPING, loop ? 1 : 0);
        AL11.alSourcePlay(sourceId);
    }

    public void stop() {
        manuallyStopped = true;
        ended = false;
        AL11.alSourceStop(sourceId);
    }

    public void pause() {
        AL11.alSourcePause(sourceId);
    }

    public void resume() {
        AL11.alSourcePlay(sourceId);
    }

    public void setVolume(float volume) {
        AL11.alSourcef(sourceId, AL11.AL_GAIN, volume);
    }

    public void setPitch(float pitch) {
        AL11.alSourcef(sourceId, AL11.AL_PITCH, pitch);
    }

    private void checkPlayback() {
        if (ended || manuallyStopped) return;

        int state = AL11.alGetSourcei(sourceId, AL11.AL_SOURCE_STATE);
        if (state == AL11.AL_STOPPED) {
            if (getPlaybackPosition() >= durationSeconds) {
                ended = true;
                if (onEnd != null) {
                    onEnd.run();
                }
            }
        }
    }

    public void cleanup() {
        scheduler.shutdownNow();
        AL11.alDeleteSources(sourceId);
        AL11.alDeleteBuffers(bufferId);
    }
}