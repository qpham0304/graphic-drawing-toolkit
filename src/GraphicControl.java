import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GraphicControl extends JPanel {

    private  DrawingBoard board;
    private  ConsoleView cview;
    private  GraphicView gview;
    private  ButtonAction buttonControl;
    private  MouseAction mouseControl;


    /*
    create a list of buttons to be chosen by user
    create these buttons as public instance variables so that listener class could get access to them when needed
    set layout for the button at the bottom right of the panel
     */

    // list of shape buttons
    static JRadioButton circleButton = new JRadioButton("Circle");
    static JRadioButton arrowButton = new JRadioButton("Arrow");
    static JRadioButton diamondButton = new JRadioButton("Diamond");

    // list of fuction buttons
    static JButton selectButton = new JButton("Select");
    static JButton drawButton = new JButton("Draw");
    static JButton deleteButton = new JButton("Delete");

    /**
     * construct a GraphicControl panel which displays almost all the graphic interfaces for user
     */
    public GraphicControl(){
        board = new DrawingBoard();
        cview = new ConsoleView(board);
        gview = new GraphicView(board);
        setLayout(new BorderLayout());

        board.addView(cview);
        board.addView(gview);

        gview.setPreferredSize(new Dimension(1000, 700));
        gview.setBackground(Color.lightGray);

        // group shape buttons together so that user cannot select 2 button at the same time
        ButtonGroup group = new ButtonGroup();
        group.add(circleButton);
        group.add(arrowButton);
        group.add(diamondButton);

        // create a buttonPanel to gather all the buttons together
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,3));
        buttonPanel.setPreferredSize(new Dimension(300, 70));

        // set color for shape buttons
        circleButton.setBackground(Color.YELLOW);
        arrowButton.setBackground(Color.RED);
        arrowButton.setForeground(Color.WHITE); // set a contrast color for the text in the button to help user read easier
        diamondButton.setBackground(Color.CYAN);

        //add those buttons to the buttonPanel
        buttonPanel.add(circleButton);
        buttonPanel.add(arrowButton);
        buttonPanel.add(diamondButton);
        buttonPanel.add(selectButton);
        buttonPanel.add(drawButton);
        buttonPanel.add(deleteButton);

        // create a right panel and place it to the right of the main panel
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(300, 700));   // the height is not important since
        rightPanel.setBackground(Color.GRAY);                                // it will be defined by the graphic view panel
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        rightPanel.add(cview, BorderLayout.CENTER);
//        rightPanel.add(scrollPane, BorderLayout.EAST);

        // set up a list of listeners for all of the buttons
        buttonControl = new ButtonAction(board);
        circleButton.addActionListener(buttonControl);
        arrowButton.addActionListener(buttonControl);
        diamondButton.addActionListener(buttonControl);

        selectButton.addActionListener(buttonControl);
        drawButton.addActionListener(buttonControl);
        deleteButton.addActionListener(buttonControl);

        /*
        set up the mouse listener to track mouse input
         */
        mouseControl = new MouseAction(board, buttonControl);
        gview.addMouseListener(mouseControl);
        gview.addMouseMotionListener(mouseControl);

        ColorPanelControl colorPanel = new ColorPanelControl(board);

        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, colorPanel, rightPanel);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, gview, colorPanel);
        this.add(splitPane1, BorderLayout.EAST);
        this.add(splitPane2, BorderLayout.CENTER);
    }
}

/**
 * this class handles the button click action for the GraphicControl window
 */
class ButtonAction implements ActionListener{

    private DrawingBoard board;
    private String strShape;
    private String strFuction;

    public ButtonAction(DrawingBoard board){
        this.board = board;
    }

