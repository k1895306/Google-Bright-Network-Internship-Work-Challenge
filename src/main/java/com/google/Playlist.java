package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a playlist. */ 
public class Playlist {
    
    private final String playlist_title;
   // private final Integer playlist_Id;
    private final List<Video> playlist_videolist;

    Playlist(String playlist_title) {
    
    this.playlist_title = playlist_title;
    // this.playlist_Id = playlist_Id;
    playlist_videolist = new ArrayList<Video>();
    }

    /** Returns the title of the playlist. */
    String getPlaylistTitle() {
        return playlist_title;
    }


    /** Returns a list of videos in the playlist. */
    List<Video> getVideos() {
        return playlist_videolist;
    }

    public void add_video(Video video) {
        playlist_videolist.add(video);
    }

    public void remove_video(Video video) {
        playlist_videolist.remove(video);
    }
    
    public void clear_all() {
        playlist_videolist.clear();
    }
}
