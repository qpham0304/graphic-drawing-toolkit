import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorPanelControl extends JPanel {

    JColorChooser colorPanel;
    DrawingBoard board;

    /**
     * construct a ColorPanel controller to set the color for the selected shape
     * @param board get the behavior from the DrawingBoard model
     */
    public ColorPanelControl(DrawingBoard board){
        super(new BorderLayout());
        this.board = board;
        colorPanel = new JColorChooser();

        /*
        create a button and register its behavior to change the color for all the shapes
        set its background to black and set the text font to white for more contrast
         */
        JButton button = new JButton("Set color for all shape");
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setAllColor(colorPanel.getColor());
            }
        });

        /*
        get the color from the color panel and change the color of the selected shape
        listen to the change in color and redraw the selected shape with the chosen color
         */
        colorPanel.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Color color = colorPanel.getColor();
                button.setBackground(colorPanel.getColor()); // the color of the button changes depending on the chosen color from the color panel
                if(board.getShape() != null)
                    board.setColor(color);
            }
        });

        /*
        add all the components to this panel and set its dimension for proper layout
         */
        this.setPreferredSize(new Dimension(300, 200));
        this.add(colorPanel, BorderLayout.CENTER);
        this.add(button, BorderLayout.SOUTH);
    }
}


