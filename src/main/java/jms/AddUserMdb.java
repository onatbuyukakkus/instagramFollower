package jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;

import core.Storage;

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
        return Storage.addUser(receivedInput);
    }
}
