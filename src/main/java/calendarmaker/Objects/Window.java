package calendarmaker.Objects;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class Window extends JFrame implements ActionListener{

	/**
	 * @param win = Jpanel containing the menu actions
	 * @param menu = Menu object for creating the calendar
	 * @param @input title = string representing the title on the window
	 * @param save = JButton for performing the save action
	 * @param load = JButton for performing the load action
	 * constructor builds JFrame for app, sets a box layout, and then adds Menu panel and then @param win panel beneath the Menu
	 */
	private static final long serialVersionUID = 1L;
	private JPanel win;
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
	/**
	 * @param @input e = ActionEvent triggered by the save or load of a calendar
	 * @param s = ActionCommand performed 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		switch(s)
		{
		case "Save":
			/**
			 * @param f = JFrame for asking image file type
			 * @param panel = panel holding prompt and buttons
			 * @param svg = button for saving as svg
			 * @param jpg = button for saving as jpg
			 * creates a window to ask what file type the image needs to be saved as and activates the corresponding event
			 */
			try
			{
				JFrame f = new JFrame();
				JPanel panel = new JPanel();
				panel.add(new JLabel("Select file type."));
				JButton svg = new JButton("SVG");
				svg.addActionListener(this);
				svg.addActionListener(c -> {f.dispose();});
				JButton jpg = new JButton("JPG");
				jpg.addActionListener(this);
				jpg.addActionListener(c -> {f.dispose();});
				panel.add(svg);
				panel.add(jpg);
				f.add(panel);
				f.pack();
				f.setLocationRelativeTo(null);
				f.setVisible(true);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case "Load":
			/**
			 * @param yub = Pane object for calling viewSVG method and displaying it
			 * @param g = Graphics2D object for painting @param yub
			 * loads and displays selected svg or jpg
			 */
			try
			{
				Pane yub = this.menu.getPane();
				Graphics2D g = Pane.viewSVG();
				yub.paint(g);
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
			break;
		case "SVG":
			try
			{
				JFrame f = new JFrame();
				Pane yub = this.menu.getPane();
				f.add(yub);
				f.pack();
				f.setLocationRelativeTo(null);
				f.setVisible(true);
				yub.getSVG();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case "JPG":
			try
			{
				JFrame f = new JFrame();
				Pane yub = this.menu.getPane();
				f.add(yub);
				f.pack();
				f.setLocationRelativeTo(null);
				BufferedImage image = new BufferedImage(yub.getWidth(), yub.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D gjpg = image.createGraphics();
				yub.paint(gjpg);
				File file = yub.findJPGFile();
				ImageIO.write(image, "jpg", file);
			}
			catch(Exception e23)
			{
				e23.printStackTrace();
			}
		}
	}
	public Menu getMenu()
	{
		return this.menu;
	}
}
