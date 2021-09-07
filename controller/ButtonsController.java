package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSlider;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import model.MainModel;
import model.MediaPlayerModel;
import model.Song;

public class ButtonsController implements Initializable, Observer {

    @FXML
    private HBox btInicio;

    @FXML
    private HBox btProcurar;

    @FXML
    private HBox btBiblioteca;

    @FXML
    private TextField txtPesquisar;

    @FXML
    private VBox vbBiblioteca;

    @FXML
    private VBox vbProcurar;

    @FXML
    private HBox hbSearchsMusics;

    @FXML
    private VBox vbInicio;

    @FXML
    private HBox recentlyPlayedCard;

    @FXML
    private HBox favoriteSongsCard;

    @FXML
    private ImageView imgMusicCurrent;

    @FXML
    private Label lbMusicCurrent;

    @FXML
    private Label lbArtistCurrent;

    @FXML
    private ImageView btPrevius;

    @FXML
    private ImageView btPlay;

    @FXML
    private ImageView btNext;

    @FXML
    private Label lbCurrentTime;

    @FXML
    private JFXSlider sldSongProgressBar;

    @FXML
    private Label lbMaxTime;

    @FXML
    private ImageView btAudio;

    @FXML
    private JFXSlider sldVolume;

    private MainModel mainModelo;
    private MediaPlayerModel mediaPlayerModelo;

    private List<Song> listRecentlyPlayed;
    private List<Song> listFavoritesSong;
    private List<Song> tempList;

    private String endTime;

    private boolean isPause = false;

    private int teste;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainModelo = new MainModel();
        mediaPlayerModelo = new MediaPlayerModel();
        this.mediaPlayerModelo.addObserver(this);

        listRecentlyPlayed = new ArrayList<Song>(mainModelo.getRecentlyPlayed(mediaPlayerModelo));
        listFavoritesSong = new ArrayList<Song>(mainModelo.getFavorites(mediaPlayerModelo));
        tempList = new ArrayList<Song>(mainModelo.searchSongs("", mediaPlayerModelo));

        addCardSong(recentlyPlayedCard, listRecentlyPlayed);
        addCardSong(favoriteSongsCard, listFavoritesSong);
        addCardSong(hbSearchsMusics, tempList);