    @Override
    public void actionPerformed(ActionEvent e){

        /*
        set the state of all the buttons to true so that they are all available to be clicked
         */
        GraphicControl.circleButton.setEnabled(true);
        GraphicControl.arrowButton.setEnabled(true);
        GraphicControl.diamondButton.setEnabled(true);

        GraphicControl.selectButton.setEnabled(true);
        GraphicControl.drawButton.setEnabled(true);
        GraphicControl.deleteButton.setEnabled(true);

        if(e.getActionCommand().equals("Circle")) {
            strShape = "Circle";
            GraphicControl.circleButton.setEnabled(false);
            GraphicControl.drawButton.doClick();    // automatically choose draw button whenever user choose a shape to be drawn
        }
        else if(e.getActionCommand().equals("Arrow")) {
            strShape = "Arrow";
            GraphicControl.arrowButton.setEnabled(false);
            GraphicControl.drawButton.doClick();    // automatically choose draw button whenever user choose a shape to be drawn
        }
        else if(e.getActionCommand().equals("Diamond")) {
            strShape = "Diamond";
            GraphicControl.diamondButton.setEnabled(false);
            GraphicControl.drawButton.doClick();    // automatically choose draw button whenever user choose a shape to be drawn
        }

        if(e.getActionCommand().equals("Select")) {
            strFuction = "Select";
            GraphicControl.selectButton.setEnabled(false);
        }
        else if(e.getActionCommand().equals("Draw")) {
            strFuction = "Draw";
            GraphicControl.drawButton.setEnabled(false);
        }
        else if(e.getActionCommand().equals("Delete")) {
            strFuction = "Delete";
            try{
                board.removeShape();    // whenever user click the delete button, delete the selected shape
            } catch(Exception ex){
                System.out.println("The board is empty, there is no shape to remove");
            }
        }
    }

    /**
     * get access to the state of the button
     * @return a string represent the state of the Shape button
     */
    public String getStringShape(){
        return strShape;
    }

    /**
     * get access to the state of the button
     * @return a string represent the state of the function button
     */
    public String getStringFunction(){
        return strFuction;
    }

}

/**
 * this class handles the mouse click action for the GraphicControl window
 */
class MouseAction implements MouseListener, MouseMotionListener{

    private DrawingBoard board;
    private ButtonAction buttonControl;
    private int prevX, prevY;
    private int newX, newY;

    /**
     * construct a mouseAction that receive all the arguments from the main controller
     * @param board retrieves all the behaviors from the drawingBoard
     * @param buttonControl retrieves the states of all the buttons from the button listener
     */
    public MouseAction(DrawingBoard board, ButtonAction buttonControl){
        super();
        this.board = board;
        this.buttonControl = buttonControl;
        prevX = newX = 0;
        prevY = newY = 0;
    }

    /**
     * whenever the mouse is clicked, performs a list of actions depending on the decision received from mouseBehave method
     * @param e keeps track of the action event from the mouse
     */
    @Override
    public void mouseClicked(MouseEvent e){
        try{
            mouseBehave(e.getX(), e.getY());
        } catch(Exception ex){
            System.out.println("Please, select a shape to be Drawn");
        }
    }

    /**
     * get the first coordinate when the mouse is pressed down for the first time
     * @param e retrieves the event input from the mouse
     */
    @Override
    public void mousePressed(MouseEvent e){
        prevX = e.getX();
        prevY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e){
    }

    @Override
    public void mouseEntered(MouseEvent e){

    }

    @Override
    public void mouseExited(MouseEvent e){

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        /*
        update new coordinate when client starts dragging from the first mouse pressed point to the new position
         */
        newX = e.getX();
        newY = e.getY();
        try{
            if(buttonControl.getStringFunction().equals("Select")){

                board.selectShape(e.getX(), e.getY());  // check whether the mouse press is on the selected shape

                if(board.getShape() != null)    // precondition check if mouse press coordinate is not on the shape
                    board.moveShape(newX - prevX, newY - prevY);
            }
        }catch(Exception ex){
            System.out.println("No shape has been drawn in the board yet");
        };


        /*
        update new coordinate when the shape is shifted to the new position
         */
        prevX = newX;
        prevY = newY;
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /**
     * create a method mouseClicked to help deciding what should be done when the mouse is clicked
     * mouse clicked could be either select a shape or draw a new shape so we need to help it decide its behavior
     */
    private void mouseBehave(int x, int y){

        /*
        select the shape if the clicked point is on the shape and always set default action to be select
         */
        board.selectShape(x, y);
        if(board.getShape() == null)
            System.out.println("No shape is currently selected");
        else
            System.out.println(board.getShape());

        /*
        draw a shape if a specific type of shape and the draw button is selected
         */
        if(buttonControl.getStringShape().equals("Circle") && buttonControl.getStringFunction().equals("Draw")) {
            board.addShape(new Circle(x, y, 50));
        }
        else if(buttonControl.getStringShape().equals("Arrow") && buttonControl.getStringFunction().equals("Draw")){
            board.addShape(new Arrow(x, y, 160, 40));
        }
        else if(buttonControl.getStringShape().equals("Diamond") && buttonControl.getStringFunction().equals("Draw")){
            board.addShape(new Diamond(x, y, 100, 50));
        }
    }
}