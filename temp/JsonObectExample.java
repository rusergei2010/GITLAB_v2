package temp;

import com.epam.functions.model.SearchRequestInstant;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Optional;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * How to write Json objects and attributes to the file
 * How to read from the json file and instantiate Json Objects
 */
public class JsonObectExample {

    public JsonObectExample() throws Throwable {
        SearchRequestInstant.Builder builder = SearchRequestInstant.newBuilder()
                .setProduct("Product 1")
                .setRequestId("Request 1")
                .setSearchRequestType(SearchRequestInstant.SearchRequestType.ADD)
                .setUserId(Optional.of("User 1"));
        SearchRequestInstant sr = builder.build();

        FileWriter fw = new FileWriter("file.json");
        fw.write(sr.toJsonObject().toString());
        fw.close();

        FileReader fr = new FileReader("file.json");
        JsonReader jsonReader = Json.createReader(fr);
        JsonObject jsonObject = jsonReader.readObject();

        SearchRequestInstant json = builder.fromJsonObject(jsonObject).build();
        System.out.println("Json = " + json);
    }


    public static void main(String[] args) {
        //SpringApplication.run(JsonObectExample.class);
    }
}
