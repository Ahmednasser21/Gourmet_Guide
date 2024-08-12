package com.ahmed.gourmetguide.iti.model;

public class IngredientDTO {

    String name;
    String measure;
    String imgURL;

    public IngredientDTO(String imgURL, String measure, String name) {
        this.imgURL = imgURL;
        this.measure = measure;
        this.name = name;
    }

    public IngredientDTO() {
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
