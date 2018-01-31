package com.game.connect4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ConnectFourTest {

    private final ConnectFour connectFour = new ConnectFour();

    //For reflection purposes when trying to modify private members
    private static Field board;
    private static Field totalMovesCount;


    //mark the board and totalMovesCount fields as accessible so we can use reflection to set them (only for the full board test)
    @BeforeClass
    public static void setConnectFourInstanceBoardFieldToAccessible() throws NoSuchFieldException {
        board = ConnectFour.class.getDeclaredField("board");
        board.setAccessible(Boolean.TRUE);

        totalMovesCount = ConnectFour.class.getDeclaredField("totalMovesCount");
        totalMovesCount.setAccessible(Boolean.TRUE);
    }

    @Before
    public void setUp() {
        connectFour.resetGame();
    }


    /**
     * | | | | | | | |
     * | | | | | | | |
     * | | | |R| | | |
     * | | |R|G| | | |
     * |G|R|G|R|| | |
     * |R|G|R|G| | | |
     */
    @Test
    public void testParticularReverseSlashWin(){
        connectFour.play(0, Player.RED);
        connectFour.play(1, Player.GREEN);
        connectFour.play(2, Player.RED);
        connectFour.play(3, Player.GREEN);
        connectFour.play(3, Player.RED);
        connectFour.play(2, Player.GREEN);
        connectFour.play(1, Player.RED);
        connectFour.play(0, Player.GREEN);
        connectFour.play(2, Player.RED);
        connectFour.play(3, Player.GREEN);
        connectFour.play(3, Player.RED);
        assertEquals(connectFour.getWinner(), Player.RED);
    }

    /**
     * | | | | | | | |
     * | | | | | | | |
     * | | | | | | | |
     * | | | | | | | |
     * | | | |G|G| | |
     * | |G|R|R|R|R| |
     */
    @Test
    public void testRedWinHorizontally() {
        connectFour.play(3, Player.RED);
        connectFour.play(3, Player.GREEN);
        connectFour.play(4, Player.RED);
        connectFour.play(4, Player.GREEN);
        connectFour.play(2, Player.RED);
        connectFour.play(1, Player.GREEN);
        connectFour.play(5, Player.RED);
        assertEquals(connectFour.getWinner(), Player.RED);
    }

    /**
     * | | | | | | | |
     * | | | | |G| | |
     * | | | | |G| | |
     * | | | | |G| | |
     * | |R|R|G|G| | |
     * | |G|R|R|R| | |
     */
    @Test
    public void testGreenWinsVertically() {
        connectFour.play(3, Player.RED);
        connectFour.play(3, Player.GREEN);
        connectFour.play(4, Player.RED);
        connectFour.play(4, Player.GREEN);
        connectFour.play(2, Player.RED);
        connectFour.play(1, Player.GREEN);
        connectFour.play(1, Player.RED);
        connectFour.play(4, Player.GREEN);
        connectFour.play(2, Player.RED);
        connectFour.play(4, Player.GREEN);
        connectFour.play(3, Player.RED);
        connectFour.play(4, Player.GREEN);
        assertEquals(connectFour.getWinner(), Player.GREEN);
    }

    /**
     * | | | | | | | |
     * | | | | | | | |
     * | | |G| | | | |
     * | | |R|G|R| | |
     * | | |R|G|G| | |
     * | |G|R|R|R|G| |
     */
    @Test
    public void testGreenWinsLeftToRightDiagonal() {
        connectFour.play(3, Player.RED);
        connectFour.play(3, Player.GREEN);
        connectFour.play(4, Player.RED);
        connectFour.play(4, Player.GREEN);
        connectFour.play(2, Player.RED);
        connectFour.play(1, Player.GREEN);
        connectFour.play(4, Player.RED);
        connectFour.play(5, Player.GREEN);
        connectFour.play(2, Player.RED);
        connectFour.play(3, Player.GREEN);
        connectFour.play(2, Player.RED);
        connectFour.play(2, Player.GREEN);
        assertEquals(connectFour.getWinner(), Player.GREEN);
    }

    /**
     * | | | | | | | |
     * | | | | | | | |
     * | | | | | |R| |
     * | | | | |R|G| |
     * | | | |R|G|G| |
     * | |G|R|R|R|G| |
     */
    @Test
    public void testRedWinsRightToLeftDiagonal() {
        connectFour.play(3, Player.RED);
        connectFour.play(1, Player.GREEN);
        connectFour.play(4, Player.RED);
        connectFour.play(4, Player.GREEN);
        connectFour.play(2, Player.RED);
        connectFour.play(5, Player.GREEN);
        connectFour.play(3, Player.RED);
        connectFour.play(5, Player.GREEN);
        connectFour.play(4, Player.RED);
        connectFour.play(5, Player.GREEN);
        connectFour.play(5, Player.RED);
        assertEquals(connectFour.getWinner(), Player.RED);
    }


    /**
     * |G|G|G|R|R|R|G|
     * |R|R|R|G|G|G|R|
     * |G|G|G|R|R|R|G|
     * |R|R|R|G|G|G|R|
     * |G|G|G|R|R|R|G|
     * |R|R|R|G|G|G|R|
     */

    @Test
    public void testFullBoardFilling_expectDrawGame() throws IllegalAccessException {
        totalMovesCount.set(connectFour, 42);
        board.set(connectFour, new char[][]{
                {'G','0','G','R','R','R','G'},
                {'R','R','R','G','G','G','R'},
                {'G','G','G','R','R','R','G'},
                {'R','R','R','G','G','G','R'},
                {'G','G','G','R','R','R','G'},
                {'R','R','R','G','G','G','R'}
        });

        connectFour.play(1, Player.RED);
        assertTrue(connectFour.isDrawGame());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChoosingOutsideLowerBoundColumn() {
        connectFour.play(-1, Player.RED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChoosingOutsideUpperBoundColumn() {
        connectFour.play(99, Player.RED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTryingToChooseAnAlreadyFullColumn() {
        connectFour.play(3, Player.RED);
        connectFour.play(3, Player.GREEN);
        connectFour.play(3, Player.RED);
        connectFour.play(3, Player.GREEN);
        connectFour.play(3, Player.RED);
        connectFour.play(3, Player.GREEN);
        connectFour.play(3, Player.RED);
    }

    @Test(expected = IllegalStateException.class)
    public void testMakingAMoveAfterGameOverFlagIsSetToTrue() {
        connectFour.play(3, Player.RED);
        connectFour.play(3, Player.GREEN);
        connectFour.play(4, Player.RED);
        connectFour.play(4, Player.GREEN);
        connectFour.play(2, Player.RED);
        connectFour.play(1, Player.GREEN);
        connectFour.play(5, Player.RED); //red has won at this point. no more moves should be allowed
        connectFour.play(5, Player.GREEN);
    }

    @Test(expected = IllegalStateException.class)
    public void testTryingToMakeToConsecutivePlaysByTheSamePlayer() {
        connectFour.play(3, Player.RED);
        connectFour.play(3, Player.RED);
    }

}
