package de.rfh.ad2.mfk.albums.server;

import java.rmi.RemoteException;

/**
 * Created by MFK on 06.07.2014.
 */
public class RmiServerFactory {

    public enum RmiServerType {DB, SER}

    public static RmiServer getInstance(RmiServerType rmiServerType) throws RemoteException {
        RmiServer rmiServer = null;
        switch (rmiServerType){
            case DB: rmiServer = new RmiServerDBImpl(); break;
            case SER: rmiServer = new RmiServerSERImpl(); break;
            default:
                // TODO throw
                break;
        }
        return rmiServer;
    }
}
