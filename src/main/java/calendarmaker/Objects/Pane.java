package calendarmaker.Objects;

import java.awt.Color;
import java.awt.Dimension;
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
	private JTable table;
	private JScrollPane tendo;
	public Pane(int year, Month month, boolean showOut, boolean circle, Color color, boolean grid)
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
		table.setDefaultRenderer(LocalDate.class, new Renderer(calendar, showOut, circle, color));
		TitledBorder bord = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), month.toString(),
				TitledBorder.TOP, TitledBorder.CENTER);
		this.setBorder(bord);
		table.setShowGrid(grid);
		tendo = new JScrollPane(table);
		add(tendo);
	}
	public void setCellSize(int x)
	{
		table.getColumnModel().setColumnMargin(0);
		table.setRowHeight(x);
		for(int i = 0; i < 7; i++)
		{
			table.getColumnModel().getColumn(i).setPreferredWidth(x);
			table.getColumnModel().getColumn(i).setMinWidth(2);
			table.getColumnModel().getColumn(i).setMaxWidth(x);
		}
		tendo.setPreferredSize(new Dimension(x*7, x*table.getRowCount()+table.getTableHeader().getPreferredSize().height+5));
	}
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
	public void getSVG() throws IOException
	{
		DOMImplementation imp = SVGDOMImplementation.getDOMImplementation();
		String s = SVGDOMImplementation.SVG_NAMESPACE_URI;
		SVGDocument doc = (SVGDocument) imp.createDocument(s, "svg", null);
		SVGGraphics2D svgGen = new SVGGraphics2D(doc);
		String ss = findFile().getAbsolutePath();
		Writer out = new OutputStreamWriter(new FileOutputStream(new File(ss)), "UTF-8");
		this.paint(svgGen);
		svgGen.stream(out, true);
		out.flush();
		out.close();
	}
	public void getJPG() throws IOException
	{
		BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D gjpg = image.createGraphics();
		this.paint(gjpg);
		File file = findJPGFile();
		ImageIO.write(image, "jpg", file);
	}
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
		else if(file.getAbsolutePath().substring(file.getAbsolutePath().length() - 4).equals(".jpg"))
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
	public static File findFile()
	{
		JFileChooser find = new JFileChooser();
		find.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = find.showSaveDialog(find);
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
