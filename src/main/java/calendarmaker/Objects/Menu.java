package calendarmaker.Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class Menu extends JPanel implements ActionListener{

	/**
	 * @param fontSize = JTextField object containing desired font size
	 * @param avai = array of available fonts
	 * @param dropdown = JComboBox containing available fonts
	 * @param showOut = JComboBox deciding whether or not to show dates outside of the month
	 * @param circle = JComboBox deciding whether or not to circle dates
	 * @param month = JComboBox deciding the selected month 
	 * @param year = JTextField containing desired year
	 * @param color = JTextField containing desired color
	 * @param grid = JComboBox deciding whether or not to show grid lines
	 */
	private static final long serialVersionUID = 1L;
	private JTextField fontSize;
	private Font[] avai;
	private JComboBox<String> dropDown;
	private JComboBox<Boolean> showOut;
	private JComboBox<Boolean> circle;
	private JComboBox<Month> month;
	private JTextField year;
	private JTextField color;
	private JComboBox<Boolean> grid;
	/**
	 * @param fonts = array containing names of available fonts
	 * @param con = GridBagConstraints object for formatting Menu object
	 * @param enter = JButton for activating ActionListener
	 * constructor for building and formatting Menu object
	 */
	public Menu()
	{
		GridBagConstraints con = new GridBagConstraints();
		JButton enter = new JButton("Enter");
		enter.addActionListener(this);
		month = new JComboBox<>(Month.values());
		year = new JTextField(4);
		year.setText(String.valueOf(LocalDate.now().getYear()));
		this.setLayout(new GridBagLayout());
		fontSize = new JTextField(2);
		fontSize.setText("12");
		avai = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		String[] fonts = new String[avai.length];
		for(int i = 0; i < avai.length; i++)
		{
			fonts[i] = avai[i].getName();
		}
		showOut = new JComboBox<>(new Boolean[]{true, false});
		circle = new JComboBox<>(new Boolean[]{true, false});
		grid = new JComboBox<>(new Boolean[] {true, false});
		dropDown = new JComboBox<>(fonts);
		color = new JTextField(12);
		color.setText("BLACK");
		con.fill = GridBagConstraints.HORIZONTAL;
		con.gridx = 0;
		con.gridy = 0;
		add(new JLabel("Month"), con);
		con.gridx = 1;
		add(month, con);
		con.gridy = 1;
		add(year, con);
		con.gridx = 0;
		add(new JLabel("Year"), con);
		con.gridy = 2;
		add(new JLabel("Font Size"), con);
		con.gridx = 1;
		add(fontSize, con);
		con.gridy = 3;
		add(dropDown, con);
		con.gridx = 0;
		add(new JLabel("Font Type"), con);
		con.gridy = 4;
		add(new JLabel("Show Outer Dates"), con);
		con.gridx = 1;
		add(showOut, con);
		con.gridy = 5;
		add(circle, con);
		con.gridx = 0;
		add(new JLabel("Circle Dates"), con);
		con.gridy = 6;
		con.gridx = 1;
		add(color, con);
		con.gridx = 0;
		add(new JLabel("Color"), con);
		con.gridy = 7;
		con.gridx = 0;
		add(new JLabel("Show Grid"), con);
		con.gridx = 1;
		add(grid, con);
		con.gridx = 0;
		con.gridy = 8;
		con.gridwidth = 2;
		add(enter, con);
	}
	@Override
	/**
	 * @param @input e = ActionEvent performed
	 * @param s = string containing command of @param e
	 * triggers @method enter when button is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if(s.equalsIgnoreCase("enter"))
		{
			enter();
		}
	}
	public void enter()
	{
		/**
		 * @param sel = font from @param dropDown
		 * @param font = font created from @param sel and @param fontSize
		 * @param f = JFrame containing created calendar
		 * @param yub = Pane object created from input information
		 * @param table = table from @param yub
		 * @param bord = TitledBorder object from @param yub
		 * creates Pane object by calling @method getPane and displays it in a JFrame
		 */
		try
		{
			Font sel = avai[this.dropDown.getSelectedIndex()];
			Font font = new Font(sel.getFontName(), sel.getStyle(), Integer.parseInt(this.fontSize.getText()));
			JFrame f = new JFrame("yub");
			Pane yub = getPane();
			JTable table = yub.getJTable();
			table.setFont(font);
			TitledBorder bord = (TitledBorder) yub.getBorder();
			bord.setTitleFont(font);
			bord.setTitleColor((Color) Color.class.getField(color.getText()).get(null));
			yub.setBorder(bord);
			f.add(yub);
			f.pack();
			f.setLocationRelativeTo(null);
			f.setVisible(true);
		}
		/**
		 * @param f = JFrame for message
		 * @param j = JLabel containing warning
		 * creates JFrame giving error warning
		 */
		catch(Exception e)
		{
			JFrame f = new JFrame("Error");
			JLabel j = new JLabel("Improper Input");
			j.setFont(new Font("Times New Roman", Font.PLAIN, 34)); 
			j.setForeground(Color.RED);
			f.add(j);
			f.pack();
			f.setLocationRelativeTo(null);
			f.setVisible(true);
			e.printStackTrace();
		}
	}
	/**
	 * @param year = user input year from @param year
	 * @param month = user input month from @param month
	 * @param fontSize = user input font size
	 * @param sel = font selected from @param dropDown
	 * @param font = font created from @param sel and @param fontSize
	 * @return yub = Pane object created from above parameters
	 */
	public Pane getPane()
	{
		try
		{
			int year = Integer.parseInt(this.year.getText());
			Month month = (Month) this.month.getSelectedItem();
			int fontSize = Integer.parseInt(this.fontSize.getText());
			Font sel = avai[this.dropDown.getSelectedIndex()];
			Font font = new Font(sel.getFontName(), sel.getStyle(), fontSize);
			Pane yub = new Pane(year, month, (boolean) this.showOut.getSelectedItem(), (boolean) this.circle.getSelectedItem(),
					(Color) Color.class.getField(color.getText()).get(null), (boolean) this.grid.getSelectedItem());
			yub.setFont(font);
			yub.getJTable().getTableHeader().setFont(new Font(font.getFontName(), font.getStyle(), fontSize-2));
			yub.getJTable().getTableHeader().setForeground((Color) Color.class.getField(color.getText()).get(null));
			yub.setCellSize((fontSize+2)*2);
			return yub;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public JComboBox<Month> getMonth() {
		return month;
	}
	public void setMonth(JComboBox<Month> month) {
		this.month = month;
	}
	public JTextField getYear() {
		return year;
	}
	public void setYear(JTextField year) {
		this.year = year;
	}
}