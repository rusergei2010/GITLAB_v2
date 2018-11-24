package temp;

import com.epam.functions.model.SearchRequestInstant;
import java.util.Optional;

/**
 * How to write Json objects and attributes to the file
 * How to read from the json file and instantiate Json Objects
 */
public class JsonObectExampleWithFunctions {

    public JsonObectExampleWithFunctions() throws Throwable {
        SearchRequestInstant.Builder builder = SearchRequestInstant.newBuilder()
                .setProduct("Product 1")
                .setRequestId("Request 1")
                .setSearchRequestType(SearchRequestInstant.SearchRequestType.ADD)
                .setUserId(Optional.of("User 1"));
        SearchRequestInstant sr = builder.build();


    }


    public static void main(String[] args) {
//        SpringApplication.run(JsonObectExampleWithFunctions.class);
    }
}
