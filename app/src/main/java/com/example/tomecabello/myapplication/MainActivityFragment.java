package com.example.tomecabello.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import org.osmdroid.views.MapView;

import com.firebase.client.Firebase;

import java.io.File;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Firebase.setAndroidContext(getContext());
        Firebase myFirebaseRef = new Firebase("https://adri.firebaseio.com/");
        myFirebaseRef.setValue("Do you have da56ta? You'll love Firebase.");


        System.out.println("coloooooeeeeddddddddddddddddddddddddddddddddddd");
       Intent i = new Intent();
        i.setAction(android.content.Intent.ACTION_VIEW);


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final Button button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                write((Button) v);
            }
        });







        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void write(Button view){
        startActivity(new Intent(".activities.MainActivity"));

    }


}
