public class King extends Piece{
    public King(PieceColor color,Position position){
        super(color,position);
    }

    @Override
    public boolean isValidMove(Position newPosition,Piece[][] board){
        //check move
        int rowDiff=Math.abs(newPosition.getRow()-position.getRow());
        int colDiff=Math.abs(newPosition.getColumn()-position.getColumn());
        boolean one= (colDiff<=1 && rowDiff<=1 && !(colDiff==0 && rowDiff==0));
        if(!one) return  false;
        //check target
        Piece target=board[newPosition.getRow()][newPosition.getColumn()];
        return target==null || target.getPieceColor()!=this.getPieceColor();


    }
}
