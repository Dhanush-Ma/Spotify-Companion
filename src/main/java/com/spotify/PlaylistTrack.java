package com.spotify;

public class PlaylistTrack {
	String trackName;
	String trackAlbum;
	String trackImage;
	String trackID;
	public PlaylistTrack(String trackName, String trackAlbum, String trackImage, String trackID) {
		super();
		this.trackName = trackName;
		this.trackAlbum = trackAlbum;
		this.trackImage = trackImage;
		this.trackID = trackID;
	}

	public String getTrackImage() {
		return trackImage;
	}
	public void setTrackImage(String trackImage) {
		this.trackImage = trackImage;
	}

	public String getTrackID() {
		return trackID;
	}

	public void setTrackID(String trackID) {
		this.trackID = trackID;
	}

	public String getTrackName() {
		return trackName;
	}
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}
	public String getTrackAlbum() {
		return trackAlbum;
	}
	public void setTrackAlbum(String trackAlbum) {
		this.trackAlbum = trackAlbum;
	}
}
