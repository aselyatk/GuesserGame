package com.example.guesser;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btnSubmit;
    private TextView tvComment;
    private EditText etGuess;
    private int guess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubmit = findViewById(R.id.btnSubmit);
        tvComment = findViewById(R.id.tvComment);
        etGuess = findViewById(R.id.etGuess);

        // Initialize the game
        startNewGame();

        // Set onClick listener
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkGuess();
            }
        });
    }

    private void startNewGame() {
        guess = new Random().nextInt(100) + 1;
        tvComment.setText(R.string.enter_number_description);
    }

    private void checkGuess() {
        String input = etGuess.getText().toString();
        if (input.isEmpty()) {
            tvComment.setText(R.string.enter_valid_number);
            return;
        }

        int userGuess;
        try {
            userGuess = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            tvComment.setText(R.string.enter_valid_number);
            return;
        }

        if (userGuess < 1 || userGuess > 100) {
            tvComment.setText(R.string.invalid_guess);
        } else if (userGuess < guess) {
            tvComment.setText(R.string.guess_too_low);
        } else if (userGuess > guess) {
            tvComment.setText(R.string.guess_too_high);
        } else {
            tvComment.setText(getString(R.string.correct_guess, guess));
            // Offer to restart the game
            tvComment.append("\n" + getString(R.string.play_again));
            btnSubmit.setText(R.string.play_again);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startNewGame();
                    btnSubmit.setText(R.string.submit_button_text);
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkGuess();
                        }
                    });
                }
            });
        }
    }
}
