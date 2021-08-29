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

    public List<Song> getRecentlyPlayed(MediaPlayerModel mediaPlayerModelo) {
        List<Song> list = mediaPlayerModelo.getGlobalLibrayMusics();

        // for (Song song : list) {
        //     list.add(song);
        // }

        return list;
    }

    public List<Song> getFavorites(MediaPlayerModel mediaPlayerModelo) {
        List<Song> list = mediaPlayerModelo.getGlobalLibrayMusics();

        // for (Song song : list) {
        //     list.add(song);
        // }

        return list;
    }

}