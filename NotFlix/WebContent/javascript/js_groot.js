var movies;
var rankedMovies;
var loggedIn = false;
var movieontop;

$(document).ready(function() {
    $( "#dialog" ).dialog({
        autoOpen: false,
        width: 300,
        show: {
            effect: "explode",
            duration: 1000
          },
          hide: {
            effect: "explode",
            duration: 1000
          }
      });
      $( "#registerbutton" ).click(function() {
        $( "#dialog" ).dialog( "open" );
      });
    if (localStorage.getItem('nickname') !== null) {
        $("#loginform").hide().css("visibility", "hidden");
        $("#shownickname").empty().append("<p>Welkom " + localStorage.getItem('nickname') + "</p>").show().css("visibility", "visible");
        $("#logout").show().css("visibility", "visible");
        $("#registercontainer").show().css('visibility', 'hidden');
        $(".disabled").disabled = false;
        loggedIn = true;
    } else {
        $("#usersbutton").hide().css("visibility", "hidden");
    }
    getMovies();

    $("#loginbutton").click(function() {
        logIn();
    });

    $("#logoutbutton").click(function() {
        logOut();
    });
    $("#ratingbutton").click(function() {
        console.log("rating button clicked");
        doRating();
    });

    $(document).on("click", "#movielistitem", function(e) {
        var index = $(this).index();
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
    $("#registercontainer").show().css('visibility', 'visible');
    $("#usersbutton").hide().css("visibility", "hidden");
    window.location = "computer.html";
    loggedIn = false;
}

function setMovie(index) {
    var curmovies = movies;
    if (index > (movies.length - 1)) {
        index = index - movies.length;
        curmovies = unratedmovies;
    }
    $.each(curmovies, function(curIndex, value) {
        if (curIndex == index) {
            movieontop = value;
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
                $("#moviesummary").append(
                    '<div class="input-group">' +
                    '<span class="input-group-addon">Your rating</span>' +
                    '<input id="mymovierating" type="text" class="form-control" placeholder="Rating">' +
                    '<span class="input-group-btn"><button onclick="javascript: doRating()" id="ratingbutton" class="btn btn-default" type="button">Change!</button></span>' +
                    '</div>'
                );
                $("#mymovierating").val(value.rating);
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
        $("#registercontainer").hide().css('visibility', 'hidden');
        $(".disabled").disabled = false;
        loggedIn = true;
        $.each(data, function(index, value) {
            localStorage.setItem("Token", value);
        });
        location.reload();
    });
}

function getMovies() {
    if (!loggedIn) {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/NotFlix/resources/movies',
            dataType: "json"
        }).fail(function(jqXHR, textStatus) {
            alert("Get movies Request failed: " + textStatus);
        }).done(function(data) {
            movies = data;
            localStorage.setItem("movies", movies);
            $("#movielist").empty();
            $.each(data, function(index, value) {
                if (index == 0) {
                    movieontop = value;
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
                        $("#moviesummary").append(
                            '<div class="input-group">' +
                            '<span class="input-group-addon">Your rating</span>' +
                            '<input id="mymovierating" type="text" class="form-control" placeholder="Rating">' +
                            '<span class="input-group-btn"><button onclick="javascript: doRating()" id="ratingbutton" class="btn btn-default" type="button">Change!</button></span>' +
                            '</div>'
                        );
                        $("#mymovierating").val(value.rating);
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
    } else {
        $('#movielist').empty();
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/NotFlix/resources/ratings',
            dataType: "json",
            headers: {
                "Token": localStorage.getItem("Token")
            }
        }).fail(function(jqXHR, textStatus) {
            alert("Get movies Request failed: " + textStatus);
        }).done(function(data) {
            movies = data;
            var listindex = 0;
            $.each(data, function(index, value) {
                listindex = index;
                if (index == 0) {
                    movieontop = value;
                    console.log("Summary made");
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
                        $("#moviesummary").append(
                            '<div class="input-group">' +
                            '<span class="input-group-addon">Your rating</span>' +
                            '<input id="mymovierating" type="text" class="form-control" placeholder="Rating">' +
                            '<span class="input-group-btn"><button onclick="javascript: doRating()" id="ratingbutton" class="btn btn-default" type="button">Change!</button></span>' +
                            '</div>'
                        );
                        $("#mymovierating").val(value.rating);
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
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/NotFlix/resources/ratings/unrated',
                dataType: "json",
                headers: {
                    "Token": localStorage.getItem("Token")
                }
            }).fail(function(jqXHR, textStatus) {
                alert("Get movies Request failed: " + textStatus);
            }).done(function(data) {
                unratedmovies = data;
                listindex++;
                $.each(data, function(index, value) {
                    listindex = listindex + index;
                    if (index == 0) {
                        movieontop = value;
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
                            $("#moviesummary").append(
                                '<div class="input-group">' +
                                '<span class="input-group-addon">Your rating</span>' +
                                '<input id="mymovierating" type="text" class="form-control" placeholder="Rating">' +
                                '<span class="input-group-btn"><button id="ratingbutton" onclick="javascript: doRating()" class="btn btn-default" type="button">Change!</button></span>' +
                                '</div>'
                            );
                            $("#mymovierating").val(value.rating);
                        }
                        getImageByMovie(value, "#movieimage");
                    }
                    $("#movielist").append(
                        '<tr class="list-group" id="movielistitem">' +
                        '<td> ' +
                        '<img class="listimage" id="listitemimage' + listindex + '" src=""/>' +
                        '</td>' +
                        '<td id="listitemtitle ">' +
                        '<h4 class="list - group - item - heading ">' + value.title + '</h4>' +
                        '</td>' +
                        '</tr>'
                    );
                    getImageByMovie(value, "#listitemimage" + listindex);
                });
            });
        });
    }
}

function getImageByMovie(movie, imgid) {
    var movieTitle = encodeURIComponent(movie.title);
    var movieYear = encodeURIComponent(movie.date);
    $.ajax({
        type: 'GET',
        url: 'http://www.omdbapi.com/?t=' + movieTitle + '&y=' + movieYear,
        dataType: "json"
    }).fail(function(jqXHR, textStatus) {
        alert("Get image Request failed: " + textStatus);
    }).done(function(data) {
        var imageUrl = data.Poster;
        $(imgid).attr("src", imageUrl)
    });
}

function register() {
    $.ajax({
        type: 'post',
        url: 'http://localhost:8080/NotFlix/resources/users',
        dataType: 'json',
        data: $("#registerform").serialize(),
        success: function(data) {
            $.each(data, function(index, value) {
            	
                alert("Geregistreerd");
                $( "#dialog" ).dialog( "close" );
            });
        },
        error: function(request, error) {
            alert("Nickname already exists!");
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
                if (value.insert === null) {
                    value.insert = "-";
                }
                $("#expList").append("<li class='list-group-item glyphicon glyphicon-chevron-down'>  " + value.nickName + "<ul><li>Firstname: " + value.firstName +
                    "</li><li>Lastname: " + value.lastName + "</li>" + "<li>Insert: " + value.insert + "</li></ul></li>");
            });
            prepareList();
        },
        error: function(request, error) {
            alert("Get users gone wrong ");
        }

    });
}

function doRating() {
    var prevrating = movieontop.rating;
    var request = 'put';
    if (typeof prevrating === 'undefined') {
        request = 'post';
    }
    var rating = $("#mymovierating").val();
    if (rating >= 1 && rating <= 10) {
        $.ajax({
            url: 'http://localhost:8080/NotFlix/resources/ratings',
            data: 'imdb=' + movieontop.imdb + '&rating=' + rating,
            headers: {
                'Token': localStorage.getItem("Token")
            },
            type: request,
            success: function(data) {
                getMovies();
            },
            error: function(request, error) {
                alert('fail: ' + error);
            }

        });
    } else {
        alert("not a good number");
    }
}