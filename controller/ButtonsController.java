package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MainModel;
import model.MediaPlayerModel;
import model.Song;
import util.ToolsUtils;

public class ButtonsController implements Initializable, Observer {

    @FXML
    private HBox btInicio;

    @FXML
    private ImageView ic_Inicio;

    @FXML
    private HBox btProcurar;

    @FXML
    private ImageView ic_Procurar;

    @FXML
    private HBox btBiblioteca;

    @FXML
    private ImageView ic_Biblioteca;

    @FXML
    private HBox btCriarPlaylist;

    @FXML
    private TextField txtPesquisar;

    @FXML
    private VBox vbBiblioteca;

    @FXML
    private HBox hbPlayLists;

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
    private VBox vbSongLists;

    @FXML
    private Label lbNomePlaylist;

    @FXML
    private VBox vbRecyclerSongs;

    @FXML
    private ImageView imgMusicCurrent;

    @FXML
    private Label lbMusicCurrent;

    @FXML
    private Label lbArtistCurrent;

    @FXML
    private ImageView btFavorite;

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
    private ImageView btQueue;

    @FXML
    private ImageView btAudio;

    @FXML
    private JFXSlider sldVolume;

    private MainModel mainModelo;
    private MediaPlayerModel mediaPlayerModelo;

    private List<Song> listRecentlyPlayed;
    private List<Song> listFavoritesSong;
    private List<Song> tempList;
    private HashMap<String, List<Song>> playLists;

    private String endTime;

