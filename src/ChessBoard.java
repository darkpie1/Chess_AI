import java.awt.*;
import java.util.ArrayDeque;
import java.util.*;

/**
 * Created by jacobsamar on 12/5/16.
 */
public class ChessBoard {

    protected static final int WHITE_PAWN = 0;
    protected static final int WHITE_KNIGHT = 2;
    protected static final int WHITE_BISHOP = 4;
    protected static final int WHITE_ROOK = 6;
    protected static final int WHITE_QUEEN = 8;
    protected static final int WHITE_KING = 10;
    protected static final int BLACK_PAWN = 1;
    protected static final int BLACK_KNIGHT = 3;
    protected static final int BLACK_BISHOP = 5;
    protected static final int BLACK_ROOK = 7;
    protected static final int BLACK_QUEEN = 9;
    protected static final int BLACK_KING = 11;
    protected static final int EMPTY_SQUARE = 12;

    protected static final int ALL_PIECES = 12;
    protected static final int ALL_SQUARES = 64;
    protected static final int WHITE_PIECES = 12;
    protected static final int BLACK_PIECES = 13;
    protected static final int ALL_BITBOARDS = 14;

    protected static int whosTurn, capturedpiece, currentplayer = 0; // change back after testing
    protected static long SquareBits[];
    private long BitBoard[];

    private int MaterialValue[];
    private int NumPawns[];
    private static int PieceValues[];
    protected int AIcolor = 1;    // black for now, needs to change for games
    protected int humancolor = 0; //

    static {
        SquareBits = new long[ALL_SQUARES];
        for(int i = 0;i < 64; i++) {
            SquareBits[i] = (1L << i);
        }

        PieceValues = new int[ALL_PIECES];
        PieceValues[WHITE_PAWN] = 100;
        PieceValues[WHITE_KNIGHT] = 300;
        PieceValues[WHITE_BISHOP] = 350;
        PieceValues[WHITE_ROOK] = 500;
        PieceValues[WHITE_QUEEN] = 900;
        PieceValues[WHITE_KING] = 3000;
        PieceValues[BLACK_PAWN] = 100;
        PieceValues[BLACK_KNIGHT] = 300;
        PieceValues[BLACK_BISHOP] = 350;
        PieceValues[BLACK_ROOK] = 500;
        PieceValues[BLACK_QUEEN] = 900;
        PieceValues[BLACK_KING] = 3000;
    } // end of building static constants

    //constructor
    public ChessBoard() {
        BitBoard = new long[ALL_BITBOARDS];
        NumPawns = new int[2];
        MaterialValue = new int[2];
    }

    public void MakeBoard() {
        for(int i = 0; i < 8; i++) {
            AddPiece(8 + i, BLACK_PAWN);
        }
        for(int i = 0; i < 8; i++) {
            AddPiece(55 - i, WHITE_PAWN);
        }
        AddPiece(56, WHITE_ROOK);
        AddPiece(57, WHITE_KNIGHT);
        AddPiece(58, WHITE_BISHOP);
        AddPiece(59, WHITE_QUEEN);
        AddPiece(60, WHITE_KING);
        AddPiece(61, WHITE_BISHOP);
        AddPiece(62, WHITE_KNIGHT);
        AddPiece(63, WHITE_ROOK);
        AddPiece(0, BLACK_ROOK);
        AddPiece(1, BLACK_KNIGHT);
        AddPiece(2, BLACK_BISHOP);
        AddPiece(3, BLACK_QUEEN);
        AddPiece(4, BLACK_KING);
        AddPiece(5, BLACK_BISHOP);
        AddPiece(6, BLACK_KNIGHT);
        AddPiece(7, BLACK_ROOK);
    }

    public void MakeMove() {
        switch (ChoiceMove.typeOfMove){
            case ChoiceMove.NORMAL_MOVE:
                AddPiece(ChoiceMove.destinationSquare, ChoiceMove.thePiece);
                RemovePiece(ChoiceMove.sourceSquare, ChoiceMove.thePiece);
                break;
            case ChoiceMove.CAPTURE_MOVE:
                capturedpiece = FindPiece(ChoiceMove.destinationSquare);
                RemovePiece(ChoiceMove.destinationSquare, capturedpiece);
                AddPiece(ChoiceMove.destinationSquare, ChoiceMove.thePiece);
                RemovePiece(ChoiceMove.sourceSquare, ChoiceMove.thePiece);
                break;
            case ChoiceMove.ENPASSANT_CAPTURE:
                /* if(whosTurn == WHITE_SIDE){
                    capturedpiece = FindPiece((ChoiceMove.destinationSquare + 8));
                    RemovePiece((ChoiceMove.destinationSquare + 8, capturedpiece));
                    AddPiece(ChoiceMove.destinationSquare, ChoiceMove.thePiece);
                    RemovePiece(ChoiceMove.sourceSquare, ChoiceMove.thePiece);
                } else {
                    capturedpiece = FindPiece((ChoiceMove.destinationSquare - 8));
                    RemovePiece((ChoiceMove.destinationSquare - 8, capturedpiece));
                    AddPiece(ChoiceMove.destinationSquare, ChoiceMove.thePiece);
                    RemovePiece(ChoiceMove.sourceSquare, ChoiceMove.thePiece);
                } */
                break;
            case ChoiceMove.CASTLING_QUEENSIDE:
                break;
            case ChoiceMove.CASTLING_KINGSIDE:
                break;
            case ChoiceMove.PROMOTE_PAWN:
                break;

        }
       /* if(currentplayer % 2 == 1) {
            currentplayer = 0;
        }else{
            currentplayer = 1;
        }*/ // implement later after testing movements
    }

