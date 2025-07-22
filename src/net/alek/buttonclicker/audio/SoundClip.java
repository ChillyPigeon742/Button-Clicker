package net.alek.buttonclicker.audio;

import org.lwjgl.openal.AL10;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SoundClip {
    private final int bufferId;
    private final int sourceId;

    private final float durationSeconds;
    private final int sampleRate;

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

    public SoundClip(int bufferId, int sourceId, float durationSeconds, int sampleRate) {
        this.bufferId = bufferId;
        this.sourceId = sourceId;
        this.durationSeconds = durationSeconds;
        this.sampleRate = sampleRate;

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
        int sampleOffset = AL10.alGetSourcei(sourceId, AL_SAMPLE_OFFSET);
        return (float) sampleOffset / sampleRate;
    }

    public void setOnEnd(Runnable onEnd) {
        this.onEnd = onEnd;
    }

    public void play(boolean loop) {
        manuallyStopped = false;
        ended = false;
        AL10.alSourcei(sourceId, AL10.AL_LOOPING, loop ? 1 : 0);
        AL10.alSourcePlay(sourceId);
    }

    public void stop() {
        manuallyStopped = true;
        ended = false;
        AL10.alSourceStop(sourceId);
    }

    public void pause() {
        AL10.alSourcePause(sourceId);
    }

    public void resume() {
        AL10.alSourcePlay(sourceId);
    }

    public void setVolume(float volume) {
        AL10.alSourcef(sourceId, AL10.AL_GAIN, volume);
    }

    public void setPitch(float pitch) {
        AL10.alSourcef(sourceId, AL10.AL_PITCH, pitch);
    }

    private void checkPlayback() {
        if (ended || manuallyStopped) return;

        int state = AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE);

        if (state == AL10.AL_STOPPED) {
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
        AL10.alDeleteSources(sourceId);
        AL10.alDeleteBuffers(bufferId);
    }
}