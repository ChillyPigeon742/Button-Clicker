package net.alek.buttonclicker.read.assets;

import net.alek.buttonclicker.audio.SoundClip;
import net.alek.buttonclicker.core.log.LogType;
import net.alek.buttonclicker.data.asset.Audio;
import net.alek.buttonclicker.data.asset.Music;
import net.alek.buttonclicker.data.asset.SFX;
import net.alek.buttonclicker.transfer.event.payload.LogPayload;
import net.alek.buttonclicker.transfer.event.type.SubscribeMethod;
import net.alek.buttonclicker.transfer.event.type.Event;
import net.alek.buttonclicker.transfer.request.Request;
import org.lwjgl.openal.AL10;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class AudioLoader {
    private static Audio audio;

    static {
        Request.GET_AUDIO.handle(AudioLoader::getAudio);
        Event.LOAD_GAME.subscribe(SubscribeMethod.SYNC, ignored -> loadAudioData());
        Event.UNLOAD_GAME.subscribe(SubscribeMethod.SYNC, ignored -> unloadAudioData());
    }

    public static Audio getAudio() {
        return audio;
    }

    private static void loadAudioData() {
        audio = new Audio(loadSFXAudioData(), loadMusicAudioData());
    }

    private static void unloadAudioData() {
        audio = null;
    }

    private static SoundClip loadSound(String path, SoundClip fallback) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            InputStream input = AudioLoader.class.getResourceAsStream(path);
            if (input == null) {
                Event.LOG.publish(new LogPayload(LogType.ERROR, "Audio not found: " + path));
                return fallback;
            }

            byte[] bytes = input.readAllBytes();
            ByteBuffer bufferData = MemoryUtil.memAlloc(bytes.length);
            bufferData.put(bytes).flip();

            IntBuffer error = stack.mallocInt(1);
            long decoder = STBVorbis.stb_vorbis_open_memory(bufferData, error, null);
            if (decoder == MemoryUtil.NULL) {
                Event.LOG.publish(new LogPayload(LogType.ERROR, "Failed to decode OGG: " + path));
                MemoryUtil.memFree(bufferData);
                return fallback;
            }

            STBVorbisInfo info = STBVorbisInfo.malloc(stack);
            STBVorbis.stb_vorbis_get_info(decoder, info);

            int channels = info.channels();
            int sampleRate = info.sample_rate();
            int samples = STBVorbis.stb_vorbis_stream_length_in_samples(decoder);

            ShortBuffer pcm = MemoryUtil.memAllocShort(samples * channels);
            STBVorbis.stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm);

            STBVorbis.stb_vorbis_close(decoder);
            MemoryUtil.memFree(bufferData);

            int format = channels == 1 ? AL10.AL_FORMAT_MONO16 : AL10.AL_FORMAT_STEREO16;

            int bufferId = AL10.alGenBuffers();
            AL10.alBufferData(bufferId, format, pcm, sampleRate);
            MemoryUtil.memFree(pcm);

            int sourceId = AL10.alGenSources();
            AL10.alSourcei(sourceId, AL10.AL_BUFFER, bufferId);

            return new SoundClip(bufferId, sourceId, (float) samples / sampleRate, sampleRate);
        } catch (Exception e) {
            Event.LOG.publish(new LogPayload(LogType.ERROR, "Exception loading audio: " + path));
            e.printStackTrace();
            return fallback;
        }
    }

    private static SFX loadSFXAudioData() {
        SoundClip fallback = null;

        return new SFX(
                loadSound("/assets/buttonclicker/audio/sfx/select.ogg", fallback),
                loadSound("/assets/buttonclicker/audio/sfx/click.ogg", fallback),
                loadSound("/assets/buttonclicker/audio/sfx/purchase.ogg", fallback),
                loadSound("/assets/buttonclicker/audio/sfx/declined.ogg", fallback)
        );
    }

    private static Music loadMusicAudioData() {
        SoundClip fallback = null;

        return new Music(
                loadSound("/assets/buttonclicker/audio/music/menu/menu1.ogg", fallback),
                loadSound("/assets/buttonclicker/audio/music/menu/menu2.ogg", fallback),
                loadSound("/assets/buttonclicker/audio/music/menu/loading.ogg", fallback),
                loadSound("/assets/buttonclicker/audio/music/game/game1.ogg", fallback),
                loadSound("/assets/buttonclicker/audio/music/game/game2.ogg", fallback),
                loadSound("/assets/buttonclicker/audio/music/game/pause.ogg", fallback),
                loadSound("/assets/buttonclicker/audio/music/game/shop.ogg", fallback)
        );
    }
}