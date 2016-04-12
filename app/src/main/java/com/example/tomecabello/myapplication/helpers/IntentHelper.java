package com.example.tomecabello.myapplication.helpers;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;

/**
 * Created by AKiniyalocts on 2/23/15.
 *
 */
public class IntentHelper {
  public final static int FILE_PICK = 1001;


  public static void chooseFileIntent(Activity activity){
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
   // intent.setType("image/*");
    activity.startActivityForResult(intent, FILE_PICK);
  }
}
