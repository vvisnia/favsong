function httpGet(theUrl) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, false);
    xmlHttp.send(null);
    return xmlHttp.responseText;

}
function populateTableWithArtists() {

    var artistName = $('#test').val();
    $('#test').val(null);
    var theUrl = "http://localhost:8080/artists/" + artistName;
    var artistJSON = JSON.parse(httpGet(theUrl));



    var array = artistJSON.artists.items;



    var reloadTable = function(artists) {
        var table = $('#table');
        table.find("tbody tr").remove();
        table.append("<tr><th>" + "Artist name" + "</th><th>" + "Artist spotify id" + "</th><th>Find albums</th></tr>");
        artists.forEach(function (artist) {
            table.append("<tr><td>" + artist.name + "</td><td>" + artist.id + "</td><td><button class='findAlbums' onclick='populateTableWithAlbums(\""+artist.id+"\")'>Find Albums</button></td></tr>");
        });
    };
    reloadTable(array);
}
function populateTableWithAlbums(artistsID) {
    var theUrl = "http://localhost:8080/albums/" + artistsID;
    var albumJSON = JSON.parse(httpGet(theUrl));

    var array = albumJSON.items;

    var reloadTable = function(albums) {
        var table = $('#table');
        table.find("tbody tr").remove();
        table.append("<tr><th>" + "Album name" + "</th><th>" + "Album spotify id"+ "</th><th>Find tracks</th></tr>");
        albums.forEach(function (album) {
            table.append("<tr><td>" + album.name + "</td><td>" + album.id + "</td><td><button class='findAlbums' onclick='populateTableWithTracks(\""+album.id+"\")'>Find Tracks</button></td></tr>");
        });
    };
    reloadTable(array);
}
function populateTableWithTracks(albumID) {
    var theUrl = "http://localhost:8080/tracks/" + albumID;
    var albumJSON = JSON.parse(httpGet(theUrl));
    console.log(albumJSON);

    var array = albumJSON.tracks.items;

    var reloadTable = function(tracks) {
        var table = $('#table');
        table.find("tbody tr").remove();
        table.append("<tr><th>" + "Album name" + "</th><th>" + "Track spotify id"+ "</th><th>Add to favourite list</th></tr>");
        tracks.forEach(function (track) {
            table.append("<tr><td>" + track.name + "</td><td>" + track.id + "<td><button class='findAlbums' onclick='addToFavList(\""+track.name+"\")'>Add Track</button></td></tr>");
        });
    };
    reloadTable(array);
}
function addToFavList(trackName) {

    sessionStorage.setItem("trackList", trackName);
 var array = [];
 array.push(trackName);
 array.push("lol");
    var favList = { "name": "wisniah", "tracksList": array };
    console.log(favList);
    $.ajax({
        url: '/favlist/save',
        type : "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        dataType : 'json',
        data : JSON.stringify(favList),
        success : function(result) {
            alert("Added song to list!");
            window.location.href = '/';
        },
        error: function(xhr, resp, text) {
            alert("Error while adding song to list!");
            console.log("errorek");
            console.log(xhr, resp, text);
        }
    })

}