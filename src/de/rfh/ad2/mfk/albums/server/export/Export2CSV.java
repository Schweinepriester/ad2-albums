package de.rfh.ad2.mfk.albums.server.export;

import de.rfh.ad2.mfk.albums.entity.Album;
import de.rfh.ad2.mfk.albums.entity.Artist;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by MFK on 06.07.2014.
 */
public class Export2CSV implements ExportService {

    @Override
    public String export(List<Artist> artists, List<Album> albums, String exportPath) {

        String stringNow = new SimpleDateFormat("yyyy-MM-dd_HHmm_").format(new Date());
        String artistsFile = stringNow.concat("artists.csv");
        String albumsFile = stringNow.concat("albums.csv");
        String artistsAndAlbumsFile = stringNow.concat("artistsAndAlbums.csv");

        try{
            FileWriter fileWriter1 = new FileWriter(exportPath.concat(artistsFile));
            BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
            for (Artist artist : artists){
                bufferedWriter1.write(artist.toCSVString());
                bufferedWriter1.newLine();
            }
            bufferedWriter1.close();
            fileWriter1.close();

            FileWriter fileWriter2 = new FileWriter(exportPath.concat(albumsFile));
            BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
            for (Album album : albums){
                bufferedWriter2.write(album.toCSVStringWithoutArtist());
                bufferedWriter2.newLine();
            }
            bufferedWriter2.close();
            fileWriter2.close();

            FileWriter fileWriter3 = new FileWriter(exportPath.concat(artistsAndAlbumsFile));
            BufferedWriter bufferedWriter3 = new BufferedWriter(fileWriter3);
            for (Album album : albums){
                bufferedWriter3.write(album.toCSVString());
                bufferedWriter3.newLine();
            }
            bufferedWriter3.close();
            fileWriter3.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "Fehler beim Exportieren der CSV-Dateien!";
        }

        return "CSV-Export durchgeführt!";
    }
}
