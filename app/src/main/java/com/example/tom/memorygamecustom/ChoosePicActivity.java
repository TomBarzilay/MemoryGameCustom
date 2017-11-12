package com.example.tom.memorygamecustom;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.drm.DrmStore;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

public class ChoosePicActivity extends AppCompatActivity {

    //uri drown from gallery
    //the intent code of the image picking from gallery
    private static final int PICK_IMAGE = 100;
    //the code for requesting the external storage permission
    private final int MEDIA_REQUEST = 102;
    //the last view that was clicked
    public static Button chosenView;
    //String[] FilesPath;
    public static HashSet<String> newImages;
    int pairs;
    int level;
    private HashSet<String> primitiveImages = new HashSet<>();
    public static boolean done;
    Handler handler;
    int ifToclear = 0;
    ArrayList<String> imagesPaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHandler();
        //if (ifToclear!=pairs)
        newImages = new HashSet<>();
        level = getIntent().getIntExtra(ChooseLevelActivity.LEVEL, 0);
        switch (level) {
            case 1:
                setContentView(R.layout.easy_choose);
                pairs = 6;
                //FilesPath = new String[pairs];
                break;
            case 2:
                setContentView(R.layout.med_choose);
                pairs = 8;
                //FilesPath = new String[pairs];
                break;
            case 3:
                setContentView(R.layout.hard_choose);
                pairs = 12;
                break;
                //FilesPath = new String[pairs];
            case 0:
                new AlertDialog.Builder(this).setMessage("someThing Went wrong bitch").show();
        }
        PermissionManager.check(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, MEDIA_REQUEST);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //send uName to gallery for picking an image
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    //the event after the uName picked an image, saves it in Uri variable "imageUri"
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            setImagesUris(imageUri);

        }


    }

    private void setImagesUris(final Uri imageUri) {

        if (imageUri != null) {
            final Button chosenView1 = chosenView;
            //connect the view that was clicked with the image uri the image that the uName chose
            final int index = Integer.parseInt(chosenView.getTag().toString());
            final String filesPath = getFilesDir() + "/img" + index + ".jpg";
            done = false;
            final Thread a;

            String path = getRealPath(imageUri);
            chosenView1.setBackground(Drawable.createFromPath(path));
            primitiveImages.add(path);
            ;
            /*a = new Thread() {
                @Override
                public void run() {

                    try (InputStream fis = getContentResolver().openInputStream(imageUri);
                         FileOutputStream fos = new FileOutputStream(filesPath)) {

                        int b;
                        while ((b = fis.read()) != -1) {
                            fos.write(b);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(ChoosePicActivity.this, "IOException", Toast.LENGTH_SHORT).show();
                    }

                    Runnable setTheImage = new Runnable() {
                        @Override
                        public void run() {
                            newImages.add(filesPath);
                            if (newImages.size() == pairs) {
                                SharedPrefs.setImages(ChoosePicActivity.this, newImages);
                                Toast.makeText(ChoosePicActivity.this, "images are saved", Toast.LENGTH_SHORT).show();
                                GameActivity.imagesAreDone(ChoosePicActivity.this);

                            }
                        }
                    };
                    Message m = Message.obtain(handler, setTheImage);
                    handler.sendMessage(m);

                }


            };
            a.start();
            //FilesPath[index] = filesPath;
*/
        }

    }


    public void chooseImage(View view) {
        chosenView = (Button) view;
        openGallery();
    }

    public void saveCandies(View view) {

           if (primitiveImages.size() == pairs) {
           ProgressDialog dialog = new ProgressDialog(this,ProgressDialog.STYLE_SPINNER);
               dialog.setMessage("lodaing...");
               dialog.show();
        //SharedPrefs.setImages(this,primitiveImages);
        startActivity(new Intent(this, GameActivity.class).putExtra(ChooseLevelActivity.LEVEL, level));
        finish();

         } else {
        AlertDialog.Builder aD = new AlertDialog.Builder(this);
        aD.setMessage("please select "+pairs+" different images").show();
    }

}


    public void setHandler(){
       handler = new Handler(Looper.getMainLooper());
    }
    public String getRealPath(final Uri ac_Uri) {
        String result = "";
        boolean isok = false;
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(ac_Uri, proj,
                    null, null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            isok = true;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return isok ? result : "";
    }
}



