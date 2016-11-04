package com.aggregator.card.util;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mawmd.offprint.R;
import com.mawmd.offprint.core.App;
import com.mawmd.offprint.core.Constant;

public class ToastHelp {

    private static Toast mToast;
    private static ToastHelp mInstance;

    private ToastHelp() {
        mToast = new Toast(App.sContext);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM, 0, Constant.WINDOWN_HEIGHT / 6 * 4);
    }

    public static ToastHelp getInstance() {
        if (mInstance == null) {
            mInstance = new ToastHelp();
        }
        return mInstance;
    }

    public void showToast(CharSequence text) {
        View view = LayoutInflater.from(App.sContext)
                .inflate(R.layout.view_toast_text, null);
        TextView textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setText(text);
        mToast.setView(view);
        if (TextUtils.equals(text, "Socket closed") || TextUtils.equals(text, "Canceled")) {
            return;
        }
        mToast.show();
    }

    public void showToast(int resid) {
        View view = LayoutInflater.from(App.sContext)
                .inflate(R.layout.view_toast_text, null);
        TextView textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setText(resid);
        mToast.setView(view);
        mToast.show();
    }

    public void showDialogToast(CharSequence text) {
        View view = LayoutInflater.from(App.sContext)
                .inflate(R.layout.view_dialog_toast, null);
        TextView textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setText(text);
        mToast.setView(view);
        mToast.setGravity(Gravity.BOTTOM, 0, Constant.WINDOWN_HEIGHT / 2);
        mToast.show();
    }

    public void showExitToast() {
        Toast toast = new Toast(App.sContext);
        toast.setDuration(Toast.LENGTH_SHORT);
        View view = LayoutInflater.from(App.sContext)
                .inflate(R.layout.view_toast_text, null);
        TextView textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setText("再按一次退出应用");
        toast.setGravity(Gravity.BOTTOM, 0, 80);
        toast.setView(view);
        toast.show();
    }


}
