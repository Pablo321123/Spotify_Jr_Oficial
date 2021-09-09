package controller;

import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import model.MainModel;
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

    private MainModel mainModelo;

    public SongController() {
    }

    // É adicionado os dados da musica no card
    public void setData(Song song, MediaPlayerModel mpm, MainModel mainModelo) {
        this.mainModelo = mainModelo;

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

        vbCard.setOnMouseClicked(arg0 -> {
            if (arg0.getButton() == MouseButton.SECONDARY) {
                openPopupMenu(arg0, song, 0);
            }
        });
    }

    public void setDataPlaylist(Song song, String namePlaylist, List<Song> playList, MediaPlayerModel mpm,
            MainModel mainModelo) {
        this.mainModelo = mainModelo;

        Image image = new Image(getClass().getResourceAsStream(song.getAlbumImage()));
        img.setImage(image);

        songName.setText(namePlaylist);

        artist.setText("");

        btPlayCard.setOnMouseClicked(arg0 -> {
            mpm.setPlaylist(playList);
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

        vbCard.setOnMouseClicked(arg0 -> {
            if (arg0.getButton() == MouseButton.SECONDARY) {
                openPopupMenu(arg0, song, 1);
            }
        });
    }

    private void openPopupMenu(MouseEvent event, Song song, int tipoJanela) { // tipoJanela define qual menu vamos
                                                                              // criar, ex, se for 0, criaremos o menu
                                                                              // dos cards de musica
        ContextMenu contextMenu = new ContextMenu();

        switch (tipoJanela) {
            case 0 -> popupSongs(song, contextMenu);
            case 1 -> popupPlaylist();
        }

        contextMenu.show(vbCard, event.getScreenX(), event.getScreenY());
    }

    private void popupSongs(Song song, ContextMenu contextMenu) {
        Menu menuSongs = new Menu("Adicionar a playlist");
        for (String nome : mainModelo.getNameExistingPlaylist()) {
            RadioMenuItem itemSong = new RadioMenuItem(nome);
            menuSongs.getItems().add(itemSong);

            itemSong.setOnAction(value -> {
                RadioMenuItem item = (RadioMenuItem) value.getSource();
                List<Song> listSong = mainModelo.getSongPlaylist(item.getText());

                listSong.add(song);
                mainModelo.addPlaylist(item.getText(), listSong);
            });
        }
        contextMenu.getItems().add(menuSongs);
    }

    private void popupPlaylist() {

    }
}
