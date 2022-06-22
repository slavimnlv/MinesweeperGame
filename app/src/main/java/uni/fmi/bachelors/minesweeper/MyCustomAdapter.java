package uni.fmi.bachelors.minesweeper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.ViewHolder> {

    public ArrayList<Cell> cells;
    public GameView game;
    public int numOfTiles;
    public int bombs;
    public int initialBombs;
    public int theBombed;
    public int height;
    public  boolean clearNearbyCells;

    public MyCustomAdapter(ArrayList<Cell> cells, GameView gameView) {
        this.game = gameView;
        this.cells = game.cellGrid;
        clearNearbyCells = false;
        height =  game.height;
        numOfTiles = height*height;
        initialBombs = game.bombs;
        bombs = initialBombs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.tile, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, int position) {
        if(game.isNewGame){
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cells.get(position).value == -1){
                        openAll();
                        MainActivity.instance.onGameLost();
                        theBombed = position;
                        game.isNewGame = false;
                        notifyDataSetChanged();

                    }else if (!cells.get(position).isFlagged && !cells.get(position).isOpened){
                       numOfTiles--;
                       cells.get(position).isOpened = true;
                       if(cells.get(position).value == 0) openNearbies(cells.get(position).x, cells.get(position).y, height );
                       notifyDataSetChanged();
                   }

                   if(numOfTiles == initialBombs){
                       MainActivity.instance.onGameWon();
                       game.isNewGame = false;
                       openAll();
                       flagBombs();
                       notifyDataSetChanged();
                   }

                }
            });

            vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(!cells.get(position).isOpened){
                        if (!cells.get(position).isFlagged) {
                            cells.get(position).isFlagged = true;
                            bombs--;
                        }else{
                            cells.get(position).isFlagged = false;
                            bombs++;
                        }
                        notifyDataSetChanged();
                    }
                    MainActivity.instance.updateBombTV(bombs);
                    return true;
                }
            });

        }

        if (cells.get(position).isFlagged){
            if(game.isNewGame) vh.valueTextView.setImageResource(R.drawable.flag);
            else{
                if(cells.get(position).value == -1) vh.valueTextView.setImageResource(R.drawable.rigthbomb);
                else if (cells.get(position).value == 0) vh.valueTextView.setImageResource(R.drawable.tilef);
                else if (cells.get(position).value == 1) vh.valueTextView.setImageResource(R.drawable.tile1f);
                else if (cells.get(position).value == 2) vh.valueTextView.setImageResource(R.drawable.tile2f);
                else if (cells.get(position).value == 3) vh.valueTextView.setImageResource(R.drawable.tile3f);
                else if (cells.get(position).value == 4) vh.valueTextView.setImageResource(R.drawable.tile4f);
                else if (cells.get(position).value == 5) vh.valueTextView.setImageResource(R.drawable.tile5f);
            }
        }

        if(cells.get(position).isOpened && !cells.get(position).isFlagged){
            if (cells.get(position).value == -1) {
                if(cells.get(theBombed) != cells.get(position)) vh.valueTextView.setImageResource(R.drawable.bomb);
                else vh.valueTextView.setImageResource(R.drawable.bombed);
            }else if (cells.get(position).value == 0) vh.valueTextView.setImageResource(R.drawable.tilezero);
            else if (cells.get(position).value == 1) vh.valueTextView.setImageResource(R.drawable.tile1);
            else if (cells.get(position).value == 2) vh.valueTextView.setImageResource(R.drawable.tile2);
            else if (cells.get(position).value == 3) vh.valueTextView.setImageResource(R.drawable.tile3);
            else if (cells.get(position).value == 4) vh.valueTextView.setImageResource(R.drawable.tile4);
            else if (cells.get(position).value == 5) vh.valueTextView.setImageResource(R.drawable.tile5);
        }

        vh.setIsRecyclable(false);

    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView valueTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            valueTextView = itemView.findViewById(R.id.tileImageView);
        }


    }

    public void openNearbies(int y, int x, int height){


        if (game.checkIndex(x+1, y+1) && game.mineGrid[y+1][x+1]!=-1){
            int newPosition = ((x+1) + (y+1)*height);
            openNearbies(newPosition);
        }
        if (game.checkIndex(x+1, y) && game.mineGrid[y][x+1]!=-1){
            int newPosition = ((x+1) + (y)*height);
            openNearbies(newPosition);
        }
        if (game.checkIndex(x+1, y-1) && game.mineGrid[y-1][x+1]!=-1){
            int newPosition = ((x+1) + (y-1)*height);
            openNearbies(newPosition);
        }
        if (game.checkIndex(x, y+1) && game.mineGrid[y+1][x]!=-1){
            int newPosition = ((x) + (y+1)*height);
            openNearbies(newPosition);
        }
        if (game.checkIndex(x-1, y+1) && game.mineGrid[y+1][x-1]!=-1){
            int newPosition = ((x-1) + (y+1)*height);
            openNearbies(newPosition);
        }
        if (game.checkIndex(x-1, y) && game.mineGrid[y][x-1]!=-1){
            int newPosition = ((x-1) + (y)*height);
            openNearbies(newPosition);
        }
        if (game.checkIndex(x-1, y-1) && game.mineGrid[y-1][x-1]!=-1){
            int newPosition = ((x-1) + (y-1)*height);
            openNearbies(newPosition);
        }
        if (game.checkIndex(x, y-1) && game.mineGrid[y-1][x]!=-1){
            int newPosition = ((x) + (y-1)*height);
            openNearbies(newPosition);
        }

    }

    public void openNearbies(int newPosition){
        if(!cells.get(newPosition).isOpened && !cells.get(newPosition).isFlagged){
            cells.get(newPosition).isOpened = true;
            numOfTiles--;
            if(cells.get(newPosition).value == 0) openNearbies(cells.get(newPosition).x, cells.get(newPosition).y, height);
        }
    }

    public void openAll(){
        for (Cell cell: cells) {
            if( cell.isFlagged && cell.value != -1){
                bombs++;
               MainActivity.instance.updateBombTV(bombs);
            }
            cell.isOpened = true;

        }
    }

    public void flagBombs(){
        for (Cell cell: cells) {
            if( cell.value == -1){
                cell.isFlagged = true;
                MainActivity.instance.updateBombTV(0);
            }
        }
    }

}