    private boolean isPause = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainModelo = new MainModel();
        this.mainModelo.addObserver(this);
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
            if (!btInicio.getStyleClass().toString().equals("selected")) {
                eventInicio();
            }
        });

        btProcurar.setOnMouseClicked(arg0 -> {
            if (!btProcurar.getStyleClass().toString().equals("selected")) {
                eventProcurar();
            }
        });

        btBiblioteca.setOnMouseClicked(arg0 -> {
            if (!btBiblioteca.getStyleClass().toString().equals("selected")) {
                eventBiblioteca();

            }
        });

        btFavorite.setOnMouseClicked(arg0 -> {
            boolean favorite = mediaPlayerModelo.getCurrentTrack().isFavorite() ? false : true;
            mediaPlayerModelo.getCurrentTrack().setFavorite(favorite);

            if (favorite) {
                btFavorite.setImage(new Image("bin/img/ic_love_active.png"));
            } else {
                btFavorite.setImage(new Image("bin/img/ic_love_hover.png"));
            }

            favoriteSongsCard.getChildren().clear();
            addCardSong(favoriteSongsCard, mainModelo.getFavorites(mediaPlayerModelo));
        });

        btFavorite.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            if (!mediaPlayerModelo.getCurrentTrack().isFavorite()) {
                btFavorite.setImage(new Image("bin/img/ic_love_hover.png"));
            }
        });

        btFavorite.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            if (!mediaPlayerModelo.getCurrentTrack().isFavorite()) {
                btFavorite.setImage(new Image("bin/img/ic_love.png"));
            }
        });

        btCriarPlaylist.setOnMouseClicked(arg0 -> {
            modalPlaylist();
        });

        btQueue.setOnMouseClicked(arg0 -> {

            List<Song> global = mediaPlayerModelo.getGlobalLibrayMusics();
            List<Song> list = new ArrayList<Song>();
            ArrayList<Integer> queue = mediaPlayerModelo.getQueue();

            if (!queue.isEmpty()) {
                for (int i : queue) {
                    list.add(global.get(i));
                }
                eventRecyclerPlaylist();
                addRecyclerSongsPlayList(list, "Fila");
            } else {
                lbNomePlaylist.setText("Fila");
            }

        });
    }

    private void textFieldsEvents() {

        txtPesquisar.textProperty().addListener((observable, oldValue, newValue) -> {
            tempList = new ArrayList<Song>(mainModelo.searchSongs(newValue, mediaPlayerModelo));
            hbSearchsMusics.getChildren().clear();
            addCardSong(hbSearchsMusics, tempList);

            if (!btProcurar.getStyleClass().toString().equals("selected")) {
                eventProcurar();
            }

        });

    }

    private void eventInicio() {
        vbInicio.toFront();
        vbInicio.setVisible(true);

        btInicio.getStyleClass().add("selected");
        btProcurar.getStyleClass().remove("selected");
        btBiblioteca.getStyleClass().remove("selected");

        ic_Inicio.setImage(new Image("/bin/img/ic_home_active.png"));
        ic_Procurar.setImage(new Image("/bin/img/ic_search.png"));
        ic_Biblioteca.setImage(new Image("/bin/img/bookshelf.png"));

        vbProcurar.toBack();
        vbProcurar.setVisible(false);

        vbBiblioteca.toBack();
        vbBiblioteca.setVisible(false);

        vbSongLists.toBack();
        vbSongLists.setVisible(false);
        vbRecyclerSongs.getChildren().clear();
    }

    private void eventProcurar() {
        vbInicio.toBack();
        vbInicio.setVisible(false);

        btInicio.getStyleClass().remove("selected");
        btProcurar.getStyleClass().add("selected");
        btBiblioteca.getStyleClass().remove("selected");

        ic_Inicio.setImage(new Image("/bin/img/ic_home.png"));
        ic_Procurar.setImage(new Image("/bin/img/ic_search_active.png"));
        ic_Biblioteca.setImage(new Image("/bin/img/bookshelf.png"));

        vbProcurar.toFront();
        vbProcurar.setVisible(true);

        vbBiblioteca.toBack();
        vbBiblioteca.setVisible(false);

        vbSongLists.toBack();
        vbSongLists.setVisible(false);

        vbRecyclerSongs.getChildren().clear();
    }

    private void eventBiblioteca() {
        vbInicio.toBack();
        vbInicio.setVisible(false);

        btInicio.getStyleClass().remove("selected");
        btProcurar.getStyleClass().remove("selected");
        btBiblioteca.getStyleClass().add("selected");

        ic_Inicio.setImage(new Image("/bin/img/ic_home.png"));
        ic_Procurar.setImage(new Image("/bin/img/ic_search.png"));
        ic_Biblioteca.setImage(new Image("/bin/img/bookshelf_active.png"));

        vbProcurar.toBack();
        vbProcurar.setVisible(false);

        vbBiblioteca.toFront();
        vbBiblioteca.setVisible(true);

        vbSongLists.toBack();
        vbSongLists.setVisible(false);
        vbRecyclerSongs.getChildren().clear();
    }

    public void eventRecyclerPlaylist() {
        eventBiblioteca();

        ic_Biblioteca.setImage(new Image("/bin/img/bookshelf.png"));
        vbBiblioteca.toBack();
        vbBiblioteca.setVisible(false);

        btInicio.getStyleClass().remove("selected");
        btProcurar.getStyleClass().remove("selected");
        btBiblioteca.getStyleClass().remove("selected");

        vbSongLists.toFront();
        vbSongLists.setVisible(true);
    }

    private void addCardSong(HBox hbTarget, List<Song> list) {
        try {
            for (Song song : list) {
                FXMLLoader loadCard = new FXMLLoader();
                loadCard.setLocation(getClass().getResource("/view/cardViewSong.fxml"));

                VBox vbox = loadCard.load();
                SongController sc = loadCard.getController();
                sc.setData(song, mediaPlayerModelo, mainModelo, vbRecyclerSongs, this);

                hbTarget.getChildren().add(vbox);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCardSong(HBox hbTarget, HashMap<String, List<Song>> playList) {
        try {

            for (String key : playList.keySet()) {

                List<Song> album = playList.get(key);

                if (album.isEmpty()) {
                    return;
                } else {
                    Song capa = album.get(0);

                    FXMLLoader loadCard = new FXMLLoader();
                    loadCard.setLocation(getClass().getResource("/view/cardViewSong.fxml"));

                    VBox vbox = loadCard.load();
                    SongController sc = loadCard.getController();
                    sc.setDataPlaylist(capa, key, album, mediaPlayerModelo, mainModelo, vbRecyclerSongs, this);

                    hbTarget.getChildren().add(vbox);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRecyclerSongs(Song song, String name) {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/recyclerViewSong.fxml"));

            HBox hbRecycler = loader.load();
            RecyclerViewSongController rvc = loader.getController();
            rvc.setData(song, new ArrayList<>(), mediaPlayerModelo, mainModelo);

            vbRecyclerSongs.getChildren().add(hbRecycler);

            lbNomePlaylist.setText(name);

        } catch (IOException e) {

        }
    }

    public void addRecyclerSongsPlayList(List<Song> list, String name) {
        for (Song song : list) {
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/recyclerViewSong.fxml"));

                HBox hbRecycler = loader.load();
                RecyclerViewSongController rvc = loader.getController();
                rvc.setData(song, list, mediaPlayerModelo, mainModelo);

                vbRecyclerSongs.getChildren().add(hbRecycler);
                lbNomePlaylist.setText(name);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void modalPlaylist() {

        // Alert alert = new Alert(AlertType.CONFIRMATION);
        // alert.setTitle("Playlist");
        // alert.setHeaderText("");
        // alert.setContentText("Por favor, digite o nome da sua playlist: ");

        // DialogPane dialogPlaylist = alert.getDialogPane();
        // dialogPlaylist.getStylesheets().add(getClass().getResource("\\css\\mainStyle.css").toString());
        // dialogPlaylist.getStyleClass().add("dialog-playlist");

        TextInputDialog dialogPlaylist = new TextInputDialog("Nome da playlist");
        dialogPlaylist.initOwner(null);
        dialogPlaylist.initStyle(StageStyle.TRANSPARENT);
        dialogPlaylist.setTitle("Playlist");
        dialogPlaylist.setHeaderText("");
        dialogPlaylist.setContentText("Por favor, digite o nome da sua playlist: ");

        Optional<String> result = dialogPlaylist.showAndWait();

        if (result.isPresent()) {
            mainModelo.addPlaylist(result.get(), new ArrayList<Song>());
        }

        mainModelo.getNameExistingPlaylist();
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {

                if (o.getClass().getName().equals("model.MainModel")) {
                    hbPlayLists.getChildren().clear();
                    addCardSong(hbPlayLists, mainModelo.getPlaylists());
                } else {

                    Song currentTrack = mediaPlayerModelo.getCurrentTrack();

                    imgMusicCurrent.setImage(new Image(getClass().getResourceAsStream(currentTrack.getAlbumImage())));
                    lbMusicCurrent.setText(currentTrack.getTitleMusic());
                    lbArtistCurrent.setText(currentTrack.getArtist());

                    sldSongProgressBar.setMax(mediaPlayerModelo.getEndTime());
                    sldSongProgressBar.setValue(mediaPlayerModelo.getCurrentTime());

                    String textoTime = ToolsUtils.hourFormatted(mediaPlayerModelo.getCurrentTime());
                    lbCurrentTime.setText(textoTime);

                    textoTime = ToolsUtils.hourFormatted(mediaPlayerModelo.getEndTime());
                    lbMaxTime.setText(textoTime.equalsIgnoreCase("Nan") ? "0:00" : textoTime);

                    if (!mediaPlayerModelo.isPlaying()) {
                        btPlay.setImage(new Image("bin/img/ic_play.png"));
                    } else {
                        btPlay.setImage(new Image("bin/img/ic_pause.png"));
                    }

                    if (mediaPlayerModelo.getCurrentTrack().isFavorite()) {
                        btFavorite.setImage(new Image("bin/img/ic_love_active.png"));
                    } else {
                        btFavorite.setImage(new Image("bin/img/ic_love.png"));
                    }
                }
            }
        });

        // String teste = String.valueOf(mediaPlayerModelo.getTimeSliderBarCurrent());
        // lbCurrentTime.setText(teste);
        //
        // teste++;

    }

}
