import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessGameGui extends JFrame {
    //initializing components
    private final ChessSquareComponent[][] squares =new ChessSquareComponent[8][8];
    private final ChessGame game=new ChessGame();
    private final Map<Class<? extends Piece>,String> pieceUnicodeMap= new HashMap<>(){
        {
            put(Pawn.class,"\u265F");
            put(Rook.class,"\u265C");
            put(Knight.class,"\u265E");
            put(Bishop.class,"\u265D");
            put(Queen.class,"\u265B");
            put(King.class,"\u265A");
        }
    };
    public ChessGameGui(){
        setTitle("Chess Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8,8));
        initializeBoard();
        addResetOption();
        pack();
        setVisible(true);
    }

    private void initializeBoard(){
        for(int r=0;r< squares.length;r++){
            for(int c=0;c<squares[r].length;c++){
                final int finr=r;
                final int finc=c;
                ChessSquareComponent square=new ChessSquareComponent(r,c);
                square.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent e){
                        handleSquareClick(finr,finc);
                    }
                });
                add(square);
                squares[r][c]=square;
            }
        }
        refreshBoard();
    }
    private void refreshBoard(){
        ChessBoard board=game.getBoard();
        for(int r=0;r<8;r++){
            for(int c=0;c<8;c++){
                Piece p= board.getPiece(r,c);
                if(p != null){
                    String symbol=pieceUnicodeMap.get(p.getClass());
                    Color color=(p.getPieceColor()==PieceColor.WHITE)?Color.WHITE:Color.BLACK;
                    squares[r][c].setPieceSymbol(symbol,color);
                }else{squares[r][c].clearPieceSymbol();}
            }
        }
    }
    private void checkGameState(){
        PieceColor curr=game.getCurrentPlayerColor();
        boolean check= game.isInCheck(curr);
        if(check){JOptionPane.showMessageDialog(this,curr+" is in check!");}
    }
    private void handleSquareClick(int row,int col){
        boolean moveRes=game.handleSquareSelection(row,col);
        clearHighlights();
        if(moveRes){
            refreshBoard();
            checkGameState();
            checkGameOver();
        }else if(game.isPieceSelected()){
            highlightLegalMoves(new Position(row,col));
        }
        refreshBoard();
    }
    private void resetGame(){
        game.resetGame();
        refreshBoard();
    }

    private void addResetOption(){
        JMenuBar bar=new JMenuBar();
        JMenu menu=new JMenu("Game");
        JMenuItem res=new JMenuItem("Reset");
        res.addActionListener(e->resetGame());
        menu.add(res);
        bar.add(menu);
        setJMenuBar(bar);
    }

    private void highlightLegalMoves(Position pos){
        List<Position> legalMoves=game.getLegalMovesForPieceAt(pos);
        for(Position m:legalMoves){
           squares[m.getRow()][m.getColumn()].setBackground(Color.GREEN);
        }
    }
    private void clearHighlights(){
        for(int r=0;r<8;r++){
            for(int c=0;c<8;c++){
                squares[r][c].setBackground(((r+c)%2==0)?Color.LIGHT_GRAY:new Color(205,133,63));
            }
        }
    }

    private void checkGameOver(){
        if(game.isCheckMate(game.getCurrentPlayerColor())){
            int response=JOptionPane.showConfirmDialog(this,"Checkmate! Would you like to play again?","Game Over",JOptionPane.YES_NO_OPTION);
            if(response==JOptionPane.YES_OPTION) resetGame();
            else System.exit(0);
        }
    }


    public  static void main(String[] args){
        SwingUtilities.invokeLater(ChessGameGui::new);
    }
}
