package uni.fmi.bachelors.minesweeper;


import java.util.ArrayList;
import java.util.Random;

public class GameView {

    public boolean isNewGame;
    public int height;
    public int bombs;

    public int [][] mineGrid;
    ArrayList<Cell> cellGrid;
    Random random = new Random();


    public GameView (int height,  int bombs){
        this.height = height;
        this.bombs = bombs;
        isNewGame = true;

        mineGrid = new int[height][height];

        cellGrid = new ArrayList<>();

        //generate the bombs on random spots
        for(int i = 0; i < bombs; i++){
            int x = random.nextInt(height);
            int y = random.nextInt(height);

            // -1 is the bomb
            if(mineGrid[x][y] == -1){
                bombs++;
            }else{
                mineGrid[x][y] = -1;
            }
        }

        //add number of bombs nearby
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < height; j++) {

                if (mineGrid[i][j] != -1) {
                    int bombsCount = 0;

                    if (checkIndex(i - 1, j - 1) && mineGrid[i - 1][j - 1] == -1) bombsCount++;
                    if (checkIndex(i - 1, j + 1) && mineGrid[i - 1][j + 1] == -1) bombsCount++;
                    if (checkIndex(i + 1, j - 1) && mineGrid[i + 1][j - 1] == -1) bombsCount++;
                    if (checkIndex(i + 1, j + 1) && mineGrid[i + 1][j + 1] == -1) bombsCount++;
                    if (checkIndex(i - 1, j) && mineGrid[i - 1][j] == -1) bombsCount++;
                    if (checkIndex(i + 1, j) && mineGrid[i + 1][j] == -1) bombsCount++;
                    if (checkIndex(i, j + 1) && mineGrid[i][j + 1] == -1) bombsCount++;
                    if (checkIndex(i, j - 1) && mineGrid[i][j - 1] == -1) bombsCount++;

                    mineGrid[i][j] = bombsCount;

                }
                Cell cell = new Cell( i, j, mineGrid[i][j]);
                cellGrid.add(cell);
            }
        }

    }

    //method to check if the index is out of range
    public boolean checkIndex ( int x, int y){
        return x >= 0 && y >= 0 && x < height && y < height;
    }

}
