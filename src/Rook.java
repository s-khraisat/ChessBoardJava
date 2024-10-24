public class Rook extends Piece{
    public Rook(PieceColor color,Position position){
        super(color,position);
    }

    @Override
    public boolean isValidMove(Position newPosition,Piece[][] board){
        //horizontally
        if(position.getRow()==newPosition.getRow()){
            int startCol=Math.min(position.getColumn(),newPosition.getColumn())+1;
            int endCol=Math.max(position.getColumn(),newPosition.getColumn());
            for(int c=startCol;c<endCol;c++)
                if(board[position.getRow()][c]!=null) return false;
        }else  //vertically
            if(position.getColumn()==newPosition.getColumn()){
                int startRow=Math.min(position.getRow(),newPosition.getRow())+1;
                int endRow=Math.max(position.getRow(),newPosition.getRow());
                for(int r=startRow;r<endRow;r++)
                    if(board[r][position.getColumn()]!=null) return false;
            }
            else return false;//invalid move
            //destination piece
            Piece destinationPiece=board[newPosition.getRow()][newPosition.getColumn()];
            if(destinationPiece==null) return true;
            else if(destinationPiece.getPieceColor()!=this.getPieceColor()) return true;
            //the same color
            return false;

    }
}
