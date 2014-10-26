var token;
var movies;
var movie;

$(document).on('pageinit','#moviespage',function(){
    $(document).on('click','#submitrating', function() {
    	console.log(movie.title + " " + movie.imdb + " " + $('#rating').val());
    });
    
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/NotFlix/resources/movies',
        dataType: "json"
    }).fail(function(jqXHR, textStatus) {
        alert("Get movies Request failed: " + textStatus);
    }).done(function(data) {
    	movies = data;
        $.each(data, function(index, value) {
            $("#movielist").append("<li> <a href=\"#\">" + value.title + "</a></li>");
        });
        $("#movielist").listview("refresh");
    });
});

$(document).on('pageinit', '#loginpage', function(){ 

    $(document).on('click', '#login', function() { // catch the form's submit event
        if($('#nickname').val().length > 0 && $('#password').val().length > 0){
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
                    success: function (data) {
                    	$.each(data, function(index,value) {
                    		token=value;
                    		$.mobile.changePage("#moviespage",{ transition:"flow"});
                    	})
                    },
                    error: function (request,error) {
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

$(document).on("click", "#movielist li" ,function (event) {
	var title = jQuery.trim($(this).text());
    $.each(movies, function(index,value) {
    	if (title === value.title) {
    		console.log(value);
    		movie = value;
    	}
    });
    $('#lnkDialog').click();
    $('#movietitle').text(movie.title);
    $('#rating').val(movie.averageRating);
    if (movie.averageRating == 0) {
    	$('#delete').closest('.ui-btn').hide();
    }
}); 

