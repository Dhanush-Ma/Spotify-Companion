package com.spotify;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@WebServlet("/playlists/download")
public class YoutubeLink extends HttpServlet {
	
	private String getYoutubeURL(String trackName) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String youtubeURL = "";
		String trackNameURL = URLEncoder.encode(trackName, "UTF-8");
		String params = "q=" + trackNameURL + "&key=" + Credentials.youtubeApiToken;
		String url = "https://www.googleapis.com/youtube/v3/search?" + params;
		try {
			HttpRequest getLink = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.build();
		
			HttpClient httpClient = HttpClient.newBuilder().build();
			var data = httpClient.send(getLink, BodyHandlers.ofString());
			
			JSONParser parser = new JSONParser();
			Object object = parser.parse(data.body());
			JSONObject jsonData = (JSONObject) object;
			JSONArray items = (JSONArray) jsonData.get("items");
			
			JSONObject itemsJSON = (JSONObject) items.get(0);
			Object idObj = itemsJSON.get("id");
			JSONObject idJSON = (JSONObject) idObj;
			
			String videoID = (String) idJSON.get("videoId");
			
			youtubeURL = "https://youtu.be/" + videoID;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return youtubeURL;
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
			String trackName = req.getParameter("trackName");
//			System.out.println(trackName);
			
			String youtubeURL = getYoutubeURL(trackName);
			
			PrintWriter out = res.getWriter();
			out.println(youtubeURL);
						 
	}

	
}
