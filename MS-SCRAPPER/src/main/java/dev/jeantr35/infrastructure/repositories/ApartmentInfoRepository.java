package dev.jeantr35.infrastructure.repositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import dev.jeantr35.domain.models.ApartmentInfo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ApartmentInfoRepository {

    private static final String MONGODB_URL = System.getenv("MONGODB_STRING");
    private static final CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    private static final CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    public static final int NOT_INFO_FOUND_INT = 99999999;

    public static MongoCollection<ApartmentInfo> instanciate() {
        ConnectionString connectionString = new ConnectionString(MONGODB_URL);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("ApartmentScrapper").withCodecRegistry(pojoCodecRegistry);
        return database.getCollection("ApartmentInfo", ApartmentInfo.class);
    }

    public static int getMinPrice(MongoCollection<ApartmentInfo> collection, String city) {
        ApartmentInfo apartmentInfo = collection.find(eq("city", city)).sort(ascending("price")).first();
        return apartmentInfo == null ? NOT_INFO_FOUND_INT : apartmentInfo.getPrice();
    }

    public static int getMaxPrice(MongoCollection<ApartmentInfo> collection, String city) {
        ApartmentInfo apartmentInfo = collection.find(eq("city", city)).sort(descending("price")).first();
        return apartmentInfo == null ? NOT_INFO_FOUND_INT : apartmentInfo.getPrice();
    }

    public static void saveApartment(MongoCollection<ApartmentInfo> collection, ApartmentInfo apartmentInfo){
        collection.insertOne(apartmentInfo);
    }
}
