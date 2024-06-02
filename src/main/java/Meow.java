import java.io.IOException;
import java.time.Month;

import javax.swing.JFrame;

import calendarmaker.Objects.Pane;

public class Meow {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Test");
		Pane yub = new Pane(2024, Month.JUNE);
		frame.add(yub);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		yub.getSVG();
		yub.viewSVG();
	}

}
