package com.example.tom.memorygamecustom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SearchImagesActivity extends AppCompatActivity {
EditText input;
final public static String q = "Q";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        input= (EditText) findViewById(R.id.input);
        SharedPrefs.getPrefs(this).edit().putBoolean(getString(R.string.isfromnet),true).apply();
    }

    public void sendQ(View view) {
        String text = input.getText().toString();
        Intent i = new Intent(this,ImageSelectionGridActivity.class);
        i.putExtra(q,text);
        i.putExtra("isFromNet",true);
        startActivity(i);

    }
}
