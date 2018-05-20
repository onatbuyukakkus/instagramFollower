package core;

import org.brunocvcunha.instagram4j.*;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowingRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetUserFollowersResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserSummary;

import java.util.ArrayList;
import java.util.List;

public class InstagramUtility {

    Instagram4j instagram;

    public String login(String username, String password) {
        this.instagram = Instagram4j.builder().username(username).password(password).build();
        instagram.setup();
        try {
            return instagram.login().toString();
        }
        catch (Exception e) {
            return null;
        }
    }

    public List<String> getFollowers(/*String username, String password*/) {
        //login(username, password);
        List<String> followerUsernames = new ArrayList<String>();
        try {
            InstagramGetUserFollowersResult followers = this.instagram.sendRequest(new InstagramGetUserFollowersRequest(instagram.getUserId()));
            List<InstagramUserSummary> users = followers.getUsers();
            for (InstagramUserSummary user : users) {
                followerUsernames.add(user.getUsername());
            }
        }
        catch (Exception e) {

        }
        return followerUsernames;
    }

    public List<String> getFollowings(/*String username, String password*/) {
        //login(username, password);
        List<String> followingUsernames = new ArrayList<String>();
        try {
            InstagramGetUserFollowersResult followers = this.instagram.sendRequest(new InstagramGetUserFollowingRequest(instagram.getUserId()));
            List<InstagramUserSummary> users = followers.getUsers();
            for (InstagramUserSummary user : users) {
                followingUsernames.add(user.getUsername());
            }
        }
        catch (Exception e) {

        }
        return followingUsernames;
    }

    public String getProfilePictureUrl(/*String username, String password*/) {
        //login(username, password);
        String result = "";
        try {
            InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(instagram.getUsername()));
            result = userResult.getUser().profile_pic_url;
        }
        catch (Exception e) {

        }
        return result;
    }
}