    public long[] copyBoard() {
        return BitBoard;

    }

    public void PrintBoard() {
        for( int line = 0; line < 8; line++ )
        {
            System.out.println( "-----------------------------------------" );
            System.out.println( "|    |    |    |    |    |    |    |    |" );
            for( int col = 0; col < 8; col++ )
            {
                long bits = SquareBits[ line * 8 + col ];

                // Scan the bitboards to find a piece, if any
                int piece = 0;
                while ( ( piece < ALL_PIECES ) && ( ( bits & BitBoard[ piece ] ) == 0 ) )
                    piece++;

                // Show the piece
                System.out.print( "| " + piece + " " );
            }
            System.out.println( "|" );
            System.out.println( "|    |    |    |    |    |    |    |    |" );
        }
        System.out.println( "-----------------------------------------" );
    }

    public void AddPiece( int whichSquare, int whichPiece) {
        // update both bitboards
        BitBoard[whichPiece] |= SquareBits[whichSquare];
        BitBoard[ALL_PIECES + (whichPiece % 2)] |= SquareBits[whichSquare];
       // BitBoard[ALL_BITBOARDS] |= SquareBits[whichSquare];

        //update the material evalutation
        MaterialValue[whichPiece % 2] += PieceValues[whichPiece];
        if (whichPiece == WHITE_PAWN)
            NumPawns[0]++;
        else if (whichPiece == BLACK_PAWN)
            NumPawns[1]++;
    } // end of add piece

    public boolean RemovePiece( int whichSquare, int whichPiece) {
        BitBoard[whichPiece] ^= SquareBits[whichSquare];
        BitBoard[ALL_PIECES + (whichPiece % 2)] ^= SquareBits[whichSquare];
      //  BitBoard[ALL_BITBOARDS] ^= SquareBits[whichSquare];

        // update the material evalutation
        MaterialValue[whichPiece % 2] -= PieceValues[whichPiece];
        if (whichPiece == WHITE_PAWN)
            NumPawns[0]--;
        else if (whichPiece == BLACK_PAWN)
            NumPawns[1]--;
        return true;
    }
//
    // Find the location of pawns for the AI
    public ArrayList findPawns(int color) {
        ArrayList<Integer> locations = new ArrayList<>();
        int pawns = 0;
      // black for now needs to be changed based on game
        for(int i = 8; i < 56; i++) {
            if((BitBoard[(WHITE_PAWN + color)] & SquareBits[i]) != 0 ) {
               pawns++;
               locations.add(i);
            }
            if(pawns == NumPawns[color]) return locations;
        }
        return locations;
    }

    // Find what piece is on a square.
    // Useful for figuring what piece to remove during a capture
    public int FindPiece(int location) {
        System.out.println((BitBoard[BLACK_PAWN] & SquareBits[location]));
        if((BitBoard[BLACK_PAWN] & SquareBits[location]) != 0)
            return(BLACK_PAWN);
        if((BitBoard[WHITE_PAWN] & SquareBits[location]) != 0)
            return(WHITE_PAWN);
        if((BitBoard[BLACK_KNIGHT] & SquareBits[location]) != 0)
            return(BLACK_KNIGHT);
        if((BitBoard[BLACK_BISHOP] & SquareBits[location]) != 0)
            return(BLACK_BISHOP);
        if((BitBoard[BLACK_ROOK] & SquareBits[location]) != 0)
            return(BLACK_ROOK);
        if((BitBoard[BLACK_KING] & SquareBits[location]) != 0)
            return(BLACK_KING);
        if((BitBoard[BLACK_QUEEN] & SquareBits[location]) != 0)
            return(BLACK_QUEEN);
        if((BitBoard[WHITE_KNIGHT] & SquareBits[location]) != 0)
            return(WHITE_KNIGHT);
        if((BitBoard[WHITE_BISHOP] & SquareBits[location]) != 0)
            return(WHITE_BISHOP);
        if((BitBoard[WHITE_ROOK] & SquareBits[location]) != 0)
            return(WHITE_ROOK);
        if((BitBoard[WHITE_KING] & SquareBits[location]) != 0)
            return(WHITE_KING);
        if((BitBoard[WHITE_QUEEN] & SquareBits[location]) != 0)
            return(WHITE_QUEEN);
        return (-125);
    }



} // end of class
