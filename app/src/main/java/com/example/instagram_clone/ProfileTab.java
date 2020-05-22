package com.example.instagram_clone;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {
    private EditText edtProfileName,edtBio,edtFavSports,edtProfession,edtHobbies;
    private Button btnUpdateInfo;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtBio = view.findViewById(R.id.edtBio);
        edtFavSports = view.findViewById(R.id.edtFavSports);
        edtProfession = view.findViewById(R.id.edtProfession);
        edtHobbies = view.findViewById(R.id.edtHobbies);
        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);
        final ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser.get("ProfileName") != null){
            edtProfileName.setText(parseUser.get("ProfileName")+"");
        }
        if (parseUser.get("Bio") != null){
            edtBio.setText(parseUser.get("Bio")+"");
        }
        if (parseUser.get("FavoriteSports") != null) {
            edtFavSports.setText(parseUser.get("FavoriteSports")+"");
        }
        if (parseUser.get("Profession") != null) {
            edtProfession.setText(parseUser.get("Profession")+"");
        }
        if (parseUser.get("Hobbies") != null){
            edtHobbies.setText(parseUser.get("Hobbies")+"");
        }
        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("ProfileName",edtProfileName.getText().toString());
                parseUser.put("Bio",edtBio.getText().toString());
                parseUser.put("Profession",edtProfession.getText().toString());
                parseUser.put("Hobbies",edtHobbies.getText().toString());
                parseUser.put("FavoriteSports",edtFavSports.getText().toString());
                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Updating Info");
                progressDialog.show();
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(getContext(),"Info Updated",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
                        }else {
                            FancyToast.makeText(getContext(), e.getMessage(), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });


        return view;
    }
}
