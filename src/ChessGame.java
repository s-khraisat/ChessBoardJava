import java.util.ArrayList;
import java.util.List;

public class ChessGame {
    private ChessBoard board;
    private boolean whiteTurn=true;

    public ChessGame(){
        this.board=new ChessBoard();
    }
    public ChessBoard getBoard() {
        return board;
    }

    public PieceColor getCurrentPlayerColor(){
        return whiteTurn?PieceColor.WHITE:PieceColor.BLACK;
    }
    public void resetGame(){
        this.board=new ChessBoard();
        this.whiteTurn=true;
    }

    private Position selectedPos;
    public boolean isPieceSelected(){
        return selectedPos!=null;
    }
    public boolean handleSquareSelection(int r,int c){
      if(selectedPos==null){
          Piece selectedPiece=board.getPiece(r,c);
          if(selectedPiece!=null && selectedPiece.getPieceColor()==(whiteTurn?PieceColor.WHITE:PieceColor.BLACK)){
              selectedPos = new Position(r,c);
              return false;
          }
      }else{
          boolean moveMade=makeMove(selectedPos,new Position(r,c));
          selectedPos=null;
          return moveMade;
      }  return false;
    }

    public boolean makeMove(Position start,Position end){
        //checking the piece existence and the turn
        Piece movingPiece= board.getPiece(start.getRow(),start.getColumn());
        if(movingPiece==null || movingPiece.getPieceColor() != (whiteTurn?PieceColor.WHITE:PieceColor.BLACK))
            return false;
        //check the move validity
        if(movingPiece.isValidMove(end,board.getBoard())){
            board.movePiece(start,end);
            whiteTurn=!whiteTurn;
            return true;
        }
        return false;

    }
public boolean isInCheck(PieceColor color){
        Position kingpos=findKingPosition(color);
        for(int r=0;r<board.getBoard().length;r++)
            for(int c=0;c<board.getBoard()[r].length;c++){
                Piece piece=board.getPiece(r,c);
                if(piece!=null && piece.getPieceColor()!=color)
                    if(piece.isValidMove(kingpos, board.getBoard()))
                        return  true;
            }
        return false;
}

public boolean isCheckMate(PieceColor color){
        //check the check
        if(!isInCheck(color)) return false;
        //try all steps the king can do
    Position kingpos=findKingPosition(color);
    King king=(King) board.getPiece(kingpos.getRow(), kingpos.getColumn());

    for(int rowf=-1;rowf<=1;rowf++) {
        for (int colf = -1; colf <= 1; colf++) {
            if (rowf == 0 && colf == 0)
                continue;
            //save the pos
            Position newpos=new Position(kingpos.getRow()+rowf, kingpos.getColumn()+colf);
            if(isPositionOnBoard(newpos) && king.isValidMove(newpos, board.getBoard()) && !wouldBeInCheck(color,kingpos,newpos))
                return false;
        }
    }
    return true;
}
    private Position findKingPosition(PieceColor color){
        for(int r=0;r<board.getBoard().length;r++)
            for(int c=0;c<board.getBoard()[r].length;c++){
                Piece piece=board.getPiece(r,c);
                if(piece instanceof King && piece.getPieceColor()==color)
                    return new Position(r,c);
            }

        throw new RuntimeException("King not found,which should not happen");
    }
    private boolean isPositionOnBoard(Position pos){
        return pos.getRow()>=0 &&pos.getRow()<board.getBoard().length && pos.getColumn()>=0 && pos.getColumn()<board.getBoard()[0].length;
    }

