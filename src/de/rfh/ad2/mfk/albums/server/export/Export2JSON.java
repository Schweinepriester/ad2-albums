package de.rfh.ad2.mfk.albums.server.export;

import com.google.gson.*;
import de.rfh.ad2.mfk.albums.entity.Album;
import de.rfh.ad2.mfk.albums.entity.Artist;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Kai on 03.07.2014.
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

        String artistsFile = "artists.json";
        String albumsFile = "albums.json";
        String artistsAndAlbumsFile = "artistsAndAlbums.json";

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
        }

        return null;
    }
}
