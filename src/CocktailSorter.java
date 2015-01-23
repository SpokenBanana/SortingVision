
import java.awt.Graphics;


public class CocktailSorter extends Sorter {

	int minStop, maxStop, next, lastStop;
	
	public CocktailSorter(int amount) {
		SCALE = CELL_WIDTH = 900.0 /  amount;
		cells = new Cell[amount];
		for (int i = 0; i < cells.length; i++)
			cells[i] = new Cell(i);
		minStop = 0;
		next = -1;
		current = 1;
		maxStop = cells.length;
	}
	public CocktailSorter(){
		this(60);
	}
	@Override
	public void step() {
		if (!finished) {
			resetAll();
			cells[current-1].selected = true;
			cells[current].selected = true;
			if (cells[current-1].value > cells[current].value){
				swap(current-1, current);
				lastStop = current;
				swaps++;
				comparisons++;
			}
			current += next*-1;
			if (current >= maxStop) {
				next = 1;
				current--;
				maxStop = lastStop;
				comparisons++;
			}
			if (current <= minStop){ 
				next = -1;
				current++;
				minStop = lastStop-1;
				comparisons++;
			}
			if (minStop == maxStop)
				finished = true;
			else
				finished = isSorted();
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		drawHeader(g, "Cocktail Sort  Swaps: " + Integer.toString(swaps) + 
			"  Comparisons: " + Integer.toString(comparisons));
	}
	
	@Override
	public void reset() {
		super.reset();
		minStop = 0;
		next = -1;
		current = 1;
		maxStop = cells.length;
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

