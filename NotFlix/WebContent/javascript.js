$(document).ready(function() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/NotFlix/resources/movies',
        dataType: "json"
    }).fail(function(jqXHR, textStatus) {
        alert("Get movies Request failed: " + textStatus);
    }).done(function(data) {
        $.each(data, function(index, value) {
            $("#movielist").append("<li>" + value.title + "</li>");
        });
        $("#movielist").listview("refresh");
    });
});

$(function() {
    $("#login").click(function() {
        alert($("#loginform").serialize());
        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/NotFlix/resources/users/login',
            dataType: "json",
            data: $("#loginform").serialize()
        }).fail(function(jqXHR, textStatus) {
            alert("Post login Request failed: " + textStatus);

        }).done(function(data) {
            $.each(data, function(index, value) {
                alert(value);
                $.mobile.changePage("#moviespage");
            });
        });
    });
});