$(document).ready(function() {
	if (localStorage.getItem('nickname') !== null) {
		$("#loginform").hide().css("visibility", "hidden");
		$("#shownickname").empty().append("<p>Welkom " + localStorage.getItem('nickname') + "</p>").show().css("visibility", "visible");
	}
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/NotFlix/resources/movies',
        dataType: "json"
    }).fail(function(jqXHR, textStatus) {
        alert("Get movies Request failed: " + textStatus);
    }).done(function(data) {

    });
    $("#loginbutton").click(function() {
        logIn();
    });
});


function logOut() {
	localStorage.clear();
	$("#loginform").show().css("visibility", "visible");
	$("#shownickname").hide().css("visibility", "hidden");
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
		headers: {'Token':localStorage.getItem("Token")},
		success: function(data) {
			$.each(data, function(index,value) {
				// Do something with user objects ( value  equals user object)
			});
		}
		error: function(request,error) {
			alert("Get users gone wrong");
		}
		
	});
}



