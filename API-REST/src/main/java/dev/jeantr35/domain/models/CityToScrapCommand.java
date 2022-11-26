package dev.jeantr35.domain.models;

import dev.jeantr35.domain.dto.CityToScrapDto;
import dev.jeantr35.domain.enums.ScrappingStatus;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;


import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@MongoEntity
@ToString
public class CityToScrapCommand extends CityToScrapDto {

    private ObjectId id;
    private List<ApartmentInfo> apartmentInfoList;
    private ScrappingStatus status;
    private Date date;

}
