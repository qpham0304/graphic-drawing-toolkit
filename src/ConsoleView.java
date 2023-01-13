import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


public class ConsoleView extends JPanel implements DrawingView {

    private DrawingBoard board;
    private JTextArea textArea;
    public ConsoleView(DrawingBoard board){

        if(board == null)
            throw new NullPointerException();

        this.board = board;
        textArea = new JTextArea();
//        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.BLUE);
        textArea.setFont(textArea.getFont().deriveFont(Font.BOLD, textArea.getFont().getSize()));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 800));


        /*
        adjustment to make the scroll bar always scroll down to the bottom to check for updates
         */
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });

        this.add(scrollPane,BorderLayout.CENTER);
        displayText();
    }

    private void displayText(){

        System.out.println("Current number of shapes in the board: " + board.getCopyList().size());
        textArea.append("Current number of shapes in the board: " + board.getCopyList().size() + "\n\n");

        if(board.getShape() == null) {
            System.out.println("No shape is currently selected\n");
            textArea.append("No Shape is currently selected \n\n");
        }

        else {
            System.out.println(board.getShape());
            textArea.append(board.getShape().toString() + "\n\n");
        }
    }

    @Override
    public void boardChanged(){
        displayText();
    }
}
