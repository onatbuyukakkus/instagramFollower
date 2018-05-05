package jms;

import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 */
@Stateless
public class MessageProducerJms {

    /**
     * Producing and sending a message to queue.
     * @param inputMessage      The message to be sent. Allowed object {@link String}
     * @param connectionFactory Allowed object {@link ConnectionFactory}
     * @param queue				The queue to receive the message. Allowed object {@link Queue}
     * @return					The text of the message. Possible object is {@link String}
     * @throws JMSException
     */
    public static String sendMessage(String inputMessage, ConnectionFactory connectionFactory, Queue queue) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(queue);
        TextMessage message = session.createTextMessage(inputMessage);

        // create a temporary queue and send it with the message for the receiver to reply to
        Destination callbackQueue = session.createTemporaryQueue();
        message.setJMSReplyTo(callbackQueue);

        producer.send(message);
        producer.close();

        // Waiting for the response
        MessageConsumer consumer = session.createConsumer(callbackQueue);
        connection.start();
        message = (TextMessage) consumer.receive();
        connection.stop();
        consumer.close();

        session.close();
        connection.close();

        return message.getText();
    }
}