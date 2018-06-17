package com.example.tom.memorygamecustom.dataholders;

/**
 * Created by TomBarzilay on 21/05/2018.
 */

public class ImageSelectionObject {
   private String path;
   private boolean isChecked=false;
   public ImageSelectionObject(String path){
        this.path=path;
   }

    public String getPath() {
        return path;
    }
    public boolean getIsChecked(){
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
