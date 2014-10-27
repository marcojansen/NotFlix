var movies;
var unratedmovies;
var movie;

$(document).on('pageinit', '#moviespage', function() {
    $(document).on('click', '#submitrating', function() {
        if (typeof movie.rating === 'undefined') {
            postRating(movie.imdb, $('#rating').val());
        } else {
            putRating(movie.imdb, $('#rating').val());
        }
    });
    $(document).on('click', '#delete', function() {
        deleteRating(movie.imdb);
    });
    getMovies();

});

$(document).on('pageinit', '#loginpage', function() {

    $(document).on('click', '#login', function() { // catch the form's submit event
        if ($('#nickname').val().length > 0 && $('#password').val().length > 0) {
            // Send data to server through the ajax call
            // action is functionality we want to call and outputJSON is our data
            $.ajax({
                url: 'http://localhost:8080/NotFlix/resources/users/login',
                data: $('#loginform').serialize(),
                type: 'post',
                dataType: 'json',
                beforeSend: function() {
                    // This callback function will trigger before data is sent
                    $.mobile.showPageLoadingMsg(true); // This will show ajax spinner
                },
                complete: function() {
                    // This callback function will trigger on data sent/received complete
                    $.mobile.hidePageLoadingMsg(); // This will hide ajax spinner
                },
                success: function(data) {
                    $.each(data, function(index, value) {
                        localStorage.setItem("Token", value);
                        $.mobile.changePage("#moviespage", {
                            transition: "flow"
                        });
                    })
                },
                error: function(request, error) {
                    // This callback function will trigger on unsuccessful action               
                    alert("Wrong username or password!");
                }
            });
        } else {
            alert('Please fill all necessary fields');
        }
        return false; // cancel original event to prevent form submitting
    });
});

$(document).on("click", "#movielist li", function(event) {
    $('#delete').closest('.ui-btn').show();
    var title = jQuery.trim($(this).text());
    $.each(movies, function(index, value) {
        if (title === value.title) {
            movie = value;
        }
    });
    $.each(unratedmovies, function(index, value) {
        if (title === value.title) {
            movie = value;
        }
    });
    $('#lnkDialog').click();
    $('#movietitle').text(movie.title);
    if (typeof movie.rating === 'undefined') {
        $('#delete').closest('.ui-btn').hide();
        $('#rating').val("");
    } else {
        $('#rating').val(movie.rating);
    }
});

function postRating(imdb, rating) {
    if (rating >= 1 && rating <= 10) {
        $.ajax({
            url: 'http://localhost:8080/NotFlix/resources/ratings',
            data: 'imdb=' + imdb + '&rating=' + rating,
            headers: {
                'Token': localStorage.getItem("Token")
            },
            type: 'post',
            dataType: 'json',
            beforeSend: function() {
                $.mobile.showPageLoadingMsg(true);
            },
            complete: function() {
                $.mobile.hidePageLoadingMsg();
            },
            success: function(data) {
                getMovies();
            },
            error: function(request, error) {
                alert('fail: ' + error);
            }

        });
    }
}

function putRating(imdb, rating) {
    if (rating >= 1 && rating <= 10) {
        $.ajax({
            url: 'http://localhost:8080/NotFlix/resources/ratings',
            data: 'imdb=' + imdb + '&rating=' + rating,
            headers: {
                'Token': localStorage.getItem("Token")
            },
            type: 'put',
            dataType: 'json',
            beforeSend: function() {
                $.mobile.showPageLoadingMsg(true);
            },
            complete: function() {
                $.mobile.hidePageLoadingMsg();
            },
            success: function(data) {
                getMovies();
            },
            error: function(request, error) {
                alert('fail: ' + error);
            }

        });
    }
}

function deleteRating(imdb) {
    $.ajax({
        url: 'http://localhost:8080/NotFlix/resources/ratings',
        data: 'imdb=' + imdb,
        headers: {
            'Token': localStorage.getItem("Token")
        },
        type: 'delete',
        dataType: 'json',
        beforeSend: function() {
            $.mobile.showPageLoadingMsg(true);
        },
        complete: function() {
            $.mobile.hidePageLoadingMsg();
        },
        success: function(data) {
            getMovies();
            $.mobile.back();
        },
        error: function(request, error) {
            alert('fail: ' + error);
        }
    });
}

function getMovies() {
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
        $.each(data, function(index, value) {
            $("#movielist").append("<li data-icon='check'> <a href=\"#\">" + value.title + "</a></li>");
        });
        $("#movielist").listview("refresh");
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
        $.each(data, function(index, value) {
            $("#movielist").append("<li data-icon='star'> <a href=\"#\">" + value.title + "</a></li>");
        });
        $("#movielist").listview("refresh");
    });
}