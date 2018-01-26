package com.game.connect4;

import java.util.Arrays;

public class ConnectFour {
    //constants
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;
    private static final int WIN_THRESHOLD = 3;
    private static final char EMPTY_SLOT = '0';

    //game state fields
    private boolean gameOver;
    private boolean drawGame;
    private int totalMovesCount;
    private Player winner = null;
    private Player currentPlayer = null;
    private char[][] board = new char[ROWS][COLUMNS];

    public ConnectFour() {
        init();
    }

    private void init() {
        for (char[] rows : this.board) {
            Arrays.fill(rows, EMPTY_SLOT);
        }
        this.gameOver = Boolean.FALSE;
        this.totalMovesCount = 0;
        this.winner = null;
        this.currentPlayer = null;
    }

    public void resetGame() {
        System.out.println("Game state has been reset");
        init();
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isDrawGame() {
        return drawGame;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void printBoard() {
        System.out.println("Board looks now like this:");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void play(int chosenColumn, Player player) {
        if (gameOver) {
            throw new IllegalStateException("game is over and no more moves are allowed");
        }

        if (!isChosenColumnWithinBoundaries(chosenColumn)) {
            throw new IllegalArgumentException("you must choose a column in the (0-6) interval");
        }

        if (currentPlayer == player) {
            throw new IllegalStateException(player + " cannot make 2 or more moves in a row. Players must take alternate turns");
        }

        if(board[0][chosenColumn] != EMPTY_SLOT){
            printBoard();
            throw new IllegalArgumentException("This column is already full. Pick another one");
        }

        int currentRow = 0;
        while (currentRow < ROWS - 1 && board[currentRow + 1][chosenColumn] == EMPTY_SLOT) {
            currentRow++;
        }

        board[currentRow][chosenColumn] = player.getSymbol();
        totalMovesCount++;
        currentPlayer = player;

        if (isBoardFull()) {
            gameOver = Boolean.TRUE;
            drawGame = Boolean.TRUE;
            printBoard();
            System.out.println("Game over. Board is full, game ended in a draw");
        }

        if (isWinner(currentRow, chosenColumn, player)) {
            printBoard();
            this.winner = player;
            gameOver = Boolean.TRUE;
            System.out.println(player + " wins. Game Over !");
        }
    }

    private boolean isChosenColumnWithinBoundaries(int chosenColumn) {
        return chosenColumn >= 0 && chosenColumn < COLUMNS;
    }

    private boolean isBoardFull() {
        return totalMovesCount >= COLUMNS * ROWS;
    }

    private boolean isRightToLeftDiagonalWin(int row, int column, Player player) {
        int currentRow = row;
        int currentColumn = column;
        int symbolCount = 1;

        while (currentRow > 1 && currentColumn < COLUMNS - 1 &&
                board[currentRow - 1][currentColumn + 1] == player.getSymbol()) { //count right
            currentRow--;
            currentColumn++;
            symbolCount++;
        }

        currentColumn = column;
        currentRow = row;

        while (currentRow < ROWS - 1 && currentColumn > 1 &&
                board[currentRow + 1][currentColumn - 1] == player.getSymbol()) { //count left
            currentRow++;
            currentColumn--;
            symbolCount++;
        }

        if (symbolCount > WIN_THRESHOLD) {
            System.out.println(String.format("Right to Left Diagonal Win for last position at row=%d and column=%d", row, column));
            return true;
        } else {
            return false;
        }
    }

    private boolean isLeftToRightDiagonalWin(int row, int column, Player player) {
        int currentRow = row;
        int currentColumn = column;
        int symbolCount = 1;

        while (currentRow < ROWS - 1 && currentColumn < COLUMNS - 1 &&
                board[currentRow + 1][currentColumn + 1] == player.getSymbol()) { //count right
            currentRow++;
            currentColumn++;
            symbolCount++;
        }

        currentColumn = column;
        currentRow = row;

        while (currentRow > 1 && currentColumn > 1 &&
                board[currentRow - 1][currentColumn - 1] == player.getSymbol()) { //count left
            currentRow--;
            currentColumn--;
            symbolCount++;
        }

        if (symbolCount > WIN_THRESHOLD) {
            System.out.println(String.format("Left to Right Diagonal Win for last position at row=%d and column=%d", row, column));
            return true;
        } else {
            return false;
        }
    }

    private boolean isVerticalWin(int row, int column, Player player) {
        int currentRow = row;
        int symbolCount = 1;

        while (currentRow < ROWS - 1 &&
                board[currentRow + 1][column] == player.getSymbol()) { //count down
            currentRow++;
            symbolCount++;
        }

        currentRow = row;

        while (currentRow > 1 &&
                board[currentRow - 1][column] == player.getSymbol()) { //count up
            currentRow--;
            symbolCount++;
        }

        if (symbolCount > WIN_THRESHOLD) {
            System.out.println(String.format("Vertical Win for last position at row=%d and column=%d", row, column));
            return true;
        } else {
            return false;
        }
    }

    private boolean isHorizontalWin(int row, int column, Player player) {
        int currentColumn = column;
        int symbolCount = 1;

        while (currentColumn < COLUMNS - 1 &&
                board[row][currentColumn + 1] == player.getSymbol()) { //count right
            currentColumn++;
            symbolCount++;
        }

        currentColumn = column;

        while (currentColumn > 1 &&
                board[row][currentColumn - 1] == player.getSymbol()) { //count left
            currentColumn--;
            symbolCount++;
        }

        if (symbolCount > WIN_THRESHOLD) {
            System.out.println(String.format("Horizontal Win for last position at row=%d and column=%d", row, column));
            return true;
        } else {
            return false;
        }
    }

    private boolean isWinner(int currentRow, int chosenColumn, Player player) {
        return isHorizontalWin(currentRow, chosenColumn, player) ||
                isVerticalWin(currentRow, chosenColumn, player) ||
                isLeftToRightDiagonalWin(currentRow, chosenColumn, player) ||
                isRightToLeftDiagonalWin(currentRow, chosenColumn, player);
    }
}
