/**
 * Created by jacobsamar on 12/5/16.
 */

public class TESTING {

    static ChessBoard board = new ChessBoard();
    static ChoiceMove choice = new ChoiceMove();
    static HumanPlayer human = new HumanPlayer();
    static BoardStates states = new BoardStates();

    public static void main(String args[]) {

        boolean test = false;



        board.MakeBoard();
                while(!test) {
                    board.PrintBoard();
                    human.humanMove();
                    choice.getHumanMove();
                    board.MakeMove();
                    board.PrintBoard();
                    choice.clear();
                    states.makeAttackedSquares();
                    states.printBoard();
                    test = false;
                    System.out.println("Quit? Enter 1");
                    if(HumanPlayer.scan.nextInt() == 1)
                        test = true;
                  //  HumanPlayer.validChoice = false;
                }
    }

    public long[] giveCurrentBoard() {
        return(board.copyBoard());
    }



}
