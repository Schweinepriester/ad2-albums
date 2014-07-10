package de.rfh.ad2.mfk.albums.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by MFK on 26.06.2014.
 */
@XmlRootElement
public class Artist implements Serializable{
    private String uuid;
    private String name;

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

    public String toCSVString(){
        return this.uuid + ";" + this.name;
    }
}
