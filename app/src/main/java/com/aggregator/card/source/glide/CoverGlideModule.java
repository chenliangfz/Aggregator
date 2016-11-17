package com.aggregator.card.source.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by ChenLiang on 16/11/16.
 */

public class CoverGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        String cacheDir = ".Aggregator/MemberCardCover";
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, cacheDir, 10* 1024 * 1024));
//        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheDir, 250 * 1024 * 1024));

//        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
//        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
//        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
//
//        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
//        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
//
//        builder.setMemoryCache( new LruResourceCache( customMemoryCacheSize ));
//        builder.setBitmapPool( new LruBitmapPool( customBitmapPoolSize ));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
