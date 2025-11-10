package com.charaquest;

public class DecisionNode {
    private String question;          
    private String character;         
    private DecisionNode yesNode;     
    private DecisionNode noNode;      

     public DecisionNode(String question) {
        this.question = question;
        this.character = null;
        this.yesNode = null;
        this.noNode = null;
    }
    
    public DecisionNode(String character, boolean isCharacterNode) {
        this.character = character;
        this.question = null;
        this.yesNode = null;
        this.noNode = null;
    }
    
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

    public boolean isCharacterNode() {
        return character != null;
    }

    public boolean isQuestionNode() {
        return question != null;
    }
    
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
