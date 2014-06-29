package de.rfh.ad2.mfk.albums.entity;

import java.io.Serializable;

/**
 * Created by Kai on 26.06.2014.
 */
public class Album implements Serializable{
    private String albumTitle;
    private String genre;
    private String releaseYear;
    private int trackCount;

    public  Album(){

    }

    public Album(String albumTitle, String genre, String releaseYear, int trackCount) {
        this.albumTitle = albumTitle;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.trackCount = trackCount;
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

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String toString(){
        return this.getAlbumTitle() + " - " + this.getGenre() + " - " + this.getReleaseYear();
    }
}
