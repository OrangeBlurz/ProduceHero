package com.example.producehero.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.producehero.R;

/**
 * View Activity
 * Responsible for getting inputs for a username and password
 */
public class SignInActivity extends AppCompatActivity {

    //Member variables
    private EditText    usernameField;
    private EditText    passwordField;
    private TextView    titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();
        usernameField = findViewById(R.id.user_name_edit_text);
        passwordField = findViewById(R.id.password_edit_text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Reset on every rotation
        usernameField.setText("");
        passwordField.setText("");
    }

    /**
     * Validate the fields actually have text in them
     * @return true if both fields have text, false if one or both are empty
     */
    private boolean validateInputs() {
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        return true;
    }

    private void init() {
        setupClickListeners();

        titleView = findViewById(R.id.title_view);

        //Could be cleaned up
        titleView.setText(Html.fromHtml("<b>P</>roduce<b>H</b>ero"));
    }

    private void setupClickListeners() {
        Button signInButton = findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    startActivity(RoutesActivity.newActivityIntent(SignInActivity.this));
                } else {
                    Toast.makeText(SignInActivity.this, "Please enter a username and a password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
