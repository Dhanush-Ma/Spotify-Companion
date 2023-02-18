const playlistElements = document.getElementsByClassName("playlist");

for (let i = 0; i < playlistElements.length; i++) {
  playlistElements[i].addEventListener("click", function () {
    this.classList.toggle("active");
  });
}

/* */

const downloadBtnElements = document.getElementsByClassName("downloadBtn");

for (let i = 0; i < downloadBtnElements.length; i++) {
  downloadBtnElements[i].addEventListener("click", function (e) {
    let trackName =  e.target.parentNode.parentNode.parentNode.childNodes[1].childNodes[2]
        .nextSibling.childNodes[1].innerText +
      " " +
      e.target.parentNode.parentNode.parentNode.childNodes[1].childNodes[2]
        .nextSibling.childNodes[3].innerText +
      " Lyrical";
	const trackTitle = e.target.parentNode.parentNode.parentNode.childNodes[1].childNodes[2]
        .nextSibling.childNodes[1].innerText
    const trackID = e.target.parentNode.parentNode.parentNode.id;
    // CALL TO SERVLET USING AJAX
	const trackNameArray = trackName.split(" ");
	trackName = trackNameArray.join("%20");
    getYoutubeLink(trackName, trackTitle);
    
  });
}

function getYoutubeLink(trackName, trackTitle) {
  let xmlhttp = new XMLHttpRequest();
  
  xmlhttp.onreadystatechange = function () {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
      console.log(xmlhttp.responseText);
      let URL = xmlhttp.responseText
      let title = trackTitle
	  window.location.href = `http://localhost:4000/download?URL=${URL}&title=${title}`;
    }
  };

  let params = "trackName=" + trackName;
  xmlhttp.open("POST", "playlists/download", true);
  xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xmlhttp.send(params)
}
