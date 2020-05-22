package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private EditText edtEmail,edtUserName,edtPassword;
    private Button btnSignUp,btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        setTitle("Sign up");
        edtEmail = findViewById(R.id.edtEmail);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);

        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);
                }
                return false;
            }
        });

        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        if (ParseUser.getCurrentUser() != null){
//            ParseUser.getCurrentUser().logOut();
            transitionToSocialActivity();
        }
        btnLogIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogIn :
                Intent intent = new Intent(SignUp.this,LogIn.class);
                startActivity(intent);
                break;
            case R.id.btnSignUp :
                if (edtEmail.getText().toString().equals("") || edtUserName.getText().toString().equals("") ||
                edtPassword.getText().toString().equals("")){
                    FancyToast.makeText(SignUp.this,"Email , Username , Password is required",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                }else {
                    try {
                        final ParseUser user = new ParseUser();
                        user.setEmail(edtEmail.getText().toString());
                        user.setUsername(edtUserName.getText().toString());
                        user.setPassword(edtPassword.getText().toString());
                        final ProgressDialog progressDialog = new ProgressDialog(SignUp.this);
                        progressDialog.setMessage("Signing Up " + user.getUsername());
                        progressDialog.show();
                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    FancyToast.makeText(SignUp.this, user.getUsername() + " is signed up successfully",
                                            FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                    transitionToSocialActivity();
                                } else {
                                    FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                                }
                                progressDialog.dismiss();
                            }
                        });

                    } catch (Exception e) {
                        FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                    }
                }
                break;
        }

    }
    public void rootLayoutTapped(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void transitionToSocialActivity(){
        Intent intent = new Intent(SignUp.this,SocialMediaActivity.class);
        startActivity(intent);
    }
}
