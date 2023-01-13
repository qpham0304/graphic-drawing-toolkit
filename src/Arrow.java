import java.awt.*;
public class Arrow extends AbstractShape {

    public Arrow(int tipX, int tipY, int length, int shaftThickness) {
        super(tipX - length, tipY - (shaftThickness * 3 / 2), length, shaftThickness * 3);
        if(length < shaftThickness * 4)
            throw new IllegalArgumentException();
    }



    /**
     * @param x represents the x-coordinate of the point
     * @param y represents the y-coordinate of the point
     * @return true if the point lies inside the arrow
     */
    @Override
    public boolean isOn(int x, int y) {

        /*
        separate the arrow shape into 2 simple shapes: rectangle and arrow
        check whether the point lies in the rectangle part of the arrow
        then check whether the point lies in the triangle part of the arrow
        getHeight() method now returns the length of the arrow
        getWidth() method now returns of the shaft thickness or the height of the arrow
         */

        /*
        declare a set of points to be used to calculate the vector product for the triangle check
        */
        int x1 = super.getX() + super.getHeight();
        int y1 = super.getY() + super.getWidth()/2;
        int x2 = super.getX() + super.getHeight() - super.getWidth() / 3 * 2;
        int y2 = super.getY();
        int y3 = super.getY() + super.getWidth();
        /*
         check whether the point lies in the rectangle by checking the coordinate of each side
         */
        if(x >= super.getX() && x <= x2
                && y >= super.getY() + super.getWidth() / 3 && y <= super.getY() +  super.getWidth() / 3 * 2 )
            return true;

        /*
        check whether the point lies in the triangle by adding another dimension and using cross product and check its sign
         */
        if(x <= x1 && x >= x2)
             return((x1-x)*(y2-y) - (y1-y)*(x2-x) <= 0 && (x1-x)*(y3-y) - (y1-y)*(x2-x) >= 0);

        /*
        return false otherwise
        */
        return false;

    }

    /**
     * this methods represents all the information of the shape
     * @return a string represents all the states of the shape
     */
    @Override
    public String toString(){
        return "Shape: Arrow\n" + super.toString();
    }

    /**
     * Display the arrow shape on the given graphic context
     * @param g is the graphic con text where the shape should be drawn
     */
    public void drawShape(Graphics g){

        int[] x = {getX() + getHeight(), getX() + getHeight() - getWidth() / 3 * 2, getX() + getHeight() - getWidth() / 3 * 2};
        int[] y = {getY() + getWidth()/2, getY(), getY() + getWidth()};

        Graphics2D g2 = (Graphics2D) g;

        if(isSelected()){
            g2.setColor(Color.green);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(getX(), getY() + getWidth()/3, getHeight() - getWidth() / 3 * 2, getWidth() / 3);
            g2.drawPolygon(x, y, 3);
        }

        g2.setColor(Color.BLACK);
        g2.setColor(super.getColor());
        g2.fillRect(getX(), getY() + getWidth()/3, getHeight() - getWidth() / 3 * 2, getWidth() / 3);
        g2.fillPolygon(x, y, 3);
    }
}
