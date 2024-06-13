package calendarmaker.Objects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.batik.swing.JSVGCanvas;

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
		this.menu = new Menu();
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
		switch(s)
		{
		case "Save":
			try
			{
				JFrame f = new JFrame();
				Pane yub = this.menu.getPane();
				/*
				f.add(yub);
				f.pack();
				f.setVisible(true);
				f.setLocationRelativeTo(null);
				*/
				JSVGCanvas canvas = new JSVGCanvas();
				yub.getSVG();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
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
