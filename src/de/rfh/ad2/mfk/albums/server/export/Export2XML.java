package de.rfh.ad2.mfk.albums.server.export;

import de.rfh.ad2.mfk.albums.entity.Album;
import de.rfh.ad2.mfk.albums.entity.Artist;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.List;

/**
 * Created by Kai on 06.07.2014.
 */
public class Export2XML implements ExportService {

    @Override
    public String export(List<Artist> artists, List<Album> albums, String exportPath) {

        try {
            JAXBContext context = JAXBContext.newInstance(Album.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            for(Album album : albums){
                m.marshal(album, System.out);
            }

            for (Artist artist : artists){
                m.marshal(artist, System.out);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
            return "Fehler beim Exportieren der XML-Dateien!";
        }

        return "XML-Export durchgeführt!";
    }
}
