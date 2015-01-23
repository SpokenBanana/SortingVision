
import java.awt.AlphaComposite;
import java.util.Random;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Sorter {

	protected double CELL_WIDTH = 5;
	protected double SCALE = CELL_WIDTH;


	// Cells are what we will use to sort
	protected class Cell extends Rectangle {
		public int value;
		public boolean selected;

		public Cell(int _value) {
			super((int)(_value * CELL_WIDTH), (int) (900 - (int)(_value * SCALE)), (int)CELL_WIDTH, (int)(_value * SCALE));
			value = _value;
			selected = false;
		}

		public void ChangeValue(int v) {
			value = v;
			y = (int) (900 - value * SCALE);
			height = (int) (value * SCALE);
		}
		public int getValue() {
			return value;
		}
		public void Draw(Graphics g) {
			g.setColor(selected ? Color.green : Color.white);
			g.fillRect(x, y, width, height);
		}
		public void DrawOutLine(Graphics g) {
			g.setColor(Color.black);
			g.drawRect(x, y, width, height);
		}
	}
	
	protected Cell[] cells;
	protected int current, comparisons, swaps;
	
	protected boolean finished;
	
	/* 
	 * step is where the implementation will go, it will usually be very
	 * modified to be able to "step" through each step of the algorithm but 
	 * the algorithms itself will be same and work the same way as if we were
	 * to write the normal version of it
	*/
	public abstract void step();
	
	public abstract int getDefaultAmount();


	public void draw(Graphics g) {
		for (Cell cell : cells) 
			cell.Draw(g);
		
		// they would be too small to outline after this point
		if (cells.length <= 180)
			for (Cell cell: cells)
				cell.DrawOutLine(g);
	}
	
	public void shuffle() {
		finished = false;
		Random random = new Random();
		for (int i = 0; i < 1900; i++) {
			int first = random.nextInt(cells.length);
			int second = 0;
			do {
				second = random.nextInt(cells.length);
			} while (second == first);
			swap(first, second);
		}
	}
	
	public void swap(int first, int second) {
		int temp = cells[first].value;
		cells[first].ChangeValue(cells[second].value);
		cells[second].ChangeValue(temp);
	}
	
	public void changeCount(int count) {
		reset();
		SCALE = CELL_WIDTH = 900.0 / count;
		cells = new Cell[count];
		for (int i =0; i < cells.length; i++)
			cells[i] = new Cell(i);
	}
	
	public int getComparisions(){
		return comparisons;
	}
	
	public int getSwaps() {
		return swaps;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	protected boolean isSorted() {
		for (int i = 1; i < cells.length; i++)
			if (cells[i-1].value > cells[i].value) return false;
		return true;
	}
	protected boolean isSorted(Cell[] cells) {
		for (int i = 1; i < cells.length; i++)
			if (cells[i] == null || cells[i-1] == null || cells[i-1].value > cells[i].value) return false;
		return true;
	}
	
	
	protected void resetAll(){
		for (Cell cell : cells) {
			cell.selected = false;
		}
	}
	
	public void reset() {
		finished = false;
		swaps = 0;
		comparisons = 0;
		shuffle();
	}
	
	public void drawHeader(Graphics g, String text) {
		Graphics2D g2 = (Graphics2D) g;
		Composite old = g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
		g2.setPaint(Color.BLACK);
		g2.fill(new Rectangle(0,0,700,30));
		g2.setComposite(old);
		g.setColor(Color.white);
		g.setFont(new Font("Sans Serif", Font.PLAIN, 20));
		g.drawString(text, 10, 20);
	}
}
