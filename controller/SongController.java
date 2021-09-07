package controller;

import com.jfoenix.controls.JFXButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import model.MediaPlayerModel;
import model.Song;

public class SongController {

    @FXML
    private VBox vbCard;

    @FXML
    private ImageView img;

    @FXML
    private Label songName;

    @FXML
    private Label artist;

    @FXML
    private ImageView btPlayCard;

    public SongController() {
    }

    // É adicionado os dados da musica no card
    public void setData(Song song, MediaPlayerModel mpm) {
        Image image = new Image(getClass().getResourceAsStream(song.getAlbumImage()));
        img.setImage(image);

        songName.setText(song.getTitleMusic());

        artist.setText(song.getArtist());

        btPlayCard.setOnMouseClicked(arg0 -> {
            mpm.setMusic(song);
        });

        // vbCard.hoverProperty().addListener(new ChangeListener<Boolean>() {

        // @Override
        // public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1,
        // Boolean arg2) {
        // System.out.println(song.getTitleMusic());
        // }

        // });
        vbCard.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
            btPlayCard.setVisible(true);
        });

        vbCard.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
            // if (!mpm.getCurrentTrack().getTitleMusic().equals(song.getTitleMusic()))
                btPlayCard.setVisible(false);
        });

    }

}
