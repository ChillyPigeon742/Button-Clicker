package net.alek.buttonclicker.data;

import javax.swing.*;
import java.awt.image.BufferedImage;

public record Images(
        ImageIcon missingIcon,
        ImageIcon bcIcon,
        ImageIcon bcIcon2,
        ImageIcon bcIcon3,
        ImageIcon lightSideIcon,
        ImageIcon darkSideIcon,
        ImageIcon magicSideIcon,
        ImageIcon neutralSideIcon,
        ImageIcon errorIcon,
        ImageIcon warningIcon,
        ImageIcon infoIcon,
        ImageIcon questionIcon,
        ImageIcon backwardArrowIcon,
        ImageIcon forwardArrowIcon,
        ImageIcon buttonBaseIcon,
        ImageIcon buttonTopIcon,
        ImageIcon clickPowerIcon,
        ImageIcon cogIcon,
        BufferedImage missingImage,
        BufferedImage portalWhitewall
) {}