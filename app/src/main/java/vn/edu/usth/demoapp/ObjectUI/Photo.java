package vn.edu.usth.demoapp.ObjectUI;

import java.io.Serializable;

public class Photo implements Serializable {
    private int imgResourceID;
    private String textResource;

    //constructor
    public Photo(int resourceID, String textResource) {
        this.imgResourceID = resourceID;
        this.textResource = textResource;
    }

    //getter
    public int getImgResourceID() {
        return imgResourceID;
    }
    public String getTextResource() {
        return textResource;
    }

    //setter
    public void setImgResourceID(int resourceID) {
        this.imgResourceID = resourceID;
    }
    public void setTextResource(String textResource) {
        this.textResource = textResource;
    }
}
