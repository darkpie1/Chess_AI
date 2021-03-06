/**
 * Created by jacobsamar on 12/8/16.
 */


import java.util.Scanner;

public class HumanPlayer {

    TESTING test = new TESTING();
    ChoiceMove moves = new ChoiceMove();
    public static int[] moveSpots; // 0 index is source square, 1 is destination
    public static long[] BitBoardCopy;
    public boolean validChoice = false;
    public static int humanColor = 0; // needs a method to decide this when the game begins
    public static int AIColor = 1; // needs a method to decide this when the game begins
    public static int movingpiece;
    public static int movetype;
    static Scanner scan = new Scanner(System.in);

    public HumanPlayer() {
        moveSpots = new int[2];
        BitBoardCopy = new long[14];
    }
   // public void determineColor();{
     // after method in main class pickSides() has been used
    // said method will have the user pick if they want to be
    // white or black and mark a static varibale to be used here
    // humanColor = mainClass.humanIs;
   // }



    public void humanMove() {
        movetype = 0;
        validChoice = false;
        while (!validChoice) {
            enterMove();
            if(isPawnMove() > 0){ // have to set movetype in isPawnMove, check if its a valid move,
                movetype = isPawnMove();
                return;
            }
            movetype = validateMove();
            if (movetype > 0)
                validChoice = true;
        }
    }

    private int isPawnMove() {
        BitBoardCopy = test.giveCurrentBoard();
        if((ChessBoard.SquareBits[moveSpots[0]] & BitBoardCopy[ChessBoard.WHITE_PAWN + ChessBoard.currentplayer]) == 0) {
            return 0;
        }
        movingpiece = ChessBoard.WHITE_PAWN + ChessBoard.currentplayer;

        if(moveSpots[1]  - moveSpots[0] + 16 * ChessBoard.currentplayer == -8) { // normal move and promote
            if((BitBoardCopy[ChessBoard.ALL_PIECES] & ChessBoard.SquareBits[moveSpots[1]]) != 0 ||
                    (BitBoardCopy[ChessBoard.ALL_PIECES + 1] & ChessBoard.SquareBits[moveSpots[1]]) != 0)
                return 0;
            if(moveSpots[1] < 8 && moveSpots[1] > 55)
                return ChoiceMove.PROMOTE_PAWN;
            return ChoiceMove.NORMAL_MOVE;
        }
        if(Math.abs(moveSpots[1] - moveSpots[0]) == 16 && ((moveSpots[0] > 47 && moveSpots[0] < 56) || (moveSpots[0] > 7 && moveSpots[0] < 16))) {// checking for double
            if(((BitBoardCopy[ChessBoard.ALL_PIECES] & ChessBoard.SquareBits[moveSpots[1]]) != 0 &&
                    (BitBoardCopy[ChessBoard.ALL_PIECES] & ChessBoard.SquareBits[moveSpots[1] - 8 + 16 * ChessBoard.currentplayer]) != 0) )
                return 0;
            return ChoiceMove.NORMAL_MOVE;
        }
        if(moveSpots[1] % 2 != moveSpots[0] % 2 && FindPiece(moveSpots[1]) < 12 && FindPiece(moveSpots[1]) != ChessBoard.currentplayer % 2
                && Math.abs(moveSpots[0] - moveSpots[1]) > 1) { // cpture and promote
            if(moveSpots[1] < 8 || moveSpots[1] > 55)
                return ChoiceMove.PRO_CAP_PAWN;
            return ChoiceMove.CAPTURE_MOVE;
        }
        // add enPassant later if i feel like it
        return 0;
    }

    private void enterMove() {
        boolean done = false;
        while (!done) {
            System.out.println("What move do you wish to make \n(Format: source square then destination square)");
            moveSpots[0] = scan.nextInt();
            moveSpots[1] = scan.nextInt();
            if (moveSpots[1] > 63 || moveSpots[1] < 0 || moveSpots[0] > 63 || moveSpots[0] < 0) {

            } else {
                done = true;
            }
        }

    }

