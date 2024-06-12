package calendarmaker.Objects;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

@SuppressWarnings("serial")
public class Pane extends JPanel{
	private JTable table;
	public Pane(int year, Month month)
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
		table.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		table.getTableHeader().setFont(new Font("Times New Roman", Font.PLAIN, 24));
		setCellSize(75);
		table.setDefaultRenderer(LocalDate.class, new Renderer(calendar));
		add(new JScrollPane(table));
	}
	public void setCellSize(int x)
	{
		table.getColumnModel().setColumnMargin(0);
		table.setRowHeight(x);
		for(int i = 0; i < 7; i++)
		{
			table.getColumnModel().getColumn(i).setPreferredWidth(x);
			table.getColumnModel().getColumn(i).setMaxWidth(x);
		}
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
		String svg = System.getProperty("user.dir") + "/tst.svg";
		String s = SVGDOMImplementation.SVG_NAMESPACE_URI;
		SVGDocument doc = (SVGDocument) imp.createDocument(s, "svg", null);
		SVGGraphics2D svgGen = new SVGGraphics2D(doc);
		Writer out = new FileWriter(svg);
		this.paint(svgGen);
		svgGen.stream(out, true);
		out.close();
	}
	public void viewSVG()
	{
		File file = findFile();
		file.deleteOnExit();
		String s = SVGDOMImplementation.SVG_NAMESPACE_URI;
		DOMImplementation imp = SVGDOMImplementation.getDOMImplementation();
		SVGDocument doc = (SVGDocument) imp.createDocument(s, "svg", null);
		doc.setDocumentURI(file.toString());
		JSVGCanvas canvas = new JSVGCanvas();
		SVGGraphics2D g = new SVGGraphics2D(doc);
		g.setSVGCanvasSize(new Dimension(720, 120));
		this.paint(g);
		Element root = doc.getDocumentElement();
		g.getRoot(root);
		JFrame frame = new JFrame();
		frame.getContentPane().add(canvas);
		canvas.setSVGDocument(doc);
		frame.pack();
		frame.setVisible(true);
	}
	/*
	 * @param find = JFileChooser object to allow user to navigate file explorer
	 * @param result = integer indicating if the selection was valid
	 * @param@return file = file selected by user
	 * uses JFileChooser to allow user to select a file and then return the file if it is valid or null if it is not
	 */
	public File findFile()
	{
		JFileChooser find = new JFileChooser();
		find.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int result = find.showOpenDialog(find);
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
}
