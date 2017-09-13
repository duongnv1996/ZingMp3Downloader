package com.duongkk.zingmp3.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MyPC on 9/12/2016.
 */
public class ZingModel {

    @SerializedName("song_id")
    private int song_id;
    @SerializedName("song_id_encode")
    private String song_id_encode;
    @SerializedName("video_id_encode")
    private String video_id_encode;
    @SerializedName("title")
    private String title;
    @SerializedName("artist_id")
    private String artist_id;
    @SerializedName("artist")
    private String artist;
    @SerializedName("album_id")
    private int album_id;
    @SerializedName("album")
    private String album;
    @SerializedName("composer_id")
    private int composer_id;
    @SerializedName("composer")
    private String composer;
    @SerializedName("genre_id")
    private String genre_id;
    @SerializedName("isrc")
    private String isrc;
    @SerializedName("zaloid")
    private int zaloid;
    @SerializedName("username")
    private String username;
    @SerializedName("is_hit")
    private int is_hit;
    @SerializedName("is_official")
    private int is_official;
    @SerializedName("duration")
    private int duration;
    @SerializedName("download_status")
    private int download_status;
    @SerializedName("copyright")
    private String copyright;
    @SerializedName("co_id")
    private int co_id;
    @SerializedName("ad_status")
    private int ad_status;
    @SerializedName("license_status")
    private int license_status;
    @SerializedName("lyrics_file")
    private String lyrics_file;
    @SerializedName("download_disable")
    private int download_disable;
    @SerializedName("vn_only")
    private boolean vn_only;
    @SerializedName("total_play")
    private int total_play;
    @SerializedName("link")
    private String link;
    @SerializedName("source")
    private Source source;
    @SerializedName("link_download")
    private Link_download link_download;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("album_cover")
    private String album_cover;
    @SerializedName("likes")
    private int likes;
    @SerializedName("like_this")
    private boolean like_this;
    @SerializedName("favourites")
    private int favourites;
    @SerializedName("favourite_this")
    private boolean favourite_this;
    @SerializedName("comments")
    private int comments;
    @SerializedName("genre_name")
    private String genre_name;
    @SerializedName("video")
    private Video video;
    @SerializedName("response")
    private Response response;

