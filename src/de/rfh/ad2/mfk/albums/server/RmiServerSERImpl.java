package de.rfh.ad2.mfk.albums.server;

import de.rfh.ad2.mfk.albums.entity.Album;
import de.rfh.ad2.mfk.albums.entity.Artist;
import de.rfh.ad2.mfk.albums.server.export.ExportFactory;
import de.rfh.ad2.mfk.albums.server.export.ExportService;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by MFK on 06.07.2014.
 */
public class RmiServerSERImpl extends UnicastRemoteObject implements RmiServer {

    // Mac
    // String targetPath = "/Users/kaihollberg/Desktop/temp_kai";
    // Windows
    private String path = "C:\\temp_kai\\";

    private String albumsSERFile = "albums.ser";
    private String artistsSERFile = "artists.ser";

    protected RmiServerSERImpl() throws RemoteException {
    }

    @Override
    public String saveNewAlbum(Album album) throws RemoteException {
        try {
            List<Album> albums = getAlbums();
            album.setUuid(UUID.randomUUID().toString());

            List<Artist> artists = getArtists();
            Artist artist = null;
            for (Artist artist1 : artists){
                if (artist1.getName().equals(album.getArtist().getName())){
                    artist = artist1;
                }
            }
            if (artist == null){
                artist = saveNewArtist(album.getArtist());
            }

            album.setArtist(artist);
            albums.add(album);
            FileOutputStream fileOutputStream = new FileOutputStream(path.concat(albumsSERFile));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(albums);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return path.concat(albumsSERFile).concat(" nicht gefunden!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return album.toString().concat(" erfolgreich in ").concat(albumsSERFile).concat(" geschrieben!");
    }

    @Override
    public List<Album> getAlbums() throws RemoteException {
        List<Album> albums = new ArrayList<Album>();
        File file = new File(path.concat(albumsSERFile));
        if(file.exists()){
            try {
                FileInputStream fileInputStream = new FileInputStream(path.concat(albumsSERFile));
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                albums = (List<Album>) objectInputStream.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return albums;
    }

    @Override
    public Artist saveNewArtist(Artist artist) throws RemoteException {
        if(artist.getUuid() == null){
            artist.setUuid(UUID.randomUUID().toString());
        }
        System.out.println(artist);
        List<Artist> artists = getArtists();
        artists.add(artist);
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(path.concat(artistsSERFile));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(artists);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return artist;
    }

    @Override
    public List<Artist> getArtists() throws RemoteException {
        List<Artist> artists = new ArrayList<Artist>();
        File file = new File(path.concat(artistsSERFile));
        if (file.exists()){
            try{
                FileInputStream fileInputStream = new FileInputStream(path.concat(artistsSERFile));
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                artists = (List<Artist>) objectInputStream.readObject();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return artists;
    }

    @Override
    public String export(ExportFactory.ExportType exportType, String path) throws RemoteException {
        ExportService exportService = ExportFactory.getInstance(exportType);
        return exportService.export(getArtists(), getAlbums(), path);
    }
}
