package de.rfh.ad2.mfk.albums.entity;

import java.io.Serializable;

/**
 * Created by kho on 26.06.2014.
 */
public class Artist implements Serializable{
    private String uuid;
    private String name;

    public Artist() {
    }

    public Artist(String name) {
        this.name = name;
    }

    public Artist(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return this.uuid + " - " + this.name;
    }
}
