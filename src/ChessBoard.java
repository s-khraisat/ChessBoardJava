

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class ChessBoard {
  private Piece[][] board;
  public ChessBoard(){
      this.board=new Piece[8][8];
        setupPieces();
  }
  private void setupPieces(){
      //place all rooks
      board[0][0]=new Rook(PieceColor.BLACK,new Position(0,0));
      board[0][7]=new Rook(PieceColor.BLACK,new Position(0,7));
      board[7][0]=new Rook(PieceColor.WHITE,new Position(7,0));
      board[7][7]=new Rook(PieceColor.WHITE,new Position(7,7));

      //place all knights
      board[0][1]=new Knight(PieceColor.BLACK,new Position(0,1));
      board[0][6]=new Knight(PieceColor.BLACK,new Position(0,6));
      board[7][1]=new Knight(PieceColor.WHITE,new Position(7,1));
      board[7][6]=new Knight(PieceColor.WHITE,new Position(7,6));

      //place all bishops
      board[0][2]=new Bishop(PieceColor.BLACK,new Position(0,2));
      board[0][5]=new Bishop(PieceColor.BLACK,new Position(0,5));
      board[7][2]=new Bishop(PieceColor.WHITE,new Position(7,2));
      board[7][5]=new Bishop(PieceColor.WHITE,new Position(7,5));

      //place all queens
      board[0][3]=new Queen(PieceColor.BLACK,new Position(0,3));
      board[7][3]=new Queen(PieceColor.WHITE,new Position(7,3));

      //place all kings
      board[0][4]=new King(PieceColor.BLACK,new Position(0,4));
      board[7][4]=new King(PieceColor.WHITE,new Position(7,4));

      //place all pawns
      for(int i=0;i<8;i++){
          board[1][i]=new Pawn(PieceColor.BLACK,new Position(1,i));
          board[6][i]=new Pawn(PieceColor.WHITE,new Position(6,i));
      }
  }
public void movePiece(Position start,Position end){
      if(board[start.getRow()][start.getColumn()]!=null && board[start.getRow()][start.getColumn()].isValidMove(end,board)){
          board[end.getRow()][end.getColumn()]=board[start.getRow()][start.getColumn()];
          board[end.getRow()][end.getColumn()].setPosition(end);
          board[start.getRow()][start.getColumn()]=null;
      }

}
public Piece getPiece(int row,int col){ return board[row][col];}

    public Piece[][] getBoard() {
        return board;
    }
    public void setPiece(int row,int col,Piece piece){
      board[row][col]=piece;
      if(piece!=null){
          piece.setPosition(new Position(row,col));
      }
    }
}