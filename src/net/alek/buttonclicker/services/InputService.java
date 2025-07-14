package net.alek.buttonclicker.services;

import javafx.scene.media.MediaPlayer;

import net.alek.buttonclicker.core.*;

import net.alek.buttonclicker.ui.components.ATimer;

import net.alek.buttonclicker.command.CommandDefinitions;
import net.alek.buttonclicker.ui.MenuManager;
import net.alek.buttonclicker.ui.RenderService;
import net.alek.buttonclicker.ui.MessageUtility;
import net.alek.buttonclicker.read.ReadUtility;
import net.alek.buttonclicker.write.WriteUtility;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class InputService{
    public static ActionListener actionListener = e -> {
        Object source = e.getSource();

        if(source== RenderService.startGameButton){
            AudioService.SFX.playSFX("select");

            if(Objects.equals(RenderService.startGameButton.getText(), "Select A Save To Continue")){
                RenderService.startGameButton.setText("I said what I said.");

                ATimer timer = new ATimer();
                timer.setDelay(2);
                timer.setTask(() -> {
                    RenderService.startGameButton.setText("Select A Save To Continue");
                });
                timer.start();
            }else if(Objects.equals(RenderService.startGameButton.getText(), "Start Game")){
                LoggingService.Logger.info("Loading Game...");

                if(Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")){
                    ReadUtility.loadSave1();
                    WriteUtility.playedBefore1 = true;
                    WriteUtility.save1();
                }else if(Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")){
                    ReadUtility.loadSave2();
                    WriteUtility.playedBefore2 = true;
                    WriteUtility.save2();
                }else if(Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")){
                    ReadUtility.loadSave3();
                    WriteUtility.playedBefore3 = true;
                    WriteUtility.save3();
                }

                MenuManager.closeMenu("Spark");
                MenuManager.openMenu("Loading");

                RenderService.loadingBar.setValue(0);
                RenderService.progressText.setText("[1/3] Loading Resources");

                RenderService.startProgress(RenderService.loadingBar, 340);

                RenderService.nextTipButton.setEnabled(true);

                Random random = new Random();
                int number = random.nextInt(1,4);

                if(number==1){
                    RenderService.tipsText.setText("Did you know you can click to\nclick?");
                }else if(number==2){
                    RenderService.tipsText.setText("Obtaining extra click power\ngains you more clicks");
                }else if(number==3){
                    RenderService.tipsText.setText("Button Clicker was made\navailable on March 20, 2024");
                }

                RenderService.titleText.setBounds(46, 40, 570, 80);
                RenderService.titleImage.setBounds(280,150,110,110);

                RenderService.titleImage.startSpinning();

                AudioService.Music.stopMusic();

                ATimer timer = new ATimer();
                timer.setDelay(17);
                timer.setTask(() -> {
                    if(RenderService.nextTipButton.isEnabled()){
                        Random random1 = new Random();
                        int number1 = random1.nextInt(1,4);

                        if(number1==1){
                            RenderService.tipsText.setText("Button Clicker was originally a lot more simplistic!");
                        }else if(number1==2){
                            RenderService.tipsText.setText("Button Clicker is out now for early access on itch.io!");
                        }else if(number1==3){
                            RenderService.tipsText.setText("Rebirthing grants you easier\nprogression next time round!");
                        }

                        RenderService.nextTipButton.setEnabled(false);
                    }
                });
                timer.start();

                ATimer timer1 = new ATimer();
                timer1.setDelay(3);
                timer1.setTask(() -> {
                    RenderService.progressText.setText("[1/3] Loading Resources.");

                    ATimer timer2 = new ATimer();
                    timer2.setDelay(3);
                    timer2.setTask(() -> {
                        RenderService.progressText.setText("[1/3] Loading Resources..");

                        ATimer timer3 = new ATimer();
                        timer3.setDelay(3);
                        timer3.setTask(() -> {
                            RenderService.progressText.setText("[1/3] Loading Resources...");

                            ATimer timer4 = new ATimer();
                            timer4.setDelay(3);
                            timer4.setTask(() -> {
                                RenderService.progressText.setText("[2/3] Loading Save");

                                ATimer timer5 = new ATimer();
                                timer5.setDelay(3);
                                timer5.setTask(() -> {
                                    RenderService.progressText.setText("[2/3] Loading Save.");

                                    ATimer timer6 = new ATimer();
                                    timer6.setDelay(3);
                                    timer6.setTask(() -> {
                                        RenderService.progressText.setText("[2/3] Loading Save..");

                                        ATimer timer7 = new ATimer();
                                        timer7.setDelay(3);
                                        timer7.setTask(() -> {
                                            RenderService.progressText.setText("[2/3] Loading Save...");

                                            ATimer timer8 = new ATimer();
                                            timer8.setDelay(3);
                                            timer8.setTask(() -> {
                                                RenderService.progressText.setText("[3/3] Channeling Dark Forces");

                                                ATimer timer9= new ATimer();
                                                timer9.setDelay(3);
                                                timer9.setTask(() -> {
                                                    RenderService.progressText.setText("[3/3] Channeling Dark Forces.");

                                                    ATimer timer10 = new ATimer();
                                                    timer10.setDelay(3);
                                                    timer10.setTask(() -> {
                                                        RenderService.progressText.setText("[3/3] Channeling Dark Forces..");

                                                        ATimer timer11 = new ATimer();
                                                        timer11.setDelay(3);
                                                        timer11.setTask(() -> {
                                                            RenderService.progressText.setText("[3/3] Channeling Dark Forces...");
                                                        });
                                                        timer11.start();
                                                    });
                                                    timer10.start();
                                                });
                                                timer9.start();
                                            });
                                            timer8.start();
                                        });
                                        timer7.start();
                                    });
                                    timer6.start();
                                });
                                timer5.start();
                            });
                            timer4.start();
                        });
                        timer3.start();
                    });
                    timer2.start();
                });
                timer1.start();

                AudioService.Music.playMusic("loading");

                LoggingService.Logger.info("Done!");
            }else if(Objects.equals(RenderService.startGameButton.getText(), "Resume Game")){
                if(MenuManager.isMenuOpen("Spark")){
                    LoggingService.Logger.info("Loading Game...");

                    MenuManager.closeMenu("Spark");
                    MenuManager.openMenu("Loading");

                    RenderService.loadingBar.setValue(0);
                    RenderService.progressText.setText("[1/3] Loading Resources");

                    RenderService.startProgress(RenderService.loadingBar, 0);

                    RenderService.nextTipButton.setEnabled(true);

                    Random random = new Random();
                    int number = random.nextInt(1,4);

                    if(number==1){
                        RenderService.tipsText.setText("Did you know you can click to\nclick?");
                    }else if(number==2){
                        RenderService.tipsText.setText("Obtaining extra click power\ngains you more clicks");
                    }else if(number==3){
                        RenderService.tipsText.setText("Button Clicker was made\navailable on March 20, 2024");
                    }

                    RenderService.titleText.setBounds(46, 40, 570, 80);
                    RenderService.titleImage.setBounds(280,150,110,110);

                    RenderService.titleImage.startSpinning();

                    AudioService.Music.stopMusic();

                    ATimer timer = new ATimer();
                    timer.setDelay(17);
                    timer.setTask(() -> {
                        if(RenderService.nextTipButton.isEnabled()){
                            Random random1 = new Random();
                            int number1 = random1.nextInt(1,4);

                            if(number1==1){
                                RenderService.tipsText.setText("Button Clicker was originally a lot more simplistic!");
                            }else if(number1==2){
                                RenderService.tipsText.setText("Button Clicker is out now for early access on itch.io!");
                            }else if(number1==3){
                                RenderService.tipsText.setText("Rebirthing grants you easier\nprogression next time round!");
                            }

                            RenderService.nextTipButton.setEnabled(false);
                        }
                    });
                    timer.start();

                    ATimer timer1 = new ATimer();
                    timer1.setDelay(3);
                    timer1.setTask(() -> {
                        RenderService.progressText.setText("[1/3] Loading Resources.");

                        ATimer timer2 = new ATimer();
                        timer2.setDelay(3);
                        timer2.setTask(() -> {
                            RenderService.progressText.setText("[1/3] Loading Resources..");

                            ATimer timer3 = new ATimer();
                            timer3.setDelay(3);
                            timer3.setTask(() -> {
                                RenderService.progressText.setText("[1/3] Loading Resources...");

                                ATimer timer4 = new ATimer();
                                timer4.setDelay(3);
                                timer4.setTask(() -> {
                                    RenderService.progressText.setText("[2/3] Loading Save");

                                    ATimer timer5 = new ATimer();
                                    timer5.setDelay(3);
                                    timer5.setTask(() -> {
                                        RenderService.progressText.setText("[2/3] Loading Save.");

                                        ATimer timer6 = new ATimer();
                                        timer6.setDelay(3);
                                        timer6.setTask(() -> {
                                            RenderService.progressText.setText("[2/3] Loading Save..");

                                            ATimer timer7 = new ATimer();
                                            timer7.setDelay(3);
                                            timer7.setTask(() -> {
                                                RenderService.progressText.setText("[2/3] Loading Save...");

                                                ATimer timer8 = new ATimer();
                                                timer8.setDelay(3);
                                                timer8.setTask(() -> {
                                                    RenderService.progressText.setText("[3/3] Channeling Dark Forces");

                                                    ATimer timer9= new ATimer();
                                                    timer9.setDelay(3);
                                                    timer9.setTask(() -> {
                                                        RenderService.progressText.setText("[3/3] Channeling Dark Forces.");

                                                        ATimer timer10 = new ATimer();
                                                        timer10.setDelay(3);
                                                        timer10.setTask(() -> {
                                                            RenderService.progressText.setText("[3/3] Channeling Dark Forces..");

                                                            ATimer timer11 = new ATimer();
                                                            timer11.setDelay(3);
                                                            timer11.setTask(() -> {
                                                                RenderService.progressText.setText("[3/3] Channeling Dark Forces...");
                                                            });
                                                            timer11.start();
                                                        });
                                                        timer10.start();
                                                    });
                                                    timer9.start();
                                                });
                                                timer8.start();
                                            });
                                            timer7.start();
                                        });
                                        timer6.start();
                                    });
                                    timer5.start();
                                });
                                timer4.start();
                            });
                            timer3.start();
                        });
                        timer2.start();
                    });
                    timer1.start();

                    AudioService.Music.playMusic("loading");

                    LoggingService.Logger.info("Done!");
                }else if(MenuManager.isMenuOpen("Pause")){
                    LoggingService.Logger.info("Resuming...");

                    MenuManager.closeMenu("Pause");

                    if(Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")){
                        RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks1);
                        RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower1);
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")){
                        RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks2);
                        RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower2);
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")){
                        RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks3);
                        RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower3);
                    }

                    RenderService.titleText.setVisible(false);
                    RenderService.titleImage.setVisible(false);

                    AudioService.SoundManager.getSMusicAudioPlayer().stop();

                    AudioService.Music.resumeMusic();

                    MenuManager.openMenu("Game");

                    LoggingService.Logger.info("Done!");
                }
            }
        }

        if(source== RenderService.saveManagerButton){
            AudioService.SFX.playSFX("select");

            MenuManager.closeMenu("Spark");
            MenuManager.openMenu("Save Manager");

            RenderService.backToButton.setVisible(true);
            RenderService.backToButton.setText("Back To Spark Menu");
            RenderService.titleText.setText("Save Manager");
            
            ReadUtility.loadSave1();
            ReadUtility.loadSave2();
            ReadUtility.loadSave3();
        }

        if(source== RenderService.wikiButton){
            AudioService.SFX.playSFX("select");

            MenuManager.closeMenu("Settings");
            MenuManager.openMenu("Wiki");

            RenderService.titleText.setVisible(false);
            RenderService.titleImage.setVisible(false);

            RenderService.backToButton.setBounds(-10, 593, 1036, 38);
            RenderService.backToButton.setText("Back To Settings");

            try {
                RenderService.wikiEditorPane.setPage(ResourceManager.class.getResource("/assets/buttonclicker/html/wiki.html"));
            } catch (IOException ex) {
                ErrorHandler.IOException();
            }
            RenderService.wikiEditorPane.setBackground(new Color(50, 50, 50));
            RenderService.wikiEditorPane.setForeground(Color.WHITE);
            RenderService.wikiEditorPane.setFont(new Font("Nunito",Font.BOLD,20));
            RenderService.wikiEditorPane.setMargin(new Insets(0,5,0,0));
            RenderService.wikiEditorPane.setBackgroundImageEnabled(false);

            RenderService.frame.setSize(1031, 668);
            RenderService.frame.setLocationRelativeTo(null);

            if(MenuManager.StupidBooleanIHaveToMakeForTheBackToButtonTextToSetProperlyWhenYouOpenAnyOfTheMenusInTheSettingsMenuSinceIDontHaveAnythingToHookOntoToMakeItSetToBackToPauseMenu){
                RenderService.wikiHelpButton.setVisible(false);
            }

            RenderService.setResetWindowTrigger();
        }

        if(source== RenderService.creditsButton){
            AudioService.SFX.playSFX("select");

            MenuManager.closeMenu("Settings");
            MenuManager.openMenu("Credits");

            RenderService.backToButton.setText("Back To Settings");
            RenderService.backToButton.setVisible(true);

            RenderService.titleText.setText("Credits");
            RenderService.titleText.setFont(new Font("Nunito",Font.BOLD,70));

        }

        if(source== RenderService.quitButton){
            AudioService.SFX.playSFX("select");
            if(Objects.equals(RenderService.quitButton.getText(), "Save & Quit")){
                RenderService.quitButton.setText("Saving...");
                LoggingService.Logger.info("Saving...");

                if(Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")){
                    WriteUtility.save1();
                }else if(Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")){
                    WriteUtility.save2();
                }else if(Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")){
                    WriteUtility.save3();
                }

                ATimer timer = new ATimer();
                timer.setDelay(2);
                timer.setTask(() -> {
                    System.exit(0);
                });
                timer.start();
            }else if(Objects.equals(RenderService.quitButton.getText(), "Quit")){
                RenderService.quitButton.setText("Quitting...");
                LoggingService.Logger.info("Quitting...");

                ATimer timer = new ATimer();
                timer.setDelay(2);
                timer.setTask(() -> {
                    System.exit(0);
                });
                timer.start();
            }else if(Objects.equals(RenderService.quitButton.getText(), "Return To Spark Menu")){
                RenderService.quitButton.setText("Saving...");
                LoggingService.Logger.info("Saving...");

                ATimer timer = new ATimer();
                timer.setDelay(2);
                timer.setTask(() -> {
                    if(Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")){
                        WriteUtility.save1();
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")){
                        WriteUtility.save2();
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")){
                        WriteUtility.save3();
                    }

                    MenuManager.closeMenu("Pause");
                    MenuManager.openMenu("Spark");

                    RenderService.startGameButton.setBounds(177,150,305,102);
                    RenderService.settingsButton.setBounds(177,348,305,102);

                    RenderService.quitButton.setBounds(177,447,305,102);
                    RenderService.quitButton.setFont(new Font("Nunito", Font.BOLD, 30));
                    RenderService.quitButton.setText("Save & Quit");

                    AudioService.SoundManager.getSMusicAudioPlayer().stop();

                    Random random = new Random();
                    int number = random.nextInt(1,3);

                    if(number==1){
                        AudioService.Music.playMusic("menu1");
                    }else if(number==2){
                        AudioService.Music.playMusic("menu2");
                    }

                    LoggingService.Logger.info("Done!");
                });
                timer.start();
            }
        }

        if(source== RenderService.backToButton) {
            if (Objects.equals(RenderService.backToButton.getText(), "Back To Spark Menu")) {
                MenuManager.closeMenu("Save Manager");
                MenuManager.closeMenu("Settings");
                MenuManager.closeMenu("Debug");
                MenuManager.openMenu("Spark");

                RenderService.titleText.setFont(new Font("Nunito", Font.BOLD, 70));
                RenderService.titleText.setText("Button Clicker");
                RenderService.backToButton.setVisible(false);
                RenderService.forwardButton.setVisible(false);
                RenderService.backwardButton.setVisible(false);

                if(!Objects.equals(WriteUtility.currentSave, "null")){
                    RenderService.startGameButton.setBackground(Color.GREEN);
                    RenderService.startGameButton.setFont(new Font("Nunito", Font.BOLD, 27));
                    RenderService.quitButton.setText("Save & Quit");

                    if(Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")){
                        if(WriteUtility.playedBefore1){
                            RenderService.startGameButton.setText("Resume Game");
                        }else if(!WriteUtility.playedBefore1){
                            RenderService.startGameButton.setText("Start Game");
                        }
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")){
                        if(WriteUtility.playedBefore2){
                            RenderService.startGameButton.setText("Resume Game");
                        }else if(!WriteUtility.playedBefore2){
                            RenderService.startGameButton.setText("Start Game");
                        }
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")){
                        if(WriteUtility.playedBefore3){
                            RenderService.startGameButton.setText("Resume Game");
                        }else if(!WriteUtility.playedBefore3){
                            RenderService.startGameButton.setText("Start Game");
                        }
                    }
                }else if(WriteUtility.currentSave.equals("null")){
                    RenderService.startGameButton.setText("Select A Save To Continue");
                    RenderService.startGameButton.setBackground(Color.GRAY);
                    RenderService.startGameButton.setFont(new Font("Nunito", Font.BOLD, 21));
                    RenderService.quitButton.setText("Quit");
                }

                if(Objects.equals(WriteUtility.currentSave, "null")){
                    RenderService.currentSaveText.setText("Current Save: none");
                }else if(Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")){
                    RenderService.currentSaveText.setText("Current Save: "+ WriteUtility.save1Name);
                }else if(Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")){
                    RenderService.currentSaveText.setText("Current Save: "+ WriteUtility.save2Name);
                }else if(Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")){
                    RenderService.currentSaveText.setText("Current Save: "+ WriteUtility.save3Name);
                }
            } else if (Objects.equals(RenderService.backToButton.getText(), "Back To Save Manager")) {
                MenuManager.closeMenu("Save Info");
                MenuManager.closeMenu("Choose A Side");
                MenuManager.openMenu("Save Manager");

                RenderService.backToButton.setText("Back To Spark Menu");
                RenderService.titleText.setText("Save Manager");

                RenderService.forwardButton.setVisible(false);
                RenderService.backwardButton.setVisible(false);

                WriteUtility.saveBeingCreated = 0;

                ReadUtility.loadSave1();
                ReadUtility.loadSave2();
                ReadUtility.loadSave3();
            } else if (Objects.equals(RenderService.backToButton.getText(), "Create Save")) {
                RenderService.titleText.setFont(new Font("Nunito", Font.BOLD, 70));

                if (RenderService.isTextFieldEmpty(RenderService.saveNameField)) {
                    Thread thread = ThreadManager.notAllowedSaveNames();
                    thread.start();

                    if(WriteUtility.saveBeingCreated==1){
                        WriteUtility.Wipe(1);
                    }else if(WriteUtility.saveBeingCreated==2){
                        WriteUtility.Wipe(2);
                    }else if(WriteUtility.saveBeingCreated==3){
                        WriteUtility.Wipe(3);
                    }

                    WriteUtility.saveBeingCreated = 0;
                }else if(Objects.equals(RenderService.saveNameField.getText(), "null")){
                    Thread thread = ThreadManager.notAllowedSaveNames();
                    thread.start();

                    if(WriteUtility.saveBeingCreated==1){
                        WriteUtility.Wipe(1);
                    }else if(WriteUtility.saveBeingCreated==2){
                        WriteUtility.Wipe(2);
                    }else if(WriteUtility.saveBeingCreated==3){
                        WriteUtility.Wipe(3);
                    }

                    WriteUtility.saveBeingCreated = 0;
                } else {
                    LoggingService.Logger.info("Creating Save...");

                    if (WriteUtility.saveBeingCreated == 1) {
                        WriteUtility.createSave("Saves/save1.bcs");

                        WriteUtility.clicks1 = 0;
                        WriteUtility.clickPower1 = 1;
                        WriteUtility.save1Name = RenderService.saveNameField.getText();
                        WriteUtility.playedBefore1 = false;

                        WriteUtility.save1();

                        WriteUtility.saveBeingCreated = 0;
                    }

                    if (WriteUtility.saveBeingCreated == 2) {
                        WriteUtility.createSave("Saves/save2.bcs");

                        WriteUtility.clicks2 = 0;
                        WriteUtility.clickPower2 = 1;
                        WriteUtility.save2Name = RenderService.saveNameField.getText();
                        WriteUtility.playedBefore2 = false;

                        WriteUtility.save2();

                        WriteUtility.saveBeingCreated = 0;
                    }

                    if (WriteUtility.saveBeingCreated == 3) {
                        WriteUtility.createSave("Saves/save3.bcs");

                        WriteUtility.clicks3 = 0;
                        WriteUtility.clickPower3 = 1;
                        WriteUtility.save3Name = RenderService.saveNameField.getText();
                        WriteUtility.playedBefore3 = false;

                        WriteUtility.save3();

                        WriteUtility.saveBeingCreated = 0;
                    }

                    LoggingService.Logger.info("Done!");
                }

                RenderService.saveNameField.setText("");

                MenuManager.closeMenu("Enter A Save Name");
                MenuManager.openMenu("Save Manager");

                LoggingService.Logger.info("Loading Saves...");
                ReadUtility.loadSave1();
                ReadUtility.loadSave2();
                ReadUtility.loadSave3();

                RenderService.backToButton.setText("Back To Spark Menu");
                RenderService.titleText.setText("Save Manager");

                LoggingService.Logger.info("Done!");
            }else if(Objects.equals(RenderService.backToButton.getText(), "Back To Settings")) {
                MenuManager.closeMenu("Wiki");
                MenuManager.closeMenu("Credits");
                MenuManager.closeMenu("Resource Packs");
                MenuManager.openMenu("Settings");

                RenderService.titleText.setVisible(true);
                RenderService.titleImage.setVisible(true);

                RenderService.titleText.setText("Settings");
                RenderService.titleText.setFont(new Font("Nunito", Font.BOLD, 80));

                if(MenuManager.StupidBooleanIHaveToMakeForTheBackToButtonTextToSetProperlyWhenYouOpenAnyOfTheMenusInTheSettingsMenuSinceIDontHaveAnythingToHookOntoToMakeItSetToBackToPauseMenu){
                    RenderService.backToButton.setText("Back To Pause Menu");
                }else{
                    RenderService.backToButton.setText("Back To Spark Menu");
                }
                RenderService.backToButton.setBounds(-10, 600, 667, 38);

                RenderService.resetWindow();

                if(AudioService.SoundManager.getMusicAudioPlayer().getStatus()==MediaPlayer.Status.PAUSED){
                    AudioService.Music.resumeMusic();
                }
            }else if(Objects.equals(RenderService.backToButton.getText(), "Back To Pause Menu")){
                MenuManager.closeMenu("Settings");
                MenuManager.openMenu("Pause");

                RenderService.titleText.setText("Button Clicker");
                RenderService.titleText.setFont(new Font("Nunito", Font.BOLD, 70));

                RenderService.backToButton.setVisible(false);
                MenuManager.StupidBooleanIHaveToMakeForTheBackToButtonTextToSetProperlyWhenYouOpenAnyOfTheMenusInTheSettingsMenuSinceIDontHaveAnythingToHookOntoToMakeItSetToBackToPauseMenu = false;
            }else if(Objects.equals(RenderService.backToButton.getText(), "Back To Game")){
                MenuManager.closeMenu("Shop");
                MenuManager.openMenu("Game");

                RenderService.forwardButton.setVisible(false);
                RenderService.backwardButton.setVisible(false);
                RenderService.backToButton.setVisible(false);

                RenderService.clicksField.setBounds(0, 0, 647, 50);
                RenderService.clickPowerField.setBounds(0, 50, 647, 50);

                RenderService.titleText.setVisible(false);

                AudioService.SoundManager.getSMusicAudioPlayer().stop();

                AudioService.Music.resumeMusic();
            }
        }

        if(source== RenderService.save1Button){
            AudioService.SFX.playSFX("select");

            if(Objects.equals(WriteUtility.save1Name, "null")){
                MenuManager.closeMenu("Save Manager");
                MenuManager.openMenu("Choose A Side");

                RenderService.titleText.setText("Choose A Side");
                RenderService.backToButton.setText("Back To Save Manager");

                WriteUtility.saveBeingCreated = 1;
            }else if(!Objects.equals(WriteUtility.save1Name, "null")){
                RenderService.save1Button.setText("Selected!");

                JSONUtility.writeAStringToJsonFile("Data/settings.json", "current_save", "Saves/save1.bcs");
                WriteUtility.currentSave = "Saves/save1.bcs";

                LoggingService.Logger.info("Current Save: "+ WriteUtility.currentSave);

                ATimer timer = new ATimer();
                timer.setDelay(2);
                timer.setTask(() -> {
                    RenderService.save1Button.setText(WriteUtility.save1Name);
                });
                timer.start();
            }
        }

        if(source== RenderService.save2Button){
            AudioService.SFX.playSFX("select");

            if(Objects.equals(WriteUtility.save2Name, "null")){
                MenuManager.closeMenu("Save Manager");
                MenuManager.openMenu("Choose A Side");

                RenderService.titleText.setText("Choose A Side");
                RenderService.backToButton.setText("Back To Save Manager");

                WriteUtility.saveBeingCreated = 2;
            }else if(!Objects.equals(WriteUtility.save2Name, "null")){
                RenderService.save2Button.setText("Selected!");

                JSONUtility.writeAStringToJsonFile("Data/settings.json", "current_save", "Saves/save2.bcs");
                WriteUtility.currentSave = "Saves/save2.bcs";

                LoggingService.Logger.info("Current Save: "+ WriteUtility.currentSave);

                ATimer timer = new ATimer();
                timer.setDelay(2);
                timer.setTask(() -> {
                    RenderService.save2Button.setText(WriteUtility.save2Name);
                });
                timer.start();
            }
        }

        if(source== RenderService.save3Button){
            AudioService.SFX.playSFX("select");

            if(Objects.equals(WriteUtility.save3Name, "null")){
                MenuManager.closeMenu("Save Manager");
                MenuManager.openMenu("Choose A Side");

                RenderService.titleText.setText("Choose A Side");
                RenderService.backToButton.setText("Back To Save Manager");

                WriteUtility.saveBeingCreated = 3;
            }else if(!Objects.equals(WriteUtility.save3Name, "null")){
                RenderService.save3Button.setText("Selected!");

                JSONUtility.writeAStringToJsonFile("Data/settings.json", "current_save", "Saves/save3.bcs");
                WriteUtility.currentSave = "Saves/save3.bcs";

                LoggingService.Logger.info("Current Save: "+ WriteUtility.currentSave);

                ATimer timer = new ATimer();
                timer.setDelay(2);
                timer.setTask(() -> {
                    RenderService.save3Button.setText(WriteUtility.save3Name);
                });
                timer.start();
            }
        }

        if(source== RenderService.lightSideButton){
            AudioService.SFX.playSFX("select");

            MenuManager.closeMenu("Choose A Side");
            MenuManager.openMenu("Enter A Save Name");

            RenderService.backToButton.setText("Create Save");
            RenderService.titleText.setText("Enter a Save Name");
            RenderService.titleText.setFont(new Font("Nunito",Font.BOLD,55));

            RenderService.textFieldLimit(RenderService.saveNameField, 10);

            if(WriteUtility.saveBeingCreated==1){
                WriteUtility.side1 = 1;
            }else if(WriteUtility.saveBeingCreated==2){
                WriteUtility.side2 = 1;
            }else if(WriteUtility.saveBeingCreated==3){
                WriteUtility.side3 = 1;
            }
        }

        if(source== RenderService.darkSideButton){
            AudioService.SFX.playSFX("select");

            MenuManager.closeMenu("Choose A Side");
            MenuManager.openMenu("Enter A Save Name");

            RenderService.backToButton.setText("Create Save");
            RenderService.titleText.setText("Enter a Save Name");
            RenderService.titleText.setFont(new Font("Nunito",Font.BOLD,55));

            RenderService.textFieldLimit(RenderService.saveNameField, 10);

            if(WriteUtility.saveBeingCreated==1){
                WriteUtility.side1 = 2;
            }else if(WriteUtility.saveBeingCreated==2){
                WriteUtility.side2 = 2;
            }else if(WriteUtility.saveBeingCreated==3){
                WriteUtility.side3 = 2;
            }
        }

        if(source== RenderService.magicSideButton){
            AudioService.SFX.playSFX("select");

            MenuManager.closeMenu("Choose A Side");
            MenuManager.openMenu("Enter A Save Name");

            RenderService.backToButton.setText("Create Save");
            RenderService.titleText.setText("Enter a Save Name");
            RenderService.titleText.setFont(new Font("Nunito",Font.BOLD,55));

            RenderService.textFieldLimit(RenderService.saveNameField, 10);

            if(WriteUtility.saveBeingCreated==1){
                WriteUtility.side1 = 3;
            }else if(WriteUtility.saveBeingCreated==2){
                WriteUtility.side2 = 3;
            }else if(WriteUtility.saveBeingCreated==3){
                WriteUtility.side3 = 3;
            }
        }

        if(source== RenderService.neutralSideButton){
            AudioService.SFX.playSFX("select");

            MenuManager.closeMenu("Choose A Side");
            MenuManager.openMenu("Enter A Save Name");

            RenderService.backToButton.setText("Create Save");
            RenderService.titleText.setText("Enter a Save Name");
            RenderService.titleText.setFont(new Font("Nunito",Font.BOLD,55));

            RenderService.textFieldLimit(RenderService.saveNameField, 10);

            if(WriteUtility.saveBeingCreated==1){
                WriteUtility.side1 = 4;
            }else if(WriteUtility.saveBeingCreated==2){
                WriteUtility.side2 = 4;
            }else if(WriteUtility.saveBeingCreated==3){
                WriteUtility.side3 = 4;
            }
        }

        if(source== RenderService.deleteSave1Button){
            AudioService.SFX.playSFX("select");
            if(!(Objects.equals(WriteUtility.save1Name, "null"))){
                int userChoice1 = MessageUtility.DeleteMessage();
                if(userChoice1 == 0){
                    int userChoice2 = MessageUtility.DeleteMessage2();
                    if(userChoice2 == 0){
                        if(Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")){
                            JSONUtility.writeAStringToJsonFile("Data/settings.json", "current_save", "null");
                            WriteUtility.currentSave = "null";

                            WriteUtility.Wipe(1);
                            WriteUtility.deleteSave("Saves/save1.bcs");
                        }else{
                            WriteUtility.Wipe(1);
                            WriteUtility.deleteSave("Saves/save1.bcs");
                        }

                        LoggingService.Logger.info("Current Save: "+ WriteUtility.currentSave);

                        ReadUtility.loadSave1();
                        ReadUtility.loadSave2();
                        ReadUtility.loadSave3();
                    }
                }
            }else if(Objects.equals(WriteUtility.save1Name, "null")){
                ThreadManager.alreadyDeletedMessageThread().start();
            }
        }

        if(source== RenderService.deleteSave2Button){
            AudioService.SFX.playSFX("select");
            if(!(Objects.equals(WriteUtility.save2Name, "null"))){
                int userChoice1 = MessageUtility.DeleteMessage();
                if(userChoice1 == 0){
                    int userChoice2 = MessageUtility.DeleteMessage2();
                    if(userChoice2 == 0){
                        if(Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")){
                            JSONUtility.writeAStringToJsonFile("Data/settings.json", "current_save", "null");
                            WriteUtility.currentSave = "null";

                            WriteUtility.Wipe(2);
                            WriteUtility.deleteSave("Saves/save2.bcs");
                        }else{
                            WriteUtility.Wipe(2);
                            WriteUtility.deleteSave("Saves/save2.bcs");
                        }

                        LoggingService.Logger.info("Current Save: "+ WriteUtility.currentSave);

                        ReadUtility.loadSave1();
                        ReadUtility.loadSave2();
                        ReadUtility.loadSave3();
                    }
                }
            }else if(Objects.equals(WriteUtility.save2Name, "null")){
                ThreadManager.alreadyDeletedMessageThread().start();
            }
        }

        if(source== RenderService.deleteSave3Button){
            AudioService.SFX.playSFX("select");
            if(!(Objects.equals(WriteUtility.save3Name, "null"))){
                int userChoice1 = MessageUtility.DeleteMessage();
                if(userChoice1 == 0){
                    int userChoice2 = MessageUtility.DeleteMessage2();
                    if(userChoice2 == 0){
                        if(Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")){
                            JSONUtility.writeAStringToJsonFile("Data/settings.json", "current_save", "null");
                            WriteUtility.currentSave = "null";

                            WriteUtility.Wipe(3);
                            WriteUtility.deleteSave("Saves/save3.bcs");
                        }else{
                            WriteUtility.Wipe(3);
                            WriteUtility.deleteSave("Saves/save3.bcs");
                        }

                        LoggingService.Logger.info("Current Save: "+ WriteUtility.currentSave);

                        ReadUtility.loadSave1();
                        ReadUtility.loadSave2();
                        ReadUtility.loadSave3();
                    }
                }
            }else if(Objects.equals(WriteUtility.save3Name, "null")){
                ThreadManager.alreadyDeletedMessageThread().start();
            }
        }

        if(source== RenderService.settingsButton){
            if(MenuManager.isMenuOpen("Spark")){
                RenderService.backToButton.setText("Back To Spark Menu");
            }else if(MenuManager.isMenuOpen("Pause")){
                RenderService.backToButton.setText("Back To Pause Menu");
                MenuManager.StupidBooleanIHaveToMakeForTheBackToButtonTextToSetProperlyWhenYouOpenAnyOfTheMenusInTheSettingsMenuSinceIDontHaveAnythingToHookOntoToMakeItSetToBackToPauseMenu = true;
            }

            AudioService.SFX.playSFX("select");

            MenuManager.closeMenu("Spark");
            MenuManager.openMenu("Settings");

            RenderService.backToButton.setVisible(true);

            RenderService.titleText.setText("Settings");
            RenderService.titleText.setFont(new Font("Nunito",Font.BOLD,77));
        }

        if(source== RenderService.bugReportButton){
            LoggingService.Logger.info("Opening Github...");
            AudioService.SFX.playSFX("select");

            try {
                Desktop.getDesktop().browse(new URI("https://github.com/ChillyPigeon742/Button-Clicker/issues/new"));
            } catch (IOException ex) {
                ErrorHandler.IOException();
            } catch (URISyntaxException ex) {
                ErrorHandler.URISyntaxException();
            }

            LoggingService.Logger.info("Done!");
        }

        if(source== RenderService.debugButton){
            AudioService.SFX.playSFX("select");

            MenuManager.closeMenu("Spark");
            MenuManager.openMenu("Debug");

            RenderService.backToButton.setVisible(true);
            RenderService.forwardButton.setVisible(true);
            RenderService.titleText.setText("Debug");
        }

        if(source== RenderService.infoButton){
            AudioService.SFX.playSFX("select");

            MenuManager.closeMenu("Save Manager");
            MenuManager.openMenu("Save Info");

            RenderService.titleText.setText("Save Info");

            if(WriteUtility.side1 == 0){
                RenderService.saveInfoText.setText("\n\n\n        Save 1 Isn't Created!");
            }else if(WriteUtility.side1 == 1){
                if(WriteUtility.playedBefore1){
                    RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Light\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: Yes");
                }else if(!WriteUtility.playedBefore1){
                    RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Light\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: No");
                }
            }else if(WriteUtility.side1 == 2){
                if(WriteUtility.playedBefore1){
                    RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Dark\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: Yes");
                }else if(!WriteUtility.playedBefore1){
                    RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Dark\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: No");
                }
            }else if(WriteUtility.side1 == 3){
                if(WriteUtility.playedBefore1){
                    RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Magic\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: Yes");
                }else if(!WriteUtility.playedBefore1){
                    RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Magic\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: No");
                }
            }else if(WriteUtility.side1 == 4) {
                if(WriteUtility.playedBefore1){
                    RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Neutral\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: Yes");
                }else if(!WriteUtility.playedBefore1){
                    RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Neutral\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: No");
                }
            }

            RenderService.saveInfoText.setBounds(50, 160, 550, 400);

            RenderService.backToButton.setText("Back To Save Manager");
        }

        if(source== RenderService.wikiHelpButton){
            AudioService.SFX.playSFX("select");

            try {
                RenderService.wikiEditorPane.setPage(ResourceManager.class.getResource("/assets/buttonclicker/html/cake.html"));
            } catch (IOException ex) {
                ErrorHandler.IOException();
            }
            RenderService.wikiEditorPane.setForeground(Color.BLACK);
            RenderService.wikiEditorPane.setBackground(new Color(181, 181, 181));
            RenderService.wikiEditorPane.setFont(new Font("Indie Flower", Font.PLAIN, 20));
            RenderService.wikiEditorPane.setMargin(new Insets(180,285,0,0));
            RenderService.wikiEditorPane.setBackgroundImageEnabled(true);

            RenderService.wikiHelpButton.setVisible(false);

            AudioService.Music.pauseMusic();

            LoggingService.Logger.info("the cake is a lie");
        }

        if(source== RenderService.backwardButton){
            if(MenuManager.isMenuOpen("Save Info")){
                if(RenderService.saveInfoText.getText().contains("Save 2")){
                    if(WriteUtility.side1 == 0){
                        RenderService.saveInfoText.setText("\n\n\n        Save 1 Isn't Created!");
                    }else if(WriteUtility.side1 == 1){
                        if(WriteUtility.playedBefore1){
                            RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Light\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore1){
                            RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Light\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side1 == 2){
                        if(WriteUtility.playedBefore1){
                            RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Dark\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore1){
                            RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Dark\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side1 == 3){
                        if(WriteUtility.playedBefore1){
                            RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Magic\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore1){
                            RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Magic\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side1 == 4) {
                        if(WriteUtility.playedBefore1){
                            RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Neutral\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore1){
                            RenderService.saveInfoText.setText("                     Save 1\n\nClicks: "+ WriteUtility.clicks1+"\nClick Power: "+ WriteUtility.clickPower1+"\nSide: Neutral\nSave Name: "+ WriteUtility.save1Name+"\nPlayed Before: No");
                        }
                    }

                    RenderService.backwardButton.setVisible(false);
                }else if(RenderService.saveInfoText.getText().contains("Save 3")){
                    if(WriteUtility.side2 == 0){
                        RenderService.saveInfoText.setText("\n\n\n        Save 2 Isn't Created!");
                    }else if(WriteUtility.side2 == 1){
                        if(WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Light\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Light\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side2 == 2){
                        if(WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Dark\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Dark\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side2 == 3){
                        if(WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Magic\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Magic\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side2 == 4){
                        if(WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Neutral\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Neutral\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: No");
                        }
                    }
                }else if(RenderService.saveInfoText.getText().contains("Current Save")){
                    if (WriteUtility.side3 == 0) {
                        RenderService.saveInfoText.setText("\n\n\n        Save 3 Isn't Created!");
                    }else if(WriteUtility.side3 == 1){
                        if(WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Light\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Light\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side3 == 2){
                        if(WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Dark\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Dark\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side3 == 3){
                        if(WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Magic\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Magic\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side3 == 4){
                        if(WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Neutral\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Neutral\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: No");
                        }
                    }

                    RenderService.saveInfoText.setBounds(50, 160, 550, 400);
                    RenderService.forwardButton.setVisible(true);
                }
            }
        }

        if(source== RenderService.forwardButton){
            if(MenuManager.isMenuOpen("Save Info")){
                if(RenderService.saveInfoText.getText().contains("Save 1")) {
                    if(WriteUtility.side2 == 0){
                        RenderService.saveInfoText.setText("\n\n\n        Save 2 Isn't Created!");
                    }else if(WriteUtility.side2 == 1){
                        if(WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Light\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Light\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side2 == 2){
                        if(WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Dark\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Dark\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side2 == 3){
                        if(WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Magic\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Magic\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side2 == 4){
                        if(WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Neutral\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore2){
                            RenderService.saveInfoText.setText("                     Save 2\n\nClicks: "+ WriteUtility.clicks2+"\nClick Power: "+ WriteUtility.clickPower2+"\nSide: Neutral\nSave Name: "+ WriteUtility.save2Name+"\nPlayed Before: No");
                        }
                    }

                    RenderService.backwardButton.setVisible(true);
                }else if(RenderService.saveInfoText.getText().contains("Save 2")){
                    if (WriteUtility.side3 == 0) {
                        RenderService.saveInfoText.setText("\n\n\n        Save 3 Isn't Created!");
                    }else if(WriteUtility.side3 == 1){
                        if(WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Light\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Light\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side3 == 2){
                        if(WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Dark\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Dark\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side3 == 3){
                        if(WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Magic\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Magic\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: No");
                        }
                    }else if(WriteUtility.side3 == 4){
                        if(WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Neutral\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: Yes");
                        }else if(!WriteUtility.playedBefore3){
                            RenderService.saveInfoText.setText("                     Save 3\n\nClicks: "+ WriteUtility.clicks3+"\nClick Power: "+ WriteUtility.clickPower3+"\nSide: Neutral\nSave Name: "+ WriteUtility.save3Name+"\nPlayed Before: No");
                        }
                    }
                }else if(RenderService.saveInfoText.getText().contains("Save 3")){
                    if(Objects.equals(WriteUtility.currentSave, "null")){
                        RenderService.saveInfoText.setText("Current Save:\nnone");
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")){
                        RenderService.saveInfoText.setText("Current Save:\n"+ WriteUtility.save1Name);
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")){
                        RenderService.saveInfoText.setText("Current Save:\n"+ WriteUtility.save2Name);
                    }else if(Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")){
                        RenderService.saveInfoText.setText("Current Save:\n"+ WriteUtility.save3Name);
                    }

                    RenderService.saveInfoText.setBounds(180, 290, 550, 350);
                    RenderService.forwardButton.setVisible(false);
                }
            }
        }

        if(source== RenderService.nextTipButton){
            Random random1 = new Random();
            int number1 = random1.nextInt(1,4);

            if(number1==1){
                RenderService.tipsText.setText("Button Clicker was originally a lot more simplistic!");
            }else if(number1==2){
                RenderService.tipsText.setText("Button Clicker is out now for early access on itch.io!");
            }else if(number1==3){
                RenderService.tipsText.setText("Rebirthing grants you easier\nprogression next time round!");
            }

            RenderService.nextTipButton.setEnabled(false);
        }

        if(source== RenderService.shopButton){
            AudioService.SFX.playSFX("select");

            MenuManager.closeMenu("Game");
            MenuManager.openMenu("Shop");

            RenderService.shopItem1.setPrice("1000 Clicks");
            RenderService.shopItem1.setIcon(ResourceManager.clickPowerIcon);
            RenderService.shopItem1.setDescription("Gives 1 more Click Power");

            RenderService.shopItem2.setPrice("10000 Clicks");
            RenderService.shopItem2.setIcon(ResourceManager.clickPowerIcon);
            RenderService.shopItem2.setDescription("Gives 10 more Click Power");

            RenderService.shopItem3.setPrice("100000 Clicks");
            RenderService.shopItem3.setIcon(ResourceManager.clickPowerIcon);
            RenderService.shopItem3.setDescription("Gives 100 more Click Power");

            RenderService.shopItem4.setPrice("1 Million Clicks");
            RenderService.shopItem4.setIcon(ResourceManager.clickPowerIcon);
            RenderService.shopItem4.setDescription("Gives 1000 more Click Power");

            RenderService.shopItem5.setPrice("10 Million Clicks");
            RenderService.shopItem5.setIcon(ResourceManager.clickPowerIcon);
            RenderService.shopItem5.setDescription("Gives 10000 more Click Power");

            RenderService.shopItem6.setPrice("100 Million Clicks");
            RenderService.shopItem6.setIcon(ResourceManager.clickPowerIcon);
            RenderService.shopItem6.setDescription("Gives 100000 more Click Power");

            RenderService.forwardButton.setVisible(true);
            RenderService.backwardButton.setVisible(false);

            RenderService.backToButton.setVisible(true);
            RenderService.backToButton.setText("Back To Game");

            RenderService.clicksField.setVisible(true);
            RenderService.clicksField.setBounds(0, 54, 647, 50);

            RenderService.clickPowerField.setVisible(true);
            RenderService.clickPowerField.setBounds(0, 104, 647, 50);

            RenderService.titleText.setVisible(true);
            RenderService.titleText.setText("Shop");
            RenderService.titleText.setBounds(100, -2, 460, 55);
            RenderService.titleText.setFont(new Font("Nunito",Font.BOLD,50));

            RenderService.titleImage.stopSpinning();
            RenderService.titleImage.setBounds(10, 0, 100, 100);
            RenderService.titleImage.setVisible(false);

            RenderService.autoSavingText.setVisible(false);

            AudioService.Music.pauseMusic();
            AudioService.Music.playMusic("shop");
        }

        if(source== RenderService.pauseButton){
            LoggingService.Logger.info("Pausing...");
            AudioService.SFX.playSFX("select");

            MenuManager.closeMenu("Game");
            MenuManager.openMenu("Pause");

            RenderService.titleText.setText("Button Clicker");
            RenderService.titleText.setBounds(100, 20, 570, 80);
            RenderService.titleText.setFont(new Font("Nunito",Font.BOLD,70));
            RenderService.titleText.setVisible(true);

            RenderService.titleImage.stopSpinning();
            RenderService.titleImage.setBounds(10,0,100,100);
            RenderService.titleImage.setVisible(true);

            RenderService.startGameButton.setBounds(177,199,305,102);
            RenderService.startGameButton.setText("Resume Game");

            RenderService.settingsButton.setBounds(177,298,305,102);

            RenderService.quitButton.setText("Return To Spark Menu");
            RenderService.quitButton.setFont(new Font("Nunito", Font.BOLD, 29));
            RenderService.quitButton.setBounds(177,397,305,102);

            RenderService.autoSavingText.setVisible(false);

            AudioService.Music.pauseMusic();
            AudioService.Music.playMusic("pause");

            LoggingService.Logger.info("Done!");
        }

        if(source== RenderService.showConsoleButton){
            AudioService.SFX.playSFX("select");

            if(Objects.equals(RenderService.showConsoleButton.getText(), "Show Console")){
                RenderService.showConsoleButton.setText("Hide Console");
                RenderService.frame.setSize(1263, 675);
                RenderService.frame.setLocationRelativeTo(null);

                RenderService.console.setVisible(true);
                RenderService.consoleScrollPane.setVisible(true);
                RenderService.commandBar.setVisible(true);
                RenderService.consoleSettingsButton.setVisible(true);
            }else if(Objects.equals(RenderService.showConsoleButton.getText(), "Hide Console")){
                RenderService.showConsoleButton.setText("Show Console");
                RenderService.frame.setSize(663, 675);
                RenderService.frame.setLocationRelativeTo(null);

                RenderService.console.setVisible(false);
                RenderService.consoleScrollPane.setVisible(false);
                RenderService.commandBar.setVisible(false);
                RenderService.consoleSettingsButton.setVisible(false);
            }
        }

        if(source== RenderService.crashButton){
            AudioService.SFX.playSFX("select");

            ErrorHandler.IOException();
        }

        if(source== RenderService.consoleSettingsButton){
            AudioService.SFX.playSFX("select");
        }
    };


    public static class keyListener implements KeyListener {
        private Method[] findMethodsByName(String command) {
            return Arrays.stream(CommandDefinitions.class.getMethods())
                    .filter(method -> method.getName().equals(command))
                    .toArray(Method[]::new);
        }

        private Method findMatchingMethod(Method[] methods, int argumentCount) {
            for (Method method : methods) {
                if (method.getParameterCount() == argumentCount) {
                    return method;
                }
            }
            return null;
        }

        private String generateExpectedArgumentsMessage(Method[] methods) {
            StringBuilder message = new StringBuilder("Expected:");
            for (Method method : methods) {
                Class<?>[] paramTypes = method.getParameterTypes();
                message.append(" [");
                for (Class<?> paramType : paramTypes) {
                    message.append(paramType.getSimpleName()).append(", ");
                }
                if (paramTypes.length > 0) {
                    message.setLength(message.length() - 2);
                }
                message.append("]");
            }
            return message.toString();
        }

        private Object convertArgument(Object arg, Class<?> expectedType) {
            String argStr = arg.toString();
            try {
                if (expectedType == String.class) {
                    return argStr;
                } else if (expectedType == int.class || expectedType == Integer.class) {
                    return Integer.parseInt(argStr);
                } else if (expectedType == boolean.class || expectedType == Boolean.class) {
                    return Boolean.parseBoolean(argStr);
                } else if (expectedType == long.class || expectedType == Long.class) {
                    return Long.parseLong(argStr);
                } else if (expectedType == double.class || expectedType == Double.class) {
                    return Double.parseDouble(argStr);
                } else if (expectedType == float.class || expectedType == Float.class) {
                    return Float.parseFloat(argStr);
                } else if (expectedType == short.class || expectedType == Short.class) {
                    return Short.parseShort(argStr);
                } else if (expectedType == byte.class || expectedType == Byte.class) {
                    return Byte.parseByte(argStr);
                } else if (expectedType == char.class || expectedType == Character.class) {
                    if (argStr.length() == 1) {
                        return argStr.charAt(0);
                    } else {
                        throw new IllegalArgumentException("Invalid char: " + argStr);
                    }
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid argument type! Expected: " + expectedType.getSimpleName() + " but got " + argStr);
            }
            throw new IllegalArgumentException("Unsupported argument type: " + expectedType.getSimpleName());
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (e.getSource() == RenderService.commandBar) {
                    String input = RenderService.commandBar.getText().trim();

                    RenderService.frame.getContentPane().requestFocus();
                    RenderService.commandBar.setForeground(Color.GRAY);
                    RenderService.commandBar.setText("Enter any command, type help for a list of all the commands");
                    RenderService.commandBar.setFont(new Font("Nunito", Font.BOLD, 14));

                    if (input.isEmpty()) {
                        AudioService.SFX.playSFX("declined");
                        RenderService.commandBar.setText("Please enter a command!");
                        return;
                    }

                    String[] arguments = input.split(" ");
                    String command = arguments[0];

                    Object[] commandArgs = new Object[arguments.length - 1];
                    for (int i = 0; i < commandArgs.length; i++) {
                        commandArgs[i] = arguments[i + 1];
                    }

                    try {
                        Method[] possibleMethods = findMethodsByName(command);

                        if (possibleMethods.length == 0) {
                            throw new NoSuchMethodException("No definition found for command: " + command);
                        }

                        Method method = findMatchingMethod(possibleMethods, commandArgs.length);

                        if (method == null) {
                            String expectedArgsInfo = generateExpectedArgumentsMessage(possibleMethods);
                            AudioService.SFX.playSFX("declined");
                            RenderService.commandBar.setText("Invalid number of arguments for command " + command + ". " + expectedArgsInfo);
                            return;
                        }

                        Class<?>[] paramTypes = method.getParameterTypes();
                        for (int i = 0; i < commandArgs.length; i++) {
                            commandArgs[i] = convertArgument(commandArgs[i], paramTypes[i]);
                        }

                        method.invoke(Spark.commandDefinitions, commandArgs);

                    } catch (NoSuchMethodException ex) {
                        AudioService.SFX.playSFX("declined");
                        RenderService.commandBar.setText("No definition found for command: " + command);
                        ErrorHandler.NoSuchMethodException();
                    } catch (IllegalAccessException ex) {
                        AudioService.SFX.playSFX("declined");
                        RenderService.commandBar.setText("Access error! Unable to invoke method.");
                        ErrorHandler.IllegalAccessException();
                    } catch (InvocationTargetException ex) {
                        AudioService.SFX.playSFX("declined");
                        RenderService.commandBar.setText("Invocation error! Something went wrong during method execution.");
                        ErrorHandler.InvocationTargetException();
                    } catch (IllegalArgumentException ex) {
                        AudioService.SFX.playSFX("declined");
                        RenderService.commandBar.setText("Invalid argument types provided for command: " + command);
                        ErrorHandler.IllegalArgumentException();
                    } catch (Exception ex) {
                        AudioService.SFX.playSFX("declined");
                        RenderService.commandBar.setText("An unknown error occurred while processing the command!");
                        ErrorHandler.Exception();
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    public static class mouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.getButton()==MouseEvent.BUTTON1){
                if(e.getSource()== RenderService.frame){
                    RenderService.frame.getContentPane().requestFocus();
                }
            }

            if (e.getButton() == MouseEvent.BUTTON3) {
                if(MenuManager.isMenuOpen("Spark")){
                    RenderService.debugButton.setVisible(true);
                }
            }

            if(e.getSource()== RenderService.shopItem1){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(Objects.equals(RenderService.shopItem1.getDescription(), "Gives 1 more Click Power")){
                        if (Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")) {
                            if(WriteUtility.clicks1<1000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem1.setPrice("Not Enough Clicks");
                                RenderService.shopItem1.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem1.setPrice("1000 Clicks");
                                    RenderService.shopItem1.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks1>=1000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks1 = WriteUtility.clicks1 - 1000;
                                WriteUtility.clickPower1 = WriteUtility.clickPower1 + 1;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks1);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower1);
                            }
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")) {
                            if(WriteUtility.clicks2<1000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem1.setPrice("Not Enough Clicks");
                                RenderService.shopItem1.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem1.setPrice("1000 Clicks");
                                    RenderService.shopItem1.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks2>=1000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks2 = WriteUtility.clicks2 - 1000;
                                WriteUtility.clickPower2 = WriteUtility.clickPower2 + 1;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks2);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower2);
                            }
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")) {
                            if(WriteUtility.clicks3<1000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem1.setPrice("Not Enough Clicks");
                                RenderService.shopItem1.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem1.setPrice("1000 Clicks");
                                    RenderService.shopItem1.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks3>=1000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks3 = WriteUtility.clicks3 - 1000;
                                WriteUtility.clickPower3 = WriteUtility.clickPower3 + 1;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks3);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower3);
                            }
                        }
                    }
                }
            }

            if(e.getSource()== RenderService.shopItem2){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(Objects.equals(RenderService.shopItem2.getDescription(), "Gives 10 more Click Power")){
                        if (Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")) {
                            if(WriteUtility.clicks1<10000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem2.setPrice("Not Enough Clicks");
                                RenderService.shopItem2.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem2.setPrice("10000 Clicks");
                                    RenderService.shopItem2.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks1>=10000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks1 = WriteUtility.clicks1 - 10000;
                                WriteUtility.clickPower1 = WriteUtility.clickPower1 + 10;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks1);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower1);
                            }
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")) {
                            if(WriteUtility.clicks2<10000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem2.setPrice("Not Enough Clicks");
                                RenderService.shopItem2.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem2.setPrice("10000 Clicks");
                                    RenderService.shopItem2.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks2>=10000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks2 = WriteUtility.clicks2 - 10000;
                                WriteUtility.clickPower2 = WriteUtility.clickPower2 + 10;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks2);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower2);
                            }
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")) {
                            if(WriteUtility.clicks3<10000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem2.setPrice("Not Enough Clicks");
                                RenderService.shopItem2.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem2.setPrice("10000 Clicks");
                                    RenderService.shopItem2.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks3>=10000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks3 = WriteUtility.clicks3 - 10000;
                                WriteUtility.clickPower3 = WriteUtility.clickPower3 + 10;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks3);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower3);
                            }
                        }
                    }
                }
            }

            if(e.getSource()== RenderService.shopItem3){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(Objects.equals(RenderService.shopItem3.getDescription(), "Gives 100 more Click Power")){
                        if (Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")) {
                            if(WriteUtility.clicks1<100000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem3.setPrice("Not Enough Clicks");
                                RenderService.shopItem3.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem3.setPrice("100000 Clicks");
                                    RenderService.shopItem3.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks1>=100000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks1 = WriteUtility.clicks1 - 100000;
                                WriteUtility.clickPower1 = WriteUtility.clickPower1 + 100;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks1);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower1);
                            }
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")) {
                            if(WriteUtility.clicks2<100000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem3.setPrice("Not Enough Clicks");
                                RenderService.shopItem3.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem3.setPrice("100000 Clicks");
                                    RenderService.shopItem3.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks2>=100000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks2 = WriteUtility.clicks2 - 100000;
                                WriteUtility.clickPower2 = WriteUtility.clickPower2 + 100;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks2);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower2);
                            }
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")) {
                            if(WriteUtility.clicks3<100000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem3.setPrice("Not Enough Clicks");
                                RenderService.shopItem3.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem3.setPrice("100000 Clicks");
                                    RenderService.shopItem3.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks3>=100000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks3 = WriteUtility.clicks3 - 100000;
                                WriteUtility.clickPower3 = WriteUtility.clickPower3 + 100;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks3);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower3);
                            }
                        }
                    }
                }
            }

            if(e.getSource()== RenderService.shopItem4){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(Objects.equals(RenderService.shopItem4.getDescription(), "Gives 1000 more Click Power")){
                        if (Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")) {
                            if(WriteUtility.clicks1<1000000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem4.setPrice("Not Enough Clicks");
                                RenderService.shopItem4.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem4.setPrice("1 Million Clicks");
                                    RenderService.shopItem4.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks1>=1000000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks1 = WriteUtility.clicks1 - 1000000;
                                WriteUtility.clickPower1 = WriteUtility.clickPower1 + 1000;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks1);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower1);
                            }
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")) {
                            if(WriteUtility.clicks2<1000000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem4.setPrice("Not Enough Clicks");
                                RenderService.shopItem4.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem4.setPrice("1 Million Clicks");
                                    RenderService.shopItem4.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks2>=1000000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks2 = WriteUtility.clicks2 - 1000000;
                                WriteUtility.clickPower2 = WriteUtility.clickPower2 + 1000;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks2);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower2);
                            }
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")) {
                            if(WriteUtility.clicks3<1000000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem4.setPrice("Not Enough Clicks");
                                RenderService.shopItem4.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem4.setPrice("1 Million Clicks");
                                    RenderService.shopItem4.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks3>=1000000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks3 = WriteUtility.clicks3 - 1000000;
                                WriteUtility.clickPower3 = WriteUtility.clickPower3 + 1000;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks3);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower3);
                            }
                        }
                    }
                }
            }

            if(e.getSource()== RenderService.shopItem5){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(Objects.equals(RenderService.shopItem5.getDescription(), "Gives 10000 more Click Power")){
                        if (Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")) {
                            if(WriteUtility.clicks1<10000000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem5.setPrice("Not Enough Clicks");
                                RenderService.shopItem5.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem5.setPrice("10 Million Clicks");
                                    RenderService.shopItem5.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks1>=10000000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks1 = WriteUtility.clicks1 - 10000000;
                                WriteUtility.clickPower1 = WriteUtility.clickPower1 + 10000;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks1);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower1);
                            }
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")) {
                            if(WriteUtility.clicks2<10000000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem5.setPrice("Not Enough Clicks");
                                RenderService.shopItem5.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem5.setPrice("10 Million Clicks");
                                    RenderService.shopItem5.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks2>=10000000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks2 = WriteUtility.clicks2 - 10000000;
                                WriteUtility.clickPower2 = WriteUtility.clickPower2 + 10000;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks2);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower2);
                            }
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")) {
                            if(WriteUtility.clicks3<10000000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem5.setPrice("Not Enough Clicks");
                                RenderService.shopItem5.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem5.setPrice("10 Million Clicks");
                                    RenderService.shopItem5.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks3>=10000000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks3 = WriteUtility.clicks3 - 10000000;
                                WriteUtility.clickPower3 = WriteUtility.clickPower3 + 10000;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks3);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower3);
                            }
                        }
                    }
                }
            }

            if(e.getSource()== RenderService.shopItem6){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(Objects.equals(RenderService.shopItem6.getDescription(), "Gives 100000 more Click Power")){
                        if (Objects.equals(WriteUtility.currentSave, "Saves/save1.bcs")) {
                            if(WriteUtility.clicks1<100000000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem6.setPrice("Not Enough Clicks");
                                RenderService.shopItem6.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem6.setPrice("100 Million Clicks");
                                    RenderService.shopItem6.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks1>=100000000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks1 = WriteUtility.clicks1 - 100000000;
                                WriteUtility.clickPower1 = WriteUtility.clickPower1 + 100000;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks1);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower1);
                            }
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save2.bcs")) {
                            if(WriteUtility.clicks2<100000000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem6.setPrice("Not Enough Clicks");
                                RenderService.shopItem6.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem6.setPrice("100 Million Clicks");
                                    RenderService.shopItem6.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks2>=100000000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks2 = WriteUtility.clicks2 - 100000000;
                                WriteUtility.clickPower2 = WriteUtility.clickPower2 + 100000;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks2);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower2);
                            }
                        } else if (Objects.equals(WriteUtility.currentSave, "Saves/save3.bcs")) {
                            if(WriteUtility.clicks3<100000000){
                                AudioService.SFX.playSFX("declined");
                                RenderService.shopItem6.setPrice("Not Enough Clicks");
                                RenderService.shopItem6.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    RenderService.shopItem6.setPrice("100 Million Clicks");
                                    RenderService.shopItem6.setDisabled(false);
                                });
                                timer.start();
                            }else if(WriteUtility.clicks3>=100000000){
                                AudioService.SFX.playSFX("purchase");

                                WriteUtility.clicks3 = WriteUtility.clicks3 - 100000000;
                                WriteUtility.clickPower3 = WriteUtility.clickPower3 + 100000;

                                RenderService.clicksField.setText("Clicks: "+ WriteUtility.clicks3);
                                RenderService.clickPowerField.setText("Click Power: "+ WriteUtility.clickPower3);
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}