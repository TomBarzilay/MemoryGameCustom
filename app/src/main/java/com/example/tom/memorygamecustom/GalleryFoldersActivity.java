package com.example.tom.memorygamecustom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class GalleryFoldersActivity extends AppCompatActivity {
    //this activity loads the folders of photos from the device local storage

    private GridView gridView; //the layout of the activity
    public final static String  IMAGE_PATH = "IMAGE_PATH";
    private String[] arrPath;
    private int count;
    private HashMap<String,ArrayList<String>> listOfLists = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //the next activities are acting different according to the isfromnet boolean
        SharedPrefs.getPrefs(this).edit().putBoolean(getString(R.string.isfromnet),false).apply();
        setContentView(R.layout.activity_gallery_folders);
        gridView =(GridView) findViewById(R.id.gridFolders);
        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media._ID;
        //gets a cursor object from the local dataBase ordered by date
        Cursor imagecursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        this.count = imagecursor.getCount(); //gets the quantity of images
        this.arrPath = new String[this.count];
        for (int i = 0; i < this.count; i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            arrPath[i] = imagecursor.getString(dataColumnIndex); //save the uri path in the paths array

        }

        imagecursor.close();
        findFolders();//initalize the listOflists map
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String a = (String) adapterView.getAdapter().getItem(i);// gets a String of the chosen folder name
                Intent toGallery = new Intent(GalleryFoldersActivity.this,ImageSelectionGridActivity.class);
                toGallery.putStringArrayListExtra(IMAGE_PATH,listOfLists.get(a)); //send the array list of the paths that are in the chosen folder
                startActivity(toGallery);
            }
        });
        gridView.setAdapter(new ImageAdapter(listOfLists));
    }

    private void findFolders() {
          // finds the name of each folder of images in the internal storage
        for (int i=0;i<arrPath.length;i++){
            String [] someFile = arrPath[i].split("/");
            String folder = someFile[someFile.length-2];// gets the name of the mother folder
            if (listOfLists.containsKey(folder)){
                //creates a list of paths for each folder, and sort the name of the folder as String key and the list of paths as a value
              ArrayList listExists =  listOfLists.get(folder);
              listExists.add(arrPath[i]);
            }else{
                ArrayList<String> list = new ArrayList<>();
                list.add(arrPath[i]);
                listOfLists.put(folder,list);
            }
        }
    }




    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
         private String [] folderList;
        public ImageAdapter(HashMap<String, ArrayList<String>> map) {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            folderList= new String[map.keySet().size()];
            folderList = map.keySet().toArray(folderList); //gets an array of strings from the list of lists map.



        }


        public int getCount() {
            return folderList.length;
        }

        public String getItem(int position) {
            return (String) folderList[position];
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView==null){
                holder=new ViewHolder();
                convertView=mInflater.inflate(R.layout.folder_gallery_item,null);
                holder.textView = (TextView)convertView.findViewById(R.id.textOne);
                holder.imageView = (ImageView)convertView.findViewById(R.id.pic);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.textView.setText(this.getItem(position));
                return convertView;
        }

    }
    class ViewHolder{
       ImageView imageView;
        TextView textView;
    }

}
