module ButtonClicker {
    requires com.formdev.flatlaf;
    requires java.datatransfer;
    requires java.desktop;
    requires javafx.media;
    requires javafx.swing;

    exports net.alek.buttonclicker.assets;
    exports net.alek.buttonclicker.command;
    exports net.alek.buttonclicker.core;
    exports net.alek.buttonclicker.core.log;
    exports net.alek.buttonclicker.data.asset;
    exports net.alek.buttonclicker.data.model;
    exports net.alek.buttonclicker.data.save;
    exports net.alek.buttonclicker.data.settings;
    exports net.alek.buttonclicker.event;
    exports net.alek.buttonclicker.event.payload;
    exports net.alek.buttonclicker.event.type;
    exports net.alek.buttonclicker.read.json;
    exports net.alek.buttonclicker.read.saves;
    exports net.alek.buttonclicker.read.settings;
    exports net.alek.buttonclicker.services;
    exports net.alek.buttonclicker.ui;
    exports net.alek.buttonclicker.ui.components;
    exports net.alek.buttonclicker.util;
    exports net.alek.buttonclicker.write.json;
    exports net.alek.buttonclicker.write.saves;
    exports net.alek.buttonclicker.write.settings;
}