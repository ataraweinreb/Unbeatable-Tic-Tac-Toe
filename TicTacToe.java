import java.util.*;
import javax.swing.*;

/**
 * Using the minimax algorithm to create a tic tac toe game.
 */
public class Main {

    private static int[] gameBoard = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    private static final int playerID = 100;
    private static final int aiID = 200;
    private static boolean humanTurn = false;
    private static int round = 1;

    public static class Move {
        protected int index, score;
        public Move (int index, int score) {
            this.index = index;
            this.score = score;
        }
        public Move() {
            this(-1, -1);
        }
    }

    private static void printInstructions() {
        System.out.println("Welcome to tic tac toe!");
        System.out.println("You will take turns selecting 'X's' and 'O's with your AI opponent.");
        System.out.println("Score 3 of a kind in any direction to win!");
        System.out.println();
        System.out.println("Now let the games begin, and may the odds be ever in your favor!");
        System.out.println("- Effie Trinket, the Hunger Games :)");
        System.out.println("\nPlease type in number key shown below to select a cell.");
        System.out.println("\n----------------\n| 00 | 01 | 02 |\n----------------\n| 10 | 11 | 12 |" +
                "\n----------------\n| 20 | 21 | 22 |\n----------------\n");
    }

    private static void printBoard() {
        int c = 1;
        for (int i = 0; i < gameBoard.length; i++) {
            if (gameBoard[i] == 100) System.out.print("X ");
            else if (gameBoard[i] == 200) System.out.print("O ");
            else System.out.print("_ ");
            if (c == 3) {System.out.println(); c = 1;}
            else c++;
        }
        System.out.println();
    }

    /**
     * Search for the positions where a move can be performed.
     */
    public static List<Integer> emptyIndexes(int[] board) {
        List<Integer> l = new LinkedList<Integer>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] != 100 && board[i] != 200) {
                l.add(i);
            }
        }
        return l;
    }

    /**
     * Checks if a player has won the game.
     */
    public static boolean winning(int[] board, int player) {
        if (
                (board[0] == player && board[1] == player && board[2] == player) ||
                        (board[3] == player && board[4] == player && board[5] == player) ||
                        (board[6] == player && board[7] == player && board[8] == player) ||
                        (board[0] == player && board[3] == player && board[6] == player) ||
                        (board[1] == player && board[4] == player && board[7] == player) ||
                        (board[2] == player && board[5] == player && board[8] == player) ||
                        (board[0] == player && board[4] == player && board[8] == player) ||
                        (board[2] == player && board[4] == player && board[6] == player)
        ) {
            return true;
        } else {
            return false;
        }
    }

    private static void humanMove() {
        while (true) {
            String first = JOptionPane.showInputDialog("Choose a cell to mark");
            if (first.equals("00") && gameBoard[0] != aiID && gameBoard[0] != playerID) {
                gameBoard[0] = playerID;
                break;
            } else if (first.equals("01") && gameBoard[1] != aiID && gameBoard[1] != playerID) {
                gameBoard[1] = playerID;
                break;
            } else if (first.equals("02") && gameBoard[2] != aiID && gameBoard[2] != playerID) {
                gameBoard[2] = playerID;
                break;
            } else if (first.equals("10") && gameBoard[3] != aiID && gameBoard[3] != playerID) {
                gameBoard[3] = playerID;
                break;
            } else if (first.equals("11") && gameBoard[4] != aiID && gameBoard[4] != playerID) {
                gameBoard[4] = playerID;
                break;
            } else if (first.equals("12") && gameBoard[5] != aiID && gameBoard[5] != playerID) {
                gameBoard[5] = playerID;
                break;
            } else if (first.equals("20") && gameBoard[6] != aiID && gameBoard[6] != playerID) {
                gameBoard[6] = playerID;
                break;
            } else if (first.equals("21") && gameBoard[7] != aiID && gameBoard[7] != playerID) {
                gameBoard[7] = playerID;
                break;
            } else if (first.equals("22") && gameBoard[8] != aiID && gameBoard[8] != playerID) {
                gameBoard[8] = playerID;
                break;
            }
        }
    }

    public static void aiMove() {
        int index = (int) miniMax(gameBoard, aiID).index;
        gameBoard[index] = aiID;
    }

    /**
     * AI uses minimax to find it's best move.
     */
    public static Move miniMax(int[] newBoard, int player) {
        List<Integer> availableSpots = emptyIndexes(newBoard);

        if (winning(newBoard, playerID)) {
            return new Move(-1, -10);
        } else if (winning(newBoard, aiID)) {
            return new Move(-1, 10);
        } else if (availableSpots.size() == 0) {
            return new Move(-1, 0);
        }

        List<Move> moves = new LinkedList<Move>();
        for (Integer n : availableSpots) {
            Move move = new Move();
            move.index = n;
            newBoard[n] = player;

            if (player == aiID) {
                Move aux = miniMax(newBoard, playerID);
                move.score = aux.score;
            } else {
                Move aux = miniMax(newBoard, aiID);
                move.score = aux.score;
            }

            newBoard[n] = move.index;
            moves.add(move);
        }

        int bestMove = 0;
        if (player == aiID) {
            int bestScore = -1000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score > bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        } else {
            int bestScore = 1000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).score < bestScore) {
                    bestScore = moves.get(i).score;
                    bestMove = i;
                }
            }
        }
        return moves.get(bestMove);
    }

    public static void main(String[] args) {
        System.out.print('\u000C');
        printInstructions();
        printBoard();
        //Select the first player
        while (true) {
            String first = JOptionPane.showInputDialog("Please choose the first player.\n Enter 'P' for yourself, or 'A' for Artificial intelligence.");
            if (first == null) {
                System.out.println("See you next time!");
                System.exit(0);
            }
            first = first.toUpperCase();
            if (first.equals("P")) {
                System.out.println("\nPlayer goes first");
                humanTurn = true;
                break;
            } else if (first.equals("A")) {
                System.out.println("\nAI goes first");
                humanTurn = false;
                break;
            } else {
                System.out.println("Invalid answer");
            }
        }

        while (true) {
            if (humanTurn) {
                humanMove();
            } else {
                if (round == 1) {
                    //AI's first move is random
                    int pos = (int) Math.floor(Math.random()*9);
                    gameBoard[pos] = aiID;
                } else {
                    aiMove();
                }
            }
            humanTurn = !humanTurn;
            printBoard();

            //Check if a player won
            if (winning(gameBoard, playerID)) {
                System.out.println("Victory is yours!");
                break;
            } else if(winning(gameBoard, aiID)) {
                System.out.println("You lose! Better luck next time.");
                break;
            }

            /**
            At round 9 the gameBoard is complete and the game finishes with a tie,
            or next round occurs
            */
            if (round > 8) {
                System.out.println("It's a tie!");
                break;
            } else {
                round++;
            }
        }
    }
}
