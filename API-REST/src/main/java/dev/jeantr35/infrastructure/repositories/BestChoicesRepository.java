package dev.jeantr35.infrastructure.repositories;

import dev.jeantr35.domain.dto.BestChoicesDTO;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BestChoicesRepository implements PanacheMongoRepository<BestChoicesDTO> {
}
