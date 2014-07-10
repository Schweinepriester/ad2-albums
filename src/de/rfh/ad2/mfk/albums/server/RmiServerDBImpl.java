package de.rfh.ad2.mfk.albums.server;

import de.rfh.ad2.mfk.albums.entity.Album;
import de.rfh.ad2.mfk.albums.entity.Artist;
import de.rfh.ad2.mfk.albums.server.export.ExportFactory;
import de.rfh.ad2.mfk.albums.server.export.ExportService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by MFK on 29.06.2014.
 */
public class RmiServerDBImpl extends UnicastRemoteObject implements RmiServer {

    // private String driver = "org.h2.driver"; not needed if h2.jar is imported!?
    private String uri = "jdbc:h2:tcp://localhost/~/test";
    private String user = "mfk";
    private String pw = "mfk2014";

    private Connection con;

    public RmiServerDBImpl() throws RemoteException{

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

    @Override
    public String saveNewAlbum(Album album){
        this.createConnection();
        String artistID = null;

        try {
            String sqlCheckArtist = "SELECT * FROM ARTIST WHERE NAME = ?;";
            PreparedStatement preparedStatementCheckArtist = con.prepareStatement(sqlCheckArtist);
            preparedStatementCheckArtist.setString(1, album.getArtist().getName());
            ResultSet resultSetCheckArtist = preparedStatementCheckArtist.executeQuery();

            if(resultSetCheckArtist.next()){
                artistID = resultSetCheckArtist.getString("ARTISTID");
                System.out.println("artistID found: " + artistID);
            } else {
                artistID = saveNewArtist(album.getArtist()).getUuid();
            }

            String sqlNewAlbum = "INSERT INTO ALBUM ( ALBUMID , ARTIST , TITLE , GENRE , YEAR , TRACKCOUNT ) VALUES (RANDOM_UUID(),?,?,?,PARSEDATETIME(?, 'yyyy'), ?) ;";
            PreparedStatement preparedStatementNewAlbum = con.prepareStatement(sqlNewAlbum);
            preparedStatementNewAlbum.setString(1, artistID);
            preparedStatementNewAlbum.setString(2, album.getAlbumTitle());
            preparedStatementNewAlbum.setString(3, album.getGenre());
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            preparedStatementNewAlbum.setString(4, format.format(album.getReleaseYear()));
            preparedStatementNewAlbum.setInt(5, album.getTrackCount());
            preparedStatementNewAlbum.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // TODO select album to get this output? or somehow the albumid?
        Artist artist = album.getArtist();
        artist.setUuid(artistID);
        album.setArtist(artist);
        return album.toString().concat(" erfolgreich in die Datenbank geschrieben!");
    }

    @Override
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

    @Override
    public Artist saveNewArtist(Artist artist) throws RemoteException {
        if(artist.getUuid() == null){
            artist.setUuid(UUID.randomUUID().toString());
        }

        try {
            String sqlNewArtist = "INSERT INTO ARTIST(ARTISTID, NAME) VALUES(?, ?);";
            PreparedStatement preparedStatementNewArtist = con.prepareStatement(sqlNewArtist);
            preparedStatementNewArtist.setString(1, artist.getUuid());
            preparedStatementNewArtist.setString(2, artist.getName());
            int affectedRows = preparedStatementNewArtist.executeUpdate();

            if(affectedRows > 0){
                System.out.println("artist inserted and artistID generated: " + artist.getUuid());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artist;
    }

    @Override
    public List<Artist> getArtists() throws RemoteException {
        List<Artist> artists = new ArrayList<Artist>();
        this.createConnection();
        try {
            String sql = "SELECT * FROM ARTIST;";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Artist artist = new Artist(resultSet.getString("ARTISTID"), resultSet.getString("NAME"));
                artists.add(artist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.closeConnection();
        return artists;
    }

    @Override
    public String export(ExportFactory.ExportType exportType, String path) throws RemoteException{
        ExportService exportService = ExportFactory.getInstance(exportType);
        return exportService.export(getArtists(), getAlbums(), path);
    }
}