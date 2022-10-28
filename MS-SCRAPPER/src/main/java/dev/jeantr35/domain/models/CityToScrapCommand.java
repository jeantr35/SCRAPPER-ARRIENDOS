package dev.jeantr35.domain.models;

import dev.jeantr35.domain.dto.CityToScrapDto;
import dev.jeantr35.domain.enums.ScrapCommandStatus;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@MongoEntity
public class CityToScrapCommand extends CityToScrapDto {

    private ScrapCommandStatus status;
    private List<ObjectId> dependencies;
    private SimpleDateFormat date;

    public CityToScrapCommand(CityToScrapDto cityToScrapDto) {
        super(cityToScrapDto.getCity(), cityToScrapDto.getEmailToNotify(), cityToScrapDto.getWebsite(), cityToScrapDto.getMinPrice(), cityToScrapDto.getMaxPrice());
        this.status = ScrapCommandStatus.IN_PROGRESS;
        this.dependencies = new ArrayList<>();
        this.date = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void addDependency(ObjectId dependency){
        getDependencies().add(dependency);
    }

}
