package dev.jeantr35.domain.dto;

import dev.jeantr35.domain.models.ApartmentInfo;
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
public class BestChoicesDTO {
    private List<ApartmentInfo> apartmentInfoList;
}
