import javax.swing.*;



public class GraphicsEditorApp {
    public static void main(String[] args){
        GraphicControl control = new GraphicControl();
        JFrame frame = new JFrame("Graphic Editor App");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(control);
        frame.pack();
        frame.setVisible(true);
    }
}
