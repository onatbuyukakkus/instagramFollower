function getProfilePic(username) { //This function gets all attendance data of a student from the Rest services of EduSys
    return $.ajax({
        type: "GET",
        url: "http://139.59.135.39:9090/instagram-follower/rest/user/getprofilepic?username=" + username,
        async: false  // This option prevents this function to execute asynchronized
    });
}

function getFollowers(username) { //This function gets all attendance data of a student from the Rest services of EduSys
    return $.ajax({
        type: "GET",
        url: "http://139.59.135.39:9090/instagram-follower/rest/user/getfollowers?username=" + username,
        async: false  // This option prevents this function to execute asynchronized
    });
}

function getFollowings(username) { //This function gets all attendance data of a student from the Rest services of EduSys
    return $.ajax({
        type: "GET",
        url: "http://139.59.135.39:9090/instagram-follower/rest/user/getfollowings?username=" + username,
        async: false  // This option prevents this function to execute asynchronized
    });
}

function getFollowerUpdates(username) { //This function gets all attendance data of a student from the Rest services of EduSys
    return $.ajax({
        type: "GET",
        url: "http://139.59.135.39:9090/instagram-follower/rest/user/getfollowerupdates?username=" + username,
        async: false  // This option prevents this function to execute asynchronized
    });
}

function getFollowingUpdates(username) { //This function gets all attendance data of a student from the Rest services of EduSys
    return $.ajax({
        type: "GET",
        url: "http://139.59.135.39:9090/instagram-follower/rest/user/getfollowingupdates?username=" + username,
        async: false  // This option prevents this function to execute asynchronized
    });
}

$(window).on('load', function() {
    // Animate loader off screen
    $(".myModal2").fadeOut("slow");
});

$(document).ready(function(){
    var username = localStorage.getItem("username");
    $('#username').html(username);

    var profilePictureUrl = JSON.parse(getProfilePic(username).responseText);
    var img = document.getElementById("profile-pic");
    img.src = String(profilePictureUrl.profilepicurl);


    //FOLLOWERS

    var followerUpdates = JSON.parse(getFollowerUpdates(username).responseText);
    var startedFollowers = [];
    if(typeof followerUpdates.startedFollower[0].username != "undefined") {
        if(typeof followerUpdates.startedFollower[0].username != "string") {
            for(i=0; i<followerUpdates.startedFollower[0].username.length; i++) {
                startedFollowers.push(followerUpdates.startedFollower[0].username[i]);
            }
        }
        else {
            startedFollowers.push(followerUpdates.startedFollower[0].username);
        }
    }
    var startedFollowerHTML = "";
    for(i=0; i<startedFollowers.length-1; i++) {
        startedFollowerHTML += startedFollowers[i] + "<br>";
    }
    startedFollowerHTML += startedFollowers[startedFollowers.length-1];

    var stoppedFollowers = [];
    if(typeof followerUpdates.stoppedFollower[0].username != "undefined") {
        if(typeof followerUpdates.stoppedFollower[0].username != "string") {
            for (i = 0; i < followerUpdates.stoppedFollower[0].username.length; i++) {
                stoppedFollowers.push(followerUpdates.stoppedFollower[0].username[i]);
            }
        }
        else {
            stoppedFollowers.push(followerUpdates.stoppedFollower[0].username);
        }
    }
    var stoppedFollowingHTML = "";
    for(i=0; i<stoppedFollowers.length-1; i++) {
        stoppedFollowingHTML += stoppedFollowers[i] + "<br>";
    }
    stoppedFollowingHTML += stoppedFollowers[stoppedFollowers.length-1] + "<hr>";

    var followersList = JSON.parse(getFollowers(username).responseText);
    var followers = [];
    if(typeof followersList[0] != "undefined") {
        if(typeof followersList[0].username != "string") {
            for(i=0; i<followersList[0].username.length; i++) {
                followers.push(followersList[0].username[i]);
            }
        }
        else {
            followers.push(followersList[0].username)
        }
    }

    var resultFollowerHTML = "<strong>Followers: </strong>" + followers.length + "<hr>" +
                    "<strong>Stopped Followers: </strong>" + stoppedFollowers.length;
    if(stoppedFollowers.length == 0) {
        resultFollowerHTML += "<hr>"
    }
    else {
        resultFollowerHTML += "<br>" + stoppedFollowingHTML;
    }
    resultFollowerHTML += "<strong>Started Followers: </strong>" + startedFollowers.length;
    if(startedFollowers.length != 0) {
        resultFollowerHTML += "<br>" + startedFollowerHTML;
    }
    $('#follower-info').html(resultFollowerHTML);

    
    
    
    //FOLLOWINGS

    var followingUpdates = JSON.parse(getFollowingUpdates(username).responseText);
    var startedFollowings = [];
    if(typeof followingUpdates.startedFollowing[0].username != "undefined") {
        if(typeof followingUpdates.startedFollowing[0].username != "string") {
            for(i=0; i<followingUpdates.startedFollowing[0].username.length; i++) {
                startedFollowings.push(followingUpdates.startedFollowing[0].username[i]);
            }
        }
        else {
            startedFollowings.push(followingUpdates.startedFollowing[0].username);
        }
    }
    var startedFollowingHTML = "";
    for(i=0; i<startedFollowings.length-1; i++) {
        startedFollowingHTML += startedFollowings[i] + "<br>";
    }
    startedFollowingHTML += startedFollowings[startedFollowings.length-1];

    var stoppedFollowings = [];
    if(typeof followingUpdates.stoppedFollowing[0].username != "undefined") {
        if(typeof followingUpdates.stoppedFollowing[0].username != "string") {
            for (i = 0; i < followingUpdates.stoppedFollowing[0].username.length; i++) {
                stoppedFollowings.push(followingUpdates.stoppedFollowing[0].username[i]);
            }
        }
        else {
            stoppedFollowings.push(followingUpdates.stoppedFollowing[0].username);
        }
    }
    var stoppedFollowingHTML = "";
    for(i=0; i<stoppedFollowings.length-1; i++) {
        stoppedFollowingHTML += stoppedFollowings[i] + "<br>";
    }
    stoppedFollowingHTML += stoppedFollowings[stoppedFollowings.length-1] + "<hr>";

    var followingsList = JSON.parse(getFollowings(username).responseText);
    var followings = [];
    if(typeof followingsList[0] != "undefined") {
        if(typeof followingsList[0].username != "string") {
            for(i=0; i<followingsList[0].username.length; i++) {
                followings.push(followingsList[0].username[i]);
            }
        }
        else {
            followings.push(followingsList[0].username)
        }
    }

    var resultFollowingHTML = "<strong>Followings: </strong>" + followings.length + "<hr>" +
        "<strong>Stopped Followings: </strong>" + stoppedFollowings.length;
    if(stoppedFollowings.length == 0) {
        resultFollowingHTML += "<hr>"
    }
    else {
        resultFollowingHTML += "<br>" + stoppedFollowingHTML;
    }
    resultFollowingHTML += "<strong>Started Followings: </strong>" + startedFollowings.length;
    if(startedFollowings.length != 0) {
        resultFollowingHTML += "<br>" + startedFollowingHTML;
    }
    $('#following-info').html(resultFollowingHTML);
});
