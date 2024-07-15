package calendarmaker.Objects;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;

public class Calendar {
	/**
	 * @param start = LocalDate for start of the month
	 * @param end = LocaDate for end of the month
	 * @param anchor = LocalDate for first day of first week
	 */
	private LocalDate start;
	private LocalDate end;
	private LocalDate anchor;
	/**
	 * @param year = user input year
	 * @param month = user input month
	 * constructor for Calendar object that sets  object variables
	 */
	public Calendar(int year, Month month)
	{
		start = LocalDate.of(year, month, 1);
		end = start.withDayOfMonth(start.lengthOfMonth());
		anchor = start.minusDays(start.get(ChronoField.DAY_OF_WEEK)-1);
	}
	/**
	 * @param target = selected date
	 * @return whether or not @param target is within the month specified in constructor
	 */
	public boolean isWithinBounds(LocalDate target)
	{
		return (target.equals(start) || target.isAfter(start)) && (target.equals(end) || target.isBefore(end));
	}
	/**
	 * @param offset = row*7 + col
	 * @return value of date based on the @param anchor and offset from calendar rows and columns
	 */
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
