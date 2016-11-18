package com.aggregator.card.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.aggregator.card.R;
import com.aggregator.card.core.App;
import com.aggregator.card.model.CacheModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ChenLiang on 16/10/13.
 */

public class ImageLoader {

    public static void load(String url, ImageView imageView) {
        load(Glide.with(imageView.getContext()), url, imageView);
    }

    public static void load(Context context, String url, ImageView imageView) {
        load(Glide.with(context), url, imageView);
    }

    public static void load(AppCompatActivity activity, String url, ImageView imageView) {
        load(Glide.with(activity), url, imageView);
    }

    public static void load(Fragment fragment, String url, ImageView imageView) {
        load(Glide.with(fragment), url, imageView);
    }

    private static void load(RequestManager requestManager, String url, ImageView imageView) {
        requestManager
                .load(url).thumbnail(0.1f).crossFade().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public static void loadCover(String url, final ImageView imageView) {
        final Context context = imageView.getContext();
        Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) {
                return null;
            }

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

}
