package com.charaquest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInterface extends JFrame {
    
    private GuessingGame game;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    // Welcome Screen
    private JPanel welcomePanel;
    private JButton startButton;
    
    // Game Screen
    private JPanel gamePanel;
    private JLabel questionLabel;
    private JLabel statusLabel;
    private JButton yesButton;
    private JButton noButton;
    private JButton confirmCorrectButton;
    private JButton confirmWrongButton;
    private JTextArea answerHistoryArea;
    
    // Result Screen
    private JPanel resultPanel;
    private JLabel resultLabel;
    private JButton playAgainButton;
    private JButton exitButton;
    
    public GameInterface(GuessingGame game) {
        this.game = game;
        initializeGUI();
        setupEventHandlers();
        showWelcomeScreen();
    }
    
    private void initializeGUI() {
        setTitle("CharaQuest - Team Member Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        createWelcomeScreen();
        createGameScreen();
        createResultScreen();
        
        add(mainPanel);
    }
    
    private void createWelcomeScreen() {
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(new Color(240, 248, 255));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JLabel titleLabel = new JLabel("CharaQuest");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descriptionLabel = new JLabel("<html><div style='text-align: center;'>" +
                "Think of a team member and I'll try to guess who it is!<br><br>" +
                "Team members: Angelica, Alleah Jane, Michael Angelo, Robert Lheon, Tommy Lee" +
                "</div></html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setPreferredSize(new Dimension(150, 40));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setBackground(new Color(34, 139, 34));
        startButton.setForeground(Color.WHITE);
        
        welcomePanel.add(Box.createVerticalGlue());
        welcomePanel.add(titleLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        welcomePanel.add(descriptionLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        welcomePanel.add(startButton);
        welcomePanel.add(Box.createVerticalGlue());
        
        mainPanel.add(welcomePanel, "WELCOME");
    }
    
    private void createGameScreen() {
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel topPanel = new JPanel();
        statusLabel = new JLabel("Question 1 of 20");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        topPanel.add(statusLabel);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        questionLabel = new JLabel("Think of a team member and click Yes/No!");
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        yesButton = new JButton("Yes");
        noButton = new JButton("No");
        confirmCorrectButton = new JButton("Correct!");
        confirmWrongButton = new JButton("Wrong");
        
        yesButton.setPreferredSize(new Dimension(100, 40));
        noButton.setPreferredSize(new Dimension(100, 40));
        confirmCorrectButton.setPreferredSize(new Dimension(100, 40));
        confirmWrongButton.setPreferredSize(new Dimension(100, 40));
        
        yesButton.setBackground(new Color(34, 139, 34));
        yesButton.setForeground(Color.WHITE);
        noButton.setBackground(new Color(220, 20, 60));
        noButton.setForeground(Color.WHITE);
        confirmCorrectButton.setBackground(new Color(34, 139, 34));
        confirmCorrectButton.setForeground(Color.WHITE);
        confirmWrongButton.setBackground(new Color(220, 20, 60));
        confirmWrongButton.setForeground(Color.WHITE);
        
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.add(confirmCorrectButton);
        buttonPanel.add(confirmWrongButton);
        
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(questionLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalGlue());
        
        answerHistoryArea = new JTextArea(6, 40);
        answerHistoryArea.setEditable(false);
        answerHistoryArea.setFont(new Font("Arial", Font.PLAIN, 12));
        answerHistoryArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(answerHistoryArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Answer History"));
        
        gamePanel.add(topPanel, BorderLayout.NORTH);
        gamePanel.add(centerPanel, BorderLayout.CENTER);
        gamePanel.add(scrollPane, BorderLayout.SOUTH);
        
        mainPanel.add(gamePanel, "GAME");
    }
    
    private void createResultScreen() {
        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBackground(new Color(240, 248, 255));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        resultLabel = new JLabel("Game Over");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        playAgainButton = new JButton("Play Again");
        exitButton = new JButton("Exit");
        
        playAgainButton.setPreferredSize(new Dimension(120, 40));
        exitButton.setPreferredSize(new Dimension(120, 40));
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);
        
        resultPanel.add(Box.createVerticalGlue());
        resultPanel.add(resultLabel);
        resultPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        resultPanel.add(buttonPanel);
        resultPanel.add(Box.createVerticalGlue());
        
        mainPanel.add(resultPanel, "RESULT");
    }
    
    private void setupEventHandlers() {
        startButton.addActionListener(e -> startGame());
        yesButton.addActionListener(e -> answerYes());
        noButton.addActionListener(e -> answerNo());
        confirmCorrectButton.addActionListener(e -> confirmCorrect());
        confirmWrongButton.addActionListener(e -> confirmWrong());
        playAgainButton.addActionListener(e -> restartGame());
        exitButton.addActionListener(e -> exitGame());
    }
    
    private void startGame() {
        game.startNewGame();
        updateGameDisplay();
        showGameScreen();
    }
    
    private void answerYes() {
        game.answerYes();
        updateGameDisplay();
    }
    
    private void answerNo() {
        game.answerNo();
        updateGameDisplay();
    }
    
    private void confirmCorrect() {
        game.confirmGuessCorrect();
        showResultScreen();
    }
    
    private void confirmWrong() {
        game.confirmGuessIncorrected();
        showResultScreen();
    }
    
    private void restartGame() {
        game = new GuessingGame();
        showWelcomeScreen();
    }
    
    private void exitGame() {
        System.exit(0);
    }
    
    private void updateGameDisplay() {
        statusLabel.setText(game.getStatusMessage());
        
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
        } else if (game.isGameOver()) {
            showResultScreen();
            return;
        }
        
        updateAnswerHistory();
    }
    
    private void updateAnswerHistory() {
        StringBuilder history = new StringBuilder();
        history.append("Questions Asked: ").append(game.getQuestionCount()).append("\n");
        history.append("Remaining: ").append(game.getRemainingQuestions()).append("\n\n");
        
        if (!game.getAnswerPath().isEmpty()) {
            history.append("Your Answers: ");
            history.append(game.getAnswerPathString());
        }
        
        answerHistoryArea.setText(history.toString());
    }
    
    private void showWelcomeScreen() {
        cardLayout.show(mainPanel, "WELCOME");
    }
    
    private void showGameScreen() {
        cardLayout.show(mainPanel, "GAME");
    }
    
    private void showResultScreen() {
        if (game.getGameState() == GuessingGame.GameState.WON) {
            resultLabel.setText("<html><div style='text-align: center;'>" +
                    "<h2>🎉 I Won! 🎉</h2>" +
                    "<p>I guessed your team member in " + game.getQuestionCount() + " questions!</p>" +
                    "<p>It was: " + game.getCurrentGuess() + "</p></div></html>");
        } else {
            resultLabel.setText("<html><div style='text-align: center;'>" +
                    "<h2>😅 You Got Me! 😅</h2>" +
                    "<p>I couldn't guess your team member.</p></div></html>");
        }
        
        cardLayout.show(mainPanel, "RESULT");
    }
}
