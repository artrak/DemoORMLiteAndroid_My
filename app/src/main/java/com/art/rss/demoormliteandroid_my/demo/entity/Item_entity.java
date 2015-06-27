package com.art.rss.demoormliteandroid_my.demo.entity;

import android.provider.ContactsContract;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "item")
public class Item_entity {

    @DatabaseField(canBeNull = true, generatedId = true)
    private int id;

    @DatabaseField(canBeNull = true)
    private String name;

    @DatabaseField(canBeNull = true)
    private String link;

    @DatabaseField(canBeNull = true)
    private String description;

    @DatabaseField(canBeNull = true)
    private String image;

    @DatabaseField(canBeNull = true)
    private ContactsContract.Data datetime;

    @DatabaseField(canBeNull = true)
    private String text;

    //<-------------<-------------<---------------<---------------<
    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "source_id")
    private Source_entity source;
    //<-------------<-------------<---------------<---------------<

    public Item_entity() {
        // all persisted classes must define a no-arg constructor with at least package visibility
    }

    public Item_entity(final String name, final String link, final String description, final String image, final ContactsContract.Data datetime, final String text, final Source_entity source) {
        this.name = name;
        this.link = link;
        this.description = description;
        this.image = image;
        this.datetime = datetime;
        this.text = text;
        this.source = source;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public ContactsContract.Data getDatetime() {
        return this.datetime;
    }

    public void setDatetime(final ContactsContract.Data datetime) {
        this.datetime = datetime;
    }

    public String getText() {
        return this.text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public Source_entity getSource() {
        return this.source;
    }

    public void setSource(final Source_entity source) {
        this.source = source;
    }

    public final boolean hasId(){
        return 0 != this.id;
    }
}
