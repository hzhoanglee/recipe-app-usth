package vn.edu.usth.demoapp.ObjectUI;

import java.io.Serializable;

public class Photo implements Serializable {
    private int imgResourceID;
    private int textResourceID;

    //constructor
    public Photo(int resourceID, int textResourceID) {
        this.imgResourceID = resourceID;
        this.textResourceID = textResourceID;
    }

    //getter
    public int getImgResourceID() {
        return imgResourceID;
    }
    public int getTextResourceID() {
        return textResourceID;
    }

    //setter
    public void setImgResourceID(int resourceID) {
        this.imgResourceID = resourceID;
    }
    public void setTextResourceID(int textResourceID) {
        this.textResourceID = textResourceID;
    }
}
