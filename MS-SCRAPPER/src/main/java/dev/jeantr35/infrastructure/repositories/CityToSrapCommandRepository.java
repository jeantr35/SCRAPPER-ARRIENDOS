package dev.jeantr35.infrastructure.repositories;

import dev.jeantr35.domain.models.CityToScrapCommand;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.text.SimpleDateFormat;

@ApplicationScoped
public class CityToSrapCommandRepository implements PanacheMongoRepository<CityToScrapCommand> {

    //TODO: Hacer los querys necesarios para saber de cuales depende el comando
    public long countInRange(SimpleDateFormat date, String city, int maxPrice){
        return count("date = ?1 and city = ?2 and maxPrice >= ?3}", date, city, maxPrice);
    }

}
