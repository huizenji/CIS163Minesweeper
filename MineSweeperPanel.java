package project2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MineSweeperPanel extends JPanel {

    private JButton[][] board;
    private JButton quitButton = new JButton("QUIT");
    ;
    private JButton recursiveButton = new JButton("Toggle Recursion");
    private Cell iCell;
    private int boardSize, wins, losses;
    private JLabel winsLabel, lossesLabel, recursionLabel;

    private MineSweeperGame game;  // model

    public MineSweeperPanel() {


        JPanel bottom = new JPanel();
        JPanel top = new JPanel();

        top.addMouseListener(new mouse());

        quitButton.addMouseListener(new mouse());
        recursiveButton.addMouseListener(new mouse());

        // create game, listeners
        mouse listener = new mouse();

        game = new MineSweeperGame();

        // create the board
        boardSize = game.getBoardSize();
        top.setLayout(new GridLayout(boardSize, boardSize));
        board = new JButton[boardSize][boardSize];
        // ImageIcon emptyIcon = new ImageIcon("icon.png");

        for (int row = 0; row < boardSize; row++)
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = new JButton("");
                board[row][col].addMouseListener(listener);
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
                } else
                    board[r][c].setEnabled(true);


                // readable, ifs are verbose

                if(iCell.isFlagged()) {
                    board[r][c].setText("?");
                }
                else if (iCell.isMine()) {
                    board[r][c].setText("!");
                }
                else if (!iCell.isExposed()) {
                    board[r][c].setText("");



                }

            }
    }


    private class mouse implements MouseListener {

            @Override
            public void mouseClicked(MouseEvent e) {
        }

            @Override
            public void mousePressed(MouseEvent e) {
        }

            @Override
            public void mouseReleased(MouseEvent e) {

                for (int r = 0; r < boardSize; r++)
                    for (int c = 0; c < boardSize; c++)
                        if (board[r][c] == e.getSource()) {
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                if (game.getCell(r, c).isFlagged()) {
                                    game.getCell(r, c).setFlagged(false);
                                } else {
                                    game.getCell(r, c).setFlagged(true);
                                }
                            }
                            if (e.getButton() == MouseEvent.BUTTON1) {
                                if (!game.getCell(r, c).isFlagged())
                                game.select(r, c);
                            }
                        }
                displayBoard();


                if (e.getSource() == quitButton) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        int confirm = JOptionPane.showConfirmDialog(null,
                                "Quit Game?", "QUIT", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            System.exit(0);
                        }
                    }
                }


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

            @Override
            public void mouseEntered(MouseEvent e) {
        }

            @Override
            public void mouseExited(MouseEvent e) {
        }



        }

    }





