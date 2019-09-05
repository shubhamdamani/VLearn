package com.example.vlearn.model.channelYahoo;

import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "item", strict = false)
public class ItemYahoo {

    //@Element(name = "title",required = false)
    @Path("title")
    @Text(required=false)
    private String title;

    //@Element(name = "link",required = false)
    @Path("link")
    @Text(required=false)
    private String mLink;

    //@Element(name = "description",required = false)
    @Path("description")
    @Text(required=false)
    private String description;

    //@Element(name = "dc:creator",required = false)
    /*@Path("dc:creator")
    @Text(required=false)
    private String creator;*/

    //@Element(name = "pubDate",required = false)
    @Path("media:content")//here pubDate
    @Text(required=false)
    private String publishDate;

/*    @Element(name = "media:content",required = false)
    private Media media;*/

    public ItemYahoo(String title, String link, String description, String lastBuildDate) {
        this.title = title;
        this.mLink = link;
        this.description = description;

        this.publishDate = lastBuildDate;
        //   this.media=media;
    }

    public ItemYahoo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        this.mLink = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getLastBuildDate() {
        return publishDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.publishDate = lastBuildDate;
    }

   /* public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }*/

    @Override
    public String toString() {
        return "\nItem{" +
                "content='" + title + '\'' +
                ", link='" + mLink + '\'' +
                ", id='" + description + '\'' +
                ", publish date='" + publishDate + '\'' +

                //    ", media url = "+ media.getUrl() +
                '}'+"\n"+
                "------------------------------------------------------------------------------------------\n";

    }
}
