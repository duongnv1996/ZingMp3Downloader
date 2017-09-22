package com.duongkk.zingmp3.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DuongKK on 9/19/2017.
 */

public class ZingMp3Reponse {

    @SerializedName("data")
    private List<Data> data;


    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("name")
        private String name;
        @SerializedName("artist")
        private String artist;

        @SerializedName("cover")
        private String cover;


        private String thumb;

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }



        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }



    }
}
