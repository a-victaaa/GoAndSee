package com.charaquest;
import javax.swing.*;
import java.awt.Window;

public class CharaQuest {
    
    public static void main(String[] args) {
    
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            System.out.println("Could not set system look and feel: " + e.getMessage());
        }
        
        // Create and run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        try {
            GuessingGame game = new GuessingGame();
            GameInterface gameInterface = new GameInterface(game);
            gameInterface.setVisible(true);
            System.out.println("CharaQuest game started successfully!");
            System.out.println("Think of a team member and I'll try to guess who it is!");
        } catch (Exception e) {
            System.err.println("Error starting the game: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                null,
                "Failed to start CharaQuest game:\n" + e.getMessage(),
                "Startup Error",
                JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }
    }

    public static void restartGame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        
                for (Window window : Window.getWindows()) {
                    window.dispose();
                }
                

                createAndShowGUI();
            }
        });
    }

    public static void exitGame() {
        System.out.println("Thanks for playing CharaQuest!");
        System.exit(0);
    }
    public static String getGameVersion() {
        return "CharaQuest v1.0 - Team Member Guessing Game";
    }
    
    public static String getGameDescription() {
        return "Think of a team member from our development team,\n" +
               "and I'll try to guess who it is by asking up to 20 questions!\n\n" +
               "Team members I can guess:\n" +
               "• Angelica \n" +
               "• Alleah Jane \n" +
               "• Michael Angelo \n" +
               "• Robert Lheon \n" +
               "• Tommy Lee";
    }
}
