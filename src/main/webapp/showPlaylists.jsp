<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>${userName}'s Playlists</title>
     <link rel="stylesheet" href="showPlaylists.css" />
     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css" integrity="sha512-SzlrxWUlpfuzQ+pcUCosxcglQRNAq/DZjVsC0lE40xsADsfeQoEypE+enwcOiGjk/bSuGGKHEyjSoQ1zVisanQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;700&display=swap" rel="stylesheet">
  </head>
  <body>
    <% 
    	if(session.getAttribute("userID") == null){
    		response.sendRedirect("index.html");
    	}
    %>
    <navbar>
    <div class="navbar">
    	<h1>Hi <span class="user">${userName}</span></h1>
    	<form action="logout">
    		<input type="submit" value="logout">
    	</form>
    </div>
    </navbar>
    <div class="playlistsContainer">
      <c:forEach items="${userPlaylists}" var="playlist" varStatus="loop">
      <div class="singlePlaylist">
      <div id="${playlist.id}" class="playlist">
            <img width=100 height=100 src="${playlist.playlistImage}" alt="poster" />
	        <div>
	        	<h1>${ playlist.name }</h1>
	        	<p>Total Songs: ${ playlist.totalSongs }</p>
	        </div>
   	  </div>
   	  <div class="playlistTracks">
  	      <c:forEach items="${allPlaylistTracks[loop.index]}" var="playlistTrack">
  	      <div id="${playlistTrack.trackID}" class="track">
  	      	<div class="trackInfo">
  	      	<img width=100 height=100 src="${playlistTrack.trackImage }" alt="poster" />
  	      	<div>
  	      	<h1>${playlistTrack.trackName }</h1>
  	      	<p>From ${playlistTrack.trackAlbum }</p>
  	      	</div>
  	      	</div>
  	      <div>
	    	<button class="playBtn"><i class="fa-sharp fa-solid fa-play"></i></button>
	        <button class="downloadBtn"><i class="fa-solid fa-cloud-arrow-down"></i></button>
	    </div>
  	      </div>
   	  	  </c:forEach>
   	  </div>
   	  </div>
      </c:forEach>
      </div>
      <script type="text/javascript">
      
      document.querySelectorAll(".playlist").forEach((element) => {
        element.addEventListener("click", () => {
          let playlistID = element.id;
          
          
        });
      });
      
    </script>
    <script src="script.js"></script>

  </body>
</html>
    