package model;

public class Song {
    private String albumImage;
    private String titleMusic;
    private String artist;
    private String path;

    public Song() {
        albumImage = "";
        titleMusic = "";
        artist = "";
        path = "";
    }

    public Song(String albumImage, String titleMusic, String artist, String path) {
        this.albumImage = albumImage;
        this.titleMusic = titleMusic;
        this.artist = artist;
        this.path = path;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public String getTitleMusic() {
        return titleMusic;
    }

    public void setTitleMusic(String titleMusic) {
        this.titleMusic = titleMusic;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
