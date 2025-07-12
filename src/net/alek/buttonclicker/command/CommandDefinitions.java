package net.alek.buttonclicker.command;

import net.alek.buttonclicker.services.LoggingService;
import net.alek.buttonclicker.ui.RenderService;

import java.awt.*;
import java.util.Objects;

public class CommandDefinitions {

    public static void help() {
        StringBuilder sb = new StringBuilder();

        if (!RenderService.console.getText().isEmpty()) {
            sb.append("\n\n");
        }

        sb.append("                                  HELP\n");
        sb.append("***************************************************************************\n\n");

        sb.append("Main Commands:\n\n");

        sb.append("  help   - Shows a list of available commands\n");
        sb.append("           Arguments: none\n");
        sb.append("           Syntax   : help\n\n");

        sb.append("  echo   - Says a specified message in the console\n");
        sb.append("           Arguments: String message, String messageType, boolean toLog\n");
        sb.append("           Syntax   : echo Button Clicker error true\n");
        sb.append("                      echo Button Clicker info false\n\n");

        sb.append("  clear  - Clears the console\n");
        sb.append("           Arguments: none\n");
        sb.append("           Syntax   : clear\n\n");

        sb.append("Alternative Commands:\n\n");

        sb.append("  hlp    - Alias for help\n");
        sb.append("           Arguments: none\n");
        sb.append("           Syntax   : hlp\n\n");

        sb.append("  h      - Alias for help\n");
        sb.append("           Arguments: none\n");
        sb.append("           Syntax   : h\n\n");

        sb.append("  say    - Alias for echo\n");
        sb.append("           Arguments: String message, String messageType, boolean toLog\n");
        sb.append("           Syntax   : say Button Clicker error true\n");
        sb.append("                      say Button Clicker info false\n\n");

        sb.append("  clr    - Alias for clear\n");
        sb.append("           Arguments: none\n");
        sb.append("           Syntax   : clr\n\n");

        sb.append("           NOTE: Commands/Arguments are CASE SENSITIVE\n");
        sb.append("***************************************************************************\n");

        RenderService.console.setText(RenderService.console.getText() + sb.toString());
        RenderService.console.setLineColor(
                RenderService.console.getLineFromText("           NOTE: Commands/Arguments are CASE SENSITIVE"),
                Color.YELLOW
        );
    }

    public static void hlp(){
        help();
    }

    public static void h(){
        help();
    }

    public static void echo(String message, String messageType, boolean toLog){
        if(!toLog){
            if(!RenderService.console.getText().isEmpty()){
                RenderService.console.setText(RenderService.console.getText()+"\n"+message);

                if(Objects.equals(messageType, "error")){
                    RenderService.console.setLineColor(RenderService.console.getLineFromText(message), Color.RED);
                }
            }else{
                RenderService.console.setText(message);

                if(Objects.equals(messageType, "error")){
                    RenderService.console.setLineColor(RenderService.console.getLineFromText(message), Color.RED);
                }
            }
        }else{
            if(Objects.equals(messageType, "error")){
                LoggingService.Logger.error(message);
            }else if(Objects.equals(messageType, "info")){
                LoggingService.Logger.info(message);
            }
        }
    }

    public static void say(String message, String messageType, boolean toLog){
        echo(message, messageType, toLog);
    }

    public static void clear(){
        RenderService.console.setText("");

        RenderService.console.setLineColor(3, Color.WHITE);
        RenderService.console.setLineColor(4, Color.WHITE);
    }

    public static void clr(){
        clear();
    }

    public static void test(){
        LoggingService.Logger.info("\n\n                                     HELP\n***************************************************************************\n\nMain Commands:\n\n      help - Shows a list of available commands, arguments needed for the commands, and syntax\n            Arguments: none\n            Syntax: help\n\n      echo - Says a specified message in the console\n            Arguments: String message, String messageType, boolean toLog\n            Syntax: echo Button Clicker error true OR echo Button Clicker info false\n\n      clear - Clears the console\n             Arguments: none\n             Syntax: clear\n\nAlternative Commands:\n\n      hlp - Routes back to help\n           Arguments: none\n           Syntax: hlp\n\n      h - Routes back to help\n         Arguments: none\n         Syntax: h\n\n      say - Routes back to echo\n           Arguments: String message, String messageType, boolean toLog\n           Syntax: say Button Clicker error true OR say Button Clicker info false\n\n      clr - Routes back to clear\n           Arguments: none\n           Syntax: clr\n\n\n                   NOTE: Commands/Arguments are CASE SENSITIVE\n\n***************************************************************************\n");
    }
}
