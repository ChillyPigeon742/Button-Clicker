module net.alek.buttonclicker {
    requires java.desktop;
    requires com.formdev.flatlaf;
    requires javafx.media;
    requires javafx.swing;
    requires java.smartcardio;

    exports net.alek.buttonclicker.audio;
    exports net.alek.buttonclicker.command;
    exports net.alek.buttonclicker.core;
    exports net.alek.buttonclicker.core.log;
    exports net.alek.buttonclicker.data.asset;
    exports net.alek.buttonclicker.data.model;
    exports net.alek.buttonclicker.data.save;
    exports net.alek.buttonclicker.data.settings;
    exports net.alek.buttonclicker.read.assets;
    exports net.alek.buttonclicker.read.json;
    exports net.alek.buttonclicker.read.saves;
    exports net.alek.buttonclicker.read.settings;
    exports net.alek.buttonclicker.services;
    exports net.alek.buttonclicker.singletons;
    exports net.alek.buttonclicker.transfer.event;
    exports net.alek.buttonclicker.transfer.event.payload;
    exports net.alek.buttonclicker.transfer.event.type;
    exports net.alek.buttonclicker.transfer.request;
    exports net.alek.buttonclicker.transfer.request.payload;
    exports net.alek.buttonclicker.ui;
    exports net.alek.buttonclicker.ui.components;
    exports net.alek.buttonclicker.util;
    exports net.alek.buttonclicker.util.arithmetic;
    exports net.alek.buttonclicker.write.json;
    exports net.alek.buttonclicker.write.saves;
    exports net.alek.buttonclicker.write.settings;
}