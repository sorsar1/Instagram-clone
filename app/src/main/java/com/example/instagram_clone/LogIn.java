package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class LogIn extends AppCompatActivity {
    private EditText edtEmail,edtPassword;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        edtEmail = findViewById(R.id.edtEmailLogIn);
        edtPassword = findViewById(R.id.edtPasswordLogIn);
        userName = "";
        findViewById(R.id.btnLogIn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        findViewById(R.id.btnSignUp2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
