$(document).ready(function(){
    $("#sign-up-form").submit(function(event) { // All the information about user is got from the fields.
        event.preventDefault();
        var username = $("#username").val(); //Name of the user
        var password = $("#password").val(); //Surname of the user
        $.ajax({
            type: "POST",
            url: "http://139.59.135.39:9090/instagram-follower/rest/user/add?username=" + username + "&password=" + password,
            success: function(response) {
                var result = JSON.parse(response);
                if(result.text == "new user created") {
                    $("#response_message").html("<p class=\"success-message\">" + result.text + "</p>");
                }
                else {
                    $("#response_message").html("<p class=\"success-message\" style='color:red'>" + result.text + "</p>");
                }
            },
            error: function(xhr) {
                $("#response_message").html("<p class=\"fail-message\" style='color:red'>server error</p>");
            }
        });
    });
});
