package de.rfh.ad2.mfk.albums.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kai on 26.06.2014.
 */
public class Album implements Serializable{
    private String uuid;
    private Artist artist;
    private String albumTitle;
    private String genre;
    private Date releaseYear;
    private int trackCount;

    public  Album(){

    }

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
        // TODO use concat or something?
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return this.artist.toString() + " - " + this.uuid + " - " + this.albumTitle + " - " + this.genre + " - " + format.format(this.releaseYear) + " - " + this.trackCount + " Tracks";
    }
}
