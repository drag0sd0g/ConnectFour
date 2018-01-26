package com.game.connect4;

import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            ConnectFour connectFour = new ConnectFour();
            int moves = 0;
            while (!connectFour.isGameOver()) {
                Player currentPlayer = moves % 2 == 0 ? Player.RED : Player.GREEN;
                System.out.println(currentPlayer + " Player's Turn. Insert column number (between 0-6)");
                int chosenColumn = in.nextInt();
                try {
                    connectFour.play(chosenColumn, currentPlayer);
                    connectFour.printBoard();
                    moves++;
                } catch (IllegalStateException | IllegalArgumentException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        } finally {
            in.close();
        }
    }
}
