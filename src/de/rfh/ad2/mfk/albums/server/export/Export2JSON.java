package de.rfh.ad2.mfk.albums.server.export;

import com.google.gson.Gson;
import de.rfh.ad2.mfk.albums.entity.Album;

import java.util.List;

/**
 * Created by Kai on 03.07.2014.
 */
public class Export2JSON implements ExportService {

    @Override
    public String export(List<Album> albums, String exportPath) {
        for(Album album : albums){
            Gson gson = new Gson();
            System.out.println(gson.toJson(album));
        }
        return null;
    }
}
