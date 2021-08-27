package model;

public class Song {
    private String albumImage;
    private String titleMusic;
    private String artist;

    public Song() {
        albumImage = "";
        titleMusic = "";
        artist = "";
    }

    

    public Song(String albumImage, String titleMusic, String artist) {
        this.albumImage = albumImage;
        this.titleMusic = titleMusic;
        this.artist = artist;
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

}
