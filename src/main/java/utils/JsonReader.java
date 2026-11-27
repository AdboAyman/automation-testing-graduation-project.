
package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonReader {

    private static final ObjectMapper mapper = new ObjectMapper();


    private static JsonNode readJsonFile() {

        try (InputStream input = JsonReader.class
                .getClassLoader()
                .getResourceAsStream("testData.json")) {

            if (input == null) {
                throw new RuntimeException("testData.json not found!");
            }

            return mapper.readTree(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to read testData.json", e);
        }
    }


    public static Object[][] getData(String section) {

        JsonNode root = readJsonFile();
        JsonNode dataArray = root.get(section);

        if (dataArray == null || !dataArray.isArray()) {
            throw new RuntimeException(
                    "JSON section not found: " + section
            );
        }

        List<Object[]> data = new ArrayList<>();

        Iterator<JsonNode> iterator = dataArray.elements();

        while (iterator.hasNext()) {

            JsonNode node = iterator.next();

            data.add(new Object[]{node});
        }

        return data.toArray(new Object[0][]);
    }
}
