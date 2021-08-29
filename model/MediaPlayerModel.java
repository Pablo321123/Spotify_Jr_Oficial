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
    private int numberSong = 0;
    private Timer timer;
    private TimerTask task;
    private boolean playing, mute = false;
    private double currentSliderBar, currentTime, endTime, currentVolume = 50.0;

    public MediaPlayerModel() {

        songs = new ArrayList<File>();
        directory = new File("music");
        globalLibrayMusics = new ArrayList<Song>();

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

    public double getTimeSliderBarCurrent() {
        return currentSliderBar;
    }

    public double getCurrentTime() {

        return currentTime;
    }

    public double getCurrentVolume() {

        return currentVolume;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
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

        currentSliderBar = 0;

        double segundos = mediaPlayerMp3.getCurrentTime().toSeconds();
        if (segundos > 2.0) {
            mediaPlayerMp3.seek(Duration.seconds(0));
        } else {
            if (playing) {
                cancelTimer();
            }
            if (numberSong > 0) {
                numberSong--;
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
        currentSliderBar = 0;

        if (numberSong < songs.size() - 1) {
            numberSong++;
            newMusic();
            play();
        } else {
            numberSong = 0;
            newMusic();

            play();
        }
    }

    private void newMusic() {
        mediaPlayerMp3.stop();
        playing = false;
        String musicPacth = songs.get(numberSong).toURI().toString();
        media = new Media(musicPacth);
        mediaPlayerMp3 = new MediaPlayer(media);
    }

    public void reestartMusic() {
        currentSliderBar = 0;
        mediaPlayerMp3.seek(Duration.seconds(0));
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

                currentSliderBar = currentTime / endTime;
                setChanged();
                notifyObservers();

                if (currentSliderBar == 1) {
                    cancelTimer();
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
