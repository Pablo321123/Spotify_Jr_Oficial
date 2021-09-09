package model;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import controller.ButtonsController;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import util.ToolsUtils;

public class MediaPlayerModel extends Observable {

    private Media media;
    private MediaPlayer mediaPlayerMp3;
    private File directory;
    private File[] allFiles;
    private List<File> songs;
    private List<Song> globalLibrayMusics;
    private int numberSong = 0, indicePlaylist = 0;
    private ArrayList<Integer> rowSongs;
    private Timer timer;
    private TimerTask task;
    private boolean playing, mute = false, havePlaylist = false;
    private double currentTime, endTime, currentVolume = 50.0; // currentSliderBar

    public MediaPlayerModel() {

        songs = new ArrayList<File>();
        directory = new File("music");
        globalLibrayMusics = new ArrayList<Song>();
        rowSongs = new ArrayList<Integer>();

        allFiles = directory.listFiles();

        if (allFiles != null) {
            for (File file : allFiles) {
                songs.add(file); // Cesar_Menotti_Fabiano_Leilao
            }
        }

        addMusicLibrary();

        String musicPacth = songs.get(numberSong).toURI().toString();

        media = new Media(musicPacth); // Foi preciso adicionar o seguinte trecho : --add-modules
        mediaPlayerMp3 = new MediaPlayer(media); // javafx.controls,javafx.media no modulo do programa

    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public List<String> getPathsMusics() {

        List<String> paths = new ArrayList<String>();

        for (File file : songs) {
            paths.add(file.getPath());
        }

        return paths;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public List<Song> getGlobalLibrayMusics() {
        return globalLibrayMusics;
    }

    public void setGlobalLibrayMusics(List<Song> globalLibrayMusics) {
        this.globalLibrayMusics = globalLibrayMusics;
    }

    public Song getCurrentTrack() {
        Song currentMusic = globalLibrayMusics.get(numberSong);

        return currentMusic;
    }

    // public double getTimeSliderBarCurrent() {
    // return currentSliderBar;
    // }

    public double getCurrentTime() {

        return currentTime;
    }

    public double getCurrentVolume() {

        return currentVolume;
    }

    public void setCurrentTime(double currentTime) {

        this.currentTime = currentTime;
        mediaPlayerMp3.stop();
        mediaPlayerMp3 = new MediaPlayer(media);
        playing = false;
        mediaPlayerMp3.setStartTime(Duration.seconds(currentTime));
        play();
    }

    public double getEndTime() {
        return endTime;
    }

    public void play() {

        if (playing) {
            pause();
        } else {
            mediaPlayerMp3.play();
            setVolume(currentVolume);
            playing = true;
            beginTimer();
        }

        this.setChanged();
        notifyObservers();
    }

    public void pause() {
        cancelTimer();
        mediaPlayerMp3.pause();
        playing = false;
    }

    public void previus() {

        currentTime = 0;

        double segundos = mediaPlayerMp3.getCurrentTime().toSeconds();
        if (segundos > 2.0) {
            restartMusic();
        } else {
            if (playing) {
                cancelTimer();
            }
            if (numberSong > 0) {
                verificaFila(false);
                newMusic();
                play();
            }
        }

        // if(mediaPlayerMp3.getTotalDuration())
    }

    public void next() { // Verificar em qual playlist está

        if (playing) {
            cancelTimer();
        }
        currentTime = 0;

        verificaFila(true);

        newMusic();
        play();
    }

    private void newMusic() {
        mediaPlayerMp3.stop();
        playing = false;

        String musicPath = songs.get(numberSong).toURI().toString();

        // if (!musicPath.substring(0, 4).equals("file")) {
        // musicPath = songs.get(numberSong).toURI().toString();
        // } else {
        // musicPath = musicPath.replaceAll("\\\\", "/");
        // }

        media = new Media(musicPath);
        mediaPlayerMp3 = new MediaPlayer(media);
    }

    public void setPlaylist(List<Song> playList) {
        numberSong = 0;
        rowSongs.clear();

        for (Song song : playList) {
            rowSongs.add(globalLibrayMusics.indexOf(song));
        }

        havePlaylist = true;
        indicePlaylist = 0;
        numberSong = rowSongs.get(0); // Caso seja definida uma playList a musica atual passa a ser a primeira da
                                      // playList

        newMusic();
        play();
    }

    public void setMusic(Song song) {
        mediaPlayerMp3.stop();
        playing = false;
        numberSong = globalLibrayMusics.indexOf(song);
        media = new Media(song.getPath());
        mediaPlayerMp3 = new MediaPlayer(media);
        play();
    }

    public void restartMusic() {
        currentTime = 0;
        setCurrentTime(currentTime);
    }

    private void addMusicLibrary() {
        for (File file : songs) {
            int authors = Integer.parseInt(file.getName().substring(0, 1));
            String[] nameFormatted = ToolsUtils.titleFormat(file.getName(), authors);
            String pathImage = file.getName().replace(".mp3", ".png");
            Song song = new Song("/bin/img/" + pathImage, nameFormatted[1], nameFormatted[2], file.toURI().toString());
            globalLibrayMusics.add(song);
        }
    }

    private void verificaFila(boolean isNext) {

        if (isNext) {
            if (havePlaylist) {
                int size = rowSongs.size() - 1;
                if (size == indicePlaylist) { // Caso o usuario chegue na ultima musica da playlist e clique
                    numberSong = rowSongs.get(0); // em next, voltara para a primeira musica desta playList
                    indicePlaylist = 0;
                } else {
                    numberSong = rowSongs.get(++indicePlaylist);
                }
            } else {
                if (numberSong < songs.size() - 1) {
                    numberSong++;
                } else {
                    numberSong = 0; // Variavel para controlar a current song
                }

            }
        } else {
            if (havePlaylist) {
                int size = rowSongs.size() - 1;
                if (indicePlaylist == 0) { // Caso o usuario chegue na ultima musica da playlist e clique
                    numberSong = rowSongs.get(size); // em previus, voltara para a ultima musica desta playList
                    indicePlaylist = size;
                } else {
                    numberSong = rowSongs.get(--indicePlaylist);
                }
            } else {
                numberSong--;
            }
        }
    }

    public void muteVolume(double volume) {
        mediaPlayerMp3.setVolume(volume);
    }

    public void setVolume(double volume) {
        this.currentVolume = volume;
        mediaPlayerMp3.setVolume(volume * 0.01); // multiplicado por 0.01 para simular a %
    }

    public void beginTimer() { // controla o ganho de progresso da progress bar

        timer = new Timer();

        task = new TimerTask() {// inicia-se uma task

            @Override
            public void run() {
                currentTime = mediaPlayerMp3.getCurrentTime().toSeconds();
                endTime = media.getDuration().toSeconds();

                // currentSliderBar = currentTime / endTime;
                setChanged();
                notifyObservers();

                if (currentTime == endTime) {
                    cancelTimer();
                    next();
                }
            }
        };

        timer.scheduleAtFixedRate(task, 0, 1000); // Define-se a quatidade de incremento da progress bar

    }

    public void cancelTimer() {
        playing = false;
        timer.cancel();
    }

}
