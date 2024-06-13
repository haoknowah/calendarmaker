import java.io.IOException;

import javax.swing.JFrame;

import calendarmaker.Objects.Pane;
import calendarmaker.Objects.Window;

public class Meow {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Window frame = new Window("Calendar");
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(50*7, meow.getColumnModel().getColumn(0).getWidth()*7-25);
	}
}
