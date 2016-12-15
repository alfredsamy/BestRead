package com.extrafunctions;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Mahmoud on 14/12/2016.
 */
@Root(strict = false)
public class Notification {

    @Element(name = "id")
    private int id;

    @ElementList(name="actors")
    private List<User> users;

    @Element(name="new")
    private boolean New;

    @Element(name = "created_at")
    private String createdAt;

    @Element(name="body")
    private Body body;

    @Element(name="url")
    private String url;

    @Element(name = "resource_type")
    private String resourceType;

    @Element(name = "group_resource_type")
    private String groupResourceType;

    @Element(name="group_resource")
    private String groupResource;

    public Notification() {
//        this.id = id;
//        this.users = users;
//        New = aNew;
//        this.createdAt = createdAt;
//        this.body = body;
//        this.url = url;
//        this.resourceType = resourceType;
//        this.groupResourceType = groupResourceType;
//        this.groupResource = groupResource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isNew() {
        return New;
    }

    public void setNew(boolean aNew) {
        New = aNew;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getGroupResourceType() {
        return groupResourceType;
    }

    public void setGroupResourceType(String groupResourceType) {
        this.groupResourceType = groupResourceType;
    }

    public String getGroupResource() {
        return groupResource;
    }

    public void setGroupResource(String groupResource) {
        this.groupResource = groupResource;
    }
}
