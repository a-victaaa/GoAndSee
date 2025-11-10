package com.charaquest;
import javax.swing.*;

/**
 * CharaQuest.java
 * Main class to run the CharaQuest 20 questions guessing game.
 * Initializes the game and launches the GUI interface.
 */
public class CharaQuest {
    
    public static void main(String[] args) {
        // Set the look and feel to the system default
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
    
    /**
     * Create and display the main game GUI */
    private static void createAndShowGUI() {
        try {
            // Create the game logic instance
            GuessingGame game = new GuessingGame();
            
            // Create and display the game interface
            GameInterface gameInterface = new GameInterface(game);
            
            // Make the frame visible
            gameInterface.setVisible(true);
            
            System.out.println("CharaQuest game started successfully!");
            System.out.println("Think of a team member and I'll try to guess who it is!");
            
        } catch (Exception e) {
            System.err.println("Error starting the game: " + e.getMessage());
            e.printStackTrace();
            
            // Show error dialog to user
            JOptionPane.showMessageDialog(
                null,
                "Failed to start CharaQuest game:\n" + e.getMessage(),
                "Startup Error",
                JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
        }
    }
    
    /**
     * Method to restart the game (can be called from GameInterface)
     */
    public static void restartGame() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Close existing windows
                for (Window window : Window.getWindows()) {
                    window.dispose();
                }
                
                // Create new game instance
                createAndShowGUI();
            }
        });
    }
    
    /**
     * Method to exit the game gracefully
     */
    public static void exitGame() {
        System.out.println("Thanks for playing CharaQuest!");
        System.exit(0);
    }
     /**
     * Get game version information
     */
    public static String getGameVersion() {
        return "CharaQuest v1.0 - Team Member Guessing Game";
    }
    
    /**
     * Get game description
     */
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