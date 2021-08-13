package com.example.pawsome;

import java.util.List;

public class Image_analysis {
    String created_at;
    String image_id;
    List<Labels>labels;

    public String getCreated_at() {
        return created_at;
    }

    public String getImage_id() {
        return image_id;
    }

    public List<Labels> getLabels() {
        return labels;
    }
}
