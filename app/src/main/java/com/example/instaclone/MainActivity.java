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

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {


    EditText username, password;
    TextView signup;
    ImageView bg;
    RelativeLayout layout;
    Button lgn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("InstaClone");
        if(ParseUser.getCurrentUser() != null)
        {
            userLists();
        }
        username = findViewById(R.id.etUsername);
        layout = findViewById(R.id.layoutLogin);
        password = findViewById(R.id.etPassword);
        lgn = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.tvSignup);
        bg = findViewById(R.id.imageView);
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
                InputMethodManager inputMethodManager =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            }
        });
        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                {
                    login(view);
                }
                return false;
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*ParseObject object = new ParseObject("Hello");
        object.put("Name","Siddharth");
        object.put("Info","Chal rha hai kya");
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null)
                {
                    Log.i("Success!","The application is connecting to parse");
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        e.printStackTrace();
                    Log.i("Failure", "Application not connecting to parse");
                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }
    public void userLists()
    {
        Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);
        finish();
    }
    public void login(View view)
    {
         String strUsername = username.getText().toString();
        String strPassword = password.getText().toString();
        if(strUsername.isEmpty() || strPassword.isEmpty())
        {   if(strUsername.isEmpty())
            {
                username.setError("Can't be empty");
                username.requestFocus();
             }
            if (strPassword.isEmpty())
            {
                password.setError("Can't be empty");
                password.requestFocus();
            }
        }
        else
        {
            ParseUser.logInInBackground(strUsername, strPassword, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user != null)
                    {
                        Toast.makeText(getApplicationContext(), "login successful", Toast.LENGTH_SHORT).show();
                        userLists();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }



    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)
        {
            login(view);
        }
        return false;
    }
}