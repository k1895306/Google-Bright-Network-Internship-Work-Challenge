package com.google;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  public boolean video_playing;
  public boolean video_paused = false;
  public String video_playing_id;
  public String video_paused_id;
  // public final Playlist playlistLibrary;
  private final HashMap<String, Playlist> playlists = new HashMap<>();
  // public List<String> videos_id_list;
  public String playlist_index;
  List<Video> videos_list;
  List<String> videos_id_list;

  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {
    System.out.println("Here's a list of all available videos:");
    List<Video> videos_list = videoLibrary.getVideos();
    videos_list.sort(Comparator.comparing(Video::getTitle));
    for(Video video: videos_list){
      System.out.print(getVideoString(video));
      System.out.println();
  }
  }

  private String getVideoString(Video video) {
    List<String> tags_list = video.getTags();
    String delim = " ";
    String tags = String.join(delim, tags_list);
    return video.getTitle()+ " ("+video.getVideoId()+") ["+ tags + "]";
  } 

  public void playVideo(String videoId) {
    videos_list = videoLibrary.getVideos();
    videos_id_list = new ArrayList<String>();
    for(int i = 0; i < videoLibrary.getVideos().size(); i++) {
      
      videos_id_list.add(videos_list.get(i).getVideoId());
    }
    if(!videos_id_list.contains(videoId)) {
      System.out.println("Cannot play video: Video does not exist");  
    }
    else if(video_playing == true) {
      System.out.println("Stopping video: "+ videoLibrary.getVideo(video_playing_id).getTitle());
      System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());  
      video_playing = true;
      video_paused = false;
      video_playing_id = videoId;

    }
    else {
    System.out.println("Playing video: "+ videoLibrary.getVideo(videoId).getTitle());
    video_playing = true;
    video_paused = false;
    video_playing_id = videoId;
    }
  }

  public void stopVideo() {
    if(video_playing == true && video_playing_id != null) {
      System.out.println("Stopping video: " + videoLibrary.getVideo(video_playing_id).getTitle());
      video_playing = false;
      video_paused = false;
      video_playing_id = null;
      video_paused_id = null;
    }
    else {
      System.out.println("Cannot stop video: No video is currently playing");

    }
  }

  public void playRandomVideo() {
    if(videoLibrary.getVideos().size() == 0) {
      System.out.println("No videos available");
    }

    if (video_playing == true && video_playing_id != null) {
      System.out.println("Stopping video: " + videoLibrary.getVideo(video_playing_id).getTitle());
      video_playing = false;
      video_playing_id = null;
    }
      
        List<Video> videos_list = videoLibrary.getVideos();
        
      
      Random rand = new Random();
      int random_index = rand.nextInt(videos_list.size());
     //  System.out.println(random_index);
      Video random_video = videos_list.get(random_index);
      System.out.println("Playing video: " + random_video.getTitle());
      video_playing = true;
      video_playing_id = random_video.getVideoId();
  }

  public void pauseVideo() {
    if (video_paused == true) {
      System.out.println("Video already paused: " + videoLibrary.getVideo(video_paused_id).getTitle());
    }

    else if(video_playing == true && video_playing_id != null) {
      System.out.println("Pausing video: " + videoLibrary.getVideo(video_playing_id).getTitle());
      video_paused = true;
      video_paused_id = video_playing_id;
    }
    
    if(video_playing == false && video_playing_id == null) {
      System.out.println("Cannot pause video: No video is currently playing");
    }

  }

  public void continueVideo() {
    if(video_paused == false && video_playing == true) {
      System.out.println("Cannot continue video: Video is not paused");
    }
    if(video_paused == true) {
      System.out.println("Continuing video: "+ videoLibrary.getVideo(video_paused_id).getTitle());
      video_paused = false;
    }
    if(video_playing == false) {
      System.out.println("Cannot continue video: No video is currently playing");
    }
    
  }

  public void showPlaying() {
    if(video_playing == true && video_playing_id != null) {
      Video video = videoLibrary.getVideo(video_playing_id);
      if(video_paused == false) {
        System.out.println("Currently playing: " + getVideoString(video));
      }
    else if(video_paused == true) {
        System.out.println("Currently playing: " + getVideoString(video) + " - PAUSED");
    }
  }
  else if(video_playing == false && video_playing_id == null) {
    System.out.println("No video is currently playing");
  }
  }

  public void createPlaylist(String playlistName) {
    boolean flag = false;
    String playlist_index = playlistName.toLowerCase();
    if(!playlists.isEmpty()) {
        
        if(playlists.containsKey(playlist_index)) {
          flag = true;
          System.out.println("Cannot create playlist: A playlist with the same name already exists");
        }
    }
    if(flag == false) {
    playlists.put(playlist_index, new Playlist(playlistName));
    System.out.println("Successfully created new playlist: " + playlistName);
  }
  }

  public void addVideoToPlaylist(String playlistName, String videoId) {
    String playlist_index = playlistName.toLowerCase();
    videos_list = videoLibrary.getVideos();
    videos_id_list = new ArrayList<String>();
    for (int i = 0; i < videoLibrary.getVideos().size(); i++) {

      videos_id_list.add(videos_list.get(i).getVideoId());
    }
    
    if(playlists.containsKey(playlist_index) && videos_id_list.contains(videoId)) {
     
      Playlist playlist1 = playlists.get(playlist_index);
      Video playlist_video = videoLibrary.getVideo(videoId);
      if(playlist1.getVideos().contains(playlist_video)) {
         System.out.println("Cannot add video to " + playlistName + ": Video already added");
      }
      else {
        playlist1.add_video(playlist_video);
        System.out.println("Added video to " + playlistName + ": " + playlist_video.getTitle());
      }
    }
    else if(!playlists.containsKey(playlist_index)) {
      System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
    } 
    else if (!videos_id_list.contains(videoId)) {
      System.out.println("Cannot add video to " + playlistName + ": Video does not exist");
    }
  }

  public void showAllPlaylists() {
    if(playlists.isEmpty()) {
      System.out.println("No playlists exist yet");
    }
    else {
      System.out.println("Showing all playlists:");
      ArrayList<String> value_list = new ArrayList<String>(playlists.keySet());
      Collections.sort(value_list);
      for(String playlist_index : value_list){
        if(playlists.containsKey(playlist_index)) {
          Playlist playlist1 = playlists.get(playlist_index);
          System.out.println(playlist1.getPlaylistTitle());
        }
      }
    }
  }

  public void showPlaylist(String playlistName) {
    String playlist_index = playlistName.toLowerCase();
    if (!playlists.containsKey(playlist_index)) {
      System.out.println("Cannot show playlist " + playlistName + ": Playlist does not exist");
    }
    else {
      System.out.println("Showing playlist: " + playlistName);
        Playlist playlist = playlists.get(playlist_index);
        if(playlist.getVideos().size() == 0) {
          System.out.println("No videos here yet");
        }
        else if(playlist.getVideos() != null) {
          for(Video video : playlist.getVideos()) {
           System.out.println(getVideoString(video));
        }
      } 
      }
    }

  public void removeFromPlaylist(String playlistName, String videoId) {
    String playlist_index = playlistName.toLowerCase();
    videos_list = videoLibrary.getVideos();
    videos_id_list = new ArrayList<String>();
    for (int i = 0; i < videoLibrary.getVideos().size(); i++) {

      videos_id_list.add(videos_list.get(i).getVideoId());
    }
    
    if(playlists.containsKey(playlist_index) && videos_id_list.contains(videoId)) {
     
      Playlist playlist1 = playlists.get(playlist_index);
      Video playlist_video = videoLibrary.getVideo(videoId);
      if(playlist1.getVideos().contains(playlist_video)) {
        System.out.println("Removed video from "+ playlistName + ": " + playlist_video.getTitle());
        playlist1.remove_video(playlist_video);
      }
      else {
        System.out.println("Cannot remove video from "+ playlistName + ": Video is not in playlist");
      }
    }
    else if (!playlists.containsKey(playlist_index)) {
      System.out.println("Cannot remove video from " + playlistName + ": Playlist does not exist");
    }
    else if(!videos_id_list.contains(videoId)) {
      System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
    }
  }

  public void clearPlaylist(String playlistName) {
    String playlist_index = playlistName.toLowerCase();
    if(playlists.containsKey(playlist_index)) {
      Playlist playlist = playlists.get(playlist_index);
      
        if(playlist.getVideos().size() == 0) {
          System.out.println("Showing playlist: " + playlistName);
          System.out.println("No videos here yet.");
        }
        else if(playlist.getVideos() != null) {
        playlist.clear_all();
        System.out.println("Successfully removed all videos from " + playlistName);
    }
  }
    else if (!playlists.containsKey(playlist_index)) {
      System.out.println("Cannot clear playlist " + playlistName + ": Playlist does not exist");
    }
  }

  public void deletePlaylist(String playlistName) {
    String playlist_index = playlistName.toLowerCase();
    if(playlists.containsKey(playlist_index)) {
      playlists.remove(playlist_index);
      System.out.println("Deleted playlist: " + playlistName); 
    }
    else {
      System.out.println("Cannot delete playlist "+ playlistName + ": Playlist does not exist");
    }
  }

  public void searchVideos(String searchTerm) {
    String video_index = searchTerm.toLowerCase();
    List<Video> videos_list1 = videoLibrary.getVideos();
    int count = 1;
    boolean flag = false;
    
    List<String> names = new ArrayList<String>();
    System.out.println("Here are the results for " + searchTerm + ":");
    for (int i = 0; i < videoLibrary.getVideos().size(); i++) {
      Video video = videos_list1.get(i);
      String to_check = video.getTitle().toLowerCase();
      if (to_check.contains(video_index)) {
        
        names.add(video.getTitle());
        System.out.println(count + ") " + getVideoString(video));
        count++;
        flag = true;
      }
    }
    if(flag == true) {
      System.out.println("Would you like to play any of the above? If yes, specify the number of the video.");
      System.out.println("If your answer is not a valid number, we will assume it's a no");
      Scanner sc = new Scanner(System.in);
      int user_input = sc.nextInt();
      Collections.sort(names);

      for(int i = 1; i < count; i++){
        if(user_input == i) {
          
          System.out.println("Playing video: " + names.get(i-1));
           
        }
      }
    }
      if(flag == false) {
        System.out.println("No search results for "+ searchTerm);
      }
    }
  
  public void searchVideosWithTag(String videoTag) {
    System.out.println("searchVideosWithTag needs implementation");
  }

  public void flagVideo(String videoId) {
    System.out.println("flagVideo needs implementation");
  }

  public void flagVideo(String videoId, String reason) {
    System.out.println("flagVideo needs implementation");
  }

  public void allowVideo(String videoId) {
    System.out.println("allowVideo needs implementation");
  }
}