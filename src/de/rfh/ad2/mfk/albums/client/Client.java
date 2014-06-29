package de.rfh.ad2.mfk.albums.client;

import de.rfh.ad2.mfk.albums.entity.Album;
import de.rfh.ad2.mfk.albums.server.RmiServer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Kai on 29.06.2014.
 */
public class Client {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        RmiServer stub = (RmiServer) Naming.lookup("rmi://localhost:1099/rmiServer");

        int userInput = 0;
        boolean firstRun = true;
        Scanner scanner = new Scanner(System.in);
        String newLine = "---";

        while (userInput > -1){
            if(!firstRun){
                System.out.println(newLine);
                System.out.print("Bitte eingeben: ");
                try {
                    userInput = scanner.nextInt();
                } catch (InputMismatchException e){
                    scanner = new Scanner(System.in);
                    System.out.println("Das war keine Zahl!");
                    userInput = 0;
                }
                System.out.println(newLine);
            } else {
                firstRun = false;
            }
            switch (userInput){
                case 0:
                    System.out.println("Menu");
                    System.out.println("1   Exit");
                    System.out.println("2   Alben anzeigen");
                    System.out.println("3   Album eintragen");
                    break;
                case 1:
                    System.out.println("Exiting...");
                    userInput = -1;
                    break;
                case 2:
                    System.out.println("Alle Alben:");
                    for(Album album : stub.getAlbums()){
                        System.out.println(album.toString());
                    }
                    break;
                case 3:
/*                    System.out.print("Albumname: ");
                    String albumname = null;
                    albumname = System.in.toString();
                    System.out.print("Genre: ");
                    String genre = scanner.next();
                    System.out.print("Erscheinungsjahr: ");
                    String year = scanner.nextLine();
                    System.out.print("Trackanzahl: ");
                    int trackcount = scanner.nextInt();
                    Album album = new Album(albumname, genre, year, trackcount);
                    stub.saveNewAlbum(album);*/
                    break;
                case 42:
                    System.out.println("Easteregg! :)");
                    break;
                default:
                    System.out.println("Exiting...");
                    userInput = -1;
                    break;
                }
        }
//      Album album = new Album("Vultures", "Psychdelic Stoner Rock", "2014", 8);
//      System.out.println(stub.saveNewAlbum(album));
    }
}
