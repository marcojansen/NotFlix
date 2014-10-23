$(document).ready(function() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/NotFlix/resources/movies',
        dataType: "json"
    }).fail(function(jqXHR, textStatus) {
        alert("API Request failed: " + textStatus);
    }).done(function(data) {
        $.each(data, function(index, value) {
            $("#movies").append("<li>" + value.title + "</li>").listview("refresh");
        });
    });
});