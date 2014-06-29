package de.rfh.ad2.mfk.albums.server;

import de.rfh.ad2.mfk.albums.entity.Album;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Kai on 29.06.2014.
 */
public interface RmiServer extends Remote{
    public String saveNewAlbum(Album album) throws RemoteException;
    public List<Album> getAlbums() throws RemoteException;
}
