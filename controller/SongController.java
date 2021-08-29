package controller;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import model.Song;

public class SongController {

    @FXML
    private ImageView img;

    @FXML
    private Label songName;

    @FXML
    private Label artist;

    @FXML
    private JFXButton btPlayCard;

    // É adicionado os dados da musica no card
    public void setData(Song song) {
        Image image = new Image(getClass().getResourceAsStream(song.getAlbumImage()));
        img.setImage(image);

        songName.setText(song.getTitleMusic());

        artist.setText(song.getArtist());

        btPlayCard.setOnMouseClicked(arg0 -> {
            System.out.println(song.getPath());
        });
    }

}
