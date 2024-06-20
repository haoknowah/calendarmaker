package calendarmaker.Objects;

import java.awt.Color;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.time.LocalDate;
import java.time.Month;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.GenericImageHandler;
import org.apache.batik.svggen.ImageHandler;
import org.apache.batik.svggen.ImageHandlerPNGEncoder;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.xmlgraphics.io.Resource;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

@SuppressWarnings("serial")
public class Pane extends JPanel{
	private JTable table;
	private JScrollPane tendo;
	public Pane(int year, Month month, boolean showOut, boolean circle, Color color)
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
		tendo = new JScrollPane(table);
		add(tendo);
		//add(table); works except doesn't show header
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
		tendo.setPreferredSize(new Dimension(x*7, x*table.getRowCount()+table.getTableHeader().getPreferredSize().height+3));
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
		//implement save naming
		DOMImplementation imp = SVGDOMImplementation.getDOMImplementation();
		String svg = System.getProperty("user.dir") + "/tst.svg";
		String s = SVGDOMImplementation.SVG_NAMESPACE_URI;
		SVGDocument doc = (SVGDocument) imp.createDocument(s, "svg", null);
		SVGGraphics2D svgGen = new SVGGraphics2D(doc);
		String ss = findFile().getAbsolutePath();
		Writer out = new FileWriter(ss);
		System.out.println(ss);
		this.paint(svgGen);
		svgGen.stream(out, true);
		out.close();
	}
	public static SVGGraphics2D viewSVG()
	{
		File file = findFile();
		DOMImplementation imp = SVGDOMImplementation.getDOMImplementation();
		SAXSVGDocumentFactory fact = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
		JSVGCanvas canvas = new JSVGCanvas();
		SVGDocument doc = null;
		SVGGraphics2D g = null;
		try {
			doc = fact.createSVGDocument(file.toURI().toString());
			g = new SVGGraphics2D(doc);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
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
		int result = find.showOpenDialog(find);
		if(find.getSelectedFile().exists() == false)
		{
			find.approveSelection();
			try {
				DOMImplementation imp = SVGDOMImplementation.getDOMImplementation();
				String s = SVGDOMImplementation.SVG_NAMESPACE_URI;
				SVGDocument doc = (SVGDocument) imp.createDocument(s, "svg", null);
				SVGGraphics2D svgGen = new SVGGraphics2D(doc);
				Writer write = new FileWriter(find.getSelectedFile().getPath() + ".svg");
				svgGen.stream(write, true);
				write.flush();
				write.close();
				find.setSelectedFile(new File(find.getSelectedFile().getPath() + ".svg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
}
