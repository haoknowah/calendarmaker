package calendarmaker.Objects;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class CalendarTable extends AbstractTableModel{
	/**
	 * @param days = array of days for column headers
	 * @param calendar = Calendar object for table
	 */
	private String[] days = 
		{
				"M",
				"T",
				"W",
				"Th",
				"F",
				"Sa",
				"Su"
		};
	private Calendar calendar;
	public CalendarTable(Calendar calendar)
	{
		this.calendar = calendar;
	}
	@Override
	public String getColumnName(int column)
	{
		return days[column];
	}
	@Override
	public int getColumnCount()
	{
		return 7;
	}
	@Override
	/**
	 * @param end = gets number of days in month plus extra days from anchor
	 * @param @return rows = number of rows in table
	 */
	public int getRowCount()
	{
		int end = calendar.getStart().get(ChronoField.DAY_OF_WEEK) - 1 + calendar.getStart().lengthOfMonth();
		int rows = end / 7;
		if(end % 7 != 0)
		{
			rows++;
		}
		return rows;
	}
	@Override
	public Class<?> getColumnClass(int index)
	{
		return LocalDate.class;
	}
	@Override
	/**
	 * @param row = selected row
	 * @param column = selected column
	 * @return LocalDate in selected row and column
	 */
	public Object getValueAt(int row, int column)
	{
		int offset = (row * 7) + column;
		return calendar.getDateForDayOffset(offset);
	}
}
