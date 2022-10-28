package dev.jeantr35.infrastructure.repositories;

import dev.jeantr35.domain.models.ApartmentInfo;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApartmentRepository implements PanacheMongoRepository<ApartmentInfo> {

    public ApartmentInfo getMinValueApartment(String city){
        return find("city", Sort.by("price", Sort.Direction.Ascending), city).firstResult();
    }

    public ApartmentInfo getMaxValueApartment(String city){
        return find("city", Sort.by("price", Sort.Direction.Descending), city).firstResult();
    }
}
