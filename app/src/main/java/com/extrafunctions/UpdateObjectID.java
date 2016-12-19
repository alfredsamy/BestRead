package com.extrafunctions;

import android.sax.Element;
import android.sax.EndTextElementListener;
import android.util.Log;

import com.goodreads.api.v1.*;
import com.goodreads.api.v1.Author;
import com.goodreads.api.v1.Book;

import java.util.HashMap;

/**
 * Created by robert on 12/19/16.
 */

public class UpdateObjectID {
    // added by robert

    private String mID = "";

    public static UpdateObjectID appendListener(Element parentElement, int depth)
    {
        final UpdateObjectID updateObjectID = new UpdateObjectID();
        HashMap<String, Element> updateObjElement = new HashMap<>();
        for(String i : new String[]{"review", "userchallenge", "readstatus", "userstatus"})
            updateObjElement.put(i, parentElement.getChild(i));

        for(Element e : updateObjElement.values())
            appendCommonListeners(e, updateObjectID, depth);

        return updateObjectID;
    }

    private static void appendCommonListeners(final Element updateObjElement, final UpdateObjectID objId, int depth)
    {
        updateObjElement.getChild("id").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                objId.setId(body);
                Log.d("robert", "set obj id: " + body);
            }
        });
    }

    public String getId()
    {
        return mID;
    }
    public void setId(String id)
    {
        this.mID = id;
    }

}
