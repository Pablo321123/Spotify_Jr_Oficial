package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Song;

public class ButtonsController implements Initializable {

    @FXML
    private HBox recentlyPlayedCard;

    @FXML
    private HBox favoriteSongsCard;

    private List<Song> listRecentlyPlayed;
    private List<Song> listFavoritesSong;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        
        listRecentlyPlayed = new ArrayList<Song>(getRecentlyPlayed());
        listFavoritesSong = new ArrayList<Song>(getRecentlyPlayed());
        try {
            for (Song song : listRecentlyPlayed) {
                FXMLLoader loadCard = new FXMLLoader();
                loadCard.setLocation(getClass().getResource("/view/cardViewSong.fxml"));

                VBox vbox = loadCard.load();
                SongController sc = loadCard.getController();
                sc.setData(song);

                recentlyPlayedCard.getChildren().add(vbox);

            }

            for (Song song : listFavoritesSong) {
                FXMLLoader loadCard = new FXMLLoader();
                loadCard.setLocation(getClass().getResource("/view/cardViewSong.fxml"));

                VBox vbox = loadCard.load();
                SongController sc = loadCard.getController();
                sc.setData(song);

                favoriteSongsCard.getChildren().add(vbox);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Song> getRecentlyPlayed() {
        List<Song> list = new ArrayList<Song>();
        Song song = new Song("/bin/img/cesar_menoti_leilao.jpg", "Leilão", "Cesár Menoti, Fabiano");
        list.add(song);        

        return list;
    }

}
