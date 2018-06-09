package com.example.android.quidditch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.color;

public class MainActivity extends AppCompatActivity {

    //declare score variables
    private int gryffindor_score, slytherin_score;
    //flag to hold if a new match is being started
    private boolean snitchCaught;

    //declare references for views in the layout
    private TextView gryffindor_score_display, slytherin_score_display, winner_display;
    private Button resetButton;

    //toast to display when a new games in started
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtain references for the score textviews
        gryffindor_score_display = findViewById(R.id.gryffindor_score);
        slytherin_score_display = findViewById(R.id.slytherin_score);

        //obtain reference for the reset button which resets the scores
        resetButton = findViewById(R.id.reset_button);

        //obtain reference for winner textview
        winner_display = findViewById(R.id.winner);
    }

    /**
     * handle button clicks to update or reset scores
     *
     * @param view reference to the button view that generated the click event
     */
    public void onClick(View view) {

        //verify if the snitch has been caught in the previous match - if yes, display a toast to the user
        // saying a new game has been started and reset the previous scores
        if (snitchCaught) {
            //cancel any outstanding toast before displaying the new toast
            cancelToast();
            toast = Toast.makeText(this, getString(R.string.new_game_text), Toast.LENGTH_SHORT);
            toast.show();
            resetScore(resetButton);
        }

        //get the ID of the button clicked and update the score for the respective team - a quaffle wins 10 points,
        // a foul gets the opponent 5 points, snitch awards 150 points and ends the game
        int id = view.getId();
        switch (id) {

            case R.id.gryffindor_snitch:
                gryffindor_score += 150;
                snitchCaught = true;
                break;

            case R.id.slytherin_snitch:
                slytherin_score += 150;
                snitchCaught = true;
                break;

            case R.id.gryffindor_quaffle:
                gryffindor_score += 10;
                break;

            case R.id.slytherin_quaffle:
                slytherin_score += 10;
                break;

            case R.id.gryffindor_foul:
                slytherin_score += 5;
                break;

            case R.id.slytherin_foul:
                gryffindor_score += 5;
                break;
        }

        //display the updated score
        displayScore();
    }


    /**
     * reset the score of both teams and display the updated score - clear the winner text, if any
     *
     * @param view reference to the reset button
     */
    public void resetScore(View view) {
        gryffindor_score = 0;
        slytherin_score = 0;
        snitchCaught = false;
        displayScore();
        winner_display.setVisibility(View.GONE);
    }

    /**
     * displays the updated score - score with leading points is displayed in green, opponent team's score is displayed in red
     */
    private void displayScore() {
        gryffindor_score_display.setText(String.valueOf(gryffindor_score));
        slytherin_score_display.setText(String.valueOf(slytherin_score));

        if (gryffindor_score > slytherin_score) {
            gryffindor_score_display.setTextColor(getResources().getColor(color.holo_green_dark));
            slytherin_score_display.setTextColor(getResources().getColor(color.holo_red_dark));
        } else if (slytherin_score > gryffindor_score) {
            slytherin_score_display.setTextColor(getResources().getColor(color.holo_green_dark));
            gryffindor_score_display.setTextColor(getResources().getColor(color.holo_red_dark));
        } else if (gryffindor_score == slytherin_score) {
            gryffindor_score_display.setTextColor(getResources().getColor(color.black));
            slytherin_score_display.setTextColor(getResources().getColor(color.black));
        }

        //if the snitch has been caught, game ends - display the user before a new game can begin
        if (snitchCaught)
            displayWinner();

    }

    /**
     * verify the winner, display the name and conclude the game
     */
    private void displayWinner() {
        String winnerText = getString(R.string.snitch_caught_text);
        winnerText += (gryffindor_score > slytherin_score ? getString(R.string.gryffindor) : getString(R.string.slytherin));
        winnerText += getString(R.string.world_cup_text);
        winner_display.setVisibility(View.VISIBLE);
        winner_display.setText(winnerText);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //cancel any outstanding toast when the user exits the app
        cancelToast();
    }

    /**
     * cancel any outstanding toasts - method will be called prior to displaying any new toasts
     */
    private void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
