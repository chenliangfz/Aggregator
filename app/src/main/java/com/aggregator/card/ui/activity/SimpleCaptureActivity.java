package com.aggregator.card.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import io.github.xudaojie.qrcodelib.CaptureActivity;

/**
 * Created by xdj on 16/9/17.
 */

public class SimpleCaptureActivity extends CaptureActivity {


    @Override
    protected void handleResult(String resultString) {
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(this, io.github.xudaojie.qrcodelib.R.string.scan_failed, Toast.LENGTH_SHORT).show();
            restartPreview();
        } else {
            Intent intent = new Intent();
            intent.putExtra("result",resultString);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}
