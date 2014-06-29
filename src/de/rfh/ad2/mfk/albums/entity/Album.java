package de.rfh.ad2.mfk.albums.entity;

/**
 * Created by Kai on 26.06.2014.
 */
public class Album {
    private String albumTitle;
    private String genre;
    private String releaseYear;

    public  Album(){

    }
    
    public Album(String albumTitle, String genre, String releaseYear) {
        this.albumTitle = albumTitle;
        this.genre = genre;
        this.releaseYear = releaseYear;
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
}
