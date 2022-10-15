package dev.jeantr35.infrastructure.consumers;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dev.jeantr35.aplication.usecase.WebScrapper;
import dev.jeantr35.domain.dto.CityToScrapDto;
import dev.jeantr35.infrastructure.utils.Mapper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public class EventTopicExchangeConsumer {

    public static final String EXCHANGE = "Eventos";
    private static final String ROUTING_KEY_CITY_TO_SCRAP = "SCRAP-CITY";
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private Channel channel;
    private Connection connection;

    public EventTopicExchangeConsumer() {
    }

    public void setupCityToScrap() throws IOException, TimeoutException {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);
            String queueName = channel.queueDeclare().getQueue();
            //Routing-key -> word.word2.word3
            // * -> identifica una palabra
            // # -> identifica multiples palabras delimitadas por por .
            // eventos tenis -> *.tenis.*
            // eventos en España -> es.*.*
            channel.queueBind(queueName, EXCHANGE, ROUTING_KEY_CITY_TO_SCRAP);
            channel.basicConsume(queueName, true, (consumerTag, message) -> {
                String messageBody = new String(message.getBody(), Charset.defaultCharset());
                CityToScrapDto cityToScrapDto = Mapper.mapJsonToCityToScrap(messageBody);
                WebScrapper.getInfoFrom(cityToScrapDto);
                System.out.println("Mensaje: " + messageBody);
            }, (consumerTag) -> {
                System.out.println("Consumidor: " + consumerTag + " cancelado");
            });

    }

}
