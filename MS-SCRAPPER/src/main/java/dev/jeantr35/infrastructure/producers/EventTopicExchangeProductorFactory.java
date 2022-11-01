package dev.jeantr35.infrastructure.producers;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
public class EventTopicExchangeProductorFactory {

    public static final String EXCHANGE = "Eventos";
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private Channel channel;
    private Connection connection;

    public Channel execute() throws IOException, TimeoutException{
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);
            return channel;
            //Routing-key -> word.word2.word3
    }

}