    public int getSong_id() {
        return song_id;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public String getSong_id_encode() {
        return song_id_encode;
    }

    public void setSong_id_encode(String song_id_encode) {
        this.song_id_encode = song_id_encode;
    }

    public String getVideo_id_encode() {
        return video_id_encode;
    }

    public void setVideo_id_encode(String video_id_encode) {
        this.video_id_encode = video_id_encode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(String artist_id) {
        this.artist_id = artist_id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getComposer_id() {
        return composer_id;
    }

    public void setComposer_id(int composer_id) {
        this.composer_id = composer_id;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(String genre_id) {
        this.genre_id = genre_id;
    }

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public int getZaloid() {
        return zaloid;
    }

    public void setZaloid(int zaloid) {
        this.zaloid = zaloid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIs_hit() {
        return is_hit;
    }

    public void setIs_hit(int is_hit) {
        this.is_hit = is_hit;
    }

    public int getIs_official() {
        return is_official;
    }

    public void setIs_official(int is_official) {
        this.is_official = is_official;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDownload_status() {
        return download_status;
    }

    public void setDownload_status(int download_status) {
        this.download_status = download_status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getCo_id() {
        return co_id;
    }

    public void setCo_id(int co_id) {
        this.co_id = co_id;
    }

    public int getAd_status() {
        return ad_status;
    }

    public void setAd_status(int ad_status) {
        this.ad_status = ad_status;
    }

    public int getLicense_status() {
        return license_status;
    }

    public void setLicense_status(int license_status) {
        this.license_status = license_status;
    }

    public String getLyrics_file() {
        return lyrics_file;
    }

    public void setLyrics_file(String lyrics_file) {
        this.lyrics_file = lyrics_file;
    }

    public int getDownload_disable() {
        return download_disable;
    }

    public void setDownload_disable(int download_disable) {
        this.download_disable = download_disable;
    }

    public boolean getVn_only() {
        return vn_only;
    }

    public void setVn_only(boolean vn_only) {
        this.vn_only = vn_only;
    }

    public int getTotal_play() {
        return total_play;
    }

    public void setTotal_play(int total_play) {
        this.total_play = total_play;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Link_download getLink_download() {
        return link_download;
    }

    public void setLink_download(Link_download link_download) {
        this.link_download = link_download;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAlbum_cover() {
        return album_cover;
    }

    public void setAlbum_cover(String album_cover) {
        this.album_cover = album_cover;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean getLike_this() {
        return like_this;
    }

    public void setLike_this(boolean like_this) {
        this.like_this = like_this;
    }

    public int getFavourites() {
        return favourites;
    }

    public void setFavourites(int favourites) {
        this.favourites = favourites;
    }

    public boolean getFavourite_this() {
        return favourite_this;
    }

    public void setFavourite_this(boolean favourite_this) {
        this.favourite_this = favourite_this;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Source {
        @SerializedName("128")
        private String m128;
        @SerializedName("lossless")
        private String lossless;
        @SerializedName("320")
        private String m320;

        public String getM128() {
            return m128;
        }

        public void setM128(String m128) {
            this.m128 = m128;
        }

        public String getLossless() {
            return lossless;
        }

        public void setLossless(String lossless) {
            this.lossless = lossless;
        }

        public String getM320() {
            return m320;
        }

        public void setM320(String m320) {
            this.m320 = m320;
        }
    }

    public static class Link_download {
        @SerializedName("128")
        private String m128;
        @SerializedName("lossless")
        private String lossless;
        @SerializedName("320")
        private String m320;

        public String getM128() {
            return m128;
        }

        public void setM128(String m128) {
            this.m128 = m128;
        }

        public String getLossless() {
            return lossless;
        }

        public void setLossless(String lossless) {
            this.lossless = lossless;
        }

        public String getM320() {
            return m320;
        }

        public void setM320(String m320) {
            this.m320 = m320;
        }
    }

    public static class Video {
        @SerializedName("video_id")
        private int video_id;
        @SerializedName("song_id_encode")
        private String song_id_encode;
        @SerializedName("title")
        private String title;
        @SerializedName("artist")
        private String artist;
        @SerializedName("thumbnail")
        private String thumbnail;
        @SerializedName("duration")
        private int duration;
        @SerializedName("download_status")
        private int download_status;
        @SerializedName("copyright")
        private int copyright;
        @SerializedName("co_id")
        private int co_id;
        @SerializedName("ad_status")
        private int ad_status;
        @SerializedName("license_status")
        private int license_status;

        public int getVideo_id() {
            return video_id;
        }

        public void setVideo_id(int video_id) {
            this.video_id = video_id;
        }

        public String getSong_id_encode() {
            return song_id_encode;
        }

        public void setSong_id_encode(String song_id_encode) {
            this.song_id_encode = song_id_encode;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getArtist() {
            return artist;
        }

        public void setArtist(String artist) {
            this.artist = artist;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getDownload_status() {
            return download_status;
        }

        public void setDownload_status(int download_status) {
            this.download_status = download_status;
        }

        public int getCopyright() {
            return copyright;
        }

        public void setCopyright(int copyright) {
            this.copyright = copyright;
        }

        public int getCo_id() {
            return co_id;
        }

        public void setCo_id(int co_id) {
            this.co_id = co_id;
        }

        public int getAd_status() {
            return ad_status;
        }

        public void setAd_status(int ad_status) {
            this.ad_status = ad_status;
        }

        public int getLicense_status() {
            return license_status;
        }

        public void setLicense_status(int license_status) {
            this.license_status = license_status;
        }
    }

    public static class Response {
        @SerializedName("msgCode")
        private int msgCode;
        @SerializedName("msg")
        private String msg;

        public int getMsgCode() {
            return msgCode;
        }

        public void setMsgCode(int msgCode) {
            this.msgCode = msgCode;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
