package calendarmaker.Objects;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;

public class Calendar {
	private LocalDate start;
	private LocalDate end;
	private LocalDate anchor;
	private boolean showOut;
	public Calendar(int year, Month month)
	{
		start = LocalDate.of(year, month, 1);
		end = start.withDayOfMonth(start.lengthOfMonth());
		anchor = start.minusDays(start.get(ChronoField.DAY_OF_WEEK)-1);
	}
	public Calendar(int year, Month month, boolean showOut)
	{
		start = LocalDate.of(year,  month, 1);
		end = start.withDayOfMonth(start.lengthOfMonth());
		anchor = start.minusDays(start.getLong(ChronoField.DAY_OF_WEEK)-1);
		this.showOut = showOut;
	}
	public boolean isWithinBounds(LocalDate target)
	{
		return (target.equals(start) || target.isAfter(start)) && (target.equals(end) || target.isBefore(end));
	}
	public LocalDate getDateForDayOffset(int offset)
	{
		return anchor.plusDays(offset);
	}
	public LocalDate getStart() {
		return start;
	}
	public void setStart(LocalDate start) {
		this.start = start;
	}
	public boolean getShowOut()
	{
		return showOut;
	}
	public void setShowOut(boolean showOut)
	{
		this.showOut = showOut;
	}
	public LocalDate getEnd() {
		return end;
	}
	public void setEnd(LocalDate end) {
		this.end = end;
	}
	public LocalDate getAnchor() {
		return anchor;
	}
	public void setAnchor(LocalDate anchor) {
		this.anchor = anchor;
	}
}
