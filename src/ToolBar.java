
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ToolBar extends JPanel {
	
	private final JButton reset;
	private JComboBox<String> combo;
	private SortingVision sortingVision;
	private JTextField count;
	
	int old;
	public ToolBar(SortingVision vision){
		combo = new JComboBox<>();

		combo.addItem("Merge Sort");
		combo.addItem("Bubble Sort");
		combo.addItem("Cocktail Sort");
		add(combo);
		count = new JTextField(3);
		count.setText("180");
		add(count);
		reset = new JButton("Reset");
		add(reset);
		old = 0;
		sortingVision = vision;
		
		/** EVENT HANDLERS **/
		count.addActionListener((ActionEvent e) -> {
			int amount = 0;
			try {
				amount = Integer.parseInt(count.getText());
			} catch (Exception ex) {
			}
			if (amount > 1)
				sortingVision.sorter.changeCount(amount);
		});
		
		reset.addActionListener((ActionEvent e) -> {
			sortingVision.sorter.reset();
		});
		
		combo.addActionListener((ActionEvent e) -> {
			if (combo.getSelectedIndex() == 0 && combo.getSelectedIndex() != old){
				sortingVision.sorter = new MergeSorter();
				sortingVision.sorter.shuffle();
				count.setText(Integer.toString(sortingVision.sorter.getDefaultAmount()));
				old = 0;
			}
			else if (combo.getSelectedIndex() == 1 && combo.getSelectedIndex() != old){
				sortingVision.sorter = new BubbleSorter();
				sortingVision.sorter.shuffle();
				count.setText(Integer.toString(sortingVision.sorter.getDefaultAmount()));
				old = 1;
			}
			else if (combo.getSelectedIndex() == 2 && combo.getSelectedIndex() != old) {
				sortingVision.sorter = new CocktailSorter();
				sortingVision.sorter.shuffle();
				count.setText(Integer.toString(sortingVision.sorter.getDefaultAmount()));
				old = 2;
			}
		});
	}
}
