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
        JPanel top = new JPanel();

        quitButton.addActionListener(new ButtonListener());
        recursiveButton.addActionListener(new ButtonListener());

        // create game, listeners
        ButtonListener listener = new ButtonListener();

        game = new MineSweeperGame();

        // create the board
        boardSize = game.getBoardSize();
        top.setLayout(new GridLayout(boardSize, boardSize));
        board = new JButton[boardSize][boardSize];
       // ImageIcon emptyIcon = new ImageIcon("icon.png");

        for (int row = 0; row < boardSize; row++)
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = new JButton("");
                board[row][col].addActionListener(listener);
                top.add(board[row][col]);
            }

        displayBoard();

        recursionLabel = new JLabel("Recursive Zero Fill");

        bottom.setLayout(new GridLayout(3, 2));
        bottom.add(quitButton);
        bottom.add(recursiveButton);

        wins = 0;
        losses = 0;
        winsLabel = new JLabel("Wins: 0");
        lossesLabel = new JLabel("Losses: 0");
        bottom.add(winsLabel);
        bottom.add(lossesLabel);

        bottom.add(recursionLabel);

        // add all to contentPane
        add(new JLabel("!!!!  Mine Sweeper  !!!!"), BorderLayout.NORTH);
        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);



    }


    private void displayBoard() {

        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++) {
                iCell = game.getCell(r, c);

                if (iCell.isExposed()) {
                    board[r][c].setEnabled(false);
                    board[r][c].setText("" + iCell.getMineCount());
                }
                else
                    board[r][c].setEnabled(true);


                // readable, ifs are verbose (?)

                if (iCell.isMine())
                    board[r][c].setText("!");
                if (!iCell.isMine() && !iCell.isExposed())
                    board[r][c].setText("");



            }
    }


    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == quitButton) {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Quit Game?","QUIT",JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
            for (int r = 0; r < boardSize; r++)
                for (int c = 0; c < boardSize; c++)
                    if (board[r][c] == e.getSource())
                        game.select(r, c);

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
                winsLabel.setText("Wins: " + Integer.toString(wins));
                displayBoard();
            }




        }

    }

}




