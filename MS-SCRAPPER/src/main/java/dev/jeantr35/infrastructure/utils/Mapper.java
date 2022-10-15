package dev.jeantr35.infrastructure.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jeantr35.domain.dto.CityToScrapDto;

import java.io.IOException;

public class Mapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static CityToScrapDto mapJsonToCityToScrap(String json) throws IOException {
        return objectMapper.readValue(json, CityToScrapDto.class);
    }

}
