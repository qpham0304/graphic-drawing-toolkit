import java.awt.*;
import java.util.Random;

public class Circle extends AbstractShape {

    public Circle(int centerX, int centerY, int radius) {
        super(centerX - radius, centerY - radius, radius * 2, radius * 2);

    }

    /**
     * @param x represents the x-coordinate of the point
     * @param y represents the y-coordinate of the point
     * @return true if the point lies inside the arrow
     */
    @Override

    /*
    check whether the point lies inside the circle by calculating the distance from that
    point to the center of the circle and then compare it with the length of the radius.
    If that distance is greater than the radius, the point lies outside the circle
     */
    public boolean isOn(int x, int y) {
        if(getHeight()/2 >= Math.sqrt((x - (super.getX() + super.getHeight()/2)) * (x - (super.getX() + super.getHeight()/2)) +
                                      (y - (super.getY() + super.getHeight()/2)) * (y - (super.getY() + super.getHeight()/2))))
            return true;
        return false;
    }

    /**
     * this methods represents all the information of the shape
     * @return a string represents all the states of the shape
     */
    @Override
    public String toString(){
        return "Shape: Circle\n" + super.toString();
    }

    /**
     * Display the circle shape on the given graphic context
     * @param g is the graphic con text where the shape should be drawn
     */
    public void drawShape(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        if(isSelected()){
            g2.setColor(Color.green);
            g2.setStroke(new BasicStroke(5));
            g2.drawOval(super.getX(),super.getY(),super.getHeight(),super.getHeight());
        }

        g2.setColor(Color.BLACK);
        g2.setColor(super.getColor());
        g2.fillOval(super.getX(),super.getY(),super.getHeight(),super.getHeight());
    }
}
