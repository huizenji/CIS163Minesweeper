package project2;


public class Cell {
    private int mineCount;
    private boolean isFlagged;
    private boolean isExposed;
    private boolean isExposedRec;
    private boolean isMarked;
    private boolean isMine;

    public Cell(boolean exposed, boolean exposedRec, boolean marked,
                boolean mine, boolean flagged) {
        isExposed = exposed;
        isExposedRec = exposedRec;
        isMarked = marked;
        isMine = mine;
        isFlagged = flagged;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isExposed() {
        return isExposed;
    }

    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    public boolean isExposedRec() {
        return isExposedRec;
    }

    public void setExposedRec(boolean exposedRec) {
        isExposedRec = exposedRec;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }


}

