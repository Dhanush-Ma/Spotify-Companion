package com.spotify;

import static com.mongodb.client.model.Filters.eq;

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

import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@WebServlet("/signinAuth")
public class Signin extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String userID = req.getParameter("userID");
		String userName = req.getParameter("userName");
		String userPassword = req.getParameter("password");
		System.out.println(userID + userName + userPassword);
		// AUTH
		// JDBC Connectivity to NoSQL Database (MongoDB)
		MongoClient mongo = new MongoClient("localhost", 27017);
		//Creating a database Name - Spotify
		MongoDatabase db = mongo.getDatabase("SpotifyCompanion");

		MongoCollection collection = db.getCollection("Users");
		
		Document existingUser = (Document) collection.find(eq("userID", userID)).first();
		if(existingUser == null) {
			Document user = new Document();
			user.append("userID", userID);
			user.append("userName", userName);
			user.append("userPassword", userPassword);
			
			collection.insertOne(user);
			System.out.println("Inserted");
			
			HttpSession session = req.getSession();
			session.setAttribute("userID", userID);
			session.setAttribute("userName", userName);
			RequestDispatcher rd = req.getRequestDispatcher("/playlists");
			rd.forward(req, res);
			
       }else {
			//handle invalid user
			PrintWriter out = res.getWriter();
			res.setStatus(401);
			System.out.println("invalid");
       }
	}
}
