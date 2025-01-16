package org.example.utils;

import com.google.gson.Gson;
import org.example.model.User;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
    private static final String FILE_PATH = "data/user_data.json";
    private static final Gson gson = new Gson();

    public static void saveUsers(Map<String, User> users) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, User> loadUsers() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<Map<String, User>>() {
            }.getType();
            return gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            return new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }

    }
}
