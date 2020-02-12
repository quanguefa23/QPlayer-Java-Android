package com.example.qplayer;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private String name;
    private String artist;
    private String quality; //0: 128kbps | 1: 320kbps | 2: lossless
    private int bannerImage;
    private int srcMusic;

    public Song(String name, String artist, String quality, int bannerImage, int srcMusic) {
        this.name = name;
        this.artist = artist;
        this.quality = quality;
        this.bannerImage = bannerImage;
        this.srcMusic = srcMusic;
    }

    protected Song(Parcel in) {
        name = in.readString();
        artist = in.readString();
        quality = in.readString();
        bannerImage = in.readInt();
        srcMusic = in.readInt();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getQuality() {
        return quality;
    }

    public int getBannerImage() {
        return bannerImage;
    }

    public int getSrcMusic() {
        return srcMusic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(artist);
        parcel.writeString(quality);
        parcel.writeInt(bannerImage);
        parcel.writeInt(srcMusic);
    }
}
