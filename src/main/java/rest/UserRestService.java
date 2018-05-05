package rest;

import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
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
    @Consumes({ "application/json" })
    public Response addUser(User user) {
        String message = "addUser/" + user.getUsername() + "/" + user.getPassword();
        if(user.getUsername() == null || user.getPassword() == null) {
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
