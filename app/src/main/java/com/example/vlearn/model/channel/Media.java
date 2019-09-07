package com.example.vlearn.model.channel;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;




// UNUSED


@Root(name = "media:content", strict = false)
public class Media{

    @Attribute(name = "url",required = false)
    private String url;

    public Media(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
