package calendarmaker.Objects;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;

public class Calendar {
	private LocalDate start;
	private LocalDate end;
	private LocalDate anchor;
	public Calendar(int year, Month month)
	{
		start = LocalDate.of(year, month, 1);
		end = start.withDayOfMonth(start.lengthOfMonth());
		anchor = start.minusDays(start.get(ChronoField.DAY_OF_WEEK)-1);
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
