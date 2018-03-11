package com.example.android.quidditch;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static android.R.color;

public class MainActivity extends AppCompatActivity {

    private TextView gryffindor_score_display, slytherin_score_display, winner_display;
    private Button resetButton;
    private int gryffindor_score, slytherin_score;
    private boolean snitchCaught;
    private int count;
    private boolean onboardAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gryffindor_score_display = findViewById(R.id.gryffindor_score);
        slytherin_score_display = findViewById(R.id.slytherin_score);

        winner_display = findViewById(R.id.winner);
        resetButton = findViewById(R.id.reset_button);

        setUpShowCaseView(R.id.slytherin_quaffle, R.string.sv_quaffle_content);


    }

    private void setUpShowCaseView(int viewID, int stringID) {
        onboardAnimation = true;

        new MaterialTapTargetPrompt.Builder(this)
                .setTarget(findViewById(viewID))
                .setPrimaryText(stringID)
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                                || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED)
                        {
                            count++;
                            switch (count) {
                                case 1:
                                    setUpShowCaseView(R.id.slytherin_foul, R.string.sv_foul_content);
                                    break;

                                case 2:
                                    setUpShowCaseView(R.id.gryffindor_snitch, R.string.sv_snitch_content);
                                    break;

                                case 3:
                                    setUpShowCaseView(resetButton.getId(), R.string.sv_reset_content);
                                    break;

                                case 4:
                                    onboardAnimation = false;

                            }

                        }
                    }
                })
                .setBackgroundColour(ContextCompat.getColor(this, R.color.colorPrimary))
                .setPromptFocal(new RectanglePromptFocal())
                .show();

    }

    public void onClick(View view) {

        if(onboardAnimation) {
            return;
        }
        if (snitchCaught) {
            Toast.makeText(this, getString(R.string.new_game_text), Toast.LENGTH_SHORT).show();
            resetScore(resetButton);
        }
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

        displayScore();
    }


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

        if (snitchCaught)
            displayWinner();

    }

    public void resetScore(View view) {
        gryffindor_score = 0;
        slytherin_score = 0;
        snitchCaught = false;
        displayScore();
        winner_display.setVisibility(View.GONE);
    }

    private void displayWinner() {
        String winnerText = getString(R.string.snitch_caught_text);
        winnerText += (gryffindor_score > slytherin_score ? getString(R.string.gryffindor) : getString(R.string.slytherin));
        winnerText += getString(R.string.world_cup_text);
        winner_display.setVisibility(View.VISIBLE);
        winner_display.setText(winnerText);
    }

}
