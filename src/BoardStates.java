/** * Created by jacobsamar on 12/28/16. */import java.util.List;import java.util.ArrayList;public class BoardStates extends ChoiceMove {    List<Integer> spots = new ArrayList<Integer>();    public static int[][] attackedboards; // attackedboards[0] = the squares that white is attacking    TESTING test = new TESTING();    public void makeAttackedSquares() {        attackedboards = new int[64][2];        long[] board = test.giveCurrentBoard();        //black knight        locatePiece(BLACK_KNIGHT);        for(int num: spots)            for (int j = 0; j < knight[num].length; j++) {                attackedboards[knight[num][j]][1]++;        }        // end of knight moves        // black bishop        locatePiece(BLACK_BISHOP);        for(int num: spots)            for (int j = 0; j < bishop[num].length; j++) {                for (int k = 0; k < bishop[num][j].length; k++) {                    attackedboards[bishop[num][j][k]][1]++;                    if ((SquareBits[bishop[num][j][k]] & board[WHITE_PIECES]) != 0)                        break;                    if ((SquareBits[bishop[num][j][k]] & board[BLACK_PIECES]) != 0)                        break;                }            } // end of bishop moves        // black king        locatePiece(BLACK_KING);        for(int num: spots)            for (int j = 0; j < king[num].length; j++) {                attackedboards[king[num][j]][1]++;            } // end of king moves        // rook        locatePiece(BLACK_ROOK);        for(int num: spots)            for(int j = 0; j < rook[num].length; j++)                for (int k = 0; k < rook[num][j].length; k++) {                    attackedboards[rook[num][j][k]][1]++;                    if ((SquareBits[rook[num][j][k]] & board[WHITE_PIECES]) != 0)                        break;                    if ((SquareBits[rook[num][j][k]] & board[BLACK_PIECES]) != 0)                        break;        } // end of rook moves        // black Queen        locatePiece(BLACK_QUEEN);        for(int num: spots){            for (int j = 0; j < bishop[num].length; j++) {                for (int k = 0; k < bishop[num][j].length; k++) {                    attackedboards[bishop[num][j][k]][1]++;                    if ((SquareBits[bishop[num][j][k]] & board[WHITE_PIECES]) != 0)                        break;                    if ((SquareBits[bishop[num][j][k]] & board[BLACK_PIECES]) != 0)                        break;                }            } // end of bishop moves                    for(int j = 0; j < rook[num].length; j++)                        for (int k = 0; k < rook[num][j].length; k++) {                            attackedboards[rook[num][j][k]][1]++;                            if ((SquareBits[rook[num][j][k]] & board[WHITE_PIECES]) != 0)                                break;                            if ((SquareBits[rook[num][j][k]] & board[BLACK_PIECES]) != 0)                                break;                        } // end of rook moves        } // end of queen moves        locatePiece(BLACK_PAWN);        for(int num: spots) {            if(num % 8 == 7) {                attackedboards[num + 7][1]++;            }else if(num % 8 == 0){                attackedboards[num + 9][1]++;            }else {                attackedboards[num + 7][1]++;                attackedboards[num + 9][1]++;            }        }        locatePiece(WHITE_PAWN);        for(int num: spots) {            if(num % 8 == 7) {                attackedboards[num - 9][0]++;            }else if(num % 8 == 0) {                attackedboards[num - 7][0]++;            }else {                attackedboards[num - 7][0]++;                attackedboards[num - 9][0]++;            }        }        locatePiece(WHITE_KNIGHT);        for(int num: spots)            for (int j = 0; j < knight[num].length; j++) {                attackedboards[knight[num][j]][0]++;            }        // end of knight moves        // White bishop        locatePiece(WHITE_BISHOP);        for(int num: spots)            for (int j = 0; j < bishop[num].length; j++) {                for (int k = 0; k < bishop[num][j].length; k++) {                    attackedboards[bishop[num][j][k]][0]++;                    if ((SquareBits[bishop[num][j][k]] & board[WHITE_PIECES]) != 0)                        break;                    if ((SquareBits[bishop[num][j][k]] & board[BLACK_PIECES]) != 0)                        break;                }            } // end of bishop moves        // White king        locatePiece(WHITE_KING);        for(int num: spots)            for (int j = 0; j < king[num].length; j++) {                attackedboards[king[num][j]][0]++;            } // end of king moves        // rook        locatePiece(WHITE_ROOK);        for(int num: spots)            for(int j = 0; j < rook[num].length; j++)                for (int k = 0; k < rook[num][j].length; k++) {                    attackedboards[rook[num][j][k]][0]++;                    if ((SquareBits[rook[num][j][k]] & board[WHITE_PIECES]) != 0)                        break;                    if ((SquareBits[rook[num][j][k]] & board[BLACK_PIECES]) != 0)                        break;                } // end of rook moves        // White Queen        locatePiece(WHITE_QUEEN);        for(int num: spots){            for (int j = 0; j < bishop[num].length; j++) {                for (int k = 0; k < bishop[num][j].length; k++) {                    attackedboards[bishop[num][j][k]][0]++;                    if ((SquareBits[bishop[num][j][k]] & board[WHITE_PIECES]) != 0)                        break;                    if ((SquareBits[bishop[num][j][k]] & board[BLACK_PIECES]) != 0)                        break;                }            } // end of bishop moves            for(int j = 0; j < rook[num].length; j++)                for (int k = 0; k < rook[num][j].length; k++) {                    attackedboards[rook[num][j][k]][0]++;                    if ((SquareBits[rook[num][j][k]] & board[WHITE_PIECES]) != 0)                        break;                    if ((SquareBits[rook[num][j][k]] & board[BLACK_PIECES]) != 0)                        break;                } // end of rook moves        } // end of queen moves    }    public void printBoard() {        for(int i = 0; i<64;i++) {            System.out.print(attackedboards[i][0] + " ");            if(i % 8 == 7)                System.out.println();        }    }    private void locatePiece(int piece) {        long[] board = test.giveCurrentBoard();        spots.clear();        for(int i = 0; i < 64; i++) {            if ((board[(piece)] & SquareBits[i]) != 0)                spots.add(i);        }    }}