package com.game.connect4;

public enum Player
{
    RED('R'), GREEN('G');

    private char symbol;

    Player(char symbol){
        this.symbol = symbol;
    }

    public char getSymbol(){
        return symbol;
    }
}
