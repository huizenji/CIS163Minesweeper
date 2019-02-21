package project2;

import javax.swing.*;

public class MineSweeperGUI {
	public static void main (String[] args) {
		// prompt user for board size and mine count
		MineSweeperGame.promptBoardSize();
		MineSweeperGame.promptNumberOfMines();

		// set up GUI
		JFrame frame = new JFrame ("MineSweeper");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		MineSweeperPanel panel = new MineSweeperPanel();
		frame.getContentPane().add(panel);
		frame.setSize(400, 400);
		frame.setVisible(true);

	}
}


