package com.aggregator.card.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;

/**
 * Created by ChenLiang on 16/11/16.
 *
 * 照相机
 *
 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 // 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null
 // 如果此处指定，则后来的data为null
 // 只有指定路径才能获取原图
 intent.putExtra(MediaStore.EXTRA_OUTPUT, CameraGalleryUtil.fileUri);
 startActivityForResult(intent, CameraGalleryUtil.PHOTO_REQUEST_TAKE_PHOTO);
 *
 *
 *
 * 相册
 *
 Intent intent = new Intent(Intent.ACTION_PICK, null);
 intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
 startActivityForResult(intent, CameraGalleryUtil.PHOTO_REQUEST_GALLERY);
 */


public class CameraGalleryUtil {

    private static final int RESULT_OK = -1;
    public static final int PHOTO_REQUEST_TAKE_PHOTO = 1;// 拍照
    public static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    public static final int PHOTO_REQUEST_CUT = 3;// 裁剪结果

    public static final Uri fileUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory()
            .getPath() + File.separator + "temp.jpg"));//临时储存的Uri

    public static Bitmap getBitmapFromCG(Activity activity, int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = null;
        switch (requestCode) {
            // 如果是拍照
            case PHOTO_REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 没有指定特定存储路径的时候，data不为null
                    if (data != null) {
                        if (data.getData() != null) {
                            startPhotoZoom(activity, data.getData());
                        }
                    } else {
                        startPhotoZoom(activity, fileUri);
                    }
                }
                break;
            // 如果是从相册选取
            case PHOTO_REQUEST_GALLERY:
                if (data != null) {
                    if (data.getData() != null) {
                        startPhotoZoom(activity, data.getData());
                    }
                }
                break;
            //如果是裁剪完成
            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        bitmap = bundle.getParcelable("data");
                    }
                }
                break;
        }
        return bitmap;
    }

    private static void startPhotoZoom(Activity activity, Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        //aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 700);
        intent.putExtra("aspectY", 300);
        //outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 700);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
}
