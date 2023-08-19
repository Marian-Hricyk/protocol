package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users";

    public static void main(String[] args) {
        try {
            // Створення нового користувача
            User newUser = new User("johndoe", "johndoe@example.com");
            String createdUser = createUser(newUser);
            System.out.println("Created User: " + createdUser);

            // Оновлення існуючого користувача
            User updatedUser = new User(1, "Updated User", "updated@example.com");
            String userAfterUpdate = updateUser(updatedUser);
            System.out.println("Updated User: " + userAfterUpdate);

            // Видалення користувача
            int userIdToDelete = 1;
            boolean deleted = delitUser(userIdToDelete);
            System.out.println("User with ID " + userIdToDelete + " deleted: " + deleted);

//Вивід всіх юзерів
            System.out.println("All user" + getAllUser());

            //Повертає юзера за id
            int id = 4;
            System.out.println(getIdUser(id));

            //Повертає юзера за ім'ям
            String getUsers = getUser("Брет");
            System.out.println(getUsers);

            //повертає коментарі під останім постом
            getUserLastPostCommentsAndSaveToFile(7);

            //повертає відкриті завдання юзера у фаїл
            getUserOpenTasksAndSaveToFile(8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createUser(User user) throws IOException {
        URL url = new URL(BASE_URL + USERS_ENDPOINT);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String userJson = objectMapper.writeValueAsString(user);
            byte[] input = userJson.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }

    public static String updateUser(User user) throws IOException {
        URL url = new URL(BASE_URL + USERS_ENDPOINT + user.getId());
        HttpURLConnection conection = (HttpURLConnection) url.openConnection();
        conection.setRequestMethod("PUT");
        conection.setRequestProperty("Content-Type", "application/json");
        conection.setDoOutput(true);
        try (OutputStream os = conection.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String userJeso = objectMapper.writeValueAsString(user);
            byte[] input = userJeso.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }

    public static boolean delitUser(int idUser) throws IOException {
        URL url = new URL(BASE_URL + USERS_ENDPOINT + "/" + idUser);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        int status = connection.getResponseCode();
        return status >= 200 && status < 300;
    }

    public static String getAllUser() throws IOException {
        URL url = new URL(BASE_URL + USERS_ENDPOINT);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder resq = new StringBuilder();
            String reskLine;
            while ((reskLine = bf.readLine()) != null) {
                resq.append(reskLine.trim());
            }
            return reskLine;
        }
    }

    public static String getIdUser(int idUser) throws IOException {
        URL url = new URL(BASE_URL + USERS_ENDPOINT + "/" + idUser);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder resq = new StringBuilder();
            String reskLine;
            while ((reskLine = bf.readLine()) != null) {
                resq.append(reskLine.trim());
            }
            return reskLine;
        }
    }

    public static String getUser(String userName) throws IOException {
        URL url = new URL(BASE_URL + USERS_ENDPOINT + "?username=" + userName);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder resq = new StringBuilder();
            String reskLine;
            while ((reskLine = bf.readLine()) != null) {
                resq.append(reskLine.trim());
            }
            return reskLine;
        }
    }

    public static void getUserLastPostCommentsAndSaveToFile(int userId) throws IOException {
        String userPostsJson = getAllUserPosts(userId);
        int lastPost = lastPost(userPostsJson);
        String allComents = lastPost(lastPost);
        File file = new File("user-" + userId + "-post-" + lastPost + "-comments.json");
        try (FileWriter failName = new FileWriter(file)) {
            failName.write(allComents);
        }
        System.out.println("Comments saved to file: " + file);
    }

    private static int lastPost(String userPostsJson) throws IOException {

        return 10;
    }

    private static String getAllUserPosts(int userId) throws IOException {
        URL url = new URL(BASE_URL + USERS_ENDPOINT + "/" + userId + "/posts");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }

    private static String lastPost(int idpost) throws IOException {
        URL url = new URL(BASE_URL + "/posts/" + idpost + "/comments");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder resq = new StringBuilder();
            String reskLine;
            while ((reskLine = bf.readLine()) != null) {
                resq.append(reskLine.trim());
            }
            return resq.toString();
        }
    }

    public static void getUserOpenTasksAndSaveToFile(int userId) throws IOException {
        String userTasksJson = getAllUserTasks(userId);
        String fileName = "user-" + userId + "-tasks.json";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(userTasksJson);
        }
        System.out.println("Tasks saved to file: " + fileName);
    }

    private static String getAllUserTasks(int userId) throws IOException {
        URL url = new URL(BASE_URL + USERS_ENDPOINT + "/" + userId + "/todos");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }
}