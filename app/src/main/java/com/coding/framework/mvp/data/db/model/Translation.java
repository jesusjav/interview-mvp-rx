package com.coding.framework.mvp.data.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Jesus Morales on 10-08-2018.
 */

@Entity(nameInDb = "historic")
public class Translation {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "text")
    private String text;

    @Property(nameInDb = "text_translated")
    private String textTranslated;

    @Generated(hash = 1847720002)
    public Translation(Long id, String text, String textTranslated) {
        this.id = id;
        this.text = text;
        this.textTranslated = textTranslated;
    }

    @Generated(hash = 321689573)
    public Translation() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextTranslated() {
        return this.textTranslated;
    }

    public void setTextTranslated(String textTranslated) {
        this.textTranslated = textTranslated;
    }

}