var movies;
var rankedMovies;
var loggedIn = false;

$(document).ready(function() {
    if (localStorage.getItem('nickname') !== null) {
        $("#loginform").hide().css("visibility", "hidden");
        $("#shownickname").empty().append("<p>Welkom " + localStorage.getItem('nickname') + "</p>").show().css("visibility", "visible");
        $("#logout").show().css("visibility", "visible");
        loggedIn = true;
    }
    getMovies();

    $("#loginbutton").click(function() {
        logIn();
    });

    $("#logoutbutton").click(function() {
        logOut();
    });

    $(document).on("click", "#movielistitem", function(e) {
        var index = $(this).index();
        console.log("list item " + index + " clicked");
        setMovie(index);
        $("html, body").animate({
            scrollTop: 0
        }, "slow");
    });
});

function logOut() {
    localStorage.clear();
    $("#loginform").show().css("visibility", "visible");
    $("#shownickname").hide().css("visibility", "hidden");
    $("#logout").hide().css("visibility", "hidden");
    window.location = "computer.html";
    loggedIn = false;
}

function setMovie(index) {
    $.each(movies, function(curIndex, value) {
        if (curIndex == index) {
            $("#moviesummary").empty();
            $("#moviesummary").append(
                '<img src="" id="movieimage"/>' +
                '<h2 id="movietitle">' + value.title + '</h2>' +
                '<p id="movielength">Length: ' + value.length + '</p > '
            );
            if (value.averageRating > 0) {
                $("#moviesummary").append('<p id="movieavgrating">Average rating ' + value.averageRating + ' </p>');
            }
            $("#moviesummary").append(
                '<p id="moviedirector">Directed by: ' + value.director + '</p > ' +
                '<p id="shortdesc">' + value.shortDesc + '</p>'
            );
            if (loggedIn) {
                console.log("slider has been added");
                $("#moviesummary").append(
                    '<div class="input-group">' +
                    '<span class="input-group-addon">Your rating</span>' +
                    '<input type="text" class="form-control" placeholder="Rating">' +
                    '<span class="input-group-btn"><button class="btn btn-default" type="button">Change!</button></span>' +
                    '</div>'
                );
            }
            getImageByMovie(value, "#movieimage");
        }
    });
}

function prepareList() {
    $('#expList').find('li:has(ul)').click(function(event) {
        if (this == event.target) {
            $(this).toggleClass('expanded');
            $(this).children('ul').toggle('medium');
        }
        return false;
    }).addClass('collapsed').children('ul').hide();
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
        $("#logout").show().css("visibility", "visible");
        loggedIn = true;
        $.each(data, function(index, value) {
            localStorage.setItem("Token", value);
        });
        location.reload();
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
                $("#moviesummary").empty();
                $("#moviesummary").append(
                    '<img src="" id="movieimage"/>' +
                    '<h2 id="movietitle">' + value.title + '</h2>' +
                    '<p id="movielength">Length: ' + value.length + '</p>'
                );
                if (value.averageRating > 0) {
                    $("#moviesummary").append('<p id="movieavgrating">Average rating ' + value.averageRating + '</p>');
                }
                $("#moviesummary").append(
                    '<p id="moviedirector">Directed by: ' + value.director + '</p > ' +
                    '<p id="shortdesc">' + value.shortDesc + '</p>'
                );
                if (loggedIn) {
                    console.log("slider has been added");
                    $("#moviesummary").append(
                        '<div class="input-group">' +
                        '<span class="input-group-addon">Your rating</span>' +
                        '<input type="text" class="form-control" placeholder="Rating">' +
                        '<span class="input-group-btn"><button class="btn btn-default" type="button">Change!</button></span>' +
                        '</div>'
                    );
                }
                getImageByMovie(value, "#movieimage");
            }
            $("#movielist").append(
                '<tr class="list-group" id="movielistitem">' +
                '<td> ' +
                '<img class="listimage" id="listitemimage' + index + '" src=""/>' +
                '</td>' +
                '<td id="listitemtitle ">' +
                '<h4 class="list - group - item - heading ">' + value.title + '</h4>' +
                '</td>' +
                '</tr>'
            );
            getImageByMovie(value, "#listitemimage" + index);
        });

    });
}

function getImageByMovie(movie, imgid) {
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
        $(imgid).attr("src", imageUrl)
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
            'Token': localStorage.getItem("Token")
        },
        success: function(data) {
            $.each(data, function(index, value) {
                $("#expList").append("<li>" + value.nickName + "<ul><li>Firstname: " + value.firstName +
                    "</li><li>Lastname: " + value.lastName + "</li></ul></li>");
            });
            prepareList();
        },
        error: function(request, error) {
            alert("Get users gone wrong ");
        }

    });
}