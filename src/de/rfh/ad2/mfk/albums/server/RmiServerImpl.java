package de.rfh.ad2.mfk.albums.server;

import de.rfh.ad2.mfk.albums.entity.Album;

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

    public RmiServerImpl() throws RemoteException{

    }

    public String saveNewAlbum(Album album){
        return album.toString().toUpperCase();
    }

    public List<Album> getAlbums(){
        List<Album> albums = new ArrayList<Album>();
        this.createConnection();
        try {
            String sql = "SELECT * FROM ALBUM";
            Statement stat = con.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while (res.next()){
                Album album = new Album(res.getString("TITLE"), res.getString("GENRE"), res.getString("YEAR"), res.getInt("TRACKCOUNT"));
                albums.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.closeConnection();
        return albums;
    }
}