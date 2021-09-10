package controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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

    public RecyclerViewSongController() {
    }

    public void setData(Song song, List<Song> playList, MediaPlayerModel mpm, MainModel mainModelo) {
        lbOrderNumber.setText(String.valueOf(playList.indexOf(song)));

        ivAlgum.setImage(new Image(song.getAlbumImage()));

        lbTitleSong.setText(song.getTitleMusic());

        lbAlgum.setText(song.getTitleMusic());

        lbCalendario.setText("Hoje");

        if (!song.isFavorite()) {
            ivLiked.setVisible(false);
        }

        lbDuration.setText(String.valueOf(mpm.getEndTime()));
    }

}
