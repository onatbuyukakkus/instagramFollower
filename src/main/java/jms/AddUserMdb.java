package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;

import core.Storage;
import core.InstagramUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a message driven bean which listens to queue that is provided in ActivationConfigProperty tag.
 *
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destination",
                propertyValue = "java:jboss/exported/AddUserQueue"
        )
})

public class AddUserMdb extends MessageReceiver {

    /**
     * Handles received message from REST on form "messagetype"/username/password".
     * Calls the message producer to check if user exists, then either add
     * it to the storage or not.
     * @param receivedInput {@link String}[]
     * @return Response to the REST service
     */
    @Override
    public String parseMessage(String[] receivedInput) {
        InstagramUtility instagram = new InstagramUtility();
        String username = receivedInput[1];
        String password = receivedInput[2];

        String loginSuccess = instagram.login(username, password);
        if(loginSuccess == null) {
            return "no instagram account with these credentials";
        }
        else {
            String storageSuccess = Storage.addUser(username, password);
            if(storageSuccess == "new user created") {
                Storage.addUserFollowers(username, instagram.getFollowers());
                Storage.addUserFollowings(username, instagram.getFollowings());
            }
            return storageSuccess;
        }
    }
}
