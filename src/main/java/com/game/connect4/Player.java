package com.game.connect4;

public enum Player
{
    RED('R', 1, "[RED]"), GREEN('G', 2, "[GREEN]");

    private char symbol;
    private int number;
    private String name;

    Player(char symbol, int number, String name){
        this.symbol = symbol;
        this.number = number;
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public char getSymbol(){
        return symbol;
    }
}
