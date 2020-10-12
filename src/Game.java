import javax.swing.JFrame;

public class Game extends JFrame{
	
	public Game() {
		add(new BrickBoard());
		setTitle("Board");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
	}
	
	public static void main(String[] args) {
        new Game();
    }
}
