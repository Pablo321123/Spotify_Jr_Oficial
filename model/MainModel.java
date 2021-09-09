package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

public class MainModel extends Observable {

    // private List<Song> listGlobalMusics;
    // private MediaPlayerModel mediaPlayerModelo;
    private HashMap<String, List<Song>> playLists;

    public MainModel() {
        playLists = new HashMap<String, List<Song>>();
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
                if (song.getArtist().toLowerCase().contains(name.toLowerCase())
                        || song.getTitleMusic().toLowerCase().contains(name.toLowerCase())) {
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
        List<Song> favoriteSongs = new ArrayList<Song>();

        for (Song song : mediaPlayerModelo.getGlobalLibrayMusics()) {
            if (song.isFavorite()) {
                favoriteSongs.add(song);
            }
        }

        return favoriteSongs;
    }

    public void addPlaylist(String name, List<Song> songs) {

        playLists.put(name, songs);

        setChanged();
        notifyObservers();

    }

    public List<Song> getSongPlaylist(String name) {

        return playLists.get(name);

    }

    public HashMap<String, List<Song>> getPlaylists() {

        return playLists;

    }

    public String[] getNameExistingPlaylist() {

        String[] names = new String[playLists.size()];
        int count = 0;

        for (String key : playLists.keySet()) {
            names[count++] = key;
        }

        return names;

    }

}