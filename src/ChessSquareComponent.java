import javax.swing.*;
import java.awt.*;

public class ChessSquareComponent extends JButton {
    private int row;
    private int col;
    public ChessSquareComponent(int row,int col){
        this.row=row;
        this.col=col;
        initButton();
    }
    private void initButton(){
        //size
        setPreferredSize(new Dimension(64,64));
        //color
        if((row+col)%2==0) setBackground(Color.lightGray);
        else setBackground(new Color(205,133,63));
        //alignment of symbol
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        //font
        setFont(new Font("Serif",Font.BOLD,36));
    }
    public void setPieceSymbol(String symbol,Color color){
        this.setText(symbol);
        this.setForeground(color);
    }
    public void clearPieceSymbol(){
        this.setText("");
    }
}
