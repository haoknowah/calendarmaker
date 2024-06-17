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
	 * 
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
		con.gridwidth = 2;
		add(enter, con);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if(s.equalsIgnoreCase("enter"))
		{
			System.out.println("yub");
			enter();
		}
	}
	public void enter()
	{
		try
		{
			Font sel = avai[this.dropDown.getSelectedIndex()];
			Font font = new Font(sel.getFontName(), sel.getStyle(), Integer.parseInt(this.fontSize.getText()));
			JFrame f = new JFrame("yub");
			Pane yub = getPane();
			JTable table = yub.getJTable();
			table.setFont(font);
			TitledBorder bord = (TitledBorder) yub.getBorder();
			System.out.println(table.getForeground());
			bord.setTitleFont(font);
			bord.setTitleColor((Color) Color.class.getField(color.getText()).get(null));
			yub.setBorder(bord);
			f.add(yub);
			f.pack();
			f.setLocationRelativeTo(null);
			f.setVisible(true);
		}
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
					(Color) Color.class.getField(color.getText()).get(null));
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
