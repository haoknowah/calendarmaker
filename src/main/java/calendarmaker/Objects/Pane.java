package calendarmaker.Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDate;
import java.time.Month;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

@SuppressWarnings("serial")
public class Pane extends JPanel{
	/**
	 * @param table = JTable of the calendar
	 * @param tendo = JScrollPane object that is apparently necessary to display column headers
	 */
	private JTable table;
	private JScrollPane tendo;
	/**
	 * @param @input year = selected year
	 * @param @input month = selected month
	 * @param @input showOut = boolean determining whether or not to show dates outside of @param month
	 * @param @input circle = boolean determining whether or not to circle dates
	 * @param @input color = selected color for calendar
	 * @param @input grid = boolean determining whether or not to show grid lines
	 * @param calendar = calendar object for @param table
	 * @param bord = TitledBorder object for displaying selected month of calendar
	 * @param rend = TableCellRenderer of table header
	 * @param head = JLabel for column headers so they can be centered on macs
	 * constructor that builds calendar table based on input parameters
	 */
	public Pane(int year, Month month, boolean showOut, boolean circle, Color color, boolean grid, int circThick, Font font)
	{
		Calendar calendar = new Calendar(year, month);
		table = new JTable(new CalendarTable(calendar))
				{
					@Override
					public TableCellRenderer getCellRenderer(int row, int column)
					{
						TableCellRenderer renderer = super.getCellRenderer(row, column);
						return renderer;
					}
				};
		table.setDefaultRenderer(LocalDate.class, new Renderer(calendar, showOut, circle, color, circThick));
		TitledBorder bord = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), month.toString(),
				TitledBorder.TOP, TitledBorder.CENTER);
		Font title = new Font(font.getFontName(), font.getStyle(), (int) (font.getSize() * 1.25));
		bord.setTitleFont(title);
		this.setBorder(bord);
		table.setShowGrid(grid);
		table.setGridColor(color);
		TableCellRenderer rend = table.getTableHeader().getDefaultRenderer();
		JLabel head = (JLabel) rend;
		head.setHorizontalAlignment(JLabel.CENTER);
		tendo = new JScrollPane(table);
		add(tendo);
	}
	/**
	 * @param @input x = selected size of cell based on font size
	 * sets preferred size of columns based on font size
	 */
	public void setCellSize(int x, int heightChange)
	{
		table.getColumnModel().setColumnMargin(0);
		table.setRowHeight(x + heightChange);
		for(int i = 0; i < 7; i++)
		{
			table.getColumnModel().getColumn(i).setPreferredWidth(x);
			table.getColumnModel().getColumn(i).setMinWidth(2);
			table.getColumnModel().getColumn(i).setMaxWidth(x);
		}
		tendo.setPreferredSize(new Dimension(x*7, (x + heightChange)*table.getRowCount()+table.getTableHeader().getPreferredSize().height+5));
	}
	/**
	 * @param imp = DOMImplementation object for getting svg dom
	 * @param s = SVG namespace uri
	 * @param doc = temporary svg document to obtain graphics data to be painted
	 * @param svgGen = SVGGraphics2D object containing graphics info for svg visuals
	 */
	public void paintTable()
	{
		DOMImplementation imp = SVGDOMImplementation.getDOMImplementation();
		String s = SVGDOMImplementation.SVG_NAMESPACE_URI;
		SVGDocument doc = (SVGDocument) imp.createDocument(s, "svg", null);
		SVGGraphics2D svgGen = new SVGGraphics2D(doc);
		this.paint(svgGen);
	}
	public JTable getJTable()
	{
		return this.table;
	}
	/**
	 * @param imp = DOMImplementation object for getting svg dom
	 * @param s = SVG namespace uri
	 * @param doc = temporary svg document to obtain graphics data to be painted
	 * @param svgGen = SVGGraphics2D object containing graphics info for svg visuals
	 * @param ss = filepath chosen using @method findFile
	 * @param out = OutputStreamWriter object for streaming data to svg file
	 * @throws IOException
	 * creates an svg file and saves created image to it
	 */
	public void getSVG() throws IOException
	{
		DOMImplementation imp = SVGDOMImplementation.getDOMImplementation();
		String s = SVGDOMImplementation.SVG_NAMESPACE_URI;
		SVGDocument doc = (SVGDocument) imp.createDocument(s, "svg", null);
		SVGGraphics2D svgGen = new SVGGraphics2D(doc);
		String ss = findFile(0).getAbsolutePath();
		Writer out = new OutputStreamWriter(new FileOutputStream(new File(ss)), "UTF-8");
		this.paint(svgGen);
		svgGen.stream(out, true);
		out.flush();
		out.close();
	}
	/**
	 * @param image = BufferedImage object containing image of created calendar
	 * @param gjpg = Graphics2D object created from @param image
	 * @param file = jpg file image is saved to
	 * @throws IOException
	 * saves created calendar to jpg file 
	 */
	public void getJPG() throws IOException
	{
		BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D gjpg = image.createGraphics();
		this.paint(gjpg);
		File file = findJPGFile();
		ImageIO.write(image, "jpg", file);
	}
	/**
	 * @param file = selected file to view
	 * @param imp = DOMImplementation object for the svg dom
	 * @param fact = SAXSVGDocumentFactory for creating SVGDocument from @param file
	 * @param canvas = JSVGCanvas object for displaying loaded svg file
	 * @param doc = SVGDocument from selected @param file
	 * @param g = SVGGraphics2D from @param doc
	 * @param frame = JFrame for displaying selected svg
	 * @param root = root element of @param doc
	 * @param image = BufferedImage from selected jpg file
	 * @param im = jpg to be displayed
	 * @return g = graphics of selected file
	 * @throws IOException
	 * finds and displays an svg specified by the user
	 */
	public static Graphics2D viewSVG() throws IOException
	{
		File file = loadFile();
		if(file.getAbsolutePath().substring(file.getAbsolutePath().length()-4).equals(".svg"))
		{
			DOMImplementation imp = SVGDOMImplementation.getDOMImplementation();
			SAXSVGDocumentFactory fact = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
			JSVGCanvas canvas = new JSVGCanvas();
			SVGDocument doc = null;
			SVGGraphics2D g = null;
			try {
				doc = fact.createSVGDocument(file.toURI().toString());
				g = new SVGGraphics2D(doc);
			} catch (IOException e1) {
				doc = (SVGDocument) imp.createDocument(file.getAbsolutePath(), "svg", null);
				e1.printStackTrace();
			}
			g.setSVGCanvasSize(new Dimension(1000, 1200));
			JFrame frame = new JFrame(doc.getLocalName());
			Element root = doc.getDocumentElement();
			g.getRoot(root);
			frame.getContentPane().add(canvas);
			canvas.setSVGDocument(doc);
			frame.paint(g);
			frame.pack();
			frame.setVisible(true);
			return g;
		}
		else if(file.getAbsolutePath().substring(file.getAbsolutePath().length() - 4).equals(".jpg") ||
				file.getAbsolutePath().substring(file.getAbsolutePath().length() - 4).equals(".png"))
		{
			BufferedImage image = ImageIO.read(file);
			JFrame frame = new JFrame();
			JLabel im = new JLabel(new ImageIcon(file.getAbsolutePath()));
			frame.add(im);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			return (Graphics2D) image.createGraphics();
		}
		else
		{
			return null;
		}
	}
	/*
	 * @param find = JFileChooser object to allow user to navigate file explorer
	 * @param result = integer indicating if the selection was valid
	 * @param@return file = file selected by user
	 * uses JFileChooser to allow user to select a file and then return the file if it is valid or null if it is not
	 */
	public File findJPGFile()
	{
		JFileChooser find = new JFileChooser();
		find.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = find.showSaveDialog(find);
		if(find.getSelectedFile().exists() == false)
		{
			find.approveSelection();
			try {
				Writer write = new FileWriter(find.getSelectedFile().getPath() + ".jpg");
				write.flush();
				write.close();
				find.setSelectedFile(new File(find.getSelectedFile().getPath() + ".jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(JFileChooser.APPROVE_OPTION == result)
		{
			File file = find.getSelectedFile();
			return file;
		}
		else
		{
			return null;
		}
	}
	/*
	 * @param find = JFileChooser object to allow user to navigate file explorer
	 * @param result = integer indicating if the selection was valid
	 * @param@return file = file selected by user
	 * uses JFileChooser to allow user to select a file and then return the file if it is valid or null if it is not
	 */
	public static File findFile(int type)
	{
		JFileChooser find = new JFileChooser();
		find.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = find.showSaveDialog(find);
		if(find.getSelectedFile().exists() == false)
		{
			find.approveSelection();
			try {
				if(type == 0)
				{
					Writer write = new FileWriter(find.getSelectedFile().getPath() + ".svg");
					write.flush();
					write.close();
					find.setSelectedFile(new File(find.getSelectedFile().getPath() + ".svg"));
				}
				if(type == 1)
				{
					Writer write = new FileWriter(find.getSelectedFile().getPath() + ".png");
					write.flush();
					write.close();
					find.setSelectedFile(new File(find.getSelectedFile().getPath() + ".png"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(JFileChooser.APPROVE_OPTION == result)
		{
			File file = find.getSelectedFile();
			return file;
		}
		else
		{
			JFrame f = new JFrame("A");
			JLabel war = new JLabel("File was not selected or could not be loaded.");
			f.add(war);
			f.pack();
			f.setLocationRelativeTo(null);
			f.setVisible(true);
			return null;
		}
	}
	/**
	 * @param find = JFileChooser object
	 * @param result = determines approval status of selected file
	 * @param write = FileWriter object for creating and writing to selected file if it does not exist
	 * @return file selected by the user
	 * different from above as this one is specifically for opening an already created file as the showSaveDialog method does not
	 * allow custom input names to create a new file on mac
	 */
	public static File loadFile()
	{
		JFileChooser find = new JFileChooser();
		find.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = find.showOpenDialog(find);
		if(find.getSelectedFile().exists() == false)
		{
			find.approveSelection();
			try {
				Writer write = new FileWriter(find.getSelectedFile().getPath() + ".svg");
				write.flush();
				write.close();
				find.setSelectedFile(new File(find.getSelectedFile().getPath() + ".svg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(JFileChooser.APPROVE_OPTION == result)
		{
			File file = find.getSelectedFile();
			return file;
		}
		else
		{
			JFrame f = new JFrame("A");
			JLabel war = new JLabel("File was not selected or could not be loaded.");
			f.add(war);
			f.pack();
			f.setLocationRelativeTo(null);
			f.setVisible(true);
			return null;
		}
	}
}