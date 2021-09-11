package controller;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.MainModel;
import model.MediaPlayerModel;
import model.Song;
import util.ToolsUtils;

public class RecyclerViewSongController implements Observer {
    @FXML
    private HBox hbRVSong;

    @FXML
    private Label lbOrderNumber;

    @FXML
    private ImageView ivAlgum;

    @FXML
    private Label lbTitleSong;

    @FXML
    private Label lbAlgum;

    @FXML
    private Label lbCalendario;

    @FXML
    private ImageView ivLiked;

    @FXML
    private Label lbDuration;

    private MediaPlayerModel mpm;

    private Song song;

    public RecyclerViewSongController() {
    }

    public void setData(Song song, List<Song> playList, MediaPlayerModel mpm, MainModel mainModelo,
            String nomePlaylist) {

        this.mpm = mpm;
        this.mpm.addObserver(this);
        this.song = song;

        int order = 0;

        if (!playList.isEmpty()) {
            order = playList.indexOf(song);
        } else {
            order = 0;
        }

        lbOrderNumber.setText(String.valueOf(order + 1));

        ivAlgum.setImage(new Image(song.getAlbumImage()));

        lbTitleSong.setText(song.getTitleMusic());

        lbAlgum.setText(song.getTitleMusic());

        lbCalendario.setText("Hoje");

        if (!song.isFavorite()) {
            ivLiked.setVisible(false);
        }

        lbDuration.setText(song.getEndTime());

        eventHbox(playList, song, nomePlaylist);

        if (mpm.getCurrentTrack().getTitleMusic().equals(lbTitleSong.getText())) {
            music_selected();
        } else {
            music_not_selected();
        }

    }

    private void eventHbox(List<Song> playList, Song song, String nomePlaylist) {

        hbRVSong.setOnMouseClicked(arg0 -> {

            if (arg0.getButton() == MouseButton.PRIMARY) {
                if (!playList.isEmpty()) {
                    mpm.setPlaylist(playList, nomePlaylist);
                }

                mpm.setMusic(song);
            } else if (arg0.getButton() == MouseButton.SECONDARY) {

            }

        });

        hbRVSong.setOnMouseEntered(arg0 -> {
            hbRVSong.setBackground(
                    new Background(new BackgroundFill(Color.web("#87878785"), new CornerRadii(5), Insets.EMPTY)));
        });

        hbRVSong.setOnMouseExited(arg0 -> {
            hbRVSong.setBackground(
                    new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        });
    }

    private void music_selected() {
        lbAlgum.setTextFill(Color.web("#1ED760"));
        lbCalendario.setTextFill(Color.web("#1ED760"));
        lbDuration.setTextFill(Color.web("#1ED760"));
        lbOrderNumber.setTextFill(Color.web("#1ED760"));
        lbTitleSong.setTextFill(Color.web("#1ED760"));
    }

    private void music_not_selected() {
        lbAlgum.setTextFill(Color.WHITE);
        lbCalendario.setTextFill(Color.WHITE);
        lbDuration.setTextFill(Color.WHITE);
        lbOrderNumber.setTextFill(Color.WHITE);
        lbTitleSong.setTextFill(Color.WHITE);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == "musica_selecionada") {
            if (mpm.getCurrentTrack().getTitleMusic().equals(lbTitleSong.getText())) {
                music_selected();
            } else {
                music_not_selected();
            }
        } else {
            if (song.isFavorite()) {
                ivLiked.setVisible(true);
            } else {
                ivLiked.setVisible(false);
            }
        }
    }

}
