package de.rfh.ad2.mfk.albums.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MFK on 26.06.2014.
 */
@XmlRootElement
public class Album implements Serializable{
    /**
     * uuid of the album as string
     */
    private String uuid;
    /**
     * artist of the album as Artist-object
     */
    private Artist artist;
    /**
     * title of the album
     */
    private String albumTitle;
    /**
     * genre of the album
     */
    private String genre;
    /**
     * release year of the album
     */
    private Date releaseYear;
    /**
     * track count of the album
     */
    private int trackCount;

    public Album(String artist, String albumTitle, String genre, String releaseYear, int trackCount) {
        this.artist = new Artist(artist);
        this.albumTitle = albumTitle;
        this.genre = genre;
        try {
            this.releaseYear = new SimpleDateFormat("yyyy").parse(releaseYear);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.trackCount = trackCount;
    }

    public Album(String uuid, Artist artist, String albumTitle, String genre, Date releaseYear, int trackCount) {
        this.uuid = uuid;
        this.artist = artist;
        this.albumTitle = albumTitle;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.trackCount = trackCount;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(int trackCount) {
        this.trackCount = trackCount;
    }

    public String toString(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return this.artist.toString() + " - " + this.uuid + " - " + this.albumTitle + " - " + this.genre + " - " + format.format(this.releaseYear) + " - " + this.trackCount + " Tracks";
    }

    public String toCSVString(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return this.artist.toCSVString() + ";" + this.uuid + ";" + this.albumTitle + ";" + this.genre + ";" + format.format(this.releaseYear) + ";" + this.trackCount;
    }

    public String toCSVStringWithoutArtist(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return this.uuid + ";" + this.albumTitle + ";" + this.genre + ";" + format.format(this.releaseYear) + ";" + this.trackCount;
    }
}
