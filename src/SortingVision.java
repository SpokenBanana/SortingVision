
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class SortingVision extends JPanel {
	
	public final int CELL_AMOUNT = 180;
	
	public Sorter sorter;
    
    public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SortingVision v = new SortingVision();
		v.setPreferredSize(new Dimension(900,900));
		frame.getContentPane().add(v);
		frame.add(new ToolBar(v), BorderLayout.NORTH);
		frame.revalidate();
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		v.run();
    }
	
	public SortingVision() {
		sorter = new MergeSorter();
		sorter.shuffle();
	}
	
	public void run() {
		while (true) {
			Update();
			repaint();
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
    
    // we'll just use bubble sort for now
    public void Update() {
		sorter.step();
    }
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(10,90,155));
		g.fillRect(0,0,900,900);
		sorter.draw(g);
    }
	
}
