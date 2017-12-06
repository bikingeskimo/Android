package prosjekt.sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;
import static android.content.ContentValues.TAG;


public class Board extends Activity {


    public static final String KEY_DIFFICULTY =
            "org.prosjekt.sudoku.difficulty";
    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;
    public static final int DIFFCUSTOM = 3;

    private int board[] = new int[9 * 9]; //BoardSize

    private String easyPuzzle =
                    "360000000004230800000004200" +
                    "070460003820000014500013020" +
                    "001900000007048300000000045";
    private String mediumPuzzle =
                    "650000070000506000014000005" +
                    "007009000002314700000700800" +
                    "500000630000201000030000097";
    private String hardPuzzle =
                    "009000000080605020501078000" +
                    "000000700706040102004000000" +
                    "000720903090301080000000600";

    private String customPuzzle =
            "000000000000000000000000000" +
            "000000000000000000000000000" +
            "000000000000000000000000000";

    private BoardView boardView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int diff = getIntent().getIntExtra(KEY_DIFFICULTY,DIFFICULTY_EASY);
        board = getBoard(diff);
        calculateUsedTiles();

        boardView = new BoardView(this);
        setContentView(boardView);
        boardView.requestFocus();
    }

    /** Get new board when requested or difficulty changed */
    private int[] getBoard(int diff){
        String board;
        switch (diff){
            case DIFFICULTY_HARD:
                board = hardPuzzle;
                break;
            case DIFFICULTY_MEDIUM:
                board = mediumPuzzle;
                break;
            case DIFFICULTY_EASY:
                board = easyPuzzle;
                break;
            case DIFFCUSTOM:
                board = customPuzzle;
                // TODO: Place buttons for saving board

                //Quickfix
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.inputNumber);
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getTextSize() == 81) {
                            customPuzzle = input.getText().toString();
                        }else {
                            Toast.makeText(Board.this, getString(R.string.error), Toast.LENGTH_LONG).show();

                        }
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();


                break;
            default:
                board = customPuzzle;
                break;
        }
        return fromBoardString(board);
    }

    /** Convert an array into a board string */
    static private String toBoardString(int[] puz) {
        StringBuilder buf = new StringBuilder();
        for (int element : puz) {
            buf.append(element);
        }
        return buf.toString();
    }

    /** Convert a board string into an array */
    static protected int[] fromBoardString(String string) {
        int[] board = new int[string.length()];
        for (int i = 0; i < board.length; i++) {
            board[i] = string.charAt(i) - '0';
        }
        return board;
    }

    /** Return the tile at the given coordinates */
    private int getTile(int x, int y) {
        return board[y * 9 + x];
    }

    /** Change the tile at the given coordinates */
    private void setTile(int x, int y, int value) {
        board[y * 9 + x] = value;
    }


    /** Return a string for the tile at the given coordinates */
    protected String getTileString(int x, int y) {
        int v = getTile(x, y);
        if (v == 0)
            return "";
        else
            return String.valueOf(v);
    }

    /** Change the tile only if it's a valid move */
    protected boolean setTileIfValid(int x, int y, int value) {
        int tiles[] = getUsedTiles(x, y);
        if (value != 0) {
            for (int tile : tiles) {
                if (tile == value)
                    return false;
            }
        }
        setTile(x, y, value);
        calculateUsedTiles();
        return true;
    }

    /** Open the keypad if there are any valid moves */
    protected void showKeypadOrError(int x, int y) {
        int tiles[] = getUsedTiles(x, y);
        if (tiles.length == 9) {
            Toast toast = Toast.makeText(this,
                    R.string.no_moves_label, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Log.d(TAG, "showKeypad: used=" + toBoardString(tiles));
            Dialog v = new Keypad(this, tiles, boardView);
            v.show();
        }
    }

    /** Cache of used tiles */
    private final int used[][][] = new int[9][9][];

    /** Return cached used tiles visible from the given coords */
    protected int[] getUsedTiles(int x, int y) {
        return used[x][y];
    }

    /** Compute the two dimensional array of used tiles */
    private void calculateUsedTiles() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                used[x][y] = calculateUsedTiles(x, y);
                // Log.d(TAG, "used[" + x + "][" + y + "] = "
                // + toPuzzleString(used[x][y]));
            }
        }
    }

    /** Compute the used tiles visible from this position */
    private int[] calculateUsedTiles(int x, int y) {
        int c[] = new int[9];
        // horizontal
        for (int i = 0; i < 9; i++) {
            if (i == y)
                continue;
            int t = getTile(x, i);
            if (t != 0)
                c[t - 1] = t;
        }
        // vertical
        for (int i = 0; i < 9; i++) {
            if (i == x)
                continue;
            int t = getTile(i, y);
            if (t != 0)
                c[t - 1] = t;
        }
        // same cell block
        int startx = (x / 3) * 3;
        int starty = (y / 3) * 3;
        for (int i = startx; i < startx + 3; i++) {
            for (int j = starty; j < starty + 3; j++) {
                if (i == x && j == y)
                    continue;
                int t = getTile(i, j);
                if (t != 0)
                    c[t - 1] = t;
            }
        }
        // compress
        int nused = 0;
        for (int t : c) {
            if (t != 0)
                nused++;
        }
        int c1[] = new int[nused];
        nused = 0;
        for (int t : c) {
            if (t != 0)
                c1[nused++] = t;
        }
        return c1;
    }


    public void setCustomPuzzle(String customPuzzle) {
        this.customPuzzle = customPuzzle;
    }
}
