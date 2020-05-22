package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail,edtPassword;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setTitle("Log in");
        if (ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }
        edtEmail = findViewById(R.id.edtEmailLogIn);
        edtPassword = findViewById(R.id.edtPasswordLogIn);
        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(findViewById(R.id.btnLogIn2));
                }
                return false;
            }


        });
        userName = "";
        findViewById(R.id.btnLogIn2).setOnClickListener(this);
        findViewById(R.id.btnSignUp2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogIn2:
                try {
                    ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
                    parseQuery.whereEqualTo("email",edtEmail.getText().toString());
                    parseQuery.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            if (e == null) {
                                if (objects.size() > 0){
                                    for (ParseUser user : objects) {
                                        userName = user.getUsername();
                                        ParseUser.logInInBackground(userName, edtPassword.getText().toString(), new LogInCallback() {
                                            @Override
                                            public void done(ParseUser user, ParseException e) {
                                                if (e == null && user != null) {
                                                    FancyToast.makeText(LogIn.this,user.getUsername()+" is logged In",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                                }else {
                                                    FancyToast.makeText(LogIn.this,e.getMessage(),FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                                }
                                            }
                                        });
//                                        FancyToast.makeText(LogIn.this,"found",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                    }
                                }
                            }
                        }
                    });

                }catch (Exception e){
                    FancyToast.makeText(LogIn.this,e.getMessage(),FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }
                break;
            case R.id.btnSignUp2:
                finish();
                break;
        }
    }
    public void layoutTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
