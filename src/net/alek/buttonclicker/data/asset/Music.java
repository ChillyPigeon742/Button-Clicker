package net.alek.buttonclicker.data.asset;

import net.alek.buttonclicker.audio.SoundClip;

public record Music(
        SoundClip menu1,
        SoundClip menu2,
        SoundClip loading,
        SoundClip game1,
        SoundClip game2,
        SoundClip pause,
        SoundClip shop
) {}