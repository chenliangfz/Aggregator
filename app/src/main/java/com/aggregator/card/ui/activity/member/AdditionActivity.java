package com.aggregator.card.ui.activity.member;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageView;

import com.aggregator.card.R;
import com.aggregator.card.core.inject.component.DaggerActivityComponent;
import com.aggregator.card.core.mvp.extension.RxAppCompatActivityView;
import com.aggregator.card.util.CameraGalleryUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChenLiang on 16/11/14.
 */

public class AdditionActivity extends RxAppCompatActivityView<AdditionPresenter> {

    @BindView(R.id.business)
    EditText mEdtBusiness;

    @BindView(R.id.screenshot)
    ImageView mCardScreenshot;

    @NonNull
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_addition;
    }

    @Override
    protected void initComponent() {
        DaggerActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
    }

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap= CameraGalleryUtil.getBitmapFromCG(this,requestCode,resultCode,data);
        mCardScreenshot.setImageBitmap(bitmap);
    }

    @OnClick(R.id.screenshot_captor)
    void onClickScreenshotCaptor(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, CameraGalleryUtil.PHOTO_REQUEST_GALLERY);
    }

    @OnClick(R.id.sure)
    void onClickSure(){

    }

    @OnClick(R.id.back)
    void onClickBack(){
        finish();
    }
}
