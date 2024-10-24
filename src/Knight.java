public class Knight extends Piece{
    public Knight(PieceColor color,Position position){
        super(color,position);
    }

    @Override
    public boolean isValidMove(Position newPosition,Piece[][] board){
        if(newPosition==this.position) return false;

        int rowDiff=Math.abs(newPosition.getRow()-position.getRow());
        int colDiff=Math.abs(newPosition.getColumn()-position.getColumn());
        boolean isValid=(rowDiff==2 && colDiff==1)||(rowDiff==1 && colDiff==2);
        if(!isValid) return false;
        //valid move then check the target
        Piece target=board[newPosition.getRow()][newPosition.getColumn()];
        if(target==null) return true;
        else return target.getPieceColor()!=this.getPieceColor();
    }
}
