import javax.swing.JFrame;

/**
 * @author Carlos Luz Junior
 */
public class SteeringBehaviors extends JFrame {

    public SteeringBehaviors() {
        add(new Board());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Java Steering Behaviors");
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SteeringBehaviors();
    }
}