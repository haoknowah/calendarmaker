package calendarmaker.Objects;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;

public class Calendar {

	/*
	 * 
	JTable calendar;
	GregorianCalendar cal;
	JPanel[][] dates;
	public Calendar()
	{
		cal = new GregorianCalendar();
		cal.set(2024, 3, 1);
		String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
		List<JPanel> mon = new ArrayList<JPanel>();
		List<JPanel> tue = new ArrayList<JPanel>();
		List<JPanel> wed = new ArrayList<JPanel>();
		List<JPanel> thur = new ArrayList<JPanel>();
		List<JPanel> fri = new ArrayList<JPanel>();
		List<JPanel> sat = new ArrayList<JPanel>();
		List<JPanel> sun = new ArrayList<JPanel>();
		System.out.println(cal.get(GregorianCalendar.DAY_OF_WEEK));
		while(cal.get(GregorianCalendar.MONTH) == 3)
		{
			int day = cal.get(GregorianCalendar.DAY_OF_WEEK);
			switch(day)
			{
			case 2:
				if(cal.get(GregorianCalendar.DAY_OF_WEEK) == 2)
				{
					JPanel yub = new JPanel();
					JLabel meow = new JLabel(String.valueOf(cal.get(GregorianCalendar.DATE)));
					yub.add(meow);
					mon.add(yub);
				}
				else if(cal.get(GregorianCalendar.DATE) == 1)
				{
					mon.add(new JPanel());
				}
				break;
			case 3:
				if(cal.get(GregorianCalendar.DAY_OF_WEEK) == 3)
				{
					JPanel yub = new JPanel();
					JLabel meow = new JLabel(String.valueOf(cal.get(GregorianCalendar.DATE)));
					yub.add(meow);
					tue.add(yub);
				}
				else if(cal.get(GregorianCalendar.DATE) == 1)
				{
					tue.add(new JPanel());
				}
				break;
			case 4:
				if(cal.get(GregorianCalendar.DAY_OF_WEEK) == 4)
				{
					JPanel yub = new JPanel();
					JLabel meow = new JLabel(String.valueOf(cal.get(GregorianCalendar.DATE)));
					yub.add(meow);
					wed.add(yub);
				}
				else if(cal.get(GregorianCalendar.DATE) == 1)
				{
					wed.add(new JPanel());
				}
				break;
			case 5:
				if(cal.get(GregorianCalendar.DAY_OF_WEEK) == 5)
				{
					JPanel yub = new JPanel();
					JLabel meow = new JLabel(String.valueOf(cal.get(GregorianCalendar.DATE)));
					yub.add(meow);
					thur.add(yub);
				}
				else if(cal.get(GregorianCalendar.DATE) == 1)
				{
					thur.add(new JPanel());
				}
				break;
			case 6:
				if(cal.get(GregorianCalendar.DAY_OF_WEEK) == 6)
				{
					JPanel yub = new JPanel();
					JLabel meow = new JLabel(String.valueOf(cal.get(GregorianCalendar.DATE)));
					yub.add(meow);
					fri.add(yub);
				}
				else if(cal.get(GregorianCalendar.DATE) == 1)
				{
					fri.add(new JPanel());
				}
				break;
			case 7:
				if(cal.get(GregorianCalendar.DAY_OF_WEEK) == 7)
				{
					JPanel yub = new JPanel();
					JLabel meow = new JLabel(String.valueOf(cal.get(GregorianCalendar.DATE)));
					yub.add(meow);
					sat.add(yub);
				}
				else if(cal.get(GregorianCalendar.DATE) == 1)
				{
					sat.add(new JPanel());
				}
				break;
			case 1:
				if(cal.get(GregorianCalendar.DAY_OF_WEEK) == 1)
				{
					JPanel yub = new JPanel();
					JLabel meow = new JLabel(String.valueOf(cal.get(GregorianCalendar.DATE)));
					yub.add(meow);
					sun.add(yub);
				}
				break;
			}
			cal.roll(GregorianCalendar.DATE, true);
			if(cal.get(GregorianCalendar.DATE) == 1)
			{
				break;
			}
		}
		if(tue.size() != mon.size())
		{
			tue.add(new JPanel());
		}
		if(wed.size() != tue.size())
		{
			wed.add(new JPanel());
		}
		if(thur.size() != wed.size())
		{
			thur.add(new JPanel());
		}
		if(fri.size() != thur.size())
		{
			fri.add(new JPanel());
		}
		if(sat.size() != fri.size())
		{
			sat.add(new JPanel());
		}
		if(sun.size() != sat.size())
		{
			sun.add(new JPanel());
		}
		System.out.println(mon.size() + " " + tue.size() + " " + wed.size() + " " + thur.size() + " " + fri.size() +
				" " + sat.size() + " " + sun.size());
		tue.stream().forEach(x -> System.out.println(x.getComponentCount()));
		JPanel[][] dates = {mon.toArray(new JPanel[mon.size()]), tue.toArray(new JPanel[tue.size()]),
				wed.toArray(new JPanel[wed.size()]), thur.toArray(new JPanel[thur.size()]),
				fri.toArray(new JPanel[fri.size()]), sat.toArray(new JPanel[sat.size()]),
				sun.toArray(new JPanel[sun.size()])};
		this.dates = dates;
		calendar = new JTable(dates, days);
	}
	public JTable getCalendar() {
		return calendar;
	}
	public void setCalendar(JTable calendar) {
		this.calendar = calendar;
	}
	*/
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
