$(document).ready(function(){
    $("#sign-in-form").submit(function(event) { // All the information about user is got from the fields.
        event.preventDefault();
        var username = $("#username").val(); //Name of the user
        var password = $("#password").val(); //Surname of the user
        $.ajax({
            type: "POST",
            url: "http://139.59.135.39:9090/instagram-follower/rest/user/login?username=" + username + "&password=" + password,
            success: function(response) {
                var result = JSON.parse(response);
                localStorage.setItem("username",username);
                window.location.href = "/instagram-follower/dashboard";
            },
            error: function(xhr) {
                $("#response_message").html("<p class=\"fail-message\" style='color:red'>your credentials are wrong</p>");
            }
        });
    });
});
