import java.awt.*;

public interface Shape {
    boolean isOn(int x, int y);
    boolean isSelected();
    void setSelected(boolean b);
    void setColor(Color color);
    Color getColor();
    void shiftUpperLeftBy(int deltaX, int deltaY);
    void moveUpperLeftTo(int newX, int newY);
    int getX();
    int getY();
    String toString();
    void drawShape(Graphics g);
}
