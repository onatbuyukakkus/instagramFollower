package rest;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.jms.Queue;
import javax.jms.ConnectionFactory;
import javax.annotation.Resource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import user.User;
import jms.MessageProducerJms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handle user rest services.
 *
 */

@Stateless
@Path("/user")
public class UserRestService {

    @Resource(mappedName = "java:jboss/exported/MyConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "java:jboss/exported/AddUserQueue")
    private Queue addUserQueue;

    @Resource(mappedName = "java:jboss/exported/LoginUserQueue")
    private Queue loginUserQueue;

    @Resource(mappedName = "java:jboss/exported/GetFollowersUserQueue")
    private Queue getFollowersUserQueue;

    @Resource(mappedName = "java:jboss/exported/GetFollowingsUserQueue")
    private Queue getFollowingsUserQueue;

    @Resource(mappedName = "java:jboss/exported/SetFollowersUserQueue")
    private Queue setFollowersUserQueue;

    @Resource(mappedName = "java:jboss/exported/SetFollowingsUserQueue")
    private Queue setFollowingsUserQueue;

    @Resource(mappedName = "java:jboss/exported/GetProfilePicQueue")
    private Queue getProfilePicQueue;

    /**
     * Add a user to system.
     *
     * @param username {@link String}
     * @param password {@link String}
     * @return Http response
     */
    @POST
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUser(@QueryParam("username") String username, @QueryParam("password") String password) {
        String message = "addUser/" + username + "/" + password;
        if (username == null || password == null) {
            return Response.status(400).entity("missing parameters").build();
        }
        try {
            String response = MessageProducerJms.sendMessage(message, connectionFactory, addUserQueue);
            JSONObject jO = new JSONObject();
            jO.accumulate("text", response);
            return Response.status(200).entity(jO.toString()).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {
        String message = "loginUser/" + username + "/" + password;
        if (username == null || password == null) {
            return Response.status(400).entity("missing parameters").build();
        }
        try {
            String response = MessageProducerJms.sendMessage(message, connectionFactory, loginUserQueue);
            JSONObject jO = new JSONObject();
            jO.accumulate("text", response);
            if(response.equals("login success")) {
                return Response.status(200).entity(jO.toString()).build();
            }
            else {
                return Response.status(404).entity(jO.toString()).build();
            }
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/getfollowers")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getFollowers(@QueryParam("username") String username) {
        String message = "getFollowers/" + username;
        if (username == null) {
            return Response.status(400).entity("missing parameters").build();
        }
        try {
            String response = MessageProducerJms.sendMessage(message, connectionFactory, getFollowersUserQueue);
            List<String> followerList = new ArrayList<String>(Arrays.asList(response.split("/")));

            JSONObject jO = new JSONObject();
            JSONArray jA = new JSONArray();
            for(String follower : followerList) {
                jO.accumulate("username", follower);
            }
            jA.put(jO);

            return Response.status(200).entity(jA.toString()).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/getfollowings")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getFollowings(@QueryParam("username") String username) {
        String message = "getFollowings/" + username;
        if (username == null) {
            return Response.status(400).entity("missing parameters").build();
        }
        try {
            String response = MessageProducerJms.sendMessage(message, connectionFactory, getFollowingsUserQueue);
            List<String> followingList = new ArrayList<String>(Arrays.asList(response.split("/")));

            JSONObject jO = new JSONObject();
            JSONArray jA = new JSONArray();
            for(String following : followingList) {
                jO.accumulate("username", following);
            }
            jA.put(jO);

            return Response.status(200).entity(jA.toString()).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/getfollowerupdates")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getFollowerUpdates(@QueryParam("username") String username) {
        String message = "setFollowers/" + username;
        if (username == null) {
            return Response.status(400).entity("missing parameters").build();
        }
        try {
            String response = MessageProducerJms.sendMessage(message, connectionFactory, setFollowersUserQueue);
            List<String> tempList = new ArrayList<String>(Arrays.asList(response.split("/")));
            int startIndex = tempList.indexOf("startedFollower");
            List<String> stoppedFollowerList = tempList.subList(0, startIndex);
            List<String> startedFollowerList = tempList.subList(startIndex, tempList.size());

            JSONObject jO1 = new JSONObject();
            JSONArray jA1 = new JSONArray();
            for(String follower : stoppedFollowerList) {
                if(!follower.equals("stoppedFollower")) {
                    jO1.accumulate("username", follower);
                }
            }
            jA1.put(jO1);

            JSONObject jO2 = new JSONObject();
            JSONArray jA2 = new JSONArray();
            for(String follower : startedFollowerList) {
                if(!follower.equals("startedFollower")) {
                    jO2.accumulate("username", follower);
                }
            }
            jA2.put(jO2);

            JSONObject mainObject = new JSONObject();
            mainObject.put("stoppedFollower", jA1);
            mainObject.put("startedFollower", jA2);

            return Response.status(200).entity(mainObject.toString()).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/getfollowingupdates")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getFollowingUpdates(@QueryParam("username") String username) {
        String message = "setFollowing/" + username;
        if (username == null) {
            return Response.status(400).entity("missing parameters").build();
        }
        try {
            String response = MessageProducerJms.sendMessage(message, connectionFactory, setFollowingsUserQueue);
            List<String> tempList = new ArrayList<String>(Arrays.asList(response.split("/")));
            int startIndex = tempList.indexOf("startedFollowing");
            List<String> stoppedFollowingList = tempList.subList(0, startIndex);
            List<String> startedFollowingList = tempList.subList(startIndex, tempList.size());

            JSONObject jO1 = new JSONObject();
            JSONArray jA1 = new JSONArray();
            for(String follower : stoppedFollowingList) {
                if(!follower.equals("stoppedFollowing")) {
                    jO1.accumulate("username", follower);
                }
            }
            jA1.put(jO1);

            JSONObject jO2 = new JSONObject();
            JSONArray jA2 = new JSONArray();
            for(String follower : startedFollowingList) {
                if(!follower.equals("startedFollowing")) {
                    jO2.accumulate("username", follower);
                }
            }
            jA2.put(jO2);

            JSONObject mainObject = new JSONObject();
            mainObject.put("stoppedFollowing", jA1);
            mainObject.put("startedFollowing", jA2);

            return Response.status(200).entity(mainObject.toString()).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/getprofilepic")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getProfilePic(@QueryParam("username") String username) {
        String message = "setProfilePic/" + username;
        if (username == null) {
            return Response.status(400).entity("missing parameters").build();
        }
        try {
            String response = MessageProducerJms.sendMessage(message, connectionFactory, getProfilePicQueue);
            JSONObject jO = new JSONObject();
            jO.accumulate("profilepicurl", response);
            return Response.status(200).entity(jO.toString()).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}