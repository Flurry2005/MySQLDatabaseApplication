package se.liam.sqldbapplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HashMap<String, String> properties = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        try {
            // Get the path to the 'resources' directory inside target/classes
            URL resourceUrl = Main.class.getClassLoader().getResource(".");
            if (resourceUrl == null) {
                System.err.println("Could not find resource path.");
                return;
            }

            Path resourcePath = Paths.get(resourceUrl.toURI());
            File file = new File(resourcePath.toFile(), "properties.json");

            if (!file.exists()) {
                // Create file and write default properties
                if (file.createNewFile()) {
                    try (FileWriter writer = new FileWriter(file)) {
                        System.out.print("Please enter database address: ");
                        String address = scanner.nextLine();

                        properties.put("address", address);

                        System.out.print("Please enter database port: ");
                        String port = scanner.nextLine();

                        properties.put("port", port);

                        System.out.print("Please enter database name ex. inventory: ");
                        String dbName = scanner.nextLine();

                        properties.put("dbName", dbName);

                        System.out.print("Please enter database username: ");
                        String username = scanner.nextLine();

                        properties.put("user", username);

                        System.out.print("Please enter database password: ");
                        String password = scanner.nextLine();

                        properties.put("password", password);

                        gson.toJson(properties, writer);
                        System.out.println("Properties file created at: " + file.getAbsolutePath());

                    }
                }
            } else {
                // Load existing properties
                try (FileReader reader = new FileReader(file)) {
                    Type type = new TypeToken<HashMap<String, String>>() {}.getType();
                    properties = gson.fromJson(reader, type);
                    System.out.println("Properties loaded: " + properties);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Example usage
        DBConnection db = new DBConnection(
                properties.get("address"),
                Integer.parseInt(properties.get("port")),
                properties.get("dbName"),
                properties.get("user"),
                properties.get("password")
        );

        Application application = new Application(db,scanner);
    }
}
