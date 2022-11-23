package dev.jeantr35.application.service;

import dev.jeantr35.domain.models.ApartmentInfo;
import dev.jeantr35.domain.models.CityToScrapCommand;
import dev.jeantr35.infrastructure.repositories.CityToSrapCommandRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Comparator;
import java.util.stream.Collectors;

@ApplicationScoped
public class SortService {

    @Inject
    public CityToSrapCommandRepository cityToSrapCommandRepository;

    // 1. customer Name comparator
    Comparator<ApartmentInfo> priceComparatorMRef = Comparator
            .comparing(ApartmentInfo::getTotalPrice);

    // 2. customer City comparator
    Comparator<ApartmentInfo> bedroomsComparatorMRef = Comparator
            .comparing(ApartmentInfo::getBedrooms).reversed();

    // 3. customer Age comparator
    Comparator<ApartmentInfo> areaComparatorMRef = Comparator
            .comparing(ApartmentInfo::getArea).reversed();

    public Response executeSort(String id){
        CityToScrapCommand cityToScrapCommand = cityToSrapCommandRepository.findById(new ObjectId(id));
        cityToScrapCommand.setApartmentInfoList(cityToScrapCommand.getApartmentInfoList().stream().sorted(priceComparatorMRef.
                thenComparing(bedroomsComparatorMRef).thenComparing(areaComparatorMRef)).collect(Collectors.toList()));
        return Response.ok(cityToScrapCommand).build();
    }

}
