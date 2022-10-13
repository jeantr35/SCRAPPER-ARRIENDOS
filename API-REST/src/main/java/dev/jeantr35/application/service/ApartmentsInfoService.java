package dev.jeantr35.application.service;

import com.rabbitmq.client.Channel;
import dev.jeantr35.domain.dto.CityToScrapDto;
import dev.jeantr35.infrastructure.utils.EventTopicExchangeProductorFactory;
import dev.jeantr35.infrastructure.utils.MapObjectToJson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static dev.jeantr35.infrastructure.utils.EventTopicExchangeProductorFactory.EXCHANGE;

@ApplicationScoped
public class ApartmentsInfoService {

    private static final String ROUTING_KEY_CITY_TO_SCRAP = "SCRAP-CITY";

    @Inject
    public EventTopicExchangeProductorFactory eventTopicExchangeProductorFactory;

    public Response sendNewCityToScrap(CityToScrapDto cityToScrapDto) throws IOException, TimeoutException {
        Channel channel = eventTopicExchangeProductorFactory.execute();
        byte[] JSON_BODY = MapObjectToJson.execute(cityToScrapDto).getBytes();
        channel.basicPublish(EXCHANGE, ROUTING_KEY_CITY_TO_SCRAP, null, JSON_BODY);
        return Response.ok().build();
    }

}
