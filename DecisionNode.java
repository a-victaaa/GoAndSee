package com.charaquest;

public class DecisionNode {
    private String question;          // The question to ask at this node
    private String character;         // The character to guess (null if this is a question node)
    private DecisionNode yesNode;     // Child node for "yes" answers
    private DecisionNode noNode;      // Child node for "no" answers

     public DecisionNode(String question) {
        this.question = question;
        this.character = null;
        this.yesNode = null;
        this.noNode = null;
    }
    
    /**
     * Constructor for character nodes (leaf nodes)
     */
    public DecisionNode(String character, boolean isCharacterNode) {
        this.character = character;
        this.question = null;
        this.yesNode = null;
        this.noNode = null;
    }
    
    // Getters
    public String getQuestion() {
        return question;
    }

     public String getCharacter() {
        return character;
    }
    
    public DecisionNode getYesNode() {
        return yesNode;
    }
    
    public DecisionNode getNoNode() {
        return noNode;
    }
    
    // Setters
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public void setCharacter(String character) {
        this.character = character;
    }
    
    public void setYesNode(DecisionNode yesNode) {
        this.yesNode = yesNode;
    }
    
    public void setNoNode(DecisionNode noNode) {
        this.noNode = noNode;
    }

     /**
     * Check if this is a leaf node (character guess)
     */
    public boolean isCharacterNode() {
        return character != null;
    }
    
    /**
     * Check if this is a question node
     */
    public boolean isQuestionNode() {
        return question != null;
    }
    
    /**
     * Check if this node has children
     */
    public boolean hasChildren() {
        return yesNode != null || noNode != null;
    }
    
    @Override
    public String toString() {
        if (isCharacterNode()) {
            return "Character: " + character;
        } else {
            return "Question: " + question;
        }
    }
}