
import java.awt.Graphics;


public class BubbleSorter extends Sorter{
	
	int lastStop;
	public BubbleSorter(int amount) {
		SCALE = CELL_WIDTH = 900.0 / amount;
		cells = new Cell[amount];
		for (int i = 0; i < cells.length; i++)
			cells[i] = new Cell(i);
		lastStop = cells.length;
		current = 1;
	}
	public BubbleSorter(){
		this(60);
	}

	@Override
	public void step() {
		if (!finished) {
			resetAll();
			cells[current-1].selected = true;
			cells[current].selected = true;
			if (cells[current-1].getValue() > cells[current].getValue()){
				swap(current-1, current);
				comparisons++;
				swaps++;
			}
			if (++current >= lastStop){
				comparisons++;
				current = 1;
				lastStop--;
			}
			finished = isSorted();
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		drawHeader(g, "Bubble Sort  Comparisions: " + Integer.toString(comparisons) +
			"  Swaps: " + Integer.toString(swaps));
	}
	
	@Override
	public void reset() {
		super.reset();
		lastStop = cells.length;
		current = 1;
	}
	
	@Override
	public void changeCount(int amount) {
		super.changeCount(amount);
		reset();
	}

	@Override
	public int getDefaultAmount() {
		return 60;
	}
	
}
