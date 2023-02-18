package com.spotify;

public class UserPlaylist {
	private String id;
	private String name;
	private String trackLink;
	private Long totalSongs;
	private String playlistImage;
	
	public String getId() {
		return id;
	}

	public Long getTotalSongs() {
		return totalSongs;
	}

	public UserPlaylist(String id, String name, String trackLink, Long totalSongs, String playlistImage) {
		super();
		this.id = id;
		this.name = name;
		this.trackLink = trackLink;
		this.totalSongs = totalSongs;
		this.playlistImage = playlistImage;
	}

	public String getPlaylistImage() {
		return playlistImage;
	}

	public void setPlaylistImage(String playlistImage) {
		this.playlistImage = playlistImage;
	}

	public void setTotalSongs(Long totalSongs) {
		this.totalSongs = totalSongs;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrackLink() {
		return trackLink;
	}

	public void setTrackLink(String trackLink) {
		this.trackLink = trackLink;
	}


}
