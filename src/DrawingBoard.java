import java.awt.*;
import java.util.ArrayList;

public class DrawingBoard {

    /*
    initialize an arraylist that is going to be used to hold the list of shapes
    create variable selectedShape to be used to control the state of the shape
     */
    private ArrayList<Shape> listContain;
    private Shape selectedShape;
    private ArrayList<DrawingView> view;

    public DrawingBoard(){
        selectedShape = null;
        listContain = new ArrayList<>();
        view = new ArrayList<>();
    }

    public void addView(DrawingView view){
        if(view == null)
            throw new IllegalArgumentException("View cannot be null");
        this.view.add(view);
    }
    /**
     * this method adds a shape into the drawing board
     * @param selectedShape is used to add a shape into the arraylist
     */
    public void addShape(Shape selectedShape){
        if(this.selectedShape != null)      // if the old selected shape has already defined, set its state to unselected
            this.selectedShape.setSelected(false);
        selectedShape.setSelected(true);    // set the state of the shape to selected
        listContain.add(selectedShape);
        this.selectedShape = selectedShape;
        notifyViewers();
    }

    /**
     * this method selects the shape from the drawing board
     * @param x representes the x-coordinate of the point
     * @param y representes the y-coordinate of the point
     */
    public void selectShape(int x, int y){
        boolean found = false;
        for ( int i = listContain.size()-1; i >= 0; i-- )
        {
            if (listContain.get(i).isOn(x,y))
            {
                selectNewShape(listContain.get(i));
                found = true;
                notifyViewers();
                break;
            }
        }
        /*
        No Shape is the selected Shape if the point is not found.
         */
        if (!found)
        {
            notifyViewers();
            if (selectedShape != null)
                selectedShape.setSelected(false);
            selectedShape = null;
        }
    }
    
    public void selectNewShape (Shape s)
    {
        /*
        UPDATE 10/15/18: Made public so that other classes may select a new Shape without using a coordinate
        Specifically: The SaveController
         */
        if (s == null)
            throw new IllegalArgumentException("Shape cannot be null");

        if (selectedShape != null) {
            selectedShape.setSelected(false); // Deselect the previous Shape
        }

        selectedShape = s; // Change the selectedShape to the given Shape
        selectedShape.setSelected(true);
        /*
        Move the Shape to the top
         */
        listContain.remove(s);
        listContain.add(selectedShape);
    }

    /**
     * this method returns the reference of the selected shape to client
     * @return the reference to the selected shape and return null if no shape is selected
     */
    public Shape getShape(){
        return this.selectedShape;
    }

    /**
     * this method removes the selected shape and selects the top most shape
     * @throws IllegalStateException when no shape is selected
     */
    public void removeShape(){
        if(selectedShape == null)
            throw new IllegalStateException("No shape is currently selected");
        listContain.remove(selectedShape);
       
        if(!listContain.isEmpty()){
            selectedShape = listContain.get(listContain.size() - 1);
            selectedShape.setSelected(true);

        }
        
        else
            selectedShape = null;

        notifyViewers();
    }

    /**
     * this method allows client to set and change the color of the selected shape in the drawing board
     * @param color is used to pass a color to the selected shape
     * @throws IllegalArgumentException when null object is passed
     * @throws IllegalStateException when no shape is selected
     */
    public void setColor(Color color){
        if(color == null)
            throw new IllegalArgumentException();
        if(selectedShape == null)
            throw new IllegalStateException("No shape is currently selected");
        selectedShape.setColor(color);
        notifyViewers();
    }

    /**
     * this method allows client to set color to all shapes at a time
     */
    public void setAllColor(Color color){
        if(color == null)
            throw new IllegalArgumentException();
        for(Shape x: listContain)
            x.setColor(color);
        notifyViewers();
    }

    /**
     * this methods allows client to move the selected shape
     * @param x representes the amount of move in the x-direction
     * @param y representes the amount of move in the y-direction
     * @throws IllegalStateException when no shape is selected
     */
    public void moveShape(int x, int y){
        if(selectedShape == null)
            throw new IllegalStateException("No shape is currently selected");
        selectedShape.shiftUpperLeftBy(x, y);
        notifyViewers();
    }

    /**
     * this methods returns a copy of the list of shapes from the drawing board
     * @return a list of shapes from the DrawingBoard
     */
    public ArrayList<Shape> getCopyList(){
        return new ArrayList<Shape>(listContain);
    }

    private void notifyViewers(){
        for(DrawingView v : view){
            v.boardChanged();
        }
    }
}
