public class Queen extends Piece{
    public Queen(PieceColor color,Position position){
        super(color,position);
    }

    @Override
    public boolean isValidMove(Position newPosition,Piece[][] board){
        //check moving
        if(newPosition==this.position) return false;
        //check the move
        int rowDiff=Math.abs(newPosition.getRow()-position.getRow());
        int colDiff=Math.abs(newPosition.getColumn()-position.getColumn());
        boolean line=(newPosition.getRow()==position.getRow())||(newPosition.getColumn()==position.getColumn());
        boolean diag=colDiff==rowDiff;
        if(!diag&&!line) return false;
        //check the path
        int rows=Integer.compare(newPosition.getRow(),position.getRow());
        int cols=Integer.compare(newPosition.getColumn(), position.getColumn());
        int currow=position.getRow()+rows;
        int curcol=position.getColumn()+cols;
        while(currow!= newPosition.getRow() && curcol!= newPosition.getColumn()){
            if(board[currow][curcol]!=null) return false;
            currow+=rows;
            curcol+=cols;
        }
        //check the target
        Piece target=board[newPosition.getRow()][newPosition.getColumn()];
        return target==null || target.getPieceColor()!=this.getPieceColor();
    }
}
