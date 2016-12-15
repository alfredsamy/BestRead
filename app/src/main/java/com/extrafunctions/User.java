package com.extrafunctions;

import org.simpleframework.xml.Element;

/**
 * Created by Mahmoud on 14/12/2016.
 */

public class User {

    @Element(name="id")
    private long id;

    @Element(name="name")
    private String name;

    @Element(name="display_name")
    private String displayName;

    @Element(name="link")
    private String link;

    @Element(name="image_url")
    private String imageUrl;

    @Element(name="small_image_url")
    private String smallImageUrl;

    @Element(name="has_image")
    private boolean hasImage;

    @Element(name="location", required = false)
    private String location;

    public User() {
//        this.id = id;
//        this.name = name;
//        this.displayName = displayName;
//        this.link = link;
//        this.imageUrl = imageUrl;
//        this.smallImageUrl = smallImageUrl;
//        this.hasImage = hasImage;
//        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
