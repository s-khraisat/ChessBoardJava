public class Bishop extends Piece{
    public Bishop(PieceColor color,Position position){
        super(color,position);
    }

    @Override
    public boolean isValidMove(Position newPosition,Piece[][] board){
        //check the move is diagonal
        int rowDiff=Math.abs(newPosition.getRow()-position.getRow());
        int colDiff=Math.abs(newPosition.getColumn()-position.getColumn());
        if(rowDiff!=colDiff) return false;

        //check the path is empty
        int rows= newPosition.getRow()>position.getRow()?1:-1;
        int cols= newPosition.getColumn()>position.getColumn()?1:-1;
        int steps=rowDiff-1;
        for(int i=1;i<=steps;i++)
            if(board[position.getRow()+i*rows][position.getColumn()+i*cols]!=null) return false;
        //check target
        Piece target=board[newPosition.getRow()][newPosition.getColumn()];
        if(target==null) return true;
        else return target.getPieceColor()!=this.getPieceColor();
    }
}
