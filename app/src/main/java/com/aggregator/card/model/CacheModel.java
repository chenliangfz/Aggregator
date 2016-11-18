package com.aggregator.card.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ChenLiang on 16/11/17.
 */
@Entity
public class CacheModel extends BaseModel {

    @Id
    Long id;

    public String url;

    public byte[] bitmapBytes;

    @Generated(hash = 900047527)
    public CacheModel(Long id, String url, byte[] bitmapBytes) {
        this.id = id;
        this.url = url;
        this.bitmapBytes = bitmapBytes;
    }

    @Generated(hash = 666297882)
    public CacheModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getBitmapBytes() {
        return this.bitmapBytes;
    }

    public void setBitmapBytes(byte[] bitmapBytes) {
        this.bitmapBytes = bitmapBytes;
    }
}
