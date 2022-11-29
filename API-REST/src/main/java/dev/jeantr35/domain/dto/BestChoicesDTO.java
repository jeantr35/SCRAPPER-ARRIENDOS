package dev.jeantr35.domain.dto;

import dev.jeantr35.domain.models.ApartmentInfo;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@MongoEntity
public class BestChoicesDTO {
    private ObjectId id;
    private List<ApartmentInfo> apartmentInfoList;
}
