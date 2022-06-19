package thesis.game.games.tictactoe;

import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.model.GameState;

/**
 * Created by Alex on 29/07/2016.
 */
public class TTTGameState extends GameState {

    private TTTGameState(Integer nrInputs) {
        super(nrInputs);
    }

    private TTTGameState(INDArray state) {
        super(state);
    }

    public static TTTGameState initialState(Integer nrInputs) {
        return new TTTGameState(nrInputs);
    }

    public static TTTGameState of(INDArray state) {
        return new TTTGameState(state);
    }


    @Override
    public INDArray getNormalizedState() {
        return state;
    }

    @SuppressWarnings("Duplicates")
    public boolean gameWonByPlayer(Integer player) {
        boolean wonGame = false;
        final int BOARD_SIZE = 3;
        final int playerMaxScore = BOARD_SIZE * player;
        int[][] matrixBoardState = new int[BOARD_SIZE][BOARD_SIZE];
        int boardIdx = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                matrixBoardState[i][j] = (int) getNormalizedState().getDouble(boardIdx);
                boardIdx++;
            }
        }
        // check rows for win
        for (int i = 0; i < BOARD_SIZE; i++) {
            int rowTotal = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                rowTotal += matrixBoardState[i][j];
            }
            if (rowTotal == playerMaxScore) {
                wonGame = true;
            }
        }
        // check columns for win
        for (int i = 0; i < BOARD_SIZE; i++) {
            int columnTotal = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                columnTotal += matrixBoardState[j][i];
            }
            if (columnTotal == playerMaxScore) {
                wonGame = true;
            }
        }
        // check diagonals for win
        int diagonalTotal = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (i == j) {
                    diagonalTotal += matrixBoardState[i][j];
                }
            }
            if (diagonalTotal == playerMaxScore) {
                wonGame = true;
            }
        }

        diagonalTotal = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i + j) == 2) {
                    diagonalTotal += matrixBoardState[i][j];
                }
            }
            if (diagonalTotal == playerMaxScore) {
                wonGame = true;
            }
        }

        return wonGame;
    }

    @Override
    public void outputToConsole() {
        int[][] board = new int[3][3];
        int boardIdx = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = (int) getNormalizedState().getDouble(boardIdx);
                boardIdx++;
            }
        }

        System.out.println(board[0][0] + "|" + board[0][1] + "|"
                + board[0][2] + "\n" +
                "-----" + "\n" +
                board[1][0] + "|" + board[1][1] + "|"
                + board[1][2] + "\n" +
                "-----" + "\n" +
                board[2][0] + "|" + board[2][1] + "|"
                + board[2][2] + "\n");
        System.out.println();
        System.out.println("----------------------------------------------");
        System.out.println();
    }

    @Override
    public String toString() {
        int[][] board = new int[3][3];
        int boardIdx = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = (int) getNormalizedState().getDouble(boardIdx);
                boardIdx++;
            }
        }

        return board[0][0] + " " + board[0][1] + " "
                + board[0][2] + "\n" +
                " | " + "\n" +
                board[1][0] + " " + board[1][1] + " "
                + board[1][2] + "\n" +
                " | " + "\n" +
                board[2][0] + " " + board[2][1] + " "
                + board[2][2] + "\n";
    }
}
