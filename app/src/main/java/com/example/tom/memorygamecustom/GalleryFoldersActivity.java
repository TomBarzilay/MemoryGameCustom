package com.example.tom.memorygamecustom;

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
import java.util.HashSet;

public class GalleryFoldersActivity extends AppCompatActivity {
private GridView gridView;
    public final static String  ID_AND_PATH = "ID_AND_PATH";
    private String[] arrPath;
    private int ids[];
    private int count;
    private HashMap<String,ArrayList<String>> listOfLists = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_folders);
        gridView =(GridView) findViewById(R.id.gridFolders);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String a = (String) adapterView.getAdapter().getItem(i);
                Intent toGallery = new Intent(GalleryFoldersActivity.this,CustomPhotoGalleryActivity.class);
                HashSet<String> idAndPathSet = new HashSet<>(listOfLists.get(a));
                SharedPrefs.getPrefs(GalleryFoldersActivity.this).edit().putStringSet(ID_AND_PATH,idAndPathSet).apply();
                //toGallery.putExtra(IMAGES_ARRAY,listOfLists.get(a));
                startActivity(toGallery);
            }
        });
        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media._ID;
        Cursor imagecursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
        this.count = imagecursor.getCount();
        this.arrPath = new String[this.count];
        ids = new int[count];
        for (int i = 0; i < this.count; i++) {
            imagecursor.moveToPosition(i);
            ids[i] = imagecursor.getInt(image_column_index);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
            arrPath[i] = imagecursor.getString(dataColumnIndex);

        }
        imagecursor.close();
        findFolders();
        gridView.setAdapter(new ImageAdapter(listOfLists));
    }

    private void findFolders() {

        for (int i=0;i<arrPath.length;i++){
            String [] someFile = arrPath[i].split("/");
            String folder = someFile[someFile.length-2];
            if (listOfLists.containsKey(folder)){
              ArrayList listExists =  listOfLists.get(folder);
              listExists.add(ids[i]+"<>"+arrPath[i]);
            }else{
                ArrayList<String> list = new ArrayList<>();
                list.add( ids[i]+"<>"+arrPath[i]);
                listOfLists.put(folder,list);
            }
        }
    }




    public class ImageAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private HashMap<String,ArrayList<String>> map;
        String [] folderList;
        public ImageAdapter(HashMap<String, ArrayList<String>> map) {
            this.map = map;
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            folderList = toStringArray(map.keySet().toArray()) ;

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
        int id;
    }
    public String[] toStringArray(Object [] array){
        String [] stringArray = new  String[array.length];
        for (int i=0;i<array.length;i++){
            stringArray[i]=array[i].toString();
        }
        return stringArray;
    }
}
