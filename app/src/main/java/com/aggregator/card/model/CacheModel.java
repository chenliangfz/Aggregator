package com.aggregator.card.model;

import com.aggregator.card.BuildConfig;
import com.aggregator.card.core.App;
import com.aggregator.card.util.EasyPreference;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ChenLiang on 16/11/17.
 */
@Entity
public class CacheModel extends BaseModel {

    private final static String CACHE_PREFERENCE_NAME = BuildConfig.APPLICATION_ID + ".CACHE_PREFERENCE_NAME";

    @Id
    public Long id;

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

    public void saveCache(App application) {
        id = application.getAppComponent().getDaoSession().getCacheModelDao().insert(this);
        EasyPreference.with(application, CACHE_PREFERENCE_NAME)
                .addLong(url, id)
                .save();
    }

    public static CacheModel getCache(App application, String url) {
        long id = EasyPreference.with(application, CACHE_PREFERENCE_NAME)
                .getLong(url, -1);
        CacheModel cacheModel = null;
        if (id != -1) {
            cacheModel = application.getAppComponent().getDaoSession().getCacheModelDao().load(id);
        }
        return cacheModel;
    }
}
