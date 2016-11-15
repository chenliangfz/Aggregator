package com.aggregator.card.mock;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Simple model class
 * One important requirement for DeckView to function
 * is that all items in the dataset *must be* uniquely
 * identifiable. No two items can be such
 * that `item1.equals(item2)` returns `true`.
 * See equals() implementation below.
 * `id` is generated using `DeckViewSampleActivity#generateuniqueKey()`
 * Implementing `Parcelable` serves only one purpose - to persist data
 * on configuration change.
 */
public class StackMock implements Parcelable {

    public int id;
    public String headerTitle, link;

    public StackMock() {
        // Nothing
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public StackMock(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        id = in.readInt();
        headerTitle = in.readString();
        link = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(headerTitle);
        dest.writeString(link);
    }

    public static final Creator<StackMock> CREATOR = new Creator<StackMock>() {
        public StackMock createFromParcel(Parcel in) {
            return new StackMock(in);
        }

        public StackMock[] newArray(int size) {
            return new StackMock[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        return ((StackMock) o).id == this.id;
    }
}