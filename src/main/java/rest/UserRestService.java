package rest;

import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;
import javax.jms.Queue;
import javax.jms.ConnectionFactory;
import javax.annotation.Resource;

import user.User;
import jms.MessageProducerJms;

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

    /**
     * Add a user to system.
     * @param user {@link User}
     * @return Http response
     */
    @POST
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUser(@QueryParam("username") String username, @QueryParam("password") String password) {
        String message = "addUser/" + username + "/" + password;
        if(username == null || password == null) {
            return Response.status(400).entity("Missing parameters.").build();
        }
        try {
            String response = MessageProducerJms.sendMessage(message, connectionFactory, addUserQueue);
            return Response.status(200).entity(response).build();
        }
        catch(Exception e) {
            return Response.serverError().build();
        }
    }
}