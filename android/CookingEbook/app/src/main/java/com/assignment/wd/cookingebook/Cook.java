package com.assignment.wd.cookingebook;

/**
 * Created by wd on 3/14/2018.
 */

public class Cook {
    String name;
    int picture;

    public Cook(String name, int picture) {
        this.name = name;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }


}