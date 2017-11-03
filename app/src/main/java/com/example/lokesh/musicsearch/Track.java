package com.example.lokesh.musicsearch;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lokesh on 11/10/2017.
 */

public class Track implements Parcelable {
    String name;
    String artist;
    String mainURL;
    String smallImageURL;
    String largeImageURL;
    boolean favourited = false;

    public Track() {

    }

    public boolean getFavourited() {
        return favourited;
    }

    public void setFavourited(boolean favourited) {
        this.favourited = favourited;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getMainURL() {
        return mainURL;
    }

    public void setMainURL(String mainURL) {
        this.mainURL = mainURL;
    }

    public String getSmallImageURL() {
        return smallImageURL;
    }

    public void setSmallImageURL(String smallImageURL) {
        this.smallImageURL = smallImageURL;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(artist);
        dest.writeString(mainURL);
        dest.writeString(smallImageURL);
        dest.writeString(largeImageURL);
    }

    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    private Track(Parcel in) {
        this.name = in.readString();
        this.artist = in.readString();
        this.mainURL = in.readString();
        this.smallImageURL = in.readString();
        this.largeImageURL = in.readString();

    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Track)) {
            return false;
        }
        Track that = (Track) other;
        return this.name.equals(that.name) && this.artist.equals(that.artist)
                && this.mainURL.equals(that.mainURL) && this.smallImageURL.equals(that.smallImageURL);
    }
}
