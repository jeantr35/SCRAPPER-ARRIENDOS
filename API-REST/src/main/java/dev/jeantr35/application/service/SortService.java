package dev.jeantr35.application.service;

import dev.jeantr35.domain.dto.BestChoicesDTO;
import dev.jeantr35.domain.models.ApartmentInfo;
import dev.jeantr35.domain.models.CityToScrapCommand;
import dev.jeantr35.infrastructure.repositories.CityToSrapCommandRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@ApplicationScoped
public class SortService {

    @Inject
    public CityToSrapCommandRepository cityToSrapCommandRepository;

    private final ArrayList<Comparator<ApartmentInfo>> comparators = new ArrayList<>();

    // 1. customer price comparator
    private static final Comparator<ApartmentInfo> priceComparatorMRef = Comparator
            .comparing(ApartmentInfo::getTotalPrice);

    // 2. customer bedrooms comparator
    private static final Comparator<ApartmentInfo> bedroomsComparatorMRef = Comparator
            .comparing(ApartmentInfo::getBedrooms).reversed();

    // 3. customer area comparator
    private static final Comparator<ApartmentInfo> areaComparatorMRef = Comparator
            .comparing(ApartmentInfo::getArea).reversed();

    // 3. customer area comparator
    private static final Comparator<ApartmentInfo> toiletsComparatorMRef = Comparator
            .comparing(ApartmentInfo::getToilets).reversed();



    public Response executeSort(String id){
        createComparator();
        CityToScrapCommand cityToScrapCommand = cityToSrapCommandRepository.findById(new ObjectId(id));
        final Comparator<ApartmentInfo>[] apartmentInfoComparator = new Comparator[]{comparators.stream().findFirst().isPresent() ? comparators.stream().findFirst().get() : null};
        if(apartmentInfoComparator[0] == null) return Response.ok().status(Response.Status.BAD_REQUEST).build();
        final BestChoicesDTO bestChoicesDTO = new BestChoicesDTO();
        comparators.forEach(comparator -> {
            apartmentInfoComparator[0] = apartmentInfoComparator[0].thenComparing(comparator);
            bestChoicesDTO.setApartmentInfoList(cityToScrapCommand.getApartmentInfoList().stream().sorted(apartmentInfoComparator[0]).
                    limit(5).collect(Collectors.toList()));
                });
        return Response.ok(bestChoicesDTO).build();
    }

    private void createComparator() {
        comparators.add(bedroomsComparatorMRef);
        comparators.add(priceComparatorMRef);
        comparators.add(toiletsComparatorMRef);
        comparators.add(areaComparatorMRef);
    }

}
