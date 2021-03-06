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
        for (int i = 0; i < ROWS; i++) {
            System.out.print("|");
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print((board[i][j] != EMPTY_SLOT ? board[i][j] : " ") + "|");
            }
            System.out.println();
        }
        System.out.println();
    }


    /**
     * This method is invoked alternatively by the RED and the GREEN players and it drops the disc at the desired column
     * on the board. This method will defend against being invoked after the game has ended (draw or with a winner),
     * against inserting a disc on an invalid or full column, against inserting in a full board and against attempting
     * to make two consecutive moves by the same player
     * @param chosenColumn chosen column number where to drop the disc
     * @param player the current player
     */

    public void play(int chosenColumn, Player player) {
        if (gameOver) {
            throw new IllegalStateException("\ngame is over and no more moves are allowed");
        }

        if (!isChosenColumnWithinBoundaries(chosenColumn)) {
            throw new IllegalArgumentException("\nyou must choose a column in the (1-7) interval");
        }

        if (currentPlayer == player) {
            throw new IllegalStateException("\n"+player + " cannot make 2 or more moves in a row. Players must take alternate turns");
        }

        if(board[0][chosenColumn] != EMPTY_SLOT){
            throw new IllegalArgumentException("\nThis column is already full. Pick another one");
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
        }

        printBoard();

        if (isWinner(currentRow, chosenColumn, player)) {
            this.winner = player;
            gameOver = Boolean.TRUE;
            System.out.println("Player "+ player.getNumber()+ " "+player.getName()+" wins!");
        }
    }

    private boolean isWinner(int currentRow, int chosenColumn, Player player) {
        return isHorizontalWin(currentRow, chosenColumn, player) ||
                isVerticalWin(currentRow, chosenColumn, player) ||
                isLeftToRightDiagonalWin(currentRow, chosenColumn, player) ||
                isRightToLeftDiagonalWin(currentRow, chosenColumn, player);
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

        while (currentRow < ROWS - 1 && currentColumn > 0 &&
                board[currentRow + 1][currentColumn - 1] == player.getSymbol()) { //count left
            currentRow++;
            currentColumn--;
            symbolCount++;
        }

        if (symbolCount > WIN_THRESHOLD) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
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

        while (currentRow > 1 && currentColumn > 0 &&
                board[currentRow - 1][currentColumn - 1] == player.getSymbol()) { //count left
            currentRow--;
            currentColumn--;
            symbolCount++;
        }

        if (symbolCount > WIN_THRESHOLD) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
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

        while (currentRow > 0 &&
                board[currentRow - 1][column] == player.getSymbol()) { //count up
            currentRow--;
            symbolCount++;
        }

        if (symbolCount > WIN_THRESHOLD) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
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

        while (currentColumn > 0 &&
                board[row][currentColumn - 1] == player.getSymbol()) { //count left
            currentColumn--;
            symbolCount++;
        }

        if (symbolCount > WIN_THRESHOLD) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
