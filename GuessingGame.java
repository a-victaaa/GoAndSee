package com.charaquest;
import java.util.*;

public class GuessingGame {

    public enum GameState {
        NOT_STARTED,
        PLAYING,
        GUESSING, 
        WON, 
        LOST,
        ERROR  
    }

    private DecisionNode rootNode;
    private DecisionNode currentNode;
    private GameState gameState;
     private List<String> answerPath;
    private String currentGuess; 
    private int questionCount; 
    private static final int MAX_QUESTIONS = 20;

    public GuessingGame() {
        initializeDecisionTree();
        resetGame();
    }
    
    private void initializeDecisionTree() {
        rootNode = new DecisionNode("Is the person female?");

        DecisionNode femaleNode = new DecisionNode("Does the person have long hair?");
        rootNode.setYesNode(femaleNode);

        DecisionNode angelica = new DecisionNode("Angelica", true);
        femaleNode.setYesNode(angelica);
        DecisionNode alleahJane = new DecisionNode("Alleah Jane", true);
        femaleNode.setNoNode(alleahJane);

        DecisionNode maleNode = new DecisionNode("Is the person's height above 5'7?");
        rootNode.setNoNode(maleNode);
        DecisionNode tallMaleNode = new DecisionNode("Is the person a class officer?");
        maleNode.setYesNode(tallMaleNode);
        DecisionNode michaelAngelo = new DecisionNode("Michael Angelo", true);
        tallMaleNode.setNoNode(michaelAngelo);
        DecisionNode robertLheon = new DecisionNode("Robert Lheon", true);
        tallMaleNode.setYesNode(robertLheon);
        DecisionNode tommyLee = new DecisionNode("Tommy Lee", true);
        maleNode.setNoNode(tommyLee);
    }
    
    public void startNewGame() {
        resetGame();
        gameState = GameState.PLAYING;
    }
    
    private void resetGame() {
        currentNode = rootNode;
        gameState = GameState.NOT_STARTED;
        answerPath = new ArrayList<>();
        currentGuess = null;
        questionCount = 0;
    }

    public boolean answerYes() {
        return processAnswer(true);
    }

    public boolean answerNo() {
        return processAnswer(false);
    }
    
    private boolean processAnswer(boolean isYes) {
        if (gameState != GameState.PLAYING) {
            return false;
        }
        answerPath.add(isYes ? "YES" : "NO");
        questionCount++;
        currentNode = isYes ? currentNode.getYesNode() : currentNode.getNoNode();

        if (currentNode == null) {
            gameState = GameState.ERROR;
            return false;
        }
        if (currentNode.isCharacterNode()) {
            gameState = GameState.GUESSING;
        currentGuess = currentNode.getCharacter();
            return true;
        }
        if (questionCount >= MAX_QUESTIONS) {
            gameState = GameState.LOST;
            return true;
        }
        return true;
    }

    public void confirmGuessCorrect() {
        if (gameState == GameState.GUESSING) {
            gameState = GameState.WON;
        }
    }

    public void confirmGuessIncorrect() {
        if (gameState == GameState.GUESSING) {
            gameState = GameState.LOST;
        }
    }
    
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

    public boolean isGameOver() {
        return gameState == GameState.WON || 
               gameState == GameState.LOST || 
               gameState == GameState.ERROR;
    }

    public boolean isPlaying() {
        return gameState == GameState.PLAYING;
    }

    public boolean isGuessing() {
        return gameState == GameState.GUESSING;
    }

    public int getRemainingQuestions() {
        return Math.max(0, MAX_QUESTIONS - questionCount);
    }

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
