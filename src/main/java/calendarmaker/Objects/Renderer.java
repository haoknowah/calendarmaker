package calendarmaker.Objects;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Renderer extends DefaultTableCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd");
	private Calendar calendar;
	public Renderer(Calendar calendar)
	{
		this.calendar = calendar;
		setHorizontalAlignment(JLabel.CENTER);
	}
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		String text = "---";
		boolean withinBounds = false;
		if(value instanceof LocalDate)
		{
			text = FORMATTER.format((LocalDate) value);
			
			withinBounds = calendar.isWithinBounds((LocalDate) value);
		}
		Component component = super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
		if(!withinBounds)
		{
			component.setForeground(Color.LIGHT_GRAY);
		}
		else
		{
			component.setForeground(Color.BLACK);
		}
		
		return component;
	}
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
	}
	
}
