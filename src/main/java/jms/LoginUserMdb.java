package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;

import core.Storage;
import core.InstagramUtility;

/**
 * This is a message driven bean which listens to queue that is provided in ActivationConfigProperty tag.
 *
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destination",
                propertyValue = "java:jboss/exported/LoginUserQueue"
        )
})

public class LoginUserMdb extends MessageReceiver {

    /**
     * Handles received message from REST on form "messagetype"/username/password".
     * Calls the message producer to check if user exists, then either add
     * it to the storage or not.
     * @param receivedInput {@link String}[]
     * @return Response to the REST service
     */
    @Override
    public String parseMessage(String[] receivedInput) {
        String username = receivedInput[1];
        String password = receivedInput[2];

        if(Storage.checkUser(username, password)) {
            return "login success";
        }
        else {
            return "your credentials are wrong";
        }
    }
}
