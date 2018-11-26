package temp;

import java.util.function.Function;
import javax.json.JsonObject;

public class AssembleCode implements Function<JsonObject, String> {

    public static final AssembleCode instance = new AssembleCode();


    @Override
    public String apply(JsonObject jsonObject) {

        return null;
    }


}