        buttonsEvents();
        sliderEvents();
        textFieldsEvents();
    }

    private void sliderEvents() {

        sldVolume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

                mediaPlayerModelo.setVolume(sldVolume.getValue());
                mediaPlayerModelo.setMute(false);

                if (sldVolume.getValue() == 0) {
                    btAudio.setImage(new Image("bin/img/volume-off.png"));
                    mediaPlayerModelo.setMute(true);
                } else if (sldVolume.getValue() < 25) {
                    btAudio.setImage(new Image("bin/img/volume-low.png"));
                } else if (sldVolume.getValue() < 75) {
                    btAudio.setImage(new Image("bin/img/volume-medium.png"));
                } else {
                    btAudio.setImage(new Image("bin/img/volume-high.png"));
                }
            }
        });

        sldSongProgressBar.setOnMouseClicked(arg0 -> {
            mediaPlayerModelo.setCurrentTime(sldSongProgressBar.getValue());
        });

        sldSongProgressBar.setOnMouseDragged(arg0 -> {
            mediaPlayerModelo.cancelTimer();
        });

        // sldSongProgressBar.valueProperty().addListener(new ChangeListener<Number>(){

        // @Override
        // public void changed(ObservableValue<? extends Number> arg0, Number arg1,
        // Number arg2) {
        // }
        // });
        // sldSongProgressBar.setOnMouseExited(arg0 -> {
        // mediaPlayerModelo.beginTimer();
        // });
    }

    private void buttonsEvents() {

        btPlay.setOnMouseClicked(arg0 -> {
            if (!mediaPlayerModelo.isPlaying()) {
                btPlay.setImage(new Image("bin/img/ic_play.png"));
                isPause = false;
            } else {
                btPlay.setImage(new Image("bin/img/ic_pause.png"));
                isPause = true;
            }

            mediaPlayerModelo.play();
        });

        btNext.setOnMouseClicked(arg0 -> {
            mediaPlayerModelo.next();
        });

        btPrevius.setOnMouseClicked(arg0 -> {
            mediaPlayerModelo.previus();
        });

        btAudio.setOnMouseClicked(arg0 -> {
            boolean mute = mediaPlayerModelo.isMute();

            if (!mute) {
                mediaPlayerModelo.muteVolume(0);
                sldVolume.setValue(0);
                mediaPlayerModelo.setMute(true);
            } else {
                double volume = mediaPlayerModelo.getCurrentVolume();
                mediaPlayerModelo.muteVolume(volume);
                sldVolume.setValue(volume);
                mediaPlayerModelo.setMute(false);
            }
        });

        btInicio.setOnMouseClicked(arg0 -> {
            vbInicio.toFront();
            vbInicio.setVisible(true);

            btInicio.getStyleClass().add("selected");
            btProcurar.getStyleClass().remove("selected");
            btBiblioteca.getStyleClass().remove("selected");

            vbProcurar.toBack();
            vbProcurar.setVisible(false);

            vbBiblioteca.toBack();
            vbBiblioteca.setVisible(false);
        });

        btProcurar.setOnMouseClicked(arg0 -> {
            vbInicio.toBack();
            vbInicio.setVisible(false);

            btInicio.getStyleClass().remove("selected");
            btProcurar.getStyleClass().add("selected");
            btBiblioteca.getStyleClass().remove("selected");

            vbProcurar.toFront();
            vbProcurar.setVisible(true);

            vbBiblioteca.toBack();
            vbBiblioteca.setVisible(false);
        });

        btBiblioteca.setOnMouseClicked(arg0 -> {
            vbInicio.toBack();
            vbInicio.setVisible(false);

            btInicio.getStyleClass().remove("selected");
            btProcurar.getStyleClass().remove("selected");
            btBiblioteca.getStyleClass().add("selected");

            vbProcurar.toBack();
            vbProcurar.setVisible(false);

            vbBiblioteca.toFront();
            vbBiblioteca.setVisible(true);
        });

    }

    private void textFieldsEvents() {

        txtPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
            tempList = new ArrayList<Song>(mainModelo.searchSongs(newValue, mediaPlayerModelo));
            hbSearchsMusics.getChildren().clear();
            addCardSong(hbSearchsMusics, tempList);
        });

    }

    private void addCardSong(HBox hbTarget, List<Song> list) {
        try {
            for (Song song : list) {
                FXMLLoader loadCard = new FXMLLoader();
                loadCard.setLocation(getClass().getResource("/view/cardViewSong.fxml"));

                VBox vbox = loadCard.load();
                SongController sc = loadCard.getController();
                sc.setData(song, mediaPlayerModelo);

                hbTarget.getChildren().add(vbox);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Song currentTrack = mediaPlayerModelo.getCurrentTrack();

                imgMusicCurrent.setImage(new Image(getClass().getResourceAsStream(currentTrack.getAlbumImage())));
                lbMusicCurrent.setText(currentTrack.getTitleMusic());
                lbArtistCurrent.setText(currentTrack.getArtist());

                sldSongProgressBar.setMax(mediaPlayerModelo.getEndTime());
                int segundos = (int) mediaPlayerModelo.getCurrentTime() % 60;

                sldSongProgressBar.setValue(mediaPlayerModelo.getCurrentTime()); // Fazer um ouvinte para atualizar os
                                                                                 // labels
                String textoTime = String.format("%d:" + (segundos < 10 ? "0" : "") + "%d",
                        (int) (mediaPlayerModelo.getCurrentTime() / 60), segundos);
                lbCurrentTime.setText(textoTime);
                textoTime = String.format("%.2f", mediaPlayerModelo.getEndTime() / 60).replace(",", ":"); //
                lbMaxTime.setText(textoTime.equalsIgnoreCase("Nan") ? "0:00" : textoTime);

                if (!mediaPlayerModelo.isPlaying()) {
                    btPlay.setImage(new Image("bin/img/ic_play.png"));
                } else {
                    btPlay.setImage(new Image("bin/img/ic_pause.png"));
                }
            }
        });

        // String teste = String.valueOf(mediaPlayerModelo.getTimeSliderBarCurrent());
        // lbCurrentTime.setText(teste);
        //
        // teste++;

    }

}
