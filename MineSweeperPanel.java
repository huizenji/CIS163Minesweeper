package project2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineSweeperPanel extends JPanel {

	private JButton[][] board;
	private JButton quitButton;
	private Cell iCell;
	private int boardSize, wins, losses;
	private JLabel winsLabel, lossesLabel;

	private MineSweeperGame game;  // model

	public MineSweeperPanel() {

		JPanel bottom = new JPanel();
		JPanel center = new JPanel();
		JButton quitButton = new JButton("QUIT");
     	        quitButton.addActionListener(new ButtonListener());

		// create game, listeners
		ButtonListener listener = new ButtonListener();

		game = new MineSweeperGame();

		// create the board
		boardSize = game.getBoardSize();
		center.setLayout(new GridLayout(boardSize, boardSize));
		board = new JButton[boardSize][boardSize];

		for (int row = 0; row < boardSize; row++)
			for (int col = 0; col < boardSize; col++) {
				board[row][col] = new JButton("");
				board[row][col].addActionListener(listener);
				center.add(board[row][col]);
			}

		displayBoard();

		bottom.setLayout(new GridLayout(3, 2));
		bottom.add(quitButton);


		// add all to contentPane
		add(new JLabel("!!!!  Mine Sweeper  !!!!"), BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

		wins = 0;
		losses = 0;
		winsLabel = new JLabel("0");
		lossesLabel = new JLabel("0");
		add(winsLabel, BorderLayout.EAST);
		add(lossesLabel, BorderLayout.WEST);
	}

	private void displayBoard() {

		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++) {
				iCell = game.getCell(r, c);

				board[r][c].setText("");

				// readable, ifs are verbose (?)
					
				if (iCell.isMine())
					board[r][c].setText("!");
				
				if (!iCell.isMine() && !iCell.isExposed())
                  		        board[r][c].setText("");

				if (iCell.isExposed())
					board[r][c].setEnabled(false);
					board[r][c].setText("" + iCell.getMineCount());
				else
					board[r][c].setEnabled(true);
			}
	}


	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == quitButton) {
              			System.exit(0);
         		}
			
			for (int r = 0; r < boardSize; r++)
				for (int c = 0; c < boardSize; c++)
					if (board[r][c] == e.getSource())
						game.select(r, c);

//			for (int r = 0; r < 10; r++)     // are only mines left
//				for (int c = 0; c < 5; c++)
//					if (!board[r][c].isExposed() && !board[r][c].isMine())
//						status = GameStatus.NotOverYet;


			displayBoard();

			if (game.getGameStatus() == GameStatus.Lost) {
				displayBoard();
				JOptionPane.showMessageDialog(null, "You Lose \n" +
                       			"The game will reset");
				//exposeMines = false;
				game.reset();
				losses++;
				lossesLabel.setText("Losses: " + Integer.toString(losses));
				displayBoard();

			}

			if (game.getGameStatus() == GameStatus.WON) {
				JOptionPane.showMessageDialog(null, "You Win: all " +
                      			"mines have been found!\n The game will reset");
				game.reset();
				wins++;
				winsLabel.setText(Integer.toString(wins));
				displayBoard();
			}

		}

	}

}



