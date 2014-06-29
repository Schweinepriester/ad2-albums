package de.rfh.ad2.mfk.albums.client;

import de.rfh.ad2.mfk.albums.entity.Album;
import de.rfh.ad2.mfk.albums.server.RmiServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Kai on 29.06.2014.
 */
public class Client {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        RmiServer stub = (RmiServer) Naming.lookup("rmi://localhost:1099/rmiServer");
        Album album = new Album("Vultures", "Psychdelic Stoner Rock", "2014", 8);
        System.out.println(stub.saveNewAlbum(album));
    }
}
