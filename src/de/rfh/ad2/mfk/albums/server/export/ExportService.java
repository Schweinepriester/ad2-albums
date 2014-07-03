package de.rfh.ad2.mfk.albums.server.export;

import de.rfh.ad2.mfk.albums.entity.Album;

import java.util.List;

/**
 * Created by Kai on 03.07.2014.
 */
public interface ExportService {
    public String export(List<Album> albums, String exportPath);
}
