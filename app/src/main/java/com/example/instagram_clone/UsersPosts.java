package com.example.instagram_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPosts extends AppCompatActivity {

    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);
        linearLayout = findViewById(R.id.linearLayout);
        Intent receivedIntentObject = getIntent();
        final String receivedUsername = receivedIntentObject.getStringExtra("username");
        setTitle(receivedUsername + "'s posts");
        // FancyToast.makeText(this,receivedUsername, Toast.LENGTH_SHORT,FancyToast.INFO,false).show();
        ParseQuery<ParseObject> query = new ParseQuery<>("Photo");
        query.whereEqualTo("username",receivedUsername);
        query.orderByDescending("createdAt");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0){
                    for (ParseObject object : objects) {
                        final TextView postDesc = new TextView(UsersPosts.this);
                        if (object.get("imgDes") != null){
                            postDesc.setText(object.get("imgDes")+"");
                        }
                        ParseFile postImageFile = (ParseFile) object.get("picture");
                        postImageFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (data != null && e == null){
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                ImageView postImage = new ImageView(UsersPosts.this);
                                LinearLayout.LayoutParams imageView_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                imageView_params.setMargins(5,5,5,5);
                                postImage.setLayoutParams(imageView_params);
                                postImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                postImage.setImageBitmap(bitmap);

                                LinearLayout.LayoutParams textView_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                textView_params.setMargins(5,5,5,15);
                                postDesc.setLayoutParams(textView_params);
                                postDesc.setGravity(Gravity.CENTER);
                                postDesc.setBackgroundColor(Color.BLUE);
                                postDesc.setTextSize(30f);
                                postDesc.setTextColor(Color.WHITE);

                                linearLayout.addView(postImage);
                                linearLayout.addView(postDesc);
                                }
                            }
                        });
                    }
                }else {
                    FancyToast.makeText(UsersPosts.this,receivedUsername+" has no posts :(",Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                    finish();
                }
                progressDialog.dismiss();
            }
        });
    }
}
