package com.example.turtlegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton[][] imageButtons = new ImageButton[3][3];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.player_1_text_view);
        textViewPlayer2 = findViewById(R.id.player_2_text_view);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                imageButtons[i][j] = findViewById(resID);
                imageButtons[i][j].setOnClickListener(this);
                imageButtons[i][j].setTag(R.drawable.icon);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!( (int) ((ImageButton) v).getTag() == R.drawable.icon)){
            return;
        }

        if (player1Turn) {
            ((ImageButton) v).setImageResource(R.drawable.straw);
            v.setTag(R.drawable.straw);
        } else {
            ((ImageButton) v).setImageResource(R.drawable.turtle);
            v.setTag(R.drawable.turtle);
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    private boolean checkForWin() {
        int[][] field = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = (int) imageButtons[i][j].getTag();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0] == (field[i][1])
                    && field[i][0] == field[i][2]
                    && field[i][0] != R.drawable.icon) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i] == field[1][i]
                    && field[0][i] == field[2][i]
                    && field[0][i] != R.drawable.icon) {
                return true;
            }
        }

        if (field[0][0] == field[1][1]
                && field[0][0] == field[2][2]
                && field[0][0] != R.drawable.icon) {
            return true;
        }

        if (field[0][2] == field[1][1]
                && field[0][2] == field[2][0]
                && field[0][2] != R.drawable.icon) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "The straws have won...", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "You've saved the turtles!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Unsurprisingly, nobody wins", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Straws: " + player1Points);
        textViewPlayer2.setText("Turtles: " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                imageButtons[i][j].setImageResource(R.drawable.icon);
                imageButtons[i][j].setTag(R.drawable.icon);
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}