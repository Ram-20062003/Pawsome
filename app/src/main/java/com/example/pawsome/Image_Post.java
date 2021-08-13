package com.example.pawsome;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class Image_Post {
    @SerializedName("file")
    File file;
    @SerializedName("sub_id")
    String sub_id;
    public Image_Post(File file, String sub_id) {
        this.file = file;
        this.sub_id = sub_id;
    }
    public File getFile() {
        return file;
    }
    public String getSub_id() {
        return sub_id;
    }
}
