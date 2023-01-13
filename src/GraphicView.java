import javax.swing.*;
import java.awt.*;

public class GraphicView extends JPanel implements DrawingView {

    private DrawingBoard board;

    public GraphicView(DrawingBoard board){
        if(board == null)
            throw new NullPointerException();
        this.board = board;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Shape s : board.getCopyList()){
            s.drawShape(g);
        }
    }

    public void boardChanged(){
        repaint();
    }
}
