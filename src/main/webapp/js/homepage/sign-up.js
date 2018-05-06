$(document).ready(function(){
    $("#sign-up-form").submit(function(event) { // All the information about user is got from the fields.
        event.preventDefault();
        var username = $("#username").val(); //Name of the user
        var password = $("#password").val(); //Surname of the user
        $.ajax({
            type: "POST",
            url: "http://139.59.135.39:9090/instagram-follower-1.0-SNAPSHOT/rest/user/add?username=" + username + "&password=" + password,
            success: function(response){
                console.log(response);
                console.log(response.text]);
                $("#response_message").html("</br><b>" + response.text + "</b>");
            },
            error: function(xhr) {
                $("#response_message").html("</br><b style='color:red'>server error</b>");
            }
        });
    });
});
