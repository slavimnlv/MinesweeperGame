package uni.fmi.bachelors.minesweeper;


public class Cell {
    public int x;
    public int y;
    public int value;
    public boolean isFlagged;
    public  boolean isOpened;

    public Cell ( int x, int y, int value){
        this.x = x;
        this.y = y;
        this.value = value;
        isFlagged = false;
        isOpened = false;
    }

}

