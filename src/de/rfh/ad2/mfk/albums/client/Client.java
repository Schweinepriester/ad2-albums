package de.rfh.ad2.mfk.albums.client;

import de.rfh.ad2.mfk.albums.entity.Album;
import de.rfh.ad2.mfk.albums.entity.Artist;
import de.rfh.ad2.mfk.albums.server.RmiServer;
import de.rfh.ad2.mfk.albums.server.export.ExportFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Client for the user.
 *
 * @author MFK
 * @since 29.06.2014.
 */
public class Client {

    /**
     * Contains variables for the export path and rmi-server-url
     *
     * @param args
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     */

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        /**
         * RMI server which will be used
         */
        RmiServer stub = (RmiServer) Naming.lookup("rmi://localhost:1099/rmiServer");

        /**
         * define where to export, e.g. JSON or CSV
         */
        String targetPath = "C:\\temp_kai\\"; // Windows
        // String targetPath = "/Users/kaihollberg/Desktop/temp_kai"; // Mac

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
                    System.out.println("Hauptmenü");
                    System.out.println("0 Exit");
                    System.out.println("1 Hauptmenü anzeigen");
                    System.out.println("2 Alle Alben anzeigen");
                    System.out.println("3 Album eintragen");
                    System.out.println("4 Alle Interpreten anzeigen");
                    System.out.println("5 Export");
                    break;
                case 2:
                    List<Album> albums = stub.getAlbums();
                    if(albums.isEmpty()){
                        System.out.println("Keine Alben vorhanden!");
                    } else {
                        System.out.println("Alle Alben:");
                        System.out.println("ArtistID - Name - AlbumID - Titel - Genre - Erscheinungsjahr - Anzahl Tracks");
                        System.out.println(newLine);
                        for(Album album : albums){
                            System.out.println(album.toString());
                        }
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
                    String year = null;
                    try{
                        System.out.print("Erscheinungsjahr: ");
                        year = scanner.nextLine();
                        Date testDate = new SimpleDateFormat("yyyy").parse(year);
                    } catch (ParseException e) {
                        // e.printStackTrace();
                        scanner = new Scanner(System.in);
                        System.out.println(newLine);
                        System.out.println("Das war wohl keine Jahreszahl! Versuchs nochmal :)");
                        System.out.println(newLine);
                        firstRun = true;
                        userInput = 1;
                        break;
                    }

                    int trackcount;
                    try {
                        System.out.print("Trackanzahl: ");
                        trackcount = scanner.nextInt();
                        if(trackcount < 1){
                            throw new InputMismatchException();
                        }
                    } catch (InputMismatchException e){
                        // e.printStackTrace();
                        scanner = new Scanner(System.in);
                        System.out.println(newLine);
                        System.out.println("Das war wohl keine gültige Zahl! Versuchs nochmal :)");
                        System.out.println(newLine);
                        firstRun = true;
                        userInput = 1;
                        break;
                    }

                    Album album = new Album(name, albumname, genre, year, trackcount);
                    System.out.println(stub.saveNewAlbum(album));
                    break;
                case 4:
                    List<Artist> artists = stub.getArtists();
                    if (artists.isEmpty()){
                        System.out.println("Keine Interpreten vorhanden!");
                    } else {
                        System.out.println("Alle Interpreten:");
                        System.out.println("ArtistID - Name");
                        System.out.println(newLine);
                        for (Artist artist : artists){
                            System.out.println(artist.toString());
                        }
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
                                System.out.println("Hauptmenü > Export-Menü");
                                System.out.println("0 Zurück zum Hauptmenü");
                                System.out.println("1 Export-Menü anzeigen");
                                System.out.println("2 Export als JSON");
                                System.out.println("3 Export als CSV");
                                System.out.println("4 !ALPHA! Export als XML");
                                break;
                            case 2:
                                exportType = ExportFactory.ExportType.JSON;
                                break;
                            case 3:
                                exportType = ExportFactory.ExportType.CSV;
                                break;
                            case 4:
                                exportType = ExportFactory.ExportType.XML;
                                break;
                            default:
                                System.out.println("Das war keine gültige Option!");
                                System.out.println(newLine);
                                firstRunExport = true;
                                userInputExport = 1;
                                break;
                            }

                        if(exportType != null){
                            System.out.println(stub.export(exportType, targetPath));
                            exportType = null;
                        }
                    }
                    break;
                case 42:
                    System.out.println("Easteregg! :)");
                    break;
                default:
                    System.out.println("Das war keine gültige Option!");
                    System.out.println(newLine);
                    firstRun = true;
                    userInput = 1;
                    break;
                }
        }
    }
}
