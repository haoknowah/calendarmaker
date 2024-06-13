package calendarmaker.Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField fontSize;
	private Font[] avai;
	private JComboBox<String> dropDown;
	private JComboBox<Boolean> showOut;
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
		dropDown = new JComboBox<>(fonts);
		color = new JTextField(12);
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
		add(color, con);
		con.gridx = 0;
		add(new JLabel("Color"), con);
		con.gridy = 6;
		con.gridx = 0;
		con.gridwidth = 2;
		add(enter, con);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
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
			int year = Integer.parseInt(this.year.getText());
			Month month = (Month) this.month.getSelectedItem();
			int fontSize = Integer.parseInt(this.fontSize.getText());
			Font sel = avai[this.dropDown.getSelectedIndex()];
			Font font = new Font(sel.getFontName(), sel.getStyle(), fontSize);
			JFrame f = new JFrame("yub");
			Pane yub = new Pane(year, month);
			yub.getJTable().setFont(font);
			f.add(yub);
			f.pack();
			f.setLocationRelativeTo(null);
			f.setVisible(true);
		}
		catch(Exception e)
		{
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
			Pane yub = new Pane(year, month);
			yub.getJTable().setFont(font);
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