    private boolean wouldBeInCheck(PieceColor color,Position from,Position to){
        //simulate the move
        Piece temp= board.getPiece(to.getRow(),to.getColumn());
        board.setPiece(to.getRow(), to.getColumn(), board.getPiece(from.getRow(),from.getColumn()) );
        board.setPiece(from.getRow(), from.getColumn(), null);
        boolean inCheck=isInCheck(color);
        //undo the move
        board.setPiece(from.getRow(), from.getColumn(), board.getPiece(to.getRow(),to.getColumn()));
        board.setPiece(to.getRow(), to.getColumn(), temp);
        return inCheck;
    }
    public List<Position> getLegalMovesForPieceAt(Position pos){
        Piece p= board.getPiece(pos.getRow(), pos.getColumn());
        if(p==null) return new ArrayList<>();

        List<Position> legalMoves=new ArrayList<>();
        switch (p.getClass().getSimpleName()){
            case"Pawn":addPawnMoves(pos,p.getPieceColor(),legalMoves); break;
            case"Rook":addLineMoves(pos,new int[][]{{1,0},{-1,0},{0,1},{0,-1}},legalMoves); break;
            case"Knight":addSingleMoves(pos, new int[][]{{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}}, legalMoves);
                break;
            case"Bishop":addLineMoves(pos,new int[][]{{1,1},{-1,1},{1,-1},{-1,-1}},legalMoves); break;
            case"Queen":addLineMoves(pos,new int[][]{{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,1},{1,-1},{-1,-1}},legalMoves); break;
            case"King":addSingleMoves(pos,new int[][]{{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,1},{1,-1},{-1,-1}},legalMoves); break;}
        return legalMoves;
    }

    private void addLineMoves(Position p,int[][] dir,List<Position> l){
        for(int[]d:dir){
        Position newp=new Position(p.getRow()+d[0], p.getColumn()+d[1]);
        while(isPositionOnBoard(newp)){
            if(board.getPiece(newp.getRow(), newp.getColumn())==null){
                l.add(newp);
                newp=new Position(newp.getRow()+d[0], newp.getColumn()+d[1]);
            }else{
                if(board.getPiece(p.getRow(), p.getColumn()).getPieceColor()!=board.getPiece(newp.getRow(), newp.getColumn()).getPieceColor())
                    l.add(newp);
                break;
            }

        }}

    }

    private void addSingleMoves(Position p,int[][] dir,List<Position> l){
        for(int[]d:dir){
            Position newp=new Position(p.getRow()+d[0], p.getColumn()+d[1]);
            if(isPositionOnBoard(newp)&&(board.getPiece(newp.getRow(), newp.getColumn())==null ||board.getPiece(p.getRow(), p.getColumn()).getPieceColor()!=board.getPiece(newp.getRow(), newp.getColumn()).getPieceColor()))
                l.add(newp);
        }
    }

    private void addPawnMoves(Position p,PieceColor c,List<Position> l){
        int dir=c==PieceColor.WHITE?-1:1;
        //single move
        Position newp=new Position(p.getRow()+dir,p.getColumn());
        if(isPositionOnBoard(newp)&&board.getPiece(newp.getRow(), newp.getColumn())==null)
            l.add(newp);
        //first 2steps move
        if((board.getPiece(p.getRow(),p.getColumn()).getPieceColor()==PieceColor.WHITE &&p.getRow()==6)||(board.getPiece(p.getRow(),p.getColumn()).getPieceColor()==PieceColor.BLACK &&p.getRow()==1))
            newp=new Position(p.getRow()+2*dir,p.getColumn());
            Position inter=new Position(p.getRow()+dir, p.getColumn());
            if(isPositionOnBoard(newp)&&board.getPiece(newp.getRow(), newp.getColumn())==null&&board.getPiece(inter.getRow(), inter.getColumn())==null)
                l.add(newp);

            //capture
        int[]cols={p.getColumn()-1,p.getColumn()+1};
        for(int col:cols){
            newp=new Position(p.getRow()+dir,col);
            if(isPositionOnBoard(newp)&&board.getPiece(newp.getRow(), newp.getColumn())!=null&&board.getPiece(newp.getRow(), newp.getColumn()).getPieceColor()!=board.getPiece(p.getRow(), p.getColumn()).getPieceColor())
                l.add(newp);
        }
    }

}
