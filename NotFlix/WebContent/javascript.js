var token;
var movies;
var movie;

$(document).on('pageinit','#moviespage',function(){
    $(document).on('click','#submitrating', function() {
    	if (movie.averageRating == 0 ) {
    		postRating(movie.imdb, $('#rating').val());
    	} else {
    		putRating(movie.imdb, $('#rating').val());
    	}
    });
    $(document).on('click','#delete', function() {
    	deleteRating(movie.imdb);
    });
    getMovies();
   
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
	$('#delete').closest('.ui-btn').show();
	var title = jQuery.trim($(this).text());
    $.each(movies, function(index,value) {
    	if (title === value.title) {
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

function postRating(imdb,rating) {
	console.log(imdb+' ' + rating + ' ' + token);
	if (rating >= 1  && rating <= 10) {
		$.ajax({
			url: 'http://localhost:8080/NotFlix/resources/ratings',
			data: 'imdb=' + imdb + '&rating=' + rating,
			headers: {'Token':token},
			type: 'post',
			dataType: 'json',
			beforeSend : function() {
				$.mobile.showPageLoadingMsg(true);
			},
			complete: function() {
				$.mobile.hidePageLoadingMsg();
			},
			success: function (data) {
				getMovies();
			},
			error: function (request,error) {
				alert('fail: ' + error);
			}
			
		});
	}
}

function putRating(imdb,rating) {
	if (rating >= 1  && rating <= 10) {
		$.ajax({
			url: 'http://localhost:8080/NotFlix/resources/ratings',
			data: 'imdb=' + imdb + '&rating=' + rating,
			headers: {'Token':token},
			type: 'put',
			dataType: 'json',
			beforeSend : function() {
				$.mobile.showPageLoadingMsg(true);
			},
			complete: function() {
				$.mobile.hidePageLoadingMsg();
			},
			success: function (data) {
				getMovies();
			},
			error: function (request,error) {
				alert('fail: ' + error);
			}
			
		});
	}
}

function deleteRating(imdb) {
	$.ajax({
		url: 'http://localhost:8080/NotFlix/resources/ratings',
		data: 'imdb=' + imdb,
		headers: {'Token':token},
		type: 'delete',
		dataType:'json',
		beforeSend : function() {
			$.mobile.showPageLoadingMsg(true);
		},
		complete: function() {
			$.mobile.hidePageLoadingMsg();
		},
		success: function (data) {
			getMovies();
			$.mobile.back();
		},
		error: function (request,error) {
			alert('fail: ' + error);
		}
	});
}

function getMovies() {
	$('#movielist').empty();
	 $.ajax({
	        type: 'GET',
	        url: 'http://localhost:8080/NotFlix/resources/movies',
	        dataType: "json"
	    }).fail(function(jqXHR, textStatus) {
	        alert("Get movies Request failed: " + textStatus);
	    }).done(function(data) {
	    	movies = data;
	        $.each(data, function(index, value) {
	        	if (value.averageRating == 0) {
	        		$("#movielist").append("<li data-icon='star'> <a href=\"#\">" + value.title + "</a></li>");	        		
	        	} else {
	        		$("#movielist").append("<li data-icon='check'> <a href=\"#\">" + value.title + "</a></li>");
	        	}
	        });
	        $("#movielist").listview("refresh");
	    });
}

