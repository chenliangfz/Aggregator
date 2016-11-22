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
import com.aggregator.card.source.greendao.CacheModelDao;
import com.aggregator.card.source.greendao.DaoSession;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

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

    public static void loadCover(final String url, final ImageView imageView) {
        final Context context = imageView.getContext();
        CacheModel cacheModel = CacheModel.getCache((App) context.getApplicationContext(), url);
        if (cacheModel != null) {
            L.e(cacheModel.toString());
            Glide.with(context)
                    .load(cacheModel.bitmapBytes)
                    .thumbnail(0.1f)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .thumbnail(0.1f)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            L.e("bitmap : " + String.valueOf(bitmap));
                            if (bitmap != null) {
                                CacheModel cacheModel = new CacheModel();
                                cacheModel.bitmapBytes = BitmapUtils.bitmap2Bytes(bitmap);
                                cacheModel.url = url;
                                cacheModel.saveCache((App) context.getApplicationContext());
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                    });
        }
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
