package de.rfh.ad2.mfk.albums.server.export;

import de.rfh.ad2.mfk.albums.entity.Album;
import de.rfh.ad2.mfk.albums.entity.Artist;

import java.util.List;

/**
 * Created by Kai on 03.07.2014.
 */
public interface ExportService {
    public String export(List<Artist> artists, List<Album> albums, String exportPath);
}
