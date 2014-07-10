package de.rfh.ad2.mfk.albums.server.export;

import com.google.gson.*;
import de.rfh.ad2.mfk.albums.entity.Album;
import de.rfh.ad2.mfk.albums.entity.Artist;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by MFK on 03.07.2014.
 */
public class Export2JSON implements ExportService {

    @Override
    public String export(List<Artist> artists, List<Album> albums, String exportPath) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy").setPrettyPrinting().create();
        JsonElement artistTree = gson.toJsonTree(artists);
        String artistJsonString = gson.toJson(artistTree);
        JsonElement albumTree = gson.toJsonTree(albums);
        String albumJsonString = gson.toJson(albumTree);

        for (JsonElement artistJson : artistTree.getAsJsonArray()){
            for (JsonElement albumJson : albumTree.getAsJsonArray()){
                if (albumJson.getAsJsonObject().has("artist")) {
                    if (albumJson.getAsJsonObject().getAsJsonObject("artist").get("uuid").equals(artistJson.getAsJsonObject().get("uuid"))) {
                        if (!artistJson.getAsJsonObject().has("albums")) {
                            artistJson.getAsJsonObject().add("albums", new JsonArray());
                        }
                        albumJson.getAsJsonObject().remove("artist");
                        artistJson.getAsJsonObject().getAsJsonArray("albums").add(albumJson);
                    }
                }
            }
        }

        String artistAndAlbumJsonString = gson.toJson(artistTree);

        Date now = new Date();
        String stringNow = new SimpleDateFormat("yyyy-MM-dd_HHmm_").format(now);
        String artistsFile = stringNow.concat("artists.json");
        String albumsFile = stringNow.concat("albums.json");
        String artistsAndAlbumsFile = stringNow.concat("artistsAndAlbums.json");

        try{
            FileWriter fileWriter1 = new FileWriter(exportPath.concat(artistsFile));
            fileWriter1.write(artistJsonString);
            fileWriter1.close();

            FileWriter fileWriter2 = new FileWriter(exportPath.concat(albumsFile));
            fileWriter2.write(albumJsonString);
            fileWriter2.close();

            FileWriter fileWriter3 = new FileWriter(exportPath.concat(artistsAndAlbumsFile));
            fileWriter3.write(artistAndAlbumJsonString);
            fileWriter3.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "Fehler beim Exportieren der JSON-Dateien!";
        }

        return "JSON-Export durchgeführt!";
    }
}
