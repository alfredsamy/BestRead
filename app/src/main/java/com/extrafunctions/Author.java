package com.extrafunctions;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Mahmoud on 17/12/2016.
 */
@Root(strict=false)
public class Author {

    @Element(name="name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author() {
    }
}
