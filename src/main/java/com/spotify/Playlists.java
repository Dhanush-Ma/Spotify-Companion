package com.spotify;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Projections;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import static com.mongodb.client.model.Filters.*;


@WebServlet("/playlists")
public class Playlists extends HttpServlet {
	
	public String getPlaylistCoverImage(String playlistID) {
		String imageURL = "";
		try {
			HttpRequest getCoverImage = HttpRequest.newBuilder()
					.uri(URI.create("https://api.spotify.com/v1/playlists/" + playlistID + "/images"))
					.header("Authorization", "Bearer " + Credentials.accessToken)
					.build();
		
			HttpClient httpClient = HttpClient.newBuilder().build();
			var data = httpClient.send(getCoverImage, BodyHandlers.ofString());
			
			JSONParser parser = new JSONParser();
			Object object = parser.parse(data.body());
			JSONArray jsonData = (JSONArray) object;
			
			if(jsonData.get(0).equals(null)) {
				imageURL = "";
			}else {
				JSONObject imageObj = (JSONObject) jsonData.get(0);
				imageURL = (String) imageObj.get("url");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return imageURL;
	}
	
	public List getTrackSongs(String playlistID) {
		List<PlaylistTrack> playlistTracks =  new ArrayList<PlaylistTrack>();
		try {
						
			
			HttpRequest getPlaylistTracks = HttpRequest.newBuilder()
					.uri(URI.create("https://api.spotify.com/v1/playlists/" + playlistID + "/tracks"))
					.header("Authorization", "Bearer " + Credentials.accessToken)
					.build();
		
			HttpClient httpClient = HttpClient.newBuilder().build();
			var data = httpClient.send(getPlaylistTracks, BodyHandlers.ofString());
			
			
			
	
			JSONParser parser = new JSONParser();
			Object object = parser.parse(data.body());
			JSONObject jsonData = (JSONObject) object;
			JSONArray items = (JSONArray) jsonData.get("items");
			for(Object item : items) {
				String songName = "";
				String albumName = "";
				String songImage = "";
				String songID = "";
				
				JSONObject jsonItem = (JSONObject) item;
				Object trackObj = jsonItem.get("track");
				JSONObject jsonTrack = (JSONObject) trackObj;
				
				songName = (String) jsonTrack.get("name");
				songID = (String) jsonTrack.get("id");
				
				Object albumObj = jsonTrack.get("album");
				JSONObject jsonAlbum = (JSONObject) albumObj;
				
				albumName = (String) jsonAlbum.get("name");
				
				JSONArray imagesArray = (JSONArray) jsonAlbum.get("images");
				JSONObject imageObj = (JSONObject) imagesArray.get(0);
				
				songImage = (String) imageObj.get("url");
				
				
				playlistTracks.add(new PlaylistTrack(songName, albumName, songImage, songID));
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return playlistTracks;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String userID = req.getParameter("userID");
		String userPassword = req.getParameter("password");
		String userName = "";
		
		// AUTH
		// JDBC Connectivity to NoSQL Database (MongoDB)
		MongoClient mongo = new MongoClient("localhost", 27017);
		//Creating a database Name - Spotify
		MongoDatabase db = mongo.getDatabase("SpotifyCompanion");

		MongoCollection<Document> collection = db.getCollection("Users");
	
		Document existingUser = collection.find(eq("userID", userID)).first();
		
		if(existingUser != null) {
			System.out.println(existingUser.toJson());
			System.out.println("entered: " + userPassword);
			System.out.println(existingUser.get("userPassword"));
			if(userPassword.equals(existingUser.get("userPassword"))) {
				userName = (String) existingUser.get("userName");
				System.out.println(userName);
				// Call to SPOTIFY API
				try {
					
					List<UserPlaylist> userPlaylists =  new ArrayList<UserPlaylist>();
					List<Object> allPlaylistTracks =  new ArrayList<Object>();

					
					HttpRequest getUserPlaylists = HttpRequest.newBuilder()
							.uri(URI.create("https://api.spotify.com/v1/users/" + userID + "/playlists"))
							.header("Authorization", "Bearer " + Credentials.accessToken)
							.build();
				
					HttpClient httpClient = HttpClient.newBuilder().build();
					var data = httpClient.send(getUserPlaylists, BodyHandlers.ofString());
					
//					PrintWriter out = res.getWriter();
//					out.println(data.body());
//					System.out.println(data.body());
					
						JSONParser parser = new JSONParser();
						Object object = parser.parse(data.body());
						JSONObject jsonData = (JSONObject) object;
						JSONArray items = (JSONArray) jsonData.get("items");
						for(Object item : items) {
							JSONObject jsonItem = (JSONObject) item;
							String id = (String) jsonItem.get("id");
							String name = (String) jsonItem.get("name");
							String trackLink = "";
							Long totalSongs = 0l;
							String playlistCoverImage = "";
							
							Object tracksObject = (Object) jsonItem.get("tracks");
							JSONObject jsonTracks = (JSONObject) tracksObject;
							trackLink = (String) jsonTracks.get("href");
							totalSongs = (Long) jsonTracks.get("total");
							playlistCoverImage = getPlaylistCoverImage(id);
							
							System.out.println(playlistCoverImage);
							userPlaylists.add(new UserPlaylist(id, name, trackLink, totalSongs, playlistCoverImage));
							allPlaylistTracks.add(getTrackSongs(id));
							
						}
					System.out.println(allPlaylistTracks.get(0));

					HttpSession session = req.getSession();
					session.setAttribute("userID", userID);
					session.setAttribute("userName", userName);
					session.setAttribute("userPlaylists", userPlaylists);
					session.setAttribute("allPlaylistTracks", allPlaylistTracks);
					res.sendRedirect("showPlaylists.jsp");
					
								
			 } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.getMessage();
					e.printStackTrace();
				} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}else {
				//wrong password
				PrintWriter out = res.getWriter();
				res.setStatus(400);
				System.out.println("Wrong Password");
			}
			
			
		}else {
			//handle invalid user
			PrintWriter out = res.getWriter();
			res.setStatus(401);
			System.out.println("invalid");
		}
			
	
	}
}





