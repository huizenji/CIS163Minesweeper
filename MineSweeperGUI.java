package W19Project2GIVETOSTUDENTS;


import javax.swing.*;

public class MineSweeperGUI {
	public static String boardSizeStr = JOptionPane.showInputDialog (null, "Enter in the " +
			"size of the board (3-30): ");
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Mine Sweeper!");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		MineSweeperPanel panel = new MineSweeperPanel();
		frame.getContentPane().add(panel);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
}

