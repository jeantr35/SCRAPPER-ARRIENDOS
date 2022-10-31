package dev.jeantr35.domain.models;

import dev.jeantr35.domain.dto.CityToScrapDto;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@MongoEntity
@ToString
public class CityToScrapCommand extends CityToScrapDto {

    private ObjectId id;
    private List<ApartmentInfo> apartmentInfoList;

    public CityToScrapCommand(CityToScrapDto cityToScrapDto) {
        super(cityToScrapDto.getCity(), cityToScrapDto.getEmailToNotify(), cityToScrapDto.getWebsite(), cityToScrapDto.getMinPrice(), cityToScrapDto.getMaxPrice());
        this.apartmentInfoList = new ArrayList<>();
    }

    public void addApartmentInfo(ApartmentInfo apartmentInfo){
        getApartmentInfoList().add(apartmentInfo);
    }

}
