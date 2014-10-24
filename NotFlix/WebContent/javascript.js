var token;

$(document).on('pageinit','#moviespage',function(){
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/NotFlix/resources/users',
        headers:{"Token":token},
        dataType: "json"
    }).fail(function(jqXHR, textStatus) {
        alert("Get movies Request failed: " + textStatus);
    }).done(function(data) {
        $.each(data, function(index, value) {
            $("#movielist").append("<li>" + value.nickName + "</li>");
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
                    		$.mobile.changePage("#moviespage");
                    	})
                    },
                    error: function (request,error) {
                        // This callback function will trigger on unsuccessful action               
                        alert('Invalid username or password.');
                    }
                });                  
        } else {
            alert('Please fill all necessary fields');
        }          
        return false; // cancel original event to prevent form submitting
    });   
});