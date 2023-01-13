import java.awt.*;

public abstract class AbstractShape implements Shape {

    private int x;
    private int y;
    private boolean select = false;
    private Color color;
    private int height;
    private int width;

    public AbstractShape(int x, int y, int height, int width){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean isSelected() {
        return select;
    }

    @Override
    public void setSelected(boolean b) {
        this.select = b;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void shiftUpperLeftBy(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
    }

    @Override
    public void moveUpperLeftTo(int newX, int newY) {
        x = newX;
        y = newY;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    @Override
    public String toString(){
        return "upperleft: " + getX() + ", " + getY() + "\ncolor: "+ getColor() + "\nstatus: " + isSelected();
    }
}
