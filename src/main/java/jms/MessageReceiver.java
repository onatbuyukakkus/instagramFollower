package jms;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Superclass of message driven beans.
 *
 */

public abstract class MessageReceiver implements MessageListener {
    @Resource(lookup = "java:jboss/exported/MyConnectionFactory")
    ConnectionFactory connectionFactory;

    /**
     * Handling a received message from JMP Producer.
     * @param message that is read {@link Message}
     */
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;

            try {
                String[] receivedInput = textMessage.getText().split("/");
                String response = parseMessage(receivedInput);
                if (message.getJMSReplyTo() != null) {
                    QueueSession session = setUpSession();

                    MessageProducer producer = session.createProducer(message.getJMSReplyTo());
                    message = session.createTextMessage(response);
                    producer.send(message);
                }

            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

    /**
     * Handling the message depending on the message type.
     * @param receivedInput Allowed object {@link String}[]
     * @return Possible object {@link QueueSession}
     */
    public abstract String parseMessage(String[] receivedInput);

    /**
     * Setting up a connection to the queue.
     * @return Possible object {@link Session}
     * @throws JMSException
     */
    public QueueSession setUpSession() throws JMSException {
        QueueConnection qc = (QueueConnection) connectionFactory.createConnection();
        QueueSession session = qc.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        return session;
    }
}
