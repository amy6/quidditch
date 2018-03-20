package com.example.android.quidditch;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static android.R.color;

public class MainActivity extends AppCompatActivity {

    private TextView gryffindor_score_display, slytherin_score_display, winner_display;
    private Button resetButton, slytherin_foul_button, gryffindor_quaffle_button, gryffindor_snitch_button;
    private Button gryffindor_foul_button, slytherin_quaffle_button, slytherin_snitch_button;
    private ArrayList<Button> buttonList;
    private int gryffindor_score, slytherin_score;
    private boolean snitchCaught;
    private Toast toast;
    private int count;
    private boolean firstUsage;
    private final Handler handler = new Handler();
    private final long delay = 300;
    private long longDelay = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gryffindor_score_display = findViewById(R.id.gryffindor_score);
        slytherin_score_display = findViewById(R.id.slytherin_score);

        winner_display = findViewById(R.id.winner);

        toast = new Toast(this);

        setUpListOfButtons();

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        firstUsage = sharedPref.getBoolean(getString(R.string.setup_onboard), true);

        if(firstUsage) {

            for (Button button : buttonList) {
                button.setClickable(false);
            }

            cancelToast();
            toast = Toast.makeText(this, R.string.welcome_text, Toast.LENGTH_SHORT);
            toast.show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setUpWalkThrough(gryffindor_quaffle_button.getId(), R.string.sv_quaffle_content);
                }
            }, longDelay);

        }


    }

    private void cancelToast() {
        if(toast != null) {
            toast.cancel();
        }
    }

    private void setUpListOfButtons() {
        buttonList = new ArrayList<>();
        resetButton = findViewById(R.id.reset_button);
        slytherin_foul_button = findViewById(R.id.slytherin_foul);
        gryffindor_quaffle_button = findViewById(R.id.gryffindor_quaffle);
        gryffindor_snitch_button = findViewById(R.id.gryffindor_snitch);
        gryffindor_foul_button = findViewById(R.id.gryffindor_foul);
        slytherin_quaffle_button = findViewById(R.id.slytherin_quaffle);
        slytherin_snitch_button = findViewById(R.id.slytherin_snitch);

        buttonList.add(resetButton);
        buttonList.add(slytherin_foul_button);
        buttonList.add(gryffindor_quaffle_button);
        buttonList.add(gryffindor_snitch_button);
        buttonList.add(gryffindor_foul_button);
        buttonList.add(slytherin_quaffle_button);
        buttonList.add(slytherin_snitch_button);
    }

    private void setUpWalkThrough(int viewID, int stringID) {

        new MaterialTapTargetPrompt.Builder(this)
                .setTarget(findViewById(viewID))
                .setPrimaryText(stringID)
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                    {

                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED
                                || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED)
                        {
                            count++;
                            switch (count) {
                                case 1:
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            setUpWalkThrough(slytherin_foul_button.getId(), R.string.sv_foul_content);
                                        }
                                    }, delay);
                                    break;

                                case 2:
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            setUpWalkThrough(gryffindor_snitch_button.getId(), R.string.sv_snitch_content);
                                        }
                                    }, delay);
                                    break;

                                case 3:
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            setUpWalkThrough(resetButton.getId(), R.string.sv_reset_content);
                                        }
                                    }, delay);
                                    firstUsage = false;
                                    break;

                                default:
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            for (Button button : buttonList) {
                                                button.setClickable(true);
                                            }
                                        }
                                    }, delay);

                            }

                        }
                    }
                })
                .setBackgroundColour(ContextCompat.getColor(this, R.color.colorPrimary))
                .setPromptFocal(new RectanglePromptFocal())
                .show();


    }

    public void onClick(View view) {

        if (snitchCaught) {
            cancelToast();
            toast = Toast.makeText(this, getString(R.string.new_game_text), Toast.LENGTH_SHORT);
            toast.show();
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


    public void resetScore(View view) {


        gryffindor_score = 0;
        slytherin_score = 0;
        snitchCaught = false;
        displayScore();
        winner_display.setVisibility(View.GONE);
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

        cancelToast();

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.setup_onboard), firstUsage);
        editor.apply();
    }
}
