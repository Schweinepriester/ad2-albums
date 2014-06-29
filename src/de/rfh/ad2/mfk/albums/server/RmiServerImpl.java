package de.rfh.ad2.mfk.albums.server;

import de.rfh.ad2.mfk.albums.entity.Album;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Kai on 29.06.2014.
 */
public class RmiServerImpl extends UnicastRemoteObject implements RmiServer {

    public RmiServerImpl() throws RemoteException{

    }

    public String saveNewAlbum(Album album){
        return album.toString().toUpperCase();
    }
}
