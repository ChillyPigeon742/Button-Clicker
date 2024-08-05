package net.alek.buttonclicker.services;

import javafx.scene.media.MediaPlayer;

import net.alek.buttonclicker.engine.ErrorHandler;
import net.alek.buttonclicker.engine.MenuManager;
import net.alek.buttonclicker.engine.ResourceManager;
import net.alek.buttonclicker.engine.ThreadManager;

import net.alek.buttonclicker.libraries.ATimer;

import net.alek.buttonclicker.utilities.*;

import net.alek.buttonclicker.libraries.StorageLibrary;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Random;

public class InputService{
    public static ActionListener actionListener = e -> {
        Object source = e.getSource();

        if(source==StorageLibrary.startGameButton){
            AudioUtility.SFX.playSFX("select");

            if(Objects.equals(StorageLibrary.startGameButton.getText(), "Select A Save To Continue")){
                StorageLibrary.startGameButton.setText("I said what I said.");

                ATimer timer = new ATimer();
                timer.setDelay(2);
                timer.setTask(() -> {
                    StorageLibrary.startGameButton.setText("Select A Save To Continue");
                });
                timer.start();
            }else if(Objects.equals(StorageLibrary.startGameButton.getText(), "Start Game")){
                StorageLibrary.logger.info("Loading Game...");

                if(Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")){
                    LoadService.loadSave1();
                    StorageLibrary.playedBefore1 = true;
                    SaveService.save1();
                }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")){
                    LoadService.loadSave2();
                    StorageLibrary.playedBefore2 = true;
                    SaveService.save2();
                }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")){
                    LoadService.loadSave3();
                    StorageLibrary.playedBefore3 = true;
                    SaveService.save3();
                }

                MenuManager.closeMenu("Main");
                MenuManager.openMenu("Loading");

                StorageLibrary.loadingBar.setValue(0);
                StorageLibrary.progressText.setText("[1/3] Loading Resources");

                RenderUtility.startProgress(StorageLibrary.loadingBar, 340);

                StorageLibrary.nextTipButton.setEnabled(true);

                Random random = new Random();
                int number = random.nextInt(1,4);

                if(number==1){
                    StorageLibrary.tipsText.setText("Did you know you can click to\nclick?");
                }else if(number==2){
                    StorageLibrary.tipsText.setText("Obtaining extra click power\ngains you more clicks");
                }else if(number==3){
                    StorageLibrary.tipsText.setText("Button Clicker was made\navailable on March 20, 2024");
                }

                StorageLibrary.titleText.setBounds(46, 40, 570, 80);
                StorageLibrary.titleImage.setBounds(280,150,110,110);

                StorageLibrary.titleImage.startSpinning();

                AudioUtility.Music.stopMusic();

                ATimer timer = new ATimer();
                timer.setDelay(17);
                timer.setTask(() -> {
                    if(StorageLibrary.nextTipButton.isEnabled()){
                        Random random1 = new Random();
                        int number1 = random1.nextInt(1,4);

                        if(number1==1){
                            StorageLibrary.tipsText.setText("Button Clicker was originally a lot more simplistic!");
                        }else if(number1==2){
                            StorageLibrary.tipsText.setText("Button Clicker is out now for early access on itch.io!");
                        }else if(number1==3){
                            StorageLibrary.tipsText.setText("Rebirthing grants you easier\nprogression next time round!");
                        }

                        StorageLibrary.nextTipButton.setEnabled(false);
                    }
                });
                timer.start();

                ATimer timer1 = new ATimer();
                timer1.setDelay(3);
                timer1.setTask(() -> {
                    StorageLibrary.progressText.setText("[1/3] Loading Resources.");

                    ATimer timer2 = new ATimer();
                    timer2.setDelay(3);
                    timer2.setTask(() -> {
                        StorageLibrary.progressText.setText("[1/3] Loading Resources..");

                        ATimer timer3 = new ATimer();
                        timer3.setDelay(3);
                        timer3.setTask(() -> {
                            StorageLibrary.progressText.setText("[1/3] Loading Resources...");

                            ATimer timer4 = new ATimer();
                            timer4.setDelay(3);
                            timer4.setTask(() -> {
                                StorageLibrary.progressText.setText("[2/3] Loading Save");

                                ATimer timer5 = new ATimer();
                                timer5.setDelay(3);
                                timer5.setTask(() -> {
                                    StorageLibrary.progressText.setText("[2/3] Loading Save.");

                                    ATimer timer6 = new ATimer();
                                    timer6.setDelay(3);
                                    timer6.setTask(() -> {
                                        StorageLibrary.progressText.setText("[2/3] Loading Save..");

                                        ATimer timer7 = new ATimer();
                                        timer7.setDelay(3);
                                        timer7.setTask(() -> {
                                            StorageLibrary.progressText.setText("[2/3] Loading Save...");

                                            ATimer timer8 = new ATimer();
                                            timer8.setDelay(3);
                                            timer8.setTask(() -> {
                                                StorageLibrary.progressText.setText("[3/3] Channeling Dark Forces");

                                                ATimer timer9= new ATimer();
                                                timer9.setDelay(3);
                                                timer9.setTask(() -> {
                                                    StorageLibrary.progressText.setText("[3/3] Channeling Dark Forces.");

                                                    ATimer timer10 = new ATimer();
                                                    timer10.setDelay(3);
                                                    timer10.setTask(() -> {
                                                        StorageLibrary.progressText.setText("[3/3] Channeling Dark Forces..");

                                                        ATimer timer11 = new ATimer();
                                                        timer11.setDelay(3);
                                                        timer11.setTask(() -> {
                                                            StorageLibrary.progressText.setText("[3/3] Channeling Dark Forces...");
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

                AudioUtility.Music.playMusic("loading");

                StorageLibrary.logger.info("Done!");
            }else if(Objects.equals(StorageLibrary.startGameButton.getText(), "Resume Game")){
                if(MenuManager.isMenuOpen("Main")){
                    StorageLibrary.logger.info("Loading Game...");

                    MenuManager.closeMenu("Main");
                    MenuManager.openMenu("Loading");

                    StorageLibrary.loadingBar.setValue(0);
                    StorageLibrary.progressText.setText("[1/3] Loading Resources");

                    RenderUtility.startProgress(StorageLibrary.loadingBar, 340);

                    StorageLibrary.nextTipButton.setEnabled(true);

                    Random random = new Random();
                    int number = random.nextInt(1,4);

                    if(number==1){
                        StorageLibrary.tipsText.setText("Did you know you can click to\nclick?");
                    }else if(number==2){
                        StorageLibrary.tipsText.setText("Obtaining extra click power\ngains you more clicks");
                    }else if(number==3){
                        StorageLibrary.tipsText.setText("Button Clicker was made\navailable on March 20, 2024");
                    }

                    StorageLibrary.titleText.setBounds(46, 40, 570, 80);
                    StorageLibrary.titleImage.setBounds(280,150,110,110);

                    StorageLibrary.titleImage.startSpinning();

                    AudioUtility.Music.stopMusic();

                    ATimer timer = new ATimer();
                    timer.setDelay(17);
                    timer.setTask(() -> {
                        if(StorageLibrary.nextTipButton.isEnabled()){
                            Random random1 = new Random();
                            int number1 = random1.nextInt(1,4);

                            if(number1==1){
                                StorageLibrary.tipsText.setText("Button Clicker was originally a lot more simplistic!");
                            }else if(number1==2){
                                StorageLibrary.tipsText.setText("Button Clicker is out now for early access on itch.io!");
                            }else if(number1==3){
                                StorageLibrary.tipsText.setText("Rebirthing grants you easier\nprogression next time round!");
                            }

                            StorageLibrary.nextTipButton.setEnabled(false);
                        }
                    });
                    timer.start();

                    ATimer timer1 = new ATimer();
                    timer1.setDelay(3);
                    timer1.setTask(() -> {
                        StorageLibrary.progressText.setText("[1/3] Loading Resources.");

                        ATimer timer2 = new ATimer();
                        timer2.setDelay(3);
                        timer2.setTask(() -> {
                            StorageLibrary.progressText.setText("[1/3] Loading Resources..");

                            ATimer timer3 = new ATimer();
                            timer3.setDelay(3);
                            timer3.setTask(() -> {
                                StorageLibrary.progressText.setText("[1/3] Loading Resources...");

                                ATimer timer4 = new ATimer();
                                timer4.setDelay(3);
                                timer4.setTask(() -> {
                                    StorageLibrary.progressText.setText("[2/3] Loading Save");

                                    ATimer timer5 = new ATimer();
                                    timer5.setDelay(3);
                                    timer5.setTask(() -> {
                                        StorageLibrary.progressText.setText("[2/3] Loading Save.");

                                        ATimer timer6 = new ATimer();
                                        timer6.setDelay(3);
                                        timer6.setTask(() -> {
                                            StorageLibrary.progressText.setText("[2/3] Loading Save..");

                                            ATimer timer7 = new ATimer();
                                            timer7.setDelay(3);
                                            timer7.setTask(() -> {
                                                StorageLibrary.progressText.setText("[2/3] Loading Save...");

                                                ATimer timer8 = new ATimer();
                                                timer8.setDelay(3);
                                                timer8.setTask(() -> {
                                                    StorageLibrary.progressText.setText("[3/3] Channeling Dark Forces");

                                                    ATimer timer9= new ATimer();
                                                    timer9.setDelay(3);
                                                    timer9.setTask(() -> {
                                                        StorageLibrary.progressText.setText("[3/3] Channeling Dark Forces.");

                                                        ATimer timer10 = new ATimer();
                                                        timer10.setDelay(3);
                                                        timer10.setTask(() -> {
                                                            StorageLibrary.progressText.setText("[3/3] Channeling Dark Forces..");

                                                            ATimer timer11 = new ATimer();
                                                            timer11.setDelay(3);
                                                            timer11.setTask(() -> {
                                                                StorageLibrary.progressText.setText("[3/3] Channeling Dark Forces...");
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

                    AudioUtility.Music.playMusic("loading");

                    StorageLibrary.logger.info("Done!");
                }else if(MenuManager.isMenuOpen("Pause")){
                    StorageLibrary.logger.info("Resuming...");

                    MenuManager.closeMenu("Pause");

                    if(Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")){
                        StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks1);
                        StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower1);
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")){
                        StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks2);
                        StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower2);
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")){
                        StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks3);
                        StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower3);
                    }

                    StorageLibrary.titleText.setVisible(false);
                    StorageLibrary.titleImage.setVisible(false);

                    AudioUtility.SoundManager.getSMusicAudioPlayer().stop();

                    if(AudioUtility.Music.playNextSong){
                        AudioUtility.Music.playMusic(AudioUtility.Music.nextSong);

                        AudioUtility.Music.playNextSong = false;
                        AudioUtility.Music.nextSong = "brothisshitissobadillfixitinthenextupdate";
                    }else{
                        AudioUtility.Music.resumeMusic();
                    }

                    MenuManager.openMenu("Game");

                    StorageLibrary.logger.info("Done!");
                }
            }
        }

        if(source==StorageLibrary.saveManagerButton){
            StorageLibrary.logger.info("Opening Save Manager...");
            AudioUtility.SFX.playSFX("select");

            MenuManager.closeMenu("Main");
            MenuManager.openMenu("Save Manager");

            StorageLibrary.backToButton.setVisible(true);
            StorageLibrary.backToButton.setText("Back To Main Menu");
            StorageLibrary.titleText.setText("Save Manager");

            StorageLibrary.logger.info("Loading Saves...");
            LoadService.loadSave1();
            LoadService.loadSave2();
            LoadService.loadSave3();

            StorageLibrary.logger.info("Done!");
        }

        if(source==StorageLibrary.wikiButton){
            StorageLibrary.logger.info("Opening Wiki...");
            AudioUtility.SFX.playSFX("select");

            MenuManager.closeMenu("Settings");
            MenuManager.openMenu("Wiki");

            StorageLibrary.backToButton.setBounds(-10, 593, 1036, 38);
            StorageLibrary.backToButton.setText("Back To Settings");

            StorageLibrary.wikiEditorPane.setText("<html>\n" +
                    "<body>\n" +
                    "\t<header>\n" +
                    "\t\t<h1>Button Clicker Wiki</h1>\n" +
                    "\t</header>\n" +
                    "\t<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Nullam non nisi est sit amet. Arcu non sodales neque sodales ut etiam sit amet. Suscipit adipiscing bibendum est ultricies integer quis auctor elit sed. Ipsum dolor sit amet consectetur adipiscing elit pellentesque habitant morbi. Netus et malesuada fames ac. Integer enim neque volutpat ac tincidunt vitae. Hendrerit dolor magna eget est lorem. Fusce ut placerat orci nulla. Sociis natoque penatibus et magnis. Facilisi cras fermentum odio eu. Urna porttitor rhoncus dolor purus non enim. At tellus at urna condimentum mattis pellentesque id. Dolor sit amet consectetur adipiscing elit pellentesque. Morbi non arcu risus quis varius quam quisque. Faucibus purus in massa tempor. Nec nam aliquam sem et tortor consequat id porta nibh.</p>\n" +
                    "\t<p>Pellentesque elit eget gravida cum. Sapien faucibus et molestie ac feugiat. Laoreet sit amet cursus sit amet dictum sit amet. Ac auctor augue mauris augue neque. Nibh nisl condimentum id venenatis a condimentum vitae. Eget duis at tellus at. Ac odio tempor orci dapibus ultrices in iaculis nunc. Mauris cursus mattis molestie a iaculis at erat. Sollicitudin aliquam ultrices sagittis orci. Leo urna molestie at elementum. Malesuada proin libero nunc consequat interdum varius sit amet. Dignissim suspendisse in est ante. Ac felis donec et odio pellentesque diam volutpat. At tempor commodo ullamcorper a lacus. Ut tellus elementum sagittis vitae et leo duis ut. Fusce id velit ut tortor pretium viverra suspendisse potenti nullam. A iaculis at erat pellentesque adipiscing commodo elit.</p>\n" +
                    "\t<p>Donec adipiscing tristique risus nec feugiat in fermentum. Aliquam ut porttitor leo a. Sed blandit libero volutpat sed cras. Pulvinar neque laoreet suspendisse interdum consectetur libero id faucibus. Tempor commodo ullamcorper a lacus vestibulum sed. Pharetra magna ac placerat vestibulum lectus mauris ultrices eros in. Montes nascetur ridiculus mus mauris. Mollis aliquam ut porttitor leo a diam sollicitudin tempor id. Amet risus nullam eget felis eget nunc lobortis mattis aliquam. Arcu felis bibendum ut tristique et egestas. Velit scelerisque in dictum non consectetur a erat nam. Ante metus dictum at tempor commodo ullamcorper a. Nunc mattis enim ut tellus elementum sagittis vitae et. Odio aenean sed adipiscing diam donec. Elit eget gravida cum sociis natoque penatibus et. Morbi tincidunt augue interdum velit euismod. Magna eget est lorem ipsum dolor. Elementum facilisis leo vel fringilla est ullamcorper eget.</p>\n" +
                    "\t<p>Vel pharetra vel turpis nunc eget lorem dolor sed viverra. Mollis nunc sed id semper. Metus vulputate eu scelerisque felis. Venenatis tellus in metus vulputate. Consectetur libero id faucibus nisl tincidunt eget. Faucibus turpis in eu mi bibendum neque. Varius sit amet mattis vulputate. Convallis convallis tellus id interdum velit laoreet. Tellus orci ac auctor augue. Fames ac turpis egestas integer eget aliquet nibh praesent. Mi eget mauris pharetra et ultrices. Nunc congue nisi vitae suscipit tellus. In vitae turpis massa sed elementum tempus. Pellentesque dignissim enim sit amet venenatis urna cursus eget. Sociis natoque penatibus et magnis dis parturient montes. Phasellus vestibulum lorem sed risus ultricies. Sem nulla pharetra diam sit amet nisl suscipit adipiscing.</p>\n" +
                    "\t<p>Donec ac odio tempor orci dapibus ultrices in. At erat pellentesque adipiscing commodo elit at imperdiet dui accumsan. Facilisi nullam vehicula ipsum a. Mauris augue neque gravida in fermentum et sollicitudin ac orci. Sed id semper risus in hendrerit. Ac odio tempor orci dapibus ultrices in iaculis nunc sed. Vulputate dignissim suspendisse in est ante in nibh. Faucibus in ornare quam viverra. Congue mauris rhoncus aenean vel. Morbi blandit cursus risus at ultrices mi. Et tortor at risus viverra adipiscing. Lectus arcu bibendum at varius vel pharetra.</p>\n" +
                    "\t<img src="+ ResourceManager.class.getResource("/assets/buttonclicker/images/button_clicker_icon3.png") +"></img>\n" +
                    "</body>\n" +
                    "</html>");
            StorageLibrary.wikiEditorPane.setBackground(new Color(50, 50, 50));
            StorageLibrary.wikiEditorPane.setForeground(Color.WHITE);
            StorageLibrary.wikiEditorPane.setFont(new Font("Nunito",Font.BOLD,20));
            StorageLibrary.wikiEditorPane.setMargin(new Insets(0,5,0,0));
            StorageLibrary.wikiEditorPane.setBackgroundImageEnabled(false);

            StorageLibrary.frame.setSize(1031, 668);
            StorageLibrary.frame.setLocationRelativeTo(null);

            if(StorageLibrary.StupidBooleanIHaveToMakeForTheBackToButtonTextToSetProperlyWhenYouOpenAnyOfTheMenusInTheSettingsMenuSinceIDontHaveAnythingToHookOntoToMakeItSetToBackToPauseMenu){
                StorageLibrary.wikiHelpButton.setVisible(false);
            }

            RenderUtility.setResetWindowTrigger();

            StorageLibrary.logger.info("Done!");
        }

        if(source==StorageLibrary.creditsButton){
            StorageLibrary.logger.info("Opening Credits...");
            AudioUtility.SFX.playSFX("select");

            MenuManager.closeMenu("Settings");
            MenuManager.openMenu("Credits");

            StorageLibrary.backToButton.setText("Back To Settings");
            StorageLibrary.backToButton.setVisible(true);

            StorageLibrary.titleText.setText("Credits");
            StorageLibrary.titleText.setFont(new Font("Nunito",Font.BOLD,70));

            StorageLibrary.logger.info("Done!");
        }

        if(source==StorageLibrary.quitButton){
            AudioUtility.SFX.playSFX("select");
            if(Objects.equals(StorageLibrary.quitButton.getText(), "Save & Quit")){
                StorageLibrary.quitButton.setText("Saving...");
                StorageLibrary.logger.info("Saving...");

                if(Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")){
                    SaveService.save1();
                }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")){
                    SaveService.save2();
                }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")){
                    SaveService.save3();
                }

                ThreadManager.exitThread.start();
            }else if(Objects.equals(StorageLibrary.quitButton.getText(), "Quit")){
                StorageLibrary.quitButton.setText("Quitting...");
                StorageLibrary.logger.info("Quitting...");
                ThreadManager.exitThread.start();
            }else if(Objects.equals(StorageLibrary.quitButton.getText(), "Return To Main Menu")){
                StorageLibrary.logger.info("Returning To Main Menu...");
                StorageLibrary.quitButton.setText("Saving...");
                StorageLibrary.logger.info("Saving...");

                ATimer timer = new ATimer();
                timer.setDelay(2);
                timer.setTask(() -> {
                    if(Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")){
                        SaveService.save1();
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")){
                        SaveService.save2();
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")){
                        SaveService.save3();
                    }

                    MenuManager.closeMenu("Pause");
                    MenuManager.openMenu("Main");

                    StorageLibrary.startGameButton.setBounds(177,150,305,102);
                    StorageLibrary.settingsButton.setBounds(177,348,305,102);

                    StorageLibrary.quitButton.setBounds(177,447,305,102);
                    StorageLibrary.quitButton.setFont(new Font("Nunito", Font.BOLD, 30));
                    StorageLibrary.quitButton.setText("Save & Quit");

                    AudioUtility.SoundManager.getSMusicAudioPlayer().stop();

                    Random random = new Random();
                    int number = random.nextInt(1,3);

                    if(number==1){
                        AudioUtility.Music.playMusic("menu1");
                    }else if(number==2){
                        AudioUtility.Music.playMusic("menu2");
                    }

                    StorageLibrary.logger.info("Done!");
                });
                timer.start();
            }
        }

        if(source==StorageLibrary.backToButton) {
            if (Objects.equals(StorageLibrary.backToButton.getText(), "Back To Main Menu")) {
                StorageLibrary.logger.info("Returning To Main Menu...");

                MenuManager.closeMenu("Save Manager");
                MenuManager.closeMenu("Settings");
                MenuManager.openMenu("Main");

                StorageLibrary.titleText.setFont(new Font("Nunito", Font.BOLD, 70));
                StorageLibrary.titleText.setText("Button Clicker");
                StorageLibrary.backToButton.setVisible(false);

                if(!Objects.equals(StorageLibrary.currentSave, "null")){
                    StorageLibrary.startGameButton.setBackground(Color.GREEN);
                    StorageLibrary.startGameButton.setFont(new Font("Nunito", Font.BOLD, 27));
                    StorageLibrary.quitButton.setText("Save & Quit");

                    if(Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")){
                        if(StorageLibrary.playedBefore1){
                            StorageLibrary.startGameButton.setText("Resume Game");
                        }else if(!StorageLibrary.playedBefore1){
                            StorageLibrary.startGameButton.setText("Start Game");
                        }
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")){
                        if(StorageLibrary.playedBefore2){
                            StorageLibrary.startGameButton.setText("Resume Game");
                        }else if(!StorageLibrary.playedBefore2){
                            StorageLibrary.startGameButton.setText("Start Game");
                        }
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")){
                        if(StorageLibrary.playedBefore3){
                            StorageLibrary.startGameButton.setText("Resume Game");
                        }else if(!StorageLibrary.playedBefore3){
                            StorageLibrary.startGameButton.setText("Start Game");
                        }
                    }
                }else if(StorageLibrary.currentSave.equals("null")){
                    StorageLibrary.startGameButton.setText("Select A Save To Continue");
                    StorageLibrary.startGameButton.setBackground(Color.GRAY);
                    StorageLibrary.startGameButton.setFont(new Font("Nunito", Font.BOLD, 21));
                    StorageLibrary.quitButton.setText("Quit");
                }

                if(Objects.equals(StorageLibrary.currentSave, "null")){
                    StorageLibrary.currentSaveText.setText("Current Save: none");
                }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")){
                    StorageLibrary.currentSaveText.setText("Current Save: "+StorageLibrary.save1Name);
                }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")){
                    StorageLibrary.currentSaveText.setText("Current Save: "+StorageLibrary.save2Name);
                }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")){
                    StorageLibrary.currentSaveText.setText("Current Save: "+StorageLibrary.save3Name);
                }

                StorageLibrary.logger.info("Done!");
            } else if (Objects.equals(StorageLibrary.backToButton.getText(), "Back To Save Manager")) {
                StorageLibrary.logger.info("Returning To Save Manager...");

                MenuManager.closeMenu("Save Info");
                MenuManager.closeMenu("Choose A Side");
                MenuManager.openMenu("Save Manager");

                StorageLibrary.backToButton.setText("Back To Main Menu");
                StorageLibrary.titleText.setText("Save Manager");

                StorageLibrary.forwardButton.setVisible(false);
                StorageLibrary.backwardButton.setVisible(false);

                StorageLibrary.saveBeingCreated = 0;

                StorageLibrary.logger.info("Loading Saves...");
                LoadService.loadSave1();
                LoadService.loadSave2();
                LoadService.loadSave3();

                StorageLibrary.logger.info("Done!");
            } else if (Objects.equals(StorageLibrary.backToButton.getText(), "Create Save")) {
                StorageLibrary.titleText.setFont(new Font("Nunito", Font.BOLD, 70));

                if (RenderUtility.isTextFieldEmpty(StorageLibrary.saveNameField)) {
                    Thread thread = ThreadManager.notAllowedSaveNames();
                    thread.start();

                    if(StorageLibrary.saveBeingCreated==1){
                        SaveService.Wipe(1);
                    }else if(StorageLibrary.saveBeingCreated==2){
                        SaveService.Wipe(2);
                    }else if(StorageLibrary.saveBeingCreated==3){
                        SaveService.Wipe(3);
                    }

                    StorageLibrary.saveBeingCreated = 0;
                }else if(Objects.equals(StorageLibrary.saveNameField.getText(), "null")){
                    Thread thread = ThreadManager.notAllowedSaveNames();
                    thread.start();

                    if(StorageLibrary.saveBeingCreated==1){
                        SaveService.Wipe(1);
                    }else if(StorageLibrary.saveBeingCreated==2){
                        SaveService.Wipe(2);
                    }else if(StorageLibrary.saveBeingCreated==3){
                        SaveService.Wipe(3);
                    }

                    StorageLibrary.saveBeingCreated = 0;
                } else {
                    StorageLibrary.logger.info("Creating Save...");

                    if (StorageLibrary.saveBeingCreated == 1) {
                        SaveService.createSave("Saves/save1.bcs");

                        StorageLibrary.clicks1 = 0;
                        StorageLibrary.clickPower1 = 1;
                        StorageLibrary.save1Name = StorageLibrary.saveNameField.getText();
                        StorageLibrary.playedBefore1 = false;

                        SaveService.save1();

                        StorageLibrary.saveBeingCreated = 0;
                    }

                    if (StorageLibrary.saveBeingCreated == 2) {
                        SaveService.createSave("Saves/save2.bcs");

                        StorageLibrary.clicks2 = 0;
                        StorageLibrary.clickPower2 = 1;
                        StorageLibrary.save2Name = StorageLibrary.saveNameField.getText();
                        StorageLibrary.playedBefore2 = false;

                        SaveService.save2();

                        StorageLibrary.saveBeingCreated = 0;
                    }

                    if (StorageLibrary.saveBeingCreated == 3) {
                        SaveService.createSave("Saves/save3.bcs");

                        StorageLibrary.clicks3 = 0;
                        StorageLibrary.clickPower3 = 1;
                        StorageLibrary.save3Name = StorageLibrary.saveNameField.getText();
                        StorageLibrary.playedBefore3 = false;

                        SaveService.save3();

                        StorageLibrary.saveBeingCreated = 0;
                    }

                    StorageLibrary.logger.info("Done!");
                }

                StorageLibrary.saveNameField.setText("");

                StorageLibrary.logger.info("Returning To Save Manager...");

                MenuManager.closeMenu("Enter A Save Name");
                MenuManager.openMenu("Save Manager");

                StorageLibrary.logger.info("Loading Saves...");
                LoadService.loadSave1();
                LoadService.loadSave2();
                LoadService.loadSave3();

                StorageLibrary.backToButton.setText("Back To Main Menu");
                StorageLibrary.titleText.setText("Save Manager");

                StorageLibrary.logger.info("Done!");
            }else if(Objects.equals(StorageLibrary.backToButton.getText(), "Back To Settings")) {
                StorageLibrary.logger.info("Returning To Settings Menu...");

                MenuManager.closeMenu("Wiki");
                MenuManager.closeMenu("Credits");
                MenuManager.closeMenu("Resource Packs");
                MenuManager.openMenu("Settings");

                StorageLibrary.titleText.setVisible(true);
                StorageLibrary.titleImage.setVisible(true);

                StorageLibrary.titleText.setText("Settings");
                StorageLibrary.titleText.setFont(new Font("Nunito", Font.BOLD, 80));

                if(StorageLibrary.StupidBooleanIHaveToMakeForTheBackToButtonTextToSetProperlyWhenYouOpenAnyOfTheMenusInTheSettingsMenuSinceIDontHaveAnythingToHookOntoToMakeItSetToBackToPauseMenu){
                    StorageLibrary.backToButton.setText("Back To Pause Menu");
                }else{
                    StorageLibrary.backToButton.setText("Back To Main Menu");
                }
                StorageLibrary.backToButton.setBounds(-10, 600, 667, 38);

                RenderUtility.resetWindow();

                if(AudioUtility.SoundManager.getMusicAudioPlayer().getStatus()==MediaPlayer.Status.PAUSED){
                    AudioUtility.Music.resumeMusic();
                }

                StorageLibrary.logger.info("Done!");
            }else if(Objects.equals(StorageLibrary.backToButton.getText(), "Back To Pause Menu")){
                StorageLibrary.logger.info("Returning To Pause Menu...");

                MenuManager.closeMenu("Settings");
                MenuManager.openMenu("Pause");

                StorageLibrary.titleText.setText("Button Clicker");
                StorageLibrary.titleText.setFont(new Font("Nunito", Font.BOLD, 70));

                StorageLibrary.backToButton.setVisible(false);
                StorageLibrary.StupidBooleanIHaveToMakeForTheBackToButtonTextToSetProperlyWhenYouOpenAnyOfTheMenusInTheSettingsMenuSinceIDontHaveAnythingToHookOntoToMakeItSetToBackToPauseMenu = false;

                StorageLibrary.logger.info("Done!");
            }else if(Objects.equals(StorageLibrary.backToButton.getText(), "Back To Game")){
                StorageLibrary.logger.info("Returning To Game...");

                MenuManager.closeMenu("Shop");
                MenuManager.openMenu("Game");

                StorageLibrary.forwardButton.setVisible(false);
                StorageLibrary.backwardButton.setVisible(false);
                StorageLibrary.backToButton.setVisible(false);

                StorageLibrary.clicksField.setBounds(0, 0, 647, 50);
                StorageLibrary.clickPowerField.setBounds(0, 50, 647, 50);

                StorageLibrary.titleText.setVisible(false);

                AudioUtility.SoundManager.getSMusicAudioPlayer().stop();

                if(AudioUtility.Music.playNextSong){
                    AudioUtility.Music.playMusic(AudioUtility.Music.nextSong);

                    AudioUtility.Music.playNextSong = false;
                    AudioUtility.Music.nextSong = "brothisshitissobadillfixitinthenextupdate";
                }else{
                    AudioUtility.Music.resumeMusic();
                }

                StorageLibrary.logger.info("Done!");
            }
        }

        if(source==StorageLibrary.save1Button){
            AudioUtility.SFX.playSFX("select");

            if(Objects.equals(StorageLibrary.save1Name, "null")){
                MenuManager.closeMenu("Save Manager");
                MenuManager.openMenu("Choose A Side");

                StorageLibrary.titleText.setText("Choose A Side");
                StorageLibrary.backToButton.setText("Back To Save Manager");

                StorageLibrary.saveBeingCreated = 1;
            }else if(!Objects.equals(StorageLibrary.save1Name, "null")){
                StorageLibrary.save1Button.setText("Selected!");

                JSONUtility.writeAStringToJsonFile("Data/settings.json", "current_save", "Saves/save1.bcs");
                StorageLibrary.currentSave = "Saves/save1.bcs";

                StorageLibrary.logger.info("Current Save: "+StorageLibrary.currentSave);

                ATimer timer = new ATimer();
                timer.setDelay(2);
                timer.setTask(() -> {
                    StorageLibrary.save1Button.setText(StorageLibrary.save1Name);
                });
                timer.start();
            }
        }

        if(source==StorageLibrary.save2Button){
            AudioUtility.SFX.playSFX("select");

            if(Objects.equals(StorageLibrary.save2Name, "null")){
                MenuManager.closeMenu("Save Manager");
                MenuManager.openMenu("Choose A Side");

                StorageLibrary.titleText.setText("Choose A Side");
                StorageLibrary.backToButton.setText("Back To Save Manager");

                StorageLibrary.saveBeingCreated = 2;
            }else if(!Objects.equals(StorageLibrary.save2Name, "null")){
                StorageLibrary.save2Button.setText("Selected!");

                JSONUtility.writeAStringToJsonFile("Data/settings.json", "current_save", "Saves/save2.bcs");
                StorageLibrary.currentSave = "Saves/save2.bcs";

                StorageLibrary.logger.info("Current Save: "+StorageLibrary.currentSave);

                ATimer timer = new ATimer();
                timer.setDelay(2);
                timer.setTask(() -> {
                    StorageLibrary.save2Button.setText(StorageLibrary.save2Name);
                });
                timer.start();
            }
        }

        if(source==StorageLibrary.save3Button){
            AudioUtility.SFX.playSFX("select");

            if(Objects.equals(StorageLibrary.save3Name, "null")){
                MenuManager.closeMenu("Save Manager");
                MenuManager.openMenu("Choose A Side");

                StorageLibrary.titleText.setText("Choose A Side");
                StorageLibrary.backToButton.setText("Back To Save Manager");

                StorageLibrary.saveBeingCreated = 3;
            }else if(!Objects.equals(StorageLibrary.save3Name, "null")){
                StorageLibrary.save3Button.setText("Selected!");

                JSONUtility.writeAStringToJsonFile("Data/settings.json", "current_save", "Saves/save3.bcs");
                StorageLibrary.currentSave = "Saves/save3.bcs";

                StorageLibrary.logger.info("Current Save: "+StorageLibrary.currentSave);

                ATimer timer = new ATimer();
                timer.setDelay(2);
                timer.setTask(() -> {
                    StorageLibrary.save3Button.setText(StorageLibrary.save3Name);
                });
                timer.start();
            }
        }

        if(source==StorageLibrary.lightSideButton){
            AudioUtility.SFX.playSFX("select");

            MenuManager.closeMenu("Choose A Side");
            MenuManager.openMenu("Enter A Save Name");

            StorageLibrary.backToButton.setText("Create Save");
            StorageLibrary.titleText.setText("Enter a Save Name");
            StorageLibrary.titleText.setFont(new Font("Nunito",Font.BOLD,55));

            RenderUtility.textFieldLimit(StorageLibrary.saveNameField, 10);

            if(StorageLibrary.saveBeingCreated==1){
                StorageLibrary.side1 = 1;
            }else if(StorageLibrary.saveBeingCreated==2){
                StorageLibrary.side2 = 1;
            }else if(StorageLibrary.saveBeingCreated==3){
                StorageLibrary.side3 = 1;
            }
        }

        if(source==StorageLibrary.darkSideButton){
            AudioUtility.SFX.playSFX("select");

            MenuManager.closeMenu("Choose A Side");
            MenuManager.openMenu("Enter A Save Name");

            StorageLibrary.backToButton.setText("Create Save");
            StorageLibrary.titleText.setText("Enter a Save Name");
            StorageLibrary.titleText.setFont(new Font("Nunito",Font.BOLD,55));

            RenderUtility.textFieldLimit(StorageLibrary.saveNameField, 10);

            if(StorageLibrary.saveBeingCreated==1){
                StorageLibrary.side1 = 2;
            }else if(StorageLibrary.saveBeingCreated==2){
                StorageLibrary.side2 = 2;
            }else if(StorageLibrary.saveBeingCreated==3){
                StorageLibrary.side3 = 2;
            }
        }

        if(source==StorageLibrary.magicSideButton){
            AudioUtility.SFX.playSFX("select");

            MenuManager.closeMenu("Choose A Side");
            MenuManager.openMenu("Enter A Save Name");

            StorageLibrary.backToButton.setText("Create Save");
            StorageLibrary.titleText.setText("Enter a Save Name");
            StorageLibrary.titleText.setFont(new Font("Nunito",Font.BOLD,55));

            RenderUtility.textFieldLimit(StorageLibrary.saveNameField, 10);

            if(StorageLibrary.saveBeingCreated==1){
                StorageLibrary.side1 = 3;
            }else if(StorageLibrary.saveBeingCreated==2){
                StorageLibrary.side2 = 3;
            }else if(StorageLibrary.saveBeingCreated==3){
                StorageLibrary.side3 = 3;
            }
        }

        if(source==StorageLibrary.neutralSideButton){
            AudioUtility.SFX.playSFX("select");

            MenuManager.closeMenu("Choose A Side");
            MenuManager.openMenu("Enter A Save Name");

            StorageLibrary.backToButton.setText("Create Save");
            StorageLibrary.titleText.setText("Enter a Save Name");
            StorageLibrary.titleText.setFont(new Font("Nunito",Font.BOLD,55));

            RenderUtility.textFieldLimit(StorageLibrary.saveNameField, 10);

            if(StorageLibrary.saveBeingCreated==1){
                StorageLibrary.side1 = 4;
            }else if(StorageLibrary.saveBeingCreated==2){
                StorageLibrary.side2 = 4;
            }else if(StorageLibrary.saveBeingCreated==3){
                StorageLibrary.side3 = 4;
            }
        }

        if(source==StorageLibrary.deleteSave1Button){
            AudioUtility.SFX.playSFX("select");
            if(!(Objects.equals(StorageLibrary.save1Name, "null"))){
                int userChoice1 = MessageUtility.DeleteMessage();
                if(userChoice1 == 0){
                    int userChoice2 = MessageUtility.DeleteMessage2();
                    if(userChoice2 == 0){
                        if(Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")){
                            JSONUtility.writeAStringToJsonFile("Data/settings.json", "current_save", "null");
                            StorageLibrary.currentSave = "null";

                            SaveService.Wipe(1);
                            SaveService.deleteSave("Saves/save1.bcs");
                        }else{
                            SaveService.Wipe(1);
                            SaveService.deleteSave("Saves/save1.bcs");
                        }

                        StorageLibrary.logger.info("Current Save: "+StorageLibrary.currentSave);

                        LoadService.loadSave1();
                        LoadService.loadSave2();
                        LoadService.loadSave3();
                    }
                }
            }else if(Objects.equals(StorageLibrary.save1Name, "null")){
                ThreadManager.alreadyDeletedMessageThread().start();
            }
        }

        if(source==StorageLibrary.deleteSave2Button){
            AudioUtility.SFX.playSFX("select");
            if(!(Objects.equals(StorageLibrary.save2Name, "null"))){
                int userChoice1 = MessageUtility.DeleteMessage();
                if(userChoice1 == 0){
                    int userChoice2 = MessageUtility.DeleteMessage2();
                    if(userChoice2 == 0){
                        if(Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")){
                            JSONUtility.writeAStringToJsonFile("Data/settings.json", "current_save", "null");
                            StorageLibrary.currentSave = "null";

                            SaveService.Wipe(2);
                            SaveService.deleteSave("Saves/save2.bcs");
                        }else{
                            SaveService.Wipe(2);
                            SaveService.deleteSave("Saves/save2.bcs");
                        }

                        StorageLibrary.logger.info("Current Save: "+StorageLibrary.currentSave);

                        LoadService.loadSave1();
                        LoadService.loadSave2();
                        LoadService.loadSave3();
                    }
                }
            }else if(Objects.equals(StorageLibrary.save2Name, "null")){
                ThreadManager.alreadyDeletedMessageThread().start();
            }
        }

        if(source==StorageLibrary.deleteSave3Button){
            AudioUtility.SFX.playSFX("select");
            if(!(Objects.equals(StorageLibrary.save3Name, "null"))){
                int userChoice1 = MessageUtility.DeleteMessage();
                if(userChoice1 == 0){
                    int userChoice2 = MessageUtility.DeleteMessage2();
                    if(userChoice2 == 0){
                        if(Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")){
                            JSONUtility.writeAStringToJsonFile("Data/settings.json", "current_save", "null");
                            StorageLibrary.currentSave = "null";

                            SaveService.Wipe(3);
                            SaveService.deleteSave("Saves/save3.bcs");
                        }else{
                            SaveService.Wipe(3);
                            SaveService.deleteSave("Saves/save3.bcs");
                        }

                        StorageLibrary.logger.info("Current Save: "+StorageLibrary.currentSave);

                        LoadService.loadSave1();
                        LoadService.loadSave2();
                        LoadService.loadSave3();
                    }
                }
            }else if(Objects.equals(StorageLibrary.save3Name, "null")){
                ThreadManager.alreadyDeletedMessageThread().start();
            }
        }

        if(source==StorageLibrary.settingsButton){
            StorageLibrary.logger.info("Opening Settings...");

            if(MenuManager.isMenuOpen("Main")){
                StorageLibrary.backToButton.setText("Back To Main Menu");
            }else if(MenuManager.isMenuOpen("Pause")){
                StorageLibrary.backToButton.setText("Back To Pause Menu");
                StorageLibrary.StupidBooleanIHaveToMakeForTheBackToButtonTextToSetProperlyWhenYouOpenAnyOfTheMenusInTheSettingsMenuSinceIDontHaveAnythingToHookOntoToMakeItSetToBackToPauseMenu = true;
            }

            AudioUtility.SFX.playSFX("select");

            MenuManager.closeMenu("Main");
            MenuManager.openMenu("Settings");

            StorageLibrary.backToButton.setVisible(true);

            StorageLibrary.titleText.setText("Settings");
            StorageLibrary.titleText.setFont(new Font("Nunito",Font.BOLD,77));

            StorageLibrary.logger.info("Done!");
        }

        if(source==StorageLibrary.bugReportButton){
            StorageLibrary.logger.info("Opening Github...");
            AudioUtility.SFX.playSFX("select");

            try {
                Desktop.getDesktop().browse(new URI("https://google.com"));
            } catch (IOException ex) {
                ErrorHandler.IOException("INPUT", 1016);
            } catch (URISyntaxException ex) {
                ErrorHandler.URISyntaxException("INPUT", 1018);
            }

            StorageLibrary.logger.info("Done!");
        }

        if(source==StorageLibrary.debugButton){
            AudioUtility.SFX.playSFX("select");

            Thread thread = ThreadManager.trollMessageThread();
            thread.start();
        }

        if(source==StorageLibrary.infoButton){
            StorageLibrary.logger.info("Opening Save Info Menu...");
            AudioUtility.SFX.playSFX("select");

            MenuManager.closeMenu("Save Manager");
            MenuManager.openMenu("Save Info");

            StorageLibrary.titleText.setText("Save Info");

            if(StorageLibrary.side1 == 0){
                StorageLibrary.saveInfoText.setText("\n\n\n        Save 1 Isn't Created!");
            }else if(StorageLibrary.side1 == 1){
                if(StorageLibrary.playedBefore1){
                    StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Light\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: Yes");
                }else if(!StorageLibrary.playedBefore1){
                    StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Light\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: No");
                }
            }else if(StorageLibrary.side1 == 2){
                if(StorageLibrary.playedBefore1){
                    StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Dark\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: Yes");
                }else if(!StorageLibrary.playedBefore1){
                    StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Dark\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: No");
                }
            }else if(StorageLibrary.side1 == 3){
                if(StorageLibrary.playedBefore1){
                    StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Magic\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: Yes");
                }else if(!StorageLibrary.playedBefore1){
                    StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Magic\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: No");
                }
            }else if(StorageLibrary.side1 == 4) {
                if(StorageLibrary.playedBefore1){
                    StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Neutral\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: Yes");
                }else if(!StorageLibrary.playedBefore1){
                    StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Neutral\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: No");
                }
            }

            StorageLibrary.saveInfoText.setBounds(50, 160, 550, 400);

            StorageLibrary.backToButton.setText("Back To Save Manager");

            StorageLibrary.logger.info("Done!");
        }

        if(source==StorageLibrary.wikiHelpButton){
            AudioUtility.SFX.playSFX("select");

            StorageLibrary.wikiEditorPane.setText("<html>\n" +
                    "<body>\n" +
                    "\t<p>the cake is a lie</p>\n" +
                    "<p>the cake is a lie</p>\n" +
                    "<p>the cake is a lie</p>\n" +
                    "<p>the cake is a lie</p>\n" +
                    "</body>\n" +
                    "</html>");
            StorageLibrary.wikiEditorPane.setForeground(Color.BLACK);
            StorageLibrary.wikiEditorPane.setBackground(new Color(181, 181, 181));
            StorageLibrary.wikiEditorPane.setFont(new Font("Indie Flower", Font.PLAIN, 20));
            StorageLibrary.wikiEditorPane.setMargin(new Insets(180,285,0,0));
            StorageLibrary.wikiEditorPane.setBackgroundImageEnabled(true);
            StorageLibrary.wikiEditorPane.setFocusable(false);

            StorageLibrary.wikiHelpButton.setVisible(false);

            AudioUtility.Music.pauseMusic();

            StorageLibrary.logger.info("the cake is a lie");
        }

        if(source==StorageLibrary.backwardButton){
            if(MenuManager.isMenuOpen("Save Info")){
                if(StorageLibrary.saveInfoText.getText().contains("Save 2")){
                    if(StorageLibrary.side1 == 0){
                        StorageLibrary.saveInfoText.setText("\n\n\n        Save 1 Isn't Created!");
                    }else if(StorageLibrary.side1 == 1){
                        if(StorageLibrary.playedBefore1){
                            StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Light\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore1){
                            StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Light\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side1 == 2){
                        if(StorageLibrary.playedBefore1){
                            StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Dark\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore1){
                            StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Dark\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side1 == 3){
                        if(StorageLibrary.playedBefore1){
                            StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Magic\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore1){
                            StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Magic\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side1 == 4) {
                        if(StorageLibrary.playedBefore1){
                            StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Neutral\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore1){
                            StorageLibrary.saveInfoText.setText("                     Save 1\n\nClicks: "+StorageLibrary.clicks1+"\nClick Power: "+StorageLibrary.clickPower1+"\nSide: Neutral\nSave Name: "+StorageLibrary.save1Name+"\nPlayed Before: No");
                        }
                    }

                    StorageLibrary.backwardButton.setVisible(false);
                }else if(StorageLibrary.saveInfoText.getText().contains("Save 3")){
                    if(StorageLibrary.side2 == 0){
                        StorageLibrary.saveInfoText.setText("\n\n\n        Save 2 Isn't Created!");
                    }else if(StorageLibrary.side2 == 1){
                        if(StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Light\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Light\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side2 == 2){
                        if(StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Dark\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Dark\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side2 == 3){
                        if(StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Magic\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Magic\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side2 == 4){
                        if(StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Neutral\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Neutral\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: No");
                        }
                    }
                }else if(StorageLibrary.saveInfoText.getText().contains("Current Save")){
                    if (StorageLibrary.side3 == 0) {
                        StorageLibrary.saveInfoText.setText("\n\n\n        Save 3 Isn't Created!");
                    }else if(StorageLibrary.side3 == 1){
                        if(StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Light\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Light\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side3 == 2){
                        if(StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Dark\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Dark\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side3 == 3){
                        if(StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Magic\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Magic\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side3 == 4){
                        if(StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Neutral\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Neutral\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: No");
                        }
                    }

                    StorageLibrary.saveInfoText.setBounds(50, 160, 550, 400);
                    StorageLibrary.forwardButton.setVisible(true);
                }
            }
        }

        if(source==StorageLibrary.forwardButton){
            if(MenuManager.isMenuOpen("Save Info")){
                if(StorageLibrary.saveInfoText.getText().contains("Save 1")) {
                    if(StorageLibrary.side2 == 0){
                        StorageLibrary.saveInfoText.setText("\n\n\n        Save 2 Isn't Created!");
                    }else if(StorageLibrary.side2 == 1){
                        if(StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Light\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Light\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side2 == 2){
                        if(StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Dark\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Dark\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side2 == 3){
                        if(StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Magic\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Magic\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side2 == 4){
                        if(StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Neutral\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore2){
                            StorageLibrary.saveInfoText.setText("                     Save 2\n\nClicks: "+StorageLibrary.clicks2+"\nClick Power: "+StorageLibrary.clickPower2+"\nSide: Neutral\nSave Name: "+StorageLibrary.save2Name+"\nPlayed Before: No");
                        }
                    }

                    StorageLibrary.backwardButton.setVisible(true);
                }else if(StorageLibrary.saveInfoText.getText().contains("Save 2")){
                    if (StorageLibrary.side3 == 0) {
                        StorageLibrary.saveInfoText.setText("\n\n\n        Save 3 Isn't Created!");
                    }else if(StorageLibrary.side3 == 1){
                        if(StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Light\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Light\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side3 == 2){
                        if(StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Dark\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Dark\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side3 == 3){
                        if(StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Magic\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Magic\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: No");
                        }
                    }else if(StorageLibrary.side3 == 4){
                        if(StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Neutral\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: Yes");
                        }else if(!StorageLibrary.playedBefore3){
                            StorageLibrary.saveInfoText.setText("                     Save 3\n\nClicks: "+StorageLibrary.clicks3+"\nClick Power: "+StorageLibrary.clickPower3+"\nSide: Neutral\nSave Name: "+StorageLibrary.save3Name+"\nPlayed Before: No");
                        }
                    }
                }else if(StorageLibrary.saveInfoText.getText().contains("Save 3")){
                    if(Objects.equals(StorageLibrary.currentSave, "null")){
                        StorageLibrary.saveInfoText.setText("Current Save:\nnone");
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")){
                        StorageLibrary.saveInfoText.setText("Current Save:\n"+StorageLibrary.save1Name);
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")){
                        StorageLibrary.saveInfoText.setText("Current Save:\n"+StorageLibrary.save2Name);
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")){
                        StorageLibrary.saveInfoText.setText("Current Save:\n"+StorageLibrary.save3Name);
                    }

                    StorageLibrary.saveInfoText.setBounds(180, 290, 550, 350);
                    StorageLibrary.forwardButton.setVisible(false);
                }
            }
        }

        if(source==StorageLibrary.nextTipButton){
            Random random1 = new Random();
            int number1 = random1.nextInt(1,4);

            if(number1==1){
                StorageLibrary.tipsText.setText("Button Clicker was originally a lot more simplistic!");
            }else if(number1==2){
                StorageLibrary.tipsText.setText("Button Clicker is out now for early access on itch.io!");
            }else if(number1==3){
                StorageLibrary.tipsText.setText("Rebirthing grants you easier\nprogression next time round!");
            }

            StorageLibrary.nextTipButton.setEnabled(false);
        }

        if(source==StorageLibrary.shopButton){
            StorageLibrary.logger.info("Opening Shop...");
            AudioUtility.SFX.playSFX("select");

            MenuManager.closeMenu("Game");
            MenuManager.openMenu("Shop");

            StorageLibrary.shopItem1.setPrice("100 Clicks");
            StorageLibrary.shopItem1.setIcon(StorageLibrary.clickPowerIcon);
            StorageLibrary.shopItem1.setDescription("Gives 1 more Click Power");

            StorageLibrary.shopItem2.setPrice("1000 Clicks");
            StorageLibrary.shopItem2.setIcon(StorageLibrary.clickPowerIcon);
            StorageLibrary.shopItem2.setDescription("Gives 10 more Click Power");

            StorageLibrary.shopItem3.setPrice("10000 Clicks");
            StorageLibrary.shopItem3.setIcon(StorageLibrary.clickPowerIcon);
            StorageLibrary.shopItem3.setDescription("Gives 100 more Click Power");

            StorageLibrary.shopItem4.setPrice("100000 Clicks");
            StorageLibrary.shopItem4.setIcon(StorageLibrary.clickPowerIcon);
            StorageLibrary.shopItem4.setDescription("Gives 1000 more Click Power");

            StorageLibrary.shopItem5.setPrice("1 Million Clicks");
            StorageLibrary.shopItem5.setIcon(StorageLibrary.clickPowerIcon);
            StorageLibrary.shopItem5.setDescription("Gives 10000 more Click Power");

            StorageLibrary.shopItem6.setPrice("10 Million Clicks");
            StorageLibrary.shopItem6.setIcon(StorageLibrary.clickPowerIcon);
            StorageLibrary.shopItem6.setDescription("Gives 100000 more Click Power");

            StorageLibrary.forwardButton.setVisible(true);
            StorageLibrary.backwardButton.setVisible(false);

            StorageLibrary.backToButton.setVisible(true);
            StorageLibrary.backToButton.setText("Back To Game");

            StorageLibrary.clicksField.setVisible(true);
            StorageLibrary.clicksField.setBounds(0, 54, 647, 50);

            StorageLibrary.clickPowerField.setVisible(true);
            StorageLibrary.clickPowerField.setBounds(0, 104, 647, 50);

            StorageLibrary.titleText.setVisible(true);
            StorageLibrary.titleText.setText("Shop");
            StorageLibrary.titleText.setBounds(100, -2, 460, 55);
            StorageLibrary.titleText.setFont(new Font("Nunito",Font.BOLD,50));

            StorageLibrary.titleImage.stopSpinning();
            StorageLibrary.titleImage.setBounds(10, 0, 100, 100);
            StorageLibrary.titleImage.setVisible(false);

            StorageLibrary.autoSavingText.setVisible(false);

            AudioUtility.Music.pauseMusic();
            AudioUtility.Music.playMusic("shop");

            StorageLibrary.logger.info("Done!");
        }

        if(source==StorageLibrary.pauseButton){
            StorageLibrary.logger.info("Pausing...");
            AudioUtility.SFX.playSFX("select");

            MenuManager.closeMenu("Game");
            MenuManager.openMenu("Pause");

            StorageLibrary.titleText.setText("Button Clicker");
            StorageLibrary.titleText.setBounds(100, 20, 570, 80);
            StorageLibrary.titleText.setFont(new Font("Nunito",Font.BOLD,70));
            StorageLibrary.titleText.setVisible(true);

            StorageLibrary.titleImage.stopSpinning();
            StorageLibrary.titleImage.setBounds(10,0,100,100);
            StorageLibrary.titleImage.setVisible(true);

            StorageLibrary.startGameButton.setBounds(177,199,305,102);
            StorageLibrary.startGameButton.setText("Resume Game");

            StorageLibrary.settingsButton.setBounds(177,298,305,102);

            StorageLibrary.quitButton.setText("Return To Main Menu");
            StorageLibrary.quitButton.setFont(new Font("Nunito", Font.BOLD, 29));
            StorageLibrary.quitButton.setBounds(177,397,305,102);

            StorageLibrary.autoSavingText.setVisible(false);

            AudioUtility.Music.pauseMusic();
            AudioUtility.Music.playMusic("pause");

            StorageLibrary.logger.info("Done!");
        }
    };

    public static class keyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

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
            if(e.getSource()==StorageLibrary.buttonTop){
                if(e.getButton()==MouseEvent.BUTTON1){
                    StorageLibrary.buttonTop.setLocation(30, 35);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                if(MenuManager.isMenuOpen("Main")){
                    StorageLibrary.debugButton.setVisible(true);
                    ErrorHandler.URISyntaxException("INPUT", 1427);
                }
            }

            if(e.getSource()==StorageLibrary.buttonTop) {
                if(e.getButton()==MouseEvent.BUTTON1){
                    StorageLibrary.buttonTop.setLocation(30, 30);

                    if(Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")){
                        StorageLibrary.clicks1 += StorageLibrary.clickPower1;
                        StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks1);
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")){
                        StorageLibrary.clicks2 += StorageLibrary.clickPower2;
                        StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks2);
                    }else if(Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")){
                        StorageLibrary.clicks3 += StorageLibrary.clickPower3;
                        StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks3);
                    }

                    AudioUtility.SFX.playSFX("click");
                }
            }

            if(e.getSource()==StorageLibrary.shopItem1){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(Objects.equals(StorageLibrary.shopItem1.getDescription(), "Gives 1 more Click Power")){
                        if (Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")) {
                            if(StorageLibrary.clicks1<100){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem1.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem1.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem1.setPrice("100 Clicks");
                                    StorageLibrary.shopItem1.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks1>=100){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks1 = StorageLibrary.clicks1 - 100;
                                StorageLibrary.clickPower1 = StorageLibrary.clickPower1 + 1;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks1);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower1);
                            }
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")) {
                            if(StorageLibrary.clicks2<100){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem1.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem1.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem1.setPrice("100 Clicks");
                                    StorageLibrary.shopItem1.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks2>=100){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks2 = StorageLibrary.clicks2 - 100;
                                StorageLibrary.clickPower2 = StorageLibrary.clickPower2 + 1;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks2);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower2);
                            }
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")) {
                            if(StorageLibrary.clicks3<100){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem1.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem1.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem1.setPrice("100 Clicks");
                                    StorageLibrary.shopItem1.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks3>=100){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks3 = StorageLibrary.clicks3 - 100;
                                StorageLibrary.clickPower3 = StorageLibrary.clickPower3 + 1;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks3);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower3);
                            }
                        }
                    }
                }
            }

            if(e.getSource()==StorageLibrary.shopItem2){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(Objects.equals(StorageLibrary.shopItem2.getDescription(), "Gives 10 more Click Power")){
                        if (Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")) {
                            if(StorageLibrary.clicks1<1000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem2.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem2.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem2.setPrice("1000 Clicks");
                                    StorageLibrary.shopItem2.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks1>=1000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks1 = StorageLibrary.clicks1 - 1000;
                                StorageLibrary.clickPower1 = StorageLibrary.clickPower1 + 10;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks1);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower1);
                            }
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")) {
                            if(StorageLibrary.clicks2<1000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem2.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem2.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem2.setPrice("1000 Clicks");
                                    StorageLibrary.shopItem2.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks2>=1000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks2 = StorageLibrary.clicks2 - 1000;
                                StorageLibrary.clickPower2 = StorageLibrary.clickPower2 + 10;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks2);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower2);
                            }
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")) {
                            if(StorageLibrary.clicks3<1000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem2.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem2.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem2.setPrice("1000 Clicks");
                                    StorageLibrary.shopItem2.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks3>=1000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks3 = StorageLibrary.clicks3 - 1000;
                                StorageLibrary.clickPower3 = StorageLibrary.clickPower3 + 10;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks3);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower3);
                            }
                        }
                    }
                }
            }

            if(e.getSource()==StorageLibrary.shopItem3){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(Objects.equals(StorageLibrary.shopItem3.getDescription(), "Gives 100 more Click Power")){
                        if (Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")) {
                            if(StorageLibrary.clicks1<10000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem3.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem3.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem3.setPrice("10000 Clicks");
                                    StorageLibrary.shopItem3.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks1>=10000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks1 = StorageLibrary.clicks1 - 10000;
                                StorageLibrary.clickPower1 = StorageLibrary.clickPower1 + 100;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks1);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower1);
                            }
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")) {
                            if(StorageLibrary.clicks2<10000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem3.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem3.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem3.setPrice("10000 Clicks");
                                    StorageLibrary.shopItem3.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks2>=10000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks2 = StorageLibrary.clicks2 - 10000;
                                StorageLibrary.clickPower2 = StorageLibrary.clickPower2 + 100;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks2);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower2);
                            }
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")) {
                            if(StorageLibrary.clicks3<10000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem3.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem3.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem3.setPrice("10000 Clicks");
                                    StorageLibrary.shopItem3.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks3>=10000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks3 = StorageLibrary.clicks3 - 10000;
                                StorageLibrary.clickPower3 = StorageLibrary.clickPower3 + 100;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks3);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower3);
                            }
                        }
                    }
                }
            }

            if(e.getSource()==StorageLibrary.shopItem4){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(Objects.equals(StorageLibrary.shopItem4.getDescription(), "Gives 1000 more Click Power")){
                        if (Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")) {
                            if(StorageLibrary.clicks1<100000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem4.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem4.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem4.setPrice("100000 Clicks");
                                    StorageLibrary.shopItem4.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks1>=100000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks1 = StorageLibrary.clicks1 - 100000;
                                StorageLibrary.clickPower1 = StorageLibrary.clickPower1 + 1000;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks1);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower1);
                            }
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")) {
                            if(StorageLibrary.clicks2<100000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem4.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem4.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem4.setPrice("100000 Clicks");
                                    StorageLibrary.shopItem4.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks2>=100000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks2 = StorageLibrary.clicks2 - 100000;
                                StorageLibrary.clickPower2 = StorageLibrary.clickPower2 + 1000;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks2);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower2);
                            }
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")) {
                            if(StorageLibrary.clicks3<100000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem4.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem4.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem4.setPrice("100000 Clicks");
                                    StorageLibrary.shopItem4.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks3>=100000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks3 = StorageLibrary.clicks3 - 100000;
                                StorageLibrary.clickPower3 = StorageLibrary.clickPower3 + 1000;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks3);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower3);
                            }
                        }
                    }
                }
            }

            if(e.getSource()==StorageLibrary.shopItem5){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(Objects.equals(StorageLibrary.shopItem5.getDescription(), "Gives 10000 more Click Power")){
                        if (Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")) {
                            if(StorageLibrary.clicks1<1000000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem5.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem5.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem5.setPrice("1 Million Clicks");
                                    StorageLibrary.shopItem5.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks1>=1000000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks1 = StorageLibrary.clicks1 - 1000000;
                                StorageLibrary.clickPower1 = StorageLibrary.clickPower1 + 10000;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks1);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower1);
                            }
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")) {
                            if(StorageLibrary.clicks2<1000000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem5.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem5.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem5.setPrice("1 Million Clicks");
                                    StorageLibrary.shopItem5.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks2>=1000000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks2 = StorageLibrary.clicks2 - 1000000;
                                StorageLibrary.clickPower2 = StorageLibrary.clickPower2 + 10000;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks2);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower2);
                            }
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")) {
                            if(StorageLibrary.clicks3<1000000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem5.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem5.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem5.setPrice("1 Million Clicks");
                                    StorageLibrary.shopItem5.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks3>=1000000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks3 = StorageLibrary.clicks3 - 1000000;
                                StorageLibrary.clickPower3 = StorageLibrary.clickPower3 + 10000;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks3);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower3);
                            }
                        }
                    }
                }
            }

            if(e.getSource()==StorageLibrary.shopItem6){
                if(e.getButton()==MouseEvent.BUTTON1){
                    if(Objects.equals(StorageLibrary.shopItem6.getDescription(), "Gives 100000 more Click Power")){
                        if (Objects.equals(StorageLibrary.currentSave, "Saves/save1.bcs")) {
                            if(StorageLibrary.clicks1<10000000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem6.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem6.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem5.setPrice("10 Million Clicks");
                                    StorageLibrary.shopItem5.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks1>=10000000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks1 = StorageLibrary.clicks1 - 10000000;
                                StorageLibrary.clickPower1 = StorageLibrary.clickPower1 + 100000;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks1);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower1);
                            }
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save2.bcs")) {
                            if(StorageLibrary.clicks2<10000000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem6.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem6.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem5.setPrice("10 Million Clicks");
                                    StorageLibrary.shopItem5.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks2>=10000000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks2 = StorageLibrary.clicks2 - 10000000;
                                StorageLibrary.clickPower2 = StorageLibrary.clickPower2 + 100000;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks2);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower2);
                            }
                        } else if (Objects.equals(StorageLibrary.currentSave, "Saves/save3.bcs")) {
                            if(StorageLibrary.clicks3<10000000){
                                AudioUtility.SFX.playSFX("declined");
                                StorageLibrary.shopItem6.setPrice("Not Enough Clicks");
                                StorageLibrary.shopItem6.setDisabled(true);

                                ATimer timer = new ATimer();
                                timer.setDelay(2);
                                timer.setTask(() -> {
                                    StorageLibrary.shopItem5.setPrice("10 Million Clicks");
                                    StorageLibrary.shopItem5.setDisabled(false);
                                });
                                timer.start();
                            }else if(StorageLibrary.clicks3>=10000000){
                                AudioUtility.SFX.playSFX("purchase");

                                StorageLibrary.clicks3 = StorageLibrary.clicks3 - 10000000;
                                StorageLibrary.clickPower3 = StorageLibrary.clickPower3 + 100000;

                                StorageLibrary.clicksField.setText("Clicks: "+StorageLibrary.clicks3);
                                StorageLibrary.clickPowerField.setText("Click Power: "+StorageLibrary.clickPower3);
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