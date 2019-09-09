package com.example.vlearn.model.rediffitem;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;


@Root(name = "item", strict = false)
public class RediffItem implements Serializable {

    @Element(name = "title")
    private String title;
    @Element(name = "link")
    private String link;
    @Element(name = "description")
    private String description;
    @Element(name = "image")
    private String imag;
    @Element(name = "Byline")
    private String auth;


    public String getLink() {
        return link;
    }
    public RediffItem(){}

    public RediffItem(String title, String desc, String imag, String auth, String link) {
        this.title = title;
        this.description = desc;
        this.imag = imag;
        this.auth = auth;
        this.link = link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public String getImag() {
        return imag;
    }

    public void setImag(String imag) {
        this.imag = imag;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }


    @Override
    public String toString() {
        return "\nEntry{" +
                "title='" + title + '\'' +
                ", author='" + auth + '\'' +
                ", id='" + link + '\'' +
                ", url='" + imag + '\'' +
                ", des='" + description + '\'' +
                '}'+"\n"+
                "------------------------------------------------------------------------------------------\n";
    }

}
