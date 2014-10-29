var movies;

$(document).ready(function() {
    if (localStorage.getItem('nickname') !== null) {
        $("#loginform").hide().css("visibility", "hidden");
        $("#shownickname").empty().append("<p>Welkom " + localStorage.getItem('nickname') + "</p>").show().css("visibility", "visible");
    }
    getMovies();

    $("#loginbutton").click(function() {
        logIn();
    });

    $(document).on("click", "#movielistitem", function(e) {
        var index = $(this).index();
        console.log("list item " + index + " clicked");
        setMovie(index);
    });
});

function logOut() {
    localStorage.clear();
    $("#loginform").show().css("visibility", "visible");
    $("#shownickname").hide().css("visibility", "hidden");
}

function setMovie(index) {
    $.each(movies, function(curIndex, value) {
        if (curIndex == index) {
            getImageByMovieSummary(value);
        }
    });
}

/**
 * Ajax calls from here --------------
 */
function logIn() {
    var nickname = $("#nickname").val();
    localStorage.setItem("nickname", nickname);
    $.ajax({
        type: "POST",
        url: 'http://localhost:8080/NotFlix/resources/users/login',
        dataType: "json",
        data: $("#loginform").serialize()
    }).fail(function(jqXHR, textStatus) {
        alert("Post login Request failed: " + textStatus);

    }).done(function(data) {
        $("#loginform").hide().css("visibility", "hidden");
        $("#shownickname").empty().append("<p>Welkom " + nickname + "</p>").show().css("visibility", "visible");

        $.each(data, function(index, value) {
            localStorage.setItem("Token", value);
        });

    });
}

function getMovies() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/NotFlix/resources/movies',
        dataType: "json"
    }).fail(function(jqXHR, textStatus) {
        alert("Get movies Request failed: " + textStatus);
    }).done(function(data) {
        console.log("movies successfully loaded");
        movies = data;
        localStorage.setItem("movies", movies);
        $("#movielist").empty();
        $.each(data, function(index, value) {
            console.log("get movies " + index);
            if (index == 0) {
                getImageByMovieSummary(value);
            }
            getImageByMovieList(value);
        });

    });
}

function getImageByMovieSummary(movie) {
    console.log(movie);
    var movieTitle = encodeURIComponent(movie.title);
    var movieYear = encodeURIComponent(movie.date);
    $.ajax({
        type: 'GET',
        url: 'http://www.omdbapi.com/?t=' + movieTitle + '&y=' + movieYear,
        dataType: "json"
    }).fail(function(jqXHR, textStatus) {
        alert("Get image Request failed: " + textStatus);
    }).done(function(data) {
        console.log("omdb movie successfully loaded: " + data.Poster);
        var imageUrl = data.Poster;
        $("#moviesummary").empty();
        $("#moviesummary").append(
            '<img src="' + data.Poster + '" id="movieimage"/>' +
            '<h2 id="movietitle">' + movie.title + '</h2>' +
            '<p id="movielength">Length: ' + movie.length + '</p > '
        );
        if (movie.averageRating > 0) {
            $("#moviesummary").append(' < p id = "movieavgrating" > Average rating ' + movie.averageRating + ' < /p>');
        }
        $("#moviesummary").append(
            '<p id="moviedirector">Directed by: ' + movie.director + '</p > ' +
            '<p id="shortdesc">' + movie.shortDesc + '</p>'
        );
    });
}

function getImageByMovieList(movie) {
    console.log(movie);
    var movieTitle = encodeURIComponent(movie.title);
    var movieYear = encodeURIComponent(movie.date);
    $.ajax({
        type: 'GET',
        url: 'http://www.omdbapi.com/?t=' + movieTitle + '&y=' + movieYear,
        dataType: "json"
    }).fail(function(jqXHR, textStatus) {
        alert("Get image Request failed: " + textStatus);
    }).done(function(data) {
        console.log("omdb movie successfully loaded: " + data.Poster);
        var imageUrl = data.Poster;
        $("#movielist").append(
            '<tr class="list-group" id="movielistitem">' +
            '<td id="listitemimage" > ' +
            '<img class="listimage" src=' + imageUrl + '"/>' +
            '</td>' +
            '<td id="listitemtitle ">' +
            '<h4 class="list - group - item - heading ">' + movie.title + '</h4>' +
            '</td>' +
            '</tr>'
        );
    });
}

function register() {
    $.ajax({
        type: 'post',
        url: 'http://localhost:8080/NotFlix/resources/users',
        dataType: 'json',
        //data: ,Register form .serialize(); Firstname, Lastname, Nickname, Password, (insert)
        success: function(data) {
            //data contains token for registered account.
        },
        error: function(request, error) {
            alert("Register gone wrong!");
        }

    });
}

function getUsers() {
    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/NotFlix/resources/users',
        dataType: 'json',
        headers: {
            'Token': localStorage.getItem("Token ")
        },
        success: function(data) {
            $.each(data, function(index, value) {
                // Do something with user objects ( value  equals user object)
            });
        },
        error: function(request, error) {
            alert("Get users gone wrong ");
        }

    });
}