import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ruse.model.definitions.ItemDefinition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConvertItemsDefinition {

    public static void main(String[] args) {
        ItemDefinition.init();
        Gson builder = new GsonBuilder().setPrettyPrinting().create();
        JsonObject object = new JsonObject();
        try(FileWriter writer = new FileWriter("items.json")) {
            writer.write(builder.toJson(ItemDefinition.getDefinitions()));
            //object.add("items", builder.toJsonTree(ItemDefinition.getDefinitions()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
