package vn.edu.usth.demoapp.object_ui;

import java.io.Serializable;

public class Photo implements Serializable {
    private String imgURL;
    private String textResource;

    //constructor
    public Photo(String imgURL, String textResource) {
        this.imgURL = imgURL;
        this.textResource = textResource;
    }

    //getter
    public String getImgURL() {
        return imgURL;
    }
    public String getTextResource() {
        return textResource;
    }

    //setter
    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
    public void setTextResource(String textResource) {
        this.textResource = textResource;
    }
}
