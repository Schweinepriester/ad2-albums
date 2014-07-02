package de.rfh.ad2.mfk.albums.server;

import de.rfh.ad2.mfk.albums.entity.Album;
import de.rfh.ad2.mfk.albums.entity.Artist;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kai on 29.06.2014.
 */
public class RmiServerImpl extends UnicastRemoteObject implements RmiServer {

    private String driver = "org.h2.driver";
    private String uri = "jdbc:h2:tcp://localhost/~/test";
    private String user = "mfk";
    private String pw = "mfk2014";

    private Connection con;

    public RmiServerImpl() throws RemoteException{

    }

    private void createConnection(){
        try {
            con = DriverManager.getConnection(uri, user, pw);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void closeConnection(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String saveNewAlbum(Album album){
        this.createConnection();

/*        try {
            String sql = "INSERT INTO ALBUM(TITLE, GENRE, YEAR, TRACKCOUNT) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, album.getAlbumTitle());
            preparedStatement.setString(2, album.getGenre());
            preparedStatement.setString(3, album.getReleaseYear());
            preparedStatement.setInt(4, album.getTrackCount());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return album.toString() + " erfolgreich in die Datenbank geschrieben!";
    }

    public List<Album> getAlbums(){
        List<Album> albums = new ArrayList<Album>();
        this.createConnection();
        try {
            String sql = "SELECT ARTISTID, NAME, ALBUMID, TITLE, GENRE, YEAR, TRACKCOUNT FROM  ALBUM JOIN ARTIST WHERE ALBUM.ARTIST IS ARTISTID;";
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()){
                Artist artist = new Artist(res.getString("ARTISTID"), res.getString("NAME"));
                Album album = new Album(res.getString("ALBUMID"), artist, res.getString("TITLE"), res.getString("GENRE"),res.getDate("YEAR"), res.getInt("TRACKCOUNT"));
                albums.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.closeConnection();
        return albums;
    }
}