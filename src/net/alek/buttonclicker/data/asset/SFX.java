package net.alek.buttonclicker.data.asset;

import net.alek.buttonclicker.audio.SoundClip;

public record SFX(
        SoundClip select,
        SoundClip click,
        SoundClip purchase,
        SoundClip declined
) {}