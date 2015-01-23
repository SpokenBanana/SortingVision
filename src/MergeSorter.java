
import java.awt.Graphics;

/*
the way this one is going to work in terms of visualization is that we are going
to leave the bucket null for now, then fill it as we find the final placements 
for the elements. We draw the ones that are filled and draw the cells from 
'cells' when there is nothing to display from bucket
*/

public class MergeSorter extends Sorter{

	Cell[] bucket;
							// the 'iters' are used to iterate through merging
	int width, left, right, iterLeft, iterRight, end, iter;
	public MergeSorter(int amount) {
		SCALE = CELL_WIDTH = 900.0 / amount;
		cells = new Cell[amount];
		bucket = new Cell[amount];
		for (int i = 0; i < cells.length; i++)
			cells[i] = new Cell(i);
		
		finished = false;
		
		// we have a couple of loops, one to go through the whole array
		width = 1;
		
		// one to iterate by two subsets
		current = 0;
		
		// and one to merge the two subsets
		iter = iterLeft = left = current;
		iterRight = right = Math.min(current+width, cells.length);
		end = Math.min(current + 2 * width, cells.length);
	}
	
	public MergeSorter(){
		this(180);
	}
	
	
	@Override
	public void step() {
		if (!finished) {
			resetAll();
			
			// we have two subsets now, we merge them putting the smallest in first
			if (iterLeft < right && (iterRight >= end || cells[iterLeft].getValue() <= cells[iterRight].getValue())) {
				comparisons++;
				if (bucket[iter] == null)
					bucket[iter] = new Cell(cells[iterLeft].value);
				else
					bucket[iter].ChangeValue(cells[iterLeft].value);
				cells[iterLeft].selected = true;
				cells[iter].selected = true;
				swaps++;
				iterLeft++;
			}
			else {
				comparisons++;
				if (bucket[iter] == null)
					bucket[iter] = new Cell(cells[iterRight].value);
				else
					bucket[iter].ChangeValue(cells[iterRight].value);
				cells[iterRight].selected = true;
				cells[iter].selected = true;
				swaps++;
				iterRight++;
			}
			
			iter++;
			
			stepMerge();
		}
	}
	
	// updates all counters that would be used if we were looping (i.e all the i's, j's, etc)
	// and checks if we have finished one or not
	private void stepMerge() {
		// finished merging two sets, now to merge the next two
		if (iter >= end) {
			comparisons++;
			copy(left, end);
			current += 2*width;

			/* the array is now split into many sorted sub-sets, now we go back
			and pair and re-merge those sets */
			if (current >= cells.length) {
				comparisons++;
				current = 0;
				copy();
				width *= 2;
			}

			// we are done (( hopefully ))
			if (width>=cells.length){
				comparisons++;
				finished = isSorted();
			}

			// we are not done now, so start from beginning and figure
			// out the
			iter = iterLeft = left = current;
			iterRight = right = Math.min(current+width, cells.length);
			end = Math.min(current + 2 * width, cells.length);
		}
	}
	
	public void copy() {
		for (int i = 0; i < cells.length; i++)
			cells[i].ChangeValue(bucket[i].value);
	}
	
	// copy on certain parts to get a better look at the merging
	public void copy(int start, int n) {
		for (int i = start; i < n; i++)
			cells[i].ChangeValue(bucket[i].value);
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		drawHeader(g,"Merge Sort  Comparisions: " + Integer.toString(comparisons) +
			"  Swaps: " + Integer.toString(swaps));
	}
	
	@Override
	public void reset() {
		super.reset();
		bucket = new Cell[cells.length];
		width = 1;
		
		// one to iterate by two subsets
		current = 0;
		
		// and one to merge the two subsets
		iter = iterLeft = left = current;
		iterRight = right = Math.min(current+width, cells.length);
		end = Math.min(current + 2 * width, cells.length);
	}
	
	@Override
	public void changeCount(int amount) {
		super.changeCount(amount);
		reset();
	}

	@Override
	public int getDefaultAmount() {
		return 180;
	}
	
}
