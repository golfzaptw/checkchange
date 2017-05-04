package com.example.flukepc.test01;

/**
 * Created by FlukePc on 4/5/2560.
 */

public class ImageObject {

    public ImageObject(String real_filename, String rent_id){
        this.real_filename = real_filename;
        this.rent_id= rent_id;
    }

    public void setReal_filename(String real_filename) {
        this.real_filename = real_filename;
    }

    public void setRent_id(String rent_id) {
        this.rent_id = rent_id;
    }

    String real_filename;
    String rent_id;

    public String getReal_filename() {
        return real_filename;
    }

    public String getRent_id() {
        return rent_id;
    }


}
