package model;

import java.util.ArrayList;
import java.util.List;

public class MainModel {

    // private List<Song> listGlobalMusics;
    // private MediaPlayerModel mediaPlayerModelo;

    public MainModel() {
        // this.mediaPlayerModelo = mediaPlayerModelo;
        // listGlobalMusics = mediaPlayerModelo.getGlobalLibrayMusics();
    }

    public List<Song> searchSongs(String name, MediaPlayerModel mediaPlayerModelo) {
        List<Song> list = new ArrayList<Song>();
        List<Song> allSongs = mediaPlayerModelo.getGlobalLibrayMusics();

        if (name.isEmpty()) {
            list = allSongs;
        } else {
            for (Song song : allSongs) {
                if(song.getArtist().toLowerCase().contains(name.toLowerCase()) || song.getTitleMusic().toLowerCase().contains(name.toLowerCase())){
                    list.add(song);
                }
            }
        }

        return list;
    }

    public List<Song> getRecentlyPlayed(MediaPlayerModel mediaPlayerModelo) {

        List<Song> list = mediaPlayerModelo.getGlobalLibrayMusics();

        return list;
    }

    public List<Song> getFavorites(MediaPlayerModel mediaPlayerModelo) {
        List<Song> list = mediaPlayerModelo.getGlobalLibrayMusics();

        return list;
    }

}