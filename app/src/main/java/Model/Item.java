package Model;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by ashish on 1/3/18.
 */

public class Item
{
    public String title;
    public String pubDate;
    public String link;
    public String guid;
    public String author;
    public String description;
    public String content;
    public Object enclosure;
    public List<String> categories;
    public String thumbnail;

    public Item(String title, String pubDate, String link, String guid, String author, String description, String content, Object enclosure, List<String> categories, String thumbnail) {
        this.title = title;
        this.pubDate = pubDate;
        this.link = link;
        this.guid = guid;
        this.author = author;
        this.description = description;
        this.content = content;
        this.enclosure = enclosure;
        this.categories = categories;
        this.thumbnail = thumbnail;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Object enclosure) {
        this.enclosure = enclosure;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }


}

