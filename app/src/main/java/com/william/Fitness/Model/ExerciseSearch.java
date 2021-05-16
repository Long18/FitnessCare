package com.william.Fitness.Model;

public class ExerciseSearch {
    int image;
    String name;
    String desc;
    String time;

    public ExerciseSearch(){

    }

    public ExerciseSearch(int image, String name, String desc, String time) {
        this.image = image;
        this.name = name;
        this.desc = desc;
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
