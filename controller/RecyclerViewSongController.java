package controller;

import java.util.List;

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
import model.MainModel;
import model.MediaPlayerModel;
import model.Song;

public class RecyclerViewSongController {
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

    public RecyclerViewSongController() {
    }

    public void setData(Song song, List<Song> playList, MediaPlayerModel mpm, MainModel mainModelo) {

        this.mpm = mpm;
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

        lbDuration.setText(String.valueOf(mpm.getEndTime()));

        eventHbox(playList, song);

    }

    private void eventHbox(List<Song> playList, Song song) {

        hbRVSong.setOnMouseClicked(arg0 -> {

            if (arg0.getButton() == MouseButton.PRIMARY) {
                if (!playList.isEmpty()) {
                    mpm.setPlaylist(playList);
                }

                mpm.setMusic(song);
            } else if (arg0.getButton() == MouseButton.SECONDARY) {

            }

        });

        hbRVSong.hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {

                hbRVSong.setBackground(
                        new Background(new BackgroundFill(Color.web("#87878785"), new CornerRadii(5), Insets.EMPTY)));
            }
        });

        hbRVSong.setOnMouseExited(arg0 -> {
            hbRVSong.setBackground(
                    new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        });
    }

}
