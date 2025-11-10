package com.charaquest;

import java.util.*;

/**
 * GuessingGame.java
 * Manages the game state and logic for the 20 questions guessing game.
 * Handles tree traversal, game states, and answer processing.
 */

public class GuessingGame {
    
    // Game states
    public enum GameState {
        NOT_STARTED,    // Game hasn't begun
        PLAYING,        // Asking questions
        GUESSING,       // Made a character guess, waiting for confirmation
        WON,           // AI guessed correctly
        LOST,          // AI guessed incorrectly
        ERROR          // Something went wrong
    }
    
    private DecisionNode rootNode;           // Root of the decision tree
    private DecisionNode currentNode;        // Current position in tree
    private GameState gameState;             // Current game state
     private List<String> answerPath;         // History of answers (for debugging)
    private String currentGuess;             // Current character guess
    private int questionCount;               // Number of questions asked
    private static final int MAX_QUESTIONS = 20;
    
    /**
     * Constructor - initializes the game
     */
    public GuessingGame() {
        initializeDecisionTree();
        resetGame();
    }
    
    /**
     * Initialize the decision tree with questions and characters
     */
    private void initializeDecisionTree() {
        // Root node: Gender classification
        rootNode = new DecisionNode("Is the person female?");

        // --- FEMALE BRANCH (YES) ---
        DecisionNode femaleNode = new DecisionNode("Does the person have long hair?");
        rootNode.setYesNode(femaleNode);

        // Female -> Long Hair (YES -> YES)
        DecisionNode angelica = new DecisionNode("Angelica", true);
        femaleNode.setYesNode(angelica);

        // Female -> Short Hair (YES -> NO)
        DecisionNode alleahJane = new DecisionNode("Alleah Jane", true);
        femaleNode.setNoNode(alleahJane);

        // --- MALE BRANCH (NO) ---
        DecisionNode maleNode = new DecisionNode("Is the person's height above 5'7\"?");
        rootNode.setNoNode(maleNode);

        // Male -> Height above 5'7" (NO -> YES)
        DecisionNode tallMaleNode = new DecisionNode("Is the person a class officer?");
        maleNode.setYesNode(tallMaleNode);

        // Tall Male -> Not Officer (NO -> YES -> NO)
        DecisionNode michaelAngelo = new DecisionNode("Michael Angelo", true);
        tallMaleNode.setNoNode(michaelAngelo);

        // Tall Male -> Officer (NO -> YES -> YES)
        DecisionNode robertLheon = new DecisionNode("Robert Lheon", true);
        tallMaleNode.setYesNode(robertLheon);

        // Male -> Height below 5'7" (NO -> NO)
        DecisionNode tommyLee = new DecisionNode("Tommy Lee", true);
        maleNode.setNoNode(tommyLee);
    }
    
    /**
     * Start a new game
     */
    public void startNewGame() {
        resetGame();
        gameState = GameState.PLAYING;
    }
    
    /**
     * Reset game to initial state
     */
    private void resetGame() {
        currentNode = rootNode;
        gameState = GameState.NOT_STARTED;
        answerPath = new ArrayList<>();
        currentGuess = null;
        questionCount = 0;
    }
    
    /**
     * Process a "Yes" answer
     */
    public boolean answerYes() {
        return processAnswer(true);
    }

    /**
     * Process a "No" answer
     */
    public boolean answerNo() {
        return processAnswer(false);
    }
    
    /**
     * Process an answer and move to the next node
     */
    private boolean processAnswer(boolean isYes) {
        if (gameState != GameState.PLAYING) {
            return false;
        }
        
        // Record the answer
        answerPath.add(isYes ? "YES" : "NO");
        questionCount++;
        
        // Move to the next node
        currentNode = isYes ? currentNode.getYesNode() : currentNode.getNoNode();
        
        if (currentNode == null) {
            gameState = GameState.ERROR;
            return false;
        }
        
        // Check if we've reached a character (leaf node)
        if (currentNode.isCharacterNode()) {
            gameState = GameState.GUESSING;
        currentGuess = currentNode.getCharacter();
            return true;
        }
        
        // Check if we've asked too many questions
        if (questionCount >= MAX_QUESTIONS) {
            gameState = GameState.LOST;
            return true;
        }
        
        // Continue playing
        return true;
    }
    
    /**
     * Confirm that the guess was correct
     */
    public void confirmGuessCorrect() {
        if (gameState == GameState.GUESSING) {
            gameState = GameState.WON;
        }
    }
    
    /**
     * Confirm that the guess was incorrect
     */
    public void confirmGuessIncorrect() {
        if (gameState == GameState.GUESSING) {
            gameState = GameState.LOST;
        }
    }
         // Getters
    public String getCurrentQuestion() {
        if (currentNode != null && currentNode.isQuestionNode()) {
            return currentNode.getQuestion();
        }
        return null;
    }
    
    public String getCurrentGuess() {
        return currentGuess;
    }
    
    public GameState getGameState() {
        return gameState;
    }
    
    public int getQuestionCount() {
        return questionCount;
    }
    
    public int getMaxQuestions() {
        return MAX_QUESTIONS;
    }
    
    public List<String> getAnswerPath() {
        return new ArrayList<>(answerPath);
    }
    
    public String getAnswerPathString() {
        return String.join(" -> ", answerPath);
    }

     /**
     * Get a status message based on current game state
     */
    public String getStatusMessage() {
        switch (gameState) {
            case NOT_STARTED:
                return "Click 'Start Game' to begin!";
            case PLAYING:
                return "Question " + (questionCount + 1) + " of " + MAX_QUESTIONS;
            case GUESSING:
                return "Is my guess correct?";
            case WON:
                return "I won! I guessed your team member in " + questionCount + " questions!";
            case LOST:
                if (questionCount >= MAX_QUESTIONS) {
                    return "You stumped me! I ran out of questions.";
                } else {
                    return "You got me! I couldn't guess your team member.";
                }
            case ERROR:
                return "Oops! Something went wrong. Please start a new game.";
            default:
                return "Unknown game state";
        }
    }
    
    /**
     * Check if the game is over
     */
    public boolean isGameOver() {
        return gameState == GameState.WON || 
               gameState == GameState.LOST || 
               gameState == GameState.ERROR;
    }
    
    /**
     * Check if we're currently asking questions
     */
    public boolean isPlaying() {
        return gameState == GameState.PLAYING;
    }
    
    /**
     * Check if we're waiting for guess confirmation
     */
    public boolean isGuessing() {
        return gameState == GameState.GUESSING;
    }
    
    /**
     * Get remaining questions
     */
    public int getRemainingQuestions() {
        return Math.max(0, MAX_QUESTIONS - questionCount);
    }
    
    /**
     * Debug method to print current state */
    public void printDebugInfo() {
        System.out.println("=== GAME DEBUG INFO ===");
        System.out.println("Game State: " + gameState);
        System.out.println("Question Count: " + questionCount);
        System.out.println("Current Node: " + (currentNode != null ? currentNode.toString() : "null"));
        System.out.println("Answer Path: " + getAnswerPathString());
        System.out.println("Current Guess: " + currentGuess);
        System.out.println("======================");
    }
}