    private int validateMove() { // does not currently check if it would put the player in check.
        boolean possible = false;
        BitBoardCopy = test.giveCurrentBoard();
        movingpiece = FindPiece(moveSpots[0]);
// knight
        if(movingpiece == ChessBoard.WHITE_KNIGHT || movingpiece == ChessBoard.BLACK_KNIGHT) {
            search:
                for (int j = 0; j < ChoiceMove.knight[moveSpots[0]].length; j++) {
                    if(moveSpots[1] == ChoiceMove.knight[moveSpots[0]][j]) {
                        possible = true;
                        break search;
                    }
                }
        } // end of knight moves
// bishop
        if(movingpiece == ChessBoard.WHITE_BISHOP || movingpiece == ChessBoard.BLACK_BISHOP) {
            search:
            for (int j = 0; j < ChoiceMove.bishop[moveSpots[0]].length; j++) {
                for (int k = 0; k < ChoiceMove.bishop[moveSpots[0]][j].length; k++) {
                    if(moveSpots[1] == ChoiceMove.bishop[moveSpots[0]][j][k]) {
                        possible = true;
                        break search;
                    }
                    if ((ChessBoard.SquareBits[moveSpots[1]] & BitBoardCopy[12 + humanColor]) != 0){
                        break;
                    }
                    if ((ChessBoard.SquareBits[moveSpots[1]] & BitBoardCopy[12 + AIColor]) != 0)
                        break;
                }
            }
        } // end of bishop moves
// king
        if(movingpiece == ChessBoard.WHITE_KING || movingpiece == ChessBoard.BLACK_KING) {
            search:
            for (int j = 0; j < ChoiceMove.king[moveSpots[0]].length; j++) {
                if(moveSpots[1] == ChoiceMove.king[moveSpots[0]][j]) {
                    possible = true;
                    break search;
                }
            }
        } // end of king moves
// rook
        if(movingpiece == ChessBoard.WHITE_ROOK || movingpiece == ChessBoard.BLACK_ROOK) {
            search:
            for (int j = 0; j < ChoiceMove.rook[moveSpots[0]].length; j++) {
                for (int k = 0; k < ChoiceMove.rook[moveSpots[0]][j].length; k++) {
                    if(moveSpots[1] == ChoiceMove.rook[moveSpots[0]][j][k]) {
                        possible = true;
                        break search;
                    }
                    if ((ChessBoard.SquareBits[moveSpots[1]] & BitBoardCopy[12 + humanColor]) != 0){
                        break;
                    }
                    if ((ChessBoard.SquareBits[moveSpots[1]] & BitBoardCopy[12 + AIColor]) != 0)
                        break;
                }
            }
        } // end of rook moves

        // bishop
        if(movingpiece == ChessBoard.WHITE_QUEEN || movingpiece == ChessBoard.BLACK_QUEEN) {
            search:
            for (int j = 0; j < ChoiceMove.bishop[moveSpots[0]].length; j++) {
                for (int k = 0; k < ChoiceMove.bishop[moveSpots[0]][j].length; k++) {
                    if(moveSpots[1] == ChoiceMove.bishop[moveSpots[0]][j][k]) {
                        possible = true;
                        break search;
                    }
                    if ((ChessBoard.SquareBits[moveSpots[1]] & BitBoardCopy[12 + humanColor]) != 0){
                        break;
                    }
                    if ((ChessBoard.SquareBits[moveSpots[1]] & BitBoardCopy[12 + AIColor]) != 0)
                        break;
                }
            }
            search:
            for (int j = 0; j < ChoiceMove.rook[moveSpots[0]].length; j++) {
                for (int k = 0; k < ChoiceMove.rook[moveSpots[0]][j].length; k++) {
                    if(moveSpots[1] == ChoiceMove.rook[moveSpots[0]][j][k]) {
                        possible = true;
                        break search;
                    }
                    if ((ChessBoard.SquareBits[moveSpots[1]] & BitBoardCopy[12 + humanColor]) != 0){
                        break;
                    }
                    if ((ChessBoard.SquareBits[moveSpots[1]] & BitBoardCopy[12 + AIColor]) != 0)
                        break;
                }
            }
        } // end of queen moves


        if (!possible) {
            System.out.println("Not a valid move for that piece");
            return -1;
        }
        if ((ChessBoard.SquareBits[moveSpots[1]] & BitBoardCopy[12 + humanColor]) != 0){
            System.out.println("Can't move because friendly piece occupies destination square");
            return -1;
        }
        if ((ChessBoard.SquareBits[moveSpots[1]] & BitBoardCopy[12 + AIColor]) != 0)
            return ChoiceMove.CAPTURE_MOVE;
        return ChoiceMove.NORMAL_MOVE;
    }// end of validating move

    private int FindPiece(int location) {
        if((BitBoardCopy[ChessBoard.BLACK_PAWN] & ChessBoard.SquareBits[location]) != 0)
            return(ChessBoard.BLACK_PAWN);
        if((BitBoardCopy[ChessBoard.BLACK_KNIGHT] & ChessBoard.SquareBits[location]) != 0)
            return(ChessBoard.BLACK_KNIGHT);
        if((BitBoardCopy[ChessBoard.BLACK_BISHOP] & ChessBoard.SquareBits[location]) != 0)
            return(ChessBoard.BLACK_BISHOP);
        if((BitBoardCopy[ChessBoard.BLACK_ROOK] & ChessBoard.SquareBits[location]) != 0)
            return(ChessBoard.BLACK_ROOK);
        if((BitBoardCopy[ChessBoard.BLACK_KING] & ChessBoard.SquareBits[location]) != 0)
            return(ChessBoard.BLACK_KING);
        if((BitBoardCopy[ChessBoard.BLACK_QUEEN] & ChessBoard.SquareBits[location]) != 0)
            return(ChessBoard.BLACK_QUEEN);
        if((BitBoardCopy[ChessBoard.WHITE_KNIGHT] & ChessBoard.SquareBits[location]) != 0)
            return(ChessBoard.WHITE_KNIGHT);
        if((BitBoardCopy[ChessBoard.WHITE_BISHOP] & ChessBoard.SquareBits[location]) != 0)
            return(ChessBoard.WHITE_BISHOP);
        if((BitBoardCopy[ChessBoard.WHITE_ROOK] & ChessBoard.SquareBits[location]) != 0)
            return(ChessBoard.WHITE_ROOK);
        if((BitBoardCopy[ChessBoard.WHITE_KING] & ChessBoard.SquareBits[location]) != 0)
            return(ChessBoard.WHITE_KING);
        if((BitBoardCopy[ChessBoard.WHITE_QUEEN] & ChessBoard.SquareBits[location]) != 0)
            return(ChessBoard.WHITE_QUEEN);
        if((BitBoardCopy[ChessBoard.WHITE_PAWN] & ChessBoard.SquareBits[location]) != 0)
            return(ChessBoard.WHITE_PAWN);
        return (125);
    }
}


