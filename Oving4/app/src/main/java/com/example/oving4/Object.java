package com.example.oving4;

/**
 * Created by Roger on 29.09.2017.
 */

class Object {
    private String title;
    private String description;

    int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    private int imageID;

    Object(String title, String description, int imageID) {
        this.title = title;
        this.description = description;
        this.imageID = imageID;
    }

    Object() {
        this.title = "Choose wisely";
        this.description = " ";
        imageID = R.drawable.def;
    }

    String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return title;
    }
}
