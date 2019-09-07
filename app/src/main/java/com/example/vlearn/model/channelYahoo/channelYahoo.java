package com.example.vlearn.model.channelYahoo;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;


@Root(name = "channel", strict = false)             //explaination same as channel
public class channelYahoo implements Serializable {

    @Element(name = "language")
    private String language;

    @Element(name = "copyright")
    private String copyright;

    @Element(name = "pubDate")
    private String pubDate;

    @ElementList(inline = true, name = "item")
    private List<ItemYahoo> items;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public List<ItemYahoo> getItems() {
        return items;
    }

    public void setItems(List<ItemYahoo> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Items : "+items+"\n";
    }


}
