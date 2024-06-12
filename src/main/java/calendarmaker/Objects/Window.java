package calendarmaker.Objects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel win;
	private Pane yub;
	private Menu menu;
	public Window(String title)
	{
		setTitle(title);
		win = new JPanel();
		win.setLayout(new BoxLayout(win, BoxLayout.Y_AXIS));
		this.yub = new Pane(2024, Month.JUNE);
		this.menu = new Menu();
		win.add(this.yub);
		win.add(this.menu);
		JButton save = new JButton("Save");
		save.addActionListener(this);
		JButton load = new JButton("Load");
		load.addActionListener(this);
		win.add(save);
		win.add(load);
		add(win);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String s = e.getActionCommand();
	}
	public Pane getYub()
	{
		return this.yub;
	}
	public Menu getMenu()
	{
		return this.menu;
	}
}
