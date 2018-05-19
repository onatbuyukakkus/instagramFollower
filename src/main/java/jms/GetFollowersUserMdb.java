package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;

import core.Storage;
import core.InstagramUtility;

import java.util.List;

/**
 * This is a message driven bean which listens to queue that is provided in ActivationConfigProperty tag.
 *
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destination",
                propertyValue = "java:jboss/exported/GetFollowersUserQueue"
        )
})

public class GetFollowersUserMdb extends MessageReceiver {

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

        List<String> followers =  Storage.getUserFromUsername(username).getFollowers();
        String response = "";
        for(String follower : followers) {
            response += follower + "/";
        }
        if(response.length() > 0) {
            response = response.substring(0, response.length() - 1); //remove last character
        }
        return response;
    }
}
