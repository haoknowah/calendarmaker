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
	 * @param FORMATTER = DateTimeFormatter object for formatting the date
	 * @param calendar = calendar object
	 * @param showOut = boolean deciding whether or not to show dates outside selected month
	 * @param circle = boolean deciding whether or not to circle dates in calendar
	 * @param withinBounds = boolean dictating whether or not LocalDate is within specified month
	 * @param color = selected color for calendar
	 */
	private static final long serialVersionUID = 1L;
	public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd");
	private Calendar calendar;
	private boolean showOut;
	private boolean circle;
	private boolean withinBounds;
	private Color color;
	public Renderer(Calendar calendar, boolean showOut, boolean circle, Color color)
	{
		this.calendar = calendar;
		this.showOut = showOut;
		this.circle = circle;
		this.color = color;
		setHorizontalAlignment(JLabel.CENTER);
	}
	@Override
	/**
	 * @param @input table = JTable of @param calendar
	 * @param @input value = value of selected cell
	 * @param text = blank text for selected cell that gets formatted
	 * @param @return component = TableCellRendererComponent from parent class
	 * gets TableCellRendererComponent for the table and formats it for the date and determines text visuals for each cell
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		String text = "---";
		withinBounds = false;
		if(value instanceof LocalDate)
		{
			text = FORMATTER.format((LocalDate) value);
			if(text.substring(0,1).equals("0"))
			{
				text = text.substring(1);
			}
			withinBounds = calendar.isWithinBounds((LocalDate) value);
		}
		Component component = super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);
		if(!withinBounds)
		{
			if(showOut)
			{
				component.setForeground(Color.LIGHT_GRAY);
			}
			else
			{
				text = "";
				component = super.getTableCellRendererComponent(table, text, isSelected, hasFocus, row, column);				
			}
		}
		else
		{
			component.setForeground(color);
		}
		return component;
	}
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(circle && (withinBounds || showOut))
		{
			g.drawOval(0, 0, g.getClipBounds().width, g.getClipBounds().height);
		}
	}
	
}
