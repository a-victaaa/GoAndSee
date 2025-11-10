package com.charaquest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GameInterface.java
 * The Graphical User Interface (GUI) for the CharaQuest game using CardLayout.
 */
public class GameInterface extends JFrame implements ActionListener {

    private GuessingGame game;

    // UI Components for the main layout
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel; // Panel that holds WELCOME, GAME, and RESULT cards

    // Components for GAME card
    private JLabel statusLabel;
    private JLabel questionLabel;
    private JButton yesButton;
    private JButton noButton;
    private JButton confirmCorrectButton;
    private JButton confirmWrongButton;
    private JTextArea answerHistoryArea;

    // Components for WELCOME/RESULT cards
    private JLabel resultLabel; // Used for the result screen message
    private JButton startButton;

    /**
     * Constructor sets up the main window and components.
     */
    public GameInterface(GuessingGame game) {
        this.game = game;

        setTitle(CharaQuest.getGameVersion());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(setupMenuBar());

        mainPanel = new JPanel(cardLayout);

        // 1. Setup the Welcome Screen Card
        mainPanel.add(createWelcomePanel(), "WELCOME");
        // 2. Setup the Game Screen Card
        mainPanel.add(createGamePanel(), "GAME");
        // 3. Setup the Result Screen Card
        mainPanel.add(createResultPanel(), "RESULT");

        add(mainPanel);

        // Final window setup
        pack();
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        showWelcomeScreen(); // Start on the welcome screen
    }
    
    // --- Panel Setup Methods ---

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel welcomeLabel = new JLabel("<html><h1 style='color: blue;'>CharaQuest</h1>" +
                                        "<p>Think of a team member. I'll guess who it is!</p>" +
                                        "<p>Click 'Start Game' to begin.</p></html>", SwingConstants.CENTER);
        startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        
        panel.add(welcomeLabel, BorderLayout.CENTER);
        
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.add(startButton);
        panel.add(buttonWrapper, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Top: Status
        statusLabel = new JLabel("Status: Initializing...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(statusLabel, BorderLayout.NORTH);

        // Center: Question and History
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Left - Question
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(BorderFactory.createTitledBorder("Question"));
        
        questionLabel = new JLabel("Question goes here.", SwingConstants.CENTER);
        questionLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        questionPanel.add(Box.createVerticalGlue());
        questionPanel.add(questionLabel);
        questionPanel.add(Box.createVerticalGlue());
        
        centerPanel.add(questionPanel);

        // Right - History
        answerHistoryArea = new JTextArea(10, 15);
        answerHistoryArea.setEditable(false);
        answerHistoryArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(answerHistoryArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Game History"));
        centerPanel.add(scrollPane);

        panel.add(centerPanel, BorderLayout.CENTER);

        // Bottom: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        yesButton = new JButton("Yes");
        noButton = new JButton("No");
        confirmCorrectButton = new JButton("Yes, that's correct!");
        confirmWrongButton = new JButton("No, you're wrong!");

        yesButton.addActionListener(this);
        noButton.addActionListener(this);
        confirmCorrectButton.addActionListener(this);
        confirmWrongButton.addActionListener(this);
        
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.add(confirmCorrectButton);
        buttonPanel.add(confirmWrongButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);

        panel.setPreferredSize(new Dimension(650, 400));
        return panel;
    }
    
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> restartGame());
        
        panel.add(resultLabel, BorderLayout.CENTER);
        
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.add(playAgainButton);
        panel.add(buttonWrapper, BorderLayout.SOUTH);

        return panel;
    }

