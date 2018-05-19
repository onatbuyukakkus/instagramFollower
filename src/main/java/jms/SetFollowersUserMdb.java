package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;

import core.Storage;
import core.InstagramUtility;
import user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a message driven bean which listens to queue that is provided in ActivationConfigProperty tag.
 *
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destination",
                propertyValue = "java:jboss/exported/SetFollowersUserQueue"
        )
})

public class SetFollowersUserMdb extends MessageReceiver {

    /**
     * Handles received message from REST on form "messagetype"/username".
     * Calls the message producer to check if user exists, then either add
     * it to the storage or not.
     * @param receivedInput {@link String}[]
     * @return Response to the REST service
     */
    @Override
    public String parseMessage(String[] receivedInput) {
        String username = receivedInput[1];
        InstagramUtility instagram = new InstagramUtility();

        User user =  Storage.getUserFromUsername(username);
        String password = user.getPassword();
        instagram.login(username, password);
        List<String> instagramFollowers = new ArrayList<String>(instagram.getFollowers());
        List<String> currentFollowers = new ArrayList<String>(user.getFollowers());

        List<String> stoppedFollower = new ArrayList<String>(currentFollowers);
        stoppedFollower.removeAll(instagramFollowers);

        List<String> startedFollower = new ArrayList<String>(instagramFollowers);
        startedFollower.removeAll(currentFollowers);

        user.setFollowers(instagramFollowers);

        String response = "stoppedFollower/";
        for(String follower : stoppedFollower) {
            response += follower + "/";
        }
        response += "startedFollower/";
        for(String follower : startedFollower) {
            response += follower + "/";
        }

        if(response.length() > 0) {
            response = response.substring(0, response.length() - 1); //remove last character
        }
        return response;
    }
}
