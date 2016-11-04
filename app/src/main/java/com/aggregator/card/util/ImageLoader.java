package com.aggregator.card.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mawmd.offprint.R;
import com.mawmd.offprint.core.App;
import com.mawmd.offprint.model.Image;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

/**
 * Created by ChenLiang on 16/10/13.
 */

public class ImageLoader {

    LocalWallpaperManager mLocalWallpaperManager;

    private static final int DEFAULT_ICON_RESID = R.drawable.default_pic;
    private static final int DEFAULT_ICON_ERR = R.drawable.default_err;

    @Inject
    public ImageLoader(LocalWallpaperManager localWallpaperManager) {
        this.mLocalWallpaperManager = localWallpaperManager;
    }

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
        requestManager.load(url).thumbnail(0.1f).crossFade().placeholder(DEFAULT_ICON_RESID).error(DEFAULT_ICON_ERR)
                .into(imageView);
    }

    public static void loadImage(String url, ImageView imageView, RequestListener listener) {
        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(DEFAULT_ICON_RESID);
            return;
        }
        Glide.with(imageView.getContext()).load(trim(url)).listener(listener).placeholder(DEFAULT_ICON_RESID)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    private static String trim(String url) {
        return Tool.trim(url);
    }

    public static void loadImageToDisk(final Context context, final String url, final String id, final int showType,
                                       final LocalWallpaperManager.OnLoadingStatusListener loadingStatusListener) {
        final App applicationContext = (App) context.getApplicationContext();
        Glide.with(applicationContext).load(url)
                .downloadOnly(new SimpleTarget<File>() {

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        L.e(e.getLocalizedMessage());
                    }

                    @Override
                    public void onResourceReady(final File bitmapFile, GlideAnimation anim) {
                        new LoadingWallpaperTask(applicationContext, id, url, showType, loadingStatusListener)
                                .execute(bitmapFile);
                    }
                });
    }

    public static class LoadingWallpaperTask extends AsyncTask<File, Void, File> {
        private LocalWallpaperManager.OnLoadingStatusListener mLoadingStatusListener;
        private App mAppContext;
        private String id;
        private String url;
        private int showType;

        LoadingWallpaperTask(App appContext, String id, String url, int showType, LocalWallpaperManager
                .OnLoadingStatusListener loadingStatusListener) {
            this.mAppContext = appContext;
            this.id = id;
            this.url = url;
            this.showType = showType;
            mLoadingStatusListener = loadingStatusListener;
        }

        @Override
        protected File doInBackground(File... params) {
            String title = String.valueOf(url.hashCode()).concat(".jpg");
            String dicPath = LocalWallpaperManager.WALLPAPER_DISK;
            File dic = new File(dicPath);
            if (!dic.exists()) {
                dic.mkdirs();
            }
            File dest;
            try {
                dest = new File(dic, title);
                FileUtil.copyFile(params[0], dest);
            } catch (IOException e) {
                dest = null;
                e.printStackTrace();
            }
            if (dest != null) {
                Image image = new Image();
                //                image.fid = id;
                image.pid = id;
                image.filePath = dest.getAbsolutePath();
                image.imgUrl = url;
                image.smallImgUrl = url;
                image.topImgUrl = url;

                // 26版 新增 展示单滚屏状态
                image.showType = showType;
                image.isShowVersion = true;
                LocalWallpaperManager.getInstance().saveWallpager(image);
            }
            return dest;
        }

        @Override
        protected void onPostExecute(File destFile) {
            if (destFile != null) {
                mLoadingStatusListener.onLoading(true, id, destFile.getAbsolutePath());
                try {
                    Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    scanIntent.setData(Uri.fromFile(destFile));
                    mAppContext.sendBroadcast(scanIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                mLoadingStatusListener.onLoading(false, id, null);
            }
        }
    }

    public static void cleanCache(Context context) {
        FileUtil.deleteDir(Glide.getPhotoCacheDir(context));
        //        ToastHelp.getInstance().showDialogToast(context.getString(R.string.clear_cache_over));
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
