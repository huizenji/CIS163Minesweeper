package project2;

import javax.swing.*;

public class MineSweeperGUI {
    private static int boardSize;
    private static int mineCount;
    private static String boardSizeStr = "";
    private static String mineCountStr = "";

    private static void promptBoardSize() {
        try {
            do {
                if (boardSizeStr.length() > 0) {
                    JOptionPane.showMessageDialog(null, "Please choose a" +
                                    " number between 3 and 30", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
                boardSizeStr = JOptionPane.showInputDialog(null, "Enter " +
                        "in the size of the board (3-30): ");
            } while (boardSizeStr.equals("") ||
                    Integer.parseInt(boardSizeStr) < 3 ||
                    Integer.parseInt(boardSizeStr) > 30);
        } catch (NumberFormatException e) {
            promptBoardSize();
        }

        boardSize = Integer.parseInt(boardSizeStr);
    }

    private static void promptMineCount() {
        try {
            mineCountStr = JOptionPane.showInputDialog(null,
                    "Enter in the desired number of mines " +
                            "(default is 10): ");

            // set mines to 10 if less than 1 or over cell count
            if (Integer.parseInt(mineCountStr) < 1 ||
                    Integer.parseInt(mineCountStr) >
                            Integer.parseInt(boardSizeStr) *
                                    Integer.parseInt(boardSizeStr))
                mineCountStr = "10";
        } catch (NumberFormatException e) {
            mineCountStr = "10";
        }

        mineCount = Integer.parseInt(mineCountStr);
    }

    public static void main(String[] args) {

        // prompt user for board size (able to exit program)
        try {
            promptBoardSize();
        } catch (NullPointerException e) {
            System.exit(0);
        }

        // prompt user for mine count (default value exists)
        promptMineCount();

        // set up GUI
        JFrame frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MineSweeperPanel panel = new MineSweeperPanel(boardSize, mineCount);
        frame.getContentPane().add(panel);
        frame.setSize(400, 400);
        frame.setVisible(true);

    }
}
