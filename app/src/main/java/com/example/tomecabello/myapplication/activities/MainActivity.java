package com.example.tomecabello.myapplication.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tomecabello.myapplication.CosasFragment;
import com.example.tomecabello.myapplication.helpers.NotificationHelper;
import com.example.tomecabello.myapplication.notitas;
import com.firebase.client.Firebase;
import com.squareup.picasso.Picasso;

import java.io.File;

import com.example.tomecabello.myapplication.R;
import com.example.tomecabello.myapplication.helpers.DocumentHelper;
import com.example.tomecabello.myapplication.helpers.IntentHelper;
import com.example.tomecabello.myapplication.imgurmodel.ImageResponse;
import com.example.tomecabello.myapplication.imgurmodel.Upload;
import com.example.tomecabello.myapplication.services.UploadService;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    public final static String TAG = MainActivity.class.getSimpleName();

    /*
      These annotations are for ButterKnife by Jake Wharton
      https://github.com/JakeWharton/butterknife
     */
    @Bind(R.id.imageview)
    ImageView uploadImage;
    @Bind(R.id.editText_upload_title)
    EditText uploadTitle;
    @Bind(R.id.editText_upload_desc)
    EditText uploadDesc;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    MediaPlayer mp;


    private Upload upload; // Upload object containging image and meta data
    private File chosenFile; //chosen file from intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainf);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

    public static File f;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri returnUri;

        if (requestCode != IntentHelper.FILE_PICK) {
            return;
        }

        if (resultCode != RESULT_OK) {
            return;
        }

        returnUri = data.getData();
        String filePath = DocumentHelper.getPath(this, returnUri);
        //Safety check to prevent null pointer exception
        if (filePath == null || filePath.isEmpty()) return;
        chosenFile = new File(filePath);
        f = new File(filePath);

                /*
                    Picasso is a wonderful image loading tool from square inc.
                    https://github.com/square/picasso
                 */
        Picasso.with(getBaseContext())
                .load(chosenFile)
                .placeholder(R.drawable.ic_photo_library_black)
                .fit()
                .into(uploadImage);

    }


    @OnClick(R.id.imageview)
    public void onChooseImage() {
        uploadDesc.clearFocus();
        uploadTitle.clearFocus();
        IntentHelper.chooseFileIntent(this);
    }

    private void clearInput() {
        uploadTitle.setText("");
        uploadDesc.clearFocus();
        uploadDesc.setText("");
        uploadTitle.clearFocus();
        uploadImage.setImageResource(R.drawable.ic_photo_library_black);
        this.finish();

    }

    @OnClick(R.id.fab)
    public void uploadImage() {
    /*
      Create the @Upload object
     */
        if (chosenFile == null) return;
        createUpload(chosenFile);

    /*
      Start upload
     */

        mp = MediaPlayer.create(this, R.raw.sound);

        new UploadService(this).Execute(upload, new UiCallback());
        mp.start();
    }

    static notitas notitas = new notitas();

    private void createUpload(File image) {
        upload = new Upload();

        notitas.setMensa(uploadDesc.getText().toString());
        notitas.setTitulo(uploadTitle.getText().toString());
        upload.image = image;
        upload.title = uploadTitle.getText().toString();
        upload.description = uploadDesc.getText().toString();

    }

    private class UiCallback implements Callback<ImageResponse> {

        @Override
        public void success(ImageResponse imageResponse, Response response) {

            clearInput();

            Firebase ref = new Firebase("https://adri.firebaseio.com");
        }

        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
            if (error == null) {
                Snackbar.make(findViewById(R.id.rootView), "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
        }

    }

    public static void subir(){
        System.out.println(NotificationHelper.rep);
        Firebase ref = new Firebase("https://adri.firebaseio.com");

        notitas.setLat(String.valueOf(CosasFragment.lat));
        notitas.setLo(String.valueOf(CosasFragment.lo));
        notitas.setUr(NotificationHelper.rep);
        ref.push().setValue(notitas);

    }
}
