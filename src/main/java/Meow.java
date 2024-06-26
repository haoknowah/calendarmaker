import java.io.IOException;

import javax.swing.JFrame;

import calendarmaker.Objects.Window;

public class Meow {

	public static void main(String[] args) throws IOException {
		Window frame = new Window("Calendar");
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
