package de.rfh.ad2.mfk.albums.client;

import de.rfh.ad2.mfk.albums.entity.Album;
import de.rfh.ad2.mfk.albums.entity.Artist;
import de.rfh.ad2.mfk.albums.server.RmiServer;
import de.rfh.ad2.mfk.albums.server.export.ExportFactory;

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
        String targetPath = "C:\\temp_kai\\";

        int userInput = 1;
        boolean firstRun = true;
        Scanner scanner = new Scanner(System.in);
        String newLine = "---";

        // TODO catch InputMismatchException everywhere!
        while (userInput > -1){
            if(!firstRun){
                System.out.println(newLine);
                System.out.print("Bitte eingeben: ");
                try {
                    userInput = scanner.nextInt();
                } catch (InputMismatchException e){
                    scanner = new Scanner(System.in);
                    System.out.println("Das war keine Zahl!");
                    userInput = 1;
                }
                System.out.println(newLine);
            } else {
                firstRun = false;
            }
            switch (userInput){
                case 0:
                    System.out.println("Exiting...");
                    userInput = -1;
                    break;
                case 1:
                    System.out.println("Menu");
                    System.out.println("0 Exit");
                    System.out.println("1 Menu anzeigen");
                    System.out.println("2 Alle Alben anzeigen");
                    System.out.println("3 Album eintragen");
                    System.out.println("4 Alle Interpreten anzeigen");
                    System.out.println("5 Export");
                    break;
                case 2:
                    System.out.println("Alle Alben:");
                    System.out.println("ArtistID - Name - AlbumID - Titel - Genre - Erscheinungsjahr - Anzahl Tracks");
                    System.out.println(newLine);
                    for(Album album : stub.getAlbums()){
                        System.out.println(album.toString());
                    }
                    break;
                case 3:
                    scanner.nextLine(); // removing '/n' remaining from nextInt();
                    System.out.print("Interpret: ");
                    String name = scanner.nextLine();
                    System.out.print("Albumname: ");
                    String albumname = scanner.nextLine();
                    System.out.print("Genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Erscheinungsjahr: ");
                    String year = scanner.nextLine();
                    System.out.print("Trackanzahl: ");
                    int trackcount = scanner.nextInt();
                    Album album = new Album(name, albumname, genre, year, trackcount);
                    System.out.println(stub.saveNewAlbum(album));
                    break;
                case 4:
                    System.out.println("Alle Interpreten:");
                    System.out.println("ArtistID - Name");
                    System.out.println(newLine);
                    for (Artist artist : stub.getArtists()){
                        System.out.println(artist.toString());
                    }
                    break;
                case 5:
                    int userInputExport = 1;
                    boolean firstRunExport = true;
                    ExportFactory.ExportType exportType = null;

                    while (userInputExport > -1) {
                        if (!firstRunExport) {
                            System.out.println(newLine);
                            System.out.print("Bitte eingeben: ");
                            try {
                                userInputExport = scanner.nextInt();
                            } catch (InputMismatchException e) {
                                scanner = new Scanner(System.in);
                                System.out.println("Das war keine Zahl!");
                                userInputExport = 1;
                            }
                            System.out.println(newLine);
                        } else {
                            firstRunExport = false;
                        }

                        switch (userInputExport) {
                            case 0:
                                userInputExport = -1;
                                // showing the main-menu again
                                userInput = 1;
                                firstRun = true;
                                break;
                            case 1:
                                System.out.println("Menu > Export");
                                System.out.println("0 Zurück zum Hauptmenü");
                                System.out.println("1 Export-Menü anzeigen");
                                System.out.println("2 Export als JSON");
                                System.out.println("3 Export als CSV");
                                System.out.println("4 Export als XML");
                                System.out.println("5 Datenbankdump mit Daten");
                                System.out.println("6 Datenbankdump ohne Daten");
                                break;
                            case 2:
                                exportType = ExportFactory.ExportType.JSON;
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                System.out.println("yay");
                                stub.export(exportType, targetPath);
                                break;
                            default:
                                firstRunExport = true;
                                userInputExport = 0;
                                break;
                            }
                    }
                    break;
                case 42:
                    System.out.println("Easteregg! :)");
                    break;
                default:
                    firstRun = true;
                    userInput = 0;
                    break;
                }
        }
    }
}
