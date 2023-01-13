import java.awt.*;

public class Diamond extends AbstractShape {

    public Diamond(int apexX, int apexY, int height, int width) {
        super(apexX - width / 2, apexY, height, width);
        if(height % 2 != 0 || height < 10 || width % 2 != 0 || width < 10)
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
        declare a set of points to be used to calculate the vector product for the triangle check
        declare these variables outside the method so that drawShape method can get access to
        these variables which makes the algorithm looks more organized
        */
        int x1 = super.getX() + super.getWidth() / 2;
        int y1 = super.getY();
        int x2 = super.getX();
        int y2 = super.getY() + super.getHeight()/2;
        int x3 = super.getX() + super.getWidth();
        int y4 = super.getY() + super.getHeight();

        /*
        separate the diamond shape into 2 opposite triangles
        check whether the point lies inside any of those triangles
        check whether the point lies inside the top and the bottom triangle
        check whether the point lies in the triangle by adding another dimension and using cross product and check its sign
         */
        if(y >= y1 && y <= y2)
            return ((x1-x)*(y2-y) - (y1-y)*(x2-x) <= 0 && (x1-x)*(y2-y) - (y1-y)*(x3-x) >= 0);

        if(y >= y2 && y <= y4)
            if((x1-x)*(y2-y) - (y4-y)*(x2-x) >= 0 && (x1-x)*(y2-y) - (y4-y)*(x3-x) <= 0)
            return true;

        return false;   // return false otherwise
    }

    /**
     * this methods represents all the information of the shape
     * @return a string represents all the states of the shape
     */
    @Override
    public String toString(){
        return "Shape: Diamond\n" + super.toString();
    }

    /**
     * Display the diamond shape on the given graphic context
     * @param g is the graphic con text where the shape should be drawn
     */
    public void drawShape(Graphics g){

        int[] x = {getX() + getWidth() / 2, getX(), getX() + getWidth() / 2, getX() + getWidth()};
        int[] y = {getY(), getY() + getHeight()/2, getY() + getHeight(), getY() + super.getHeight()/2};

        Graphics2D g2 = (Graphics2D) g;

        if(isSelected()){
            g2.setColor(Color.green);
            g2.setStroke(new BasicStroke(5));
            g2.drawPolygon(x, y, 4);
        }

        g2.setColor(Color.BLACK);
        g2.setColor(super.getColor());
        g2.fillPolygon(x, y, 4);
    }
}
