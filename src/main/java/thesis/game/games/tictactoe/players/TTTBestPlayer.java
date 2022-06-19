package thesis.game.games.tictactoe.players;

import org.jenetics.util.RandomRegistry;
import org.nd4j.linalg.api.ndarray.INDArray;
import thesis.game.games.tictactoe.TTTGame;
import thesis.game.games.tictactoe.TTTMove;
import thesis.game.model.GameState;
import thesis.game.model.Move;
import thesis.game.model.Player;

import java.util.Random;

/**
 * Created by Alex on 29/07/2016.
 */
public class TTTBestPlayer extends Player {


    @Override
    public Move move(GameState currentGameState, Integer myId, Integer opposingPlayerId) {
        Random rand = RandomRegistry.getRandom();
        INDArray state = currentGameState.getNormalizedState().getRow(0);
        int[] boardState = new int[9];

        for (int i = 0; i < state.columns(); i++) {
            boardState[i] = (int) state.getDouble(i);
        }

        int myMove = 0;
        int randMoveIdx = 0;
        int forkMove = 0;
        boolean forkAvailable = false;

        //if forking move is available, make it
        int[] tempBoardState = new int[9];

        System.arraycopy(boardState, 0, tempBoardState, 0, 9);

        int numThreats = 0;
        for (int i = 0; i < 9; i++) {
            numThreats = 0;
            if (tempBoardState[i] == 0) {
                tempBoardState[i] = myId;
                if ((tempBoardState[0] + tempBoardState[1] + tempBoardState[2]) == myId * 2) {
                    numThreats++;
                }
                if ((tempBoardState[3] + tempBoardState[4] + tempBoardState[5]) == myId * 2) {
                    numThreats++;
                }
                if ((tempBoardState[6] + tempBoardState[7] + tempBoardState[8]) == myId * 2) {
                    numThreats++;
                }
                if ((tempBoardState[0] + tempBoardState[3] + tempBoardState[6]) == myId * 2) {
                    numThreats++;
                }
                if ((tempBoardState[1] + tempBoardState[4] + tempBoardState[7]) == myId * 2) {
                    numThreats++;
                }
                if ((tempBoardState[2] + tempBoardState[5] + tempBoardState[8]) == myId * 2) {
                    numThreats++;
                }
                if ((tempBoardState[0] + tempBoardState[4] + tempBoardState[8]) == myId * 2) {
                    numThreats++;
                }
                if ((tempBoardState[2] + tempBoardState[4] + tempBoardState[6]) == myId * 2) {
                    numThreats++;
                }
                if (numThreats >= 2) {
                    forkMove = i;
                    forkAvailable = true;
                }
                tempBoardState[i] = 0;
            }
        }

        boolean firstMove = true;

        for (int i = 0; i < 9 && firstMove; i++) {
            if (boardState[i] != 0)
                firstMove = false;
        }

        if (firstMove)
            myMove = rand.nextInt(9);

            //complete winning moves
        else if ((boardState[0] == myId) && (boardState[1] == myId) && (boardState[2] == 0)) {
            myMove = 2;
        } else if ((boardState[0] == myId) && (boardState[2] == myId) && (boardState[1] == 0)) {
            myMove = 1;
        } else if ((boardState[1] == myId) && (boardState[2] == myId) && (boardState[0] == 0)) {
            myMove = 0;
        } else if ((boardState[3] == myId) && (boardState[4] == myId) && (boardState[5] == 0)) {
            myMove = 5;
        } else if ((boardState[3] == myId) && (boardState[5] == myId) && (boardState[4] == 0)) {
            myMove = 4;
        } else if ((boardState[4] == myId) && (boardState[5] == myId) && (boardState[3] == 0)) {
            myMove = 3;
        } else if ((boardState[6] == myId) && (boardState[7] == myId) && (boardState[8] == 0)) {
            myMove = 8;
        } else if ((boardState[6] == myId) && (boardState[8] == myId) && (boardState[7] == 0)) {
            myMove = 7;
        } else if ((boardState[7] == myId) && (boardState[8] == myId) && (boardState[6] == 0)) {
            myMove = 6;
        } else if ((boardState[0] == myId) && (boardState[3] == myId) && (boardState[6] == 0)) {
            myMove = 6;
        } else if ((boardState[0] == myId) && (boardState[6] == myId) && (boardState[3] == 0)) {
            myMove = 3;
        } else if ((boardState[3] == myId) && (boardState[6] == myId) && (boardState[0] == 0)) {
            myMove = 0;
        } else if ((boardState[1] == myId) && (boardState[4] == myId) && (boardState[7] == 0)) {
            myMove = 7;
        } else if ((boardState[1] == myId) && (boardState[7] == myId) && (boardState[4] == 0)) {
            myMove = 4;
        } else if ((boardState[4] == myId) && (boardState[7] == myId) && (boardState[1] == 0)) {
            myMove = 1;
        } else if ((boardState[2] == myId) && (boardState[5] == myId) && (boardState[8] == 0)) {
            myMove = 8;
        } else if ((boardState[2] == myId) && (boardState[8] == myId) && (boardState[5] == 0)) {
            myMove = 5;
        } else if ((boardState[5] == myId) && (boardState[8] == myId) && (boardState[2] == 0)) {
            myMove = 2;
        } else if ((boardState[0] == myId) && (boardState[4] == myId) && (boardState[8] == 0)) {
            myMove = 8;
        } else if ((boardState[0] == myId) && (boardState[8] == myId) && (boardState[4] == 0)) {
            myMove = 4;
        } else if ((boardState[4] == myId) && (boardState[8] == myId) && (boardState[0] == 0)) {
            myMove = 0;
        } else if ((boardState[6] == myId) && (boardState[4] == myId) && (boardState[2] == 0)) {
            myMove = 2;
        } else if ((boardState[6] == myId) && (boardState[2] == myId) && (boardState[4] == 0)) {
            myMove = 4;
        } else if ((boardState[4] == myId) && (boardState[2] == myId) && (boardState[6] == 0)) {
            myMove = 6;
        } else if ((boardState[0] == opposingPlayerId) && //block opponent's winning moves
                (boardState[1] == opposingPlayerId) && (boardState[2] == 0)) {
            myMove = 2;
        } else if ((boardState[0] == opposingPlayerId) && (boardState[2] == opposingPlayerId) && (boardState[1] == 0)) {
            myMove = 1;
        } else if ((boardState[1] == opposingPlayerId) && (boardState[2] == opposingPlayerId) && (boardState[0] == 0)) {
            myMove = 0;
        } else if ((boardState[3] == opposingPlayerId) && (boardState[4] == opposingPlayerId) && (boardState[5] == 0)) {
            myMove = 5;
        } else if ((boardState[3] == opposingPlayerId) && (boardState[5] == opposingPlayerId) && (boardState[4] == 0)) {
            myMove = 4;
        } else if ((boardState[4] == opposingPlayerId) && (boardState[5] == opposingPlayerId) && (boardState[3] == 0)) {
            myMove = 3;
        } else if ((boardState[6] == opposingPlayerId) && (boardState[7] == opposingPlayerId) && (boardState[8] == 0)) {
            myMove = 8;
        } else if ((boardState[6] == opposingPlayerId) && (boardState[8] == opposingPlayerId) && (boardState[7] == 0)) {
            myMove = 7;
        } else if ((boardState[7] == opposingPlayerId) && (boardState[8] == opposingPlayerId) && (boardState[6] == 0)) {
            myMove = 6;
        } else if ((boardState[0] == opposingPlayerId) && (boardState[3] == opposingPlayerId) && (boardState[6] == 0)) {
            myMove = 6;
        } else if ((boardState[0] == opposingPlayerId) && (boardState[6] == opposingPlayerId) && (boardState[3] == 0)) {
            myMove = 3;
        } else if ((boardState[3] == opposingPlayerId) && (boardState[6] == opposingPlayerId) && (boardState[0] == 0)) {
            myMove = 0;
        } else if ((boardState[1] == opposingPlayerId) && (boardState[4] == opposingPlayerId) && (boardState[7] == 0)) {
            myMove = 7;
        } else if ((boardState[1] == opposingPlayerId) && (boardState[7] == opposingPlayerId) && (boardState[4] == 0)) {
            myMove = 4;
        } else if ((boardState[4] == opposingPlayerId) && (boardState[7] == opposingPlayerId) && (boardState[1] == 0)) {
            myMove = 1;
        } else if ((boardState[2] == opposingPlayerId) && (boardState[5] == opposingPlayerId) && (boardState[8] == 0)) {
            myMove = 8;
        } else if ((boardState[2] == opposingPlayerId) && (boardState[8] == opposingPlayerId) && (boardState[5] == 0)) {
            myMove = 5;
        } else if ((boardState[5] == opposingPlayerId) && (boardState[8] == opposingPlayerId) && (boardState[2] == 0)) {
            myMove = 2;
        } else if ((boardState[0] == opposingPlayerId) && (boardState[4] == opposingPlayerId) && (boardState[8] == 0)) {
            myMove = 8;
        } else if ((boardState[0] == opposingPlayerId) && (boardState[8] == opposingPlayerId) && (boardState[4] == 0)) {
            myMove = 4;
        } else if ((boardState[4] == opposingPlayerId) && (boardState[8] == opposingPlayerId) && (boardState[0] == 0)) {
            myMove = 0;
        } else if ((boardState[6] == opposingPlayerId) && (boardState[4] == opposingPlayerId) && (boardState[2] == 0)) {
            myMove = 2;
        } else if ((boardState[6] == opposingPlayerId) && (boardState[2] == opposingPlayerId) && (boardState[4] == 0)) {
            myMove = 4;
        } else if ((boardState[4] == opposingPlayerId) && (boardState[2] == opposingPlayerId) && (boardState[6] == 0)) {
            myMove = 6;
        } else if (forkAvailable) {
            myMove = forkMove;
        }

        //avoid fork
        else if ((boardState[4] == myId) && (boardState[0] == opposingPlayerId) && (boardState[1] == 0)
                && (boardState[2] == 0) && (boardState[3] == 0) && (boardState[5] == 0)
                && (boardState[6] == 0) && (boardState[7] == 0) && (boardState[8] == opposingPlayerId)) {
            myMove = 1;
        } else if ((boardState[4] == myId) && (boardState[0] == 0) && (boardState[1] == 0)
                && (boardState[2] == opposingPlayerId) && (boardState[3] == 0) && (boardState[5] == 0)
                && (boardState[6] == opposingPlayerId) && (boardState[7] == 0) && (boardState[8] == 0)) {
            myMove = 1;
        }

        //avoid fork
        else if ((boardState[0] == 0) && (boardState[4] != 0)
                && ((boardState[1] == opposingPlayerId) || (boardState[3] == opposingPlayerId))) {
            myMove = 0;
        } else if ((boardState[8] == 0) && (boardState[4] != 0)
                && ((boardState[5] == opposingPlayerId) || (boardState[7] == opposingPlayerId))) {
            myMove = 8;
        }

        //play in center if it is open
        else if (boardState[4] == 0) {
            myMove = 4;
        }

        //play randomly in open corner
        else if (boardState[0] == 0 || boardState[2] == 0 || boardState[6] == 0
                || boardState[8] == 0) {
            randMoveIdx = Math.abs(rand.nextInt(4));
            switch (randMoveIdx) {
                case 0:
                    myMove = 0;
                    break;
                case 1:
                    myMove = 2;
                    break;
                case 2:
                    myMove = 6;
                    break;
                case 3:
                    myMove = 8;
                    break;
            }
            while (boardState[myMove] != 0) {
                randMoveIdx = rand.nextInt(4);
                switch (randMoveIdx) {
                    case 0:
                        myMove = 0;
                        break;
                    case 1:
                        myMove = 2;
                        break;
                    case 2:
                        myMove = 6;
                        break;
                    case 3:
                        myMove = 8;
                        break;
                }
            }
        }

        //play randomly
        else {
            int m = rand.nextInt(9);
            while (boardState[m] != 0) {
                m = rand.nextInt(9);
            }
            myMove = m;
        }

        return TTTMove.of(myMove, TTTGame.NON_NEURAL_PLAYER_ID);
    }
}
