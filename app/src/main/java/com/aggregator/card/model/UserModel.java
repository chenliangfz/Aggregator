package com.aggregator.card.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.aggregator.card.BuildConfig;
import com.aggregator.card.util.EasyPreference;
import com.aggregator.card.util.L;
import com.aggregator.card.util.StringUtils;

/**
 * Created by ChenLiang on 16/11/17.
 */

public class UserModel extends BaseModel implements Parcelable {
    public static final long OVERTIME = 1000 * 60 * 60 * 24;

    private final String USER_PREFERENCE_NAME = BuildConfig.APPLICATION_ID + ".USER_PREFERENCE_NAME";

    private final String KEY_USER_ACCOUNT = BuildConfig.APPLICATION_ID + ".USER_ACCOUNT";

    private final String KEY_USER_PASSWORD = BuildConfig.APPLICATION_ID + ".USER_PASSWORD";

    private final String KEY_USER_SAVE_TIME_MILLIS = BuildConfig.APPLICATION_ID + ".USER_SAVE_TIME_MILLIS";

    public String account;

    public String password;

    long saveTimeMillis;

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        this.account = in.readString();
        this.password = in.readString();
        this.saveTimeMillis = in.readLong();
    }

    public UserModel getCache(Context context) {
        EasyPreference.Builder builder = EasyPreference.with(context, USER_PREFERENCE_NAME);
        account = builder.getString(KEY_USER_ACCOUNT, "");
        password = builder.getString(KEY_USER_PASSWORD, "");
        saveTimeMillis = builder.getLong(KEY_USER_SAVE_TIME_MILLIS, 0);
        return this;
    }

    public void saveCache(Context context) {
        saveTimeMillis = System.currentTimeMillis();
        EasyPreference.with(context, USER_PREFERENCE_NAME)
                .addString(KEY_USER_ACCOUNT, account)
                .addString(KEY_USER_PASSWORD, password)
                .addLong(KEY_USER_SAVE_TIME_MILLIS, saveTimeMillis)
                .save();

    }

    public boolean isOverTime() {
        return System.currentTimeMillis() - saveTimeMillis > OVERTIME;
    }

    public boolean isValid() {
        return !StringUtils.isEmpty(account, password) && !isOverTime();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.account);
        dest.writeString(this.password);
        dest.writeLong(this.saveTimeMillis);
    }

    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
