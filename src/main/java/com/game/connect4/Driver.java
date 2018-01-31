package com.game.connect4;

import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try {
            ConnectFour connectFour = new ConnectFour();
            int moves = 0;
            connectFour.printBoard();
            while (!connectFour.isGameOver()) {
                Player currentPlayer = moves % 2 == 0 ? Player.RED : Player.GREEN;
                System.out.print("Player "+ currentPlayer.getNumber()+ " "+currentPlayer.getName()+" - choose column (1-7):");
                int chosenColumn = in.nextInt();
                try {
                    connectFour.play(chosenColumn - 1, currentPlayer);
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
