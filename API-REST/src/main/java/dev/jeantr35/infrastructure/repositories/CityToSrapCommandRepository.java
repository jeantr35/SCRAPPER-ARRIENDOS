package dev.jeantr35.infrastructure.repositories;

import dev.jeantr35.domain.models.CityToScrapCommand;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CityToSrapCommandRepository implements PanacheMongoRepository<CityToScrapCommand> {

}