    private JMenuBar setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        
        JMenuItem startItem = new JMenuItem("Start New Game");
        startItem.addActionListener(e -> restartGame());
        
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> exitGame());

        gameMenu.add(startItem);
        gameMenu.addSeparator();
        gameMenu.add(aboutItem);
        gameMenu.add(exitItem);
        
        menuBar.add(gameMenu);
        return menuBar;
    }

    // --- Action Handling ---
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            handleStartGame();
        } else if (e.getSource() == yesButton) {
            game.answerYes();
        } else if (e.getSource() == noButton) {
            game.answerNo();
        } else if (e.getSource() == confirmCorrectButton) {
            game.confirmGuessCorrect();
        } else if (e.getSource() == confirmWrongButton) {
            // Note: The original suggestion had confirmGuessIncorrected(), 
            // but the code in GuessingGame is confirmGuessIncorrect().
            game.confirmGuessIncorrect(); 
        }

        // Handle screen transitions
        if (game.getGameState() == GuessingGame.GameState.GUESSING) {
            updateGameDisplay();
        } else if (game.isGameOver()) {
            showResultScreen();
        } else {
            updateGameDisplay();
        }
    }
    
    // --- Game Logic Methods ---

    private void handleStartGame() {
        game.startNewGame();
        showGameScreen();
        updateGameDisplay();
    }
    
    private void restartGame() {
        // Since CharaQuest main method creates a new instance of GameInterface
        // and GuessingGame, we use the static restart method.
        // This is necessary to properly clean up the old window on the EDT.
        CharaQuest.restartGame(); 
    }
    
    private void exitGame() {
        System.exit(0);
    }

    // --- UI Update Methods ---
    
    private void updateGameDisplay() {
        statusLabel.setText(game.getStatusMessage());
        
        // Button visibility logic
        if (game.isPlaying()) {
            questionLabel.setText(game.getCurrentQuestion());
            yesButton.setVisible(true);
            noButton.setVisible(true);
            confirmCorrectButton.setVisible(false);
            confirmWrongButton.setVisible(false);
        } else if (game.isGuessing()) {
            questionLabel.setText("Is your team member " + game.getCurrentGuess() + "?");
            yesButton.setVisible(false);
            noButton.setVisible(false);
            confirmCorrectButton.setVisible(true);
            confirmWrongButton.setVisible(true);
        }
        
        updateAnswerHistory();
        pack(); // Re-pack to adjust size if text length changes
    }
    
    private void updateAnswerHistory() {
        StringBuilder history = new StringBuilder();
        history.append("Questions Asked: ").append(game.getQuestionCount()).append("\n");
        history.append("Remaining: ").append(game.getRemainingQuestions()).append("\n\n");
        
        if (!game.getAnswerPath().isEmpty()) {
            history.append("Your Answers: \n");
            // Display path line-by-line or with clear separators
            String path = String.join(" -> ", game.getAnswerPath());
            
            // Simple word-wrap attempt for history:
            int maxLineLength = 40; 
            while (path.length() > maxLineLength) {
                int lastSpace = path.substring(0, maxLineLength).lastIndexOf(' ');
                int splitPoint = (lastSpace > 0) ? lastSpace : maxLineLength;
                history.append(path.substring(0, splitPoint)).append("\n");
                path = path.substring(splitPoint).trim();
            }
            history.append(path);
        }
        
        answerHistoryArea.setText(history.toString());
    }

    private void showWelcomeScreen() {
        cardLayout.show(mainPanel, "WELCOME");
        pack();
    }
    
    private void showGameScreen() {
        cardLayout.show(mainPanel, "GAME");
        pack();
    }
    
    private void showResultScreen() {
        String message;
        if (game.getGameState() == GuessingGame.GameState.WON) {
            message = "<h2>🎉 I Won! 🎉</h2>" +
                      "<p>I guessed your team member in **" + game.getQuestionCount() + "** questions!</p>" +
                      "<p>It was: **" + game.getCurrentGuess() + "**</p>";
        } else {
            message = "<h2>😅 You Got Me! 😅</h2>" +
                      "<p>I couldn't guess your team member.</p>" +
                      "<p>Try thinking of one of the other members!</p>";
        }
        
        resultLabel.setText("<html><div style='text-align: center;'>" + message + "</div></html>");
        cardLayout.show(mainPanel, "RESULT");
        pack();
    }
    
    private void showAboutDialog() {
         JOptionPane.showMessageDialog(
            this,
            CharaQuest.getGameDescription(),
            CharaQuest.getGameVersion(),
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}