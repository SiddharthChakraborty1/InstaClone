package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity implements  View.OnKeyListener {
    EditText username, pass, confPass;
    Button signup;
    ImageView bg;
    TextView tvLogin;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("InstaClone");
        if(ParseUser.getCurrentUser() != null)
        {
            userLists();
        }

        username = findViewById(R.id.etUname);
        pass = findViewById(R.id.etPass);
        confPass = findViewById(R.id.confPass);
        signup = findViewById(R.id.btnSignup);
        tvLogin = findViewById(R.id.tvLogin);
        layout = findViewById(R.id.layoutSignin);
        bg = findViewById(R.id.imageView2);
        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            }
        });
        confPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                {
                    signup(view);
                }
                return false;
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void userLists()
    {
        Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);
        finish();
    }

    public void signup(View view)
    {
        String strUsername = username.getText().toString();
        String strPassword = pass.getText().toString();
        String strConfPass = confPass.getText().toString();

        if(strUsername.isEmpty() || strPassword.isEmpty() || strConfPass.isEmpty())
        {
            if(strUsername.isEmpty())
            {
                username.requestFocus();
                username.setError("Can't be empty");
            }
            if(strPassword.isEmpty())
            {
                pass.requestFocus();
                pass.setError("Can't be empty");
            }
            if(strConfPass.isEmpty())
            {
                confPass.requestFocus();
                confPass.setError("Can't be empty");
            }
        }else
        {
            if(strPassword.equals(strConfPass))
            {
                ParseUser user = new ParseUser();
                user.setUsername(strUsername);
                user.setPassword(strPassword);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null)
                        {
                            Toast.makeText(getApplicationContext(), "Created successfully", Toast.LENGTH_SHORT).show();
                            userLists();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            else
            {
                Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)
        {
            signup(view);
        }
        return false;
    }
}