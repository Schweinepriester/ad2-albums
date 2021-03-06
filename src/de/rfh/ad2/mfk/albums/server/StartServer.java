package de.rfh.ad2.mfk.albums.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by MFK on 29.06.2014.
 */
public class StartServer {

    /**
     * change datasource here, for options see {@link de.rfh.ad2.mfk.albums.server.RmiServerFactory.RmiServerType}
     */
    private static RmiServerFactory.RmiServerType serverType = RmiServerFactory.RmiServerType.DB;

    public static void main(String[] args) {
        try {
            RmiServer impl = RmiServerFactory.getInstance(serverType);
            LocateRegistry.createRegistry(1099);
            Naming.bind("rmi://localhost:1099/rmiServer", impl);

            System.out.println("server läuft!");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
