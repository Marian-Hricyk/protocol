package org.example;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users";

    public static void main(String[] args) {
        Main User = new Main();
        try {
            String newUserJson = "{\"name\": \"John Doe\", \"username\": \"johndoe\", \"email\": \"johndoe@example.com\"}";
            String createdUser = User.createUser(newUserJson);
            System.out.println("Created User: " + createdUser);

            // Оновлення існуючого користувача
            String updatedUserJson = "{\"id\": 11, \"name\": \"Updated User\", \"username\": \"updateduser\", \"email\": \"updated@example.com\"}";
            String updatedUser = User.updateUser(updatedUserJson);
            System.out.println("Updated User: " + updatedUser);

            // Видалення користувача
            int userIdToDelete = 10;
            boolean deleted = User.delitUser(userIdToDelete);
            System.out.println("User with ID " + userIdToDelete + " deleted: " + deleted);

            // Отримання інформації про всіх користувачів
            String allUsersInfo = User.getAllUser();
            System.out.println("All Users: " + allUsersInfo);

            // Отримання інформації про користувача за id
            int userIdToGet = 5;
            String userInfoById = User.getIdUser(userIdToGet);
            System.out.println("User with ID " + userIdToGet + ": " + userInfoById);

            // Отримання інформації про користувача за username
            String usernameToGet = "Bret";
            String userInfoByUsername = User.getUser(usernameToGet);
            System.out.println("User with username " + usernameToGet + ": " + userInfoByUsername);

// Отримання всіх коментарів до останнього поста користувача та запис у файл
            int userId = 1;
            User.getUserLastPostCommentsAndSaveToFile(userId);

            // Отримання всіх відкритих задач користувача та запис у файл
            User.getUserOpenTasksAndSaveToFile(userId);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String createUser(String userJeson) throws IOException {

        URL url = new URL(BASE_URL + USERS_ENDPOINT);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] bytes = userJeson.getBytes(StandardCharsets.UTF_8);
            os.write(bytes, 0, bytes.length);
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

    public static String updateUser(String userJeson) throws IOException {
        URL url = new URL(BASE_URL + USERS_ENDPOINT + "/11");
        HttpURLConnection conection = (HttpURLConnection) url.openConnection();
        conection.setRequestMethod("PUT");
        conection.setRequestProperty("Content-Type", "application/json");
        conection.setDoOutput(true);
        try (OutputStream os = conection.getOutputStream()) {
            byte[] input = userJeson.getBytes("utf-8");
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
        String allComents = allComents(lastPost);
        File file = new File("user-" + userId + "-post-" + allComents + "-comments.json");
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
            return responseLine.toString();
        }
    }


    private static String allComents(int idpost) throws IOException {
        URL url = new URL(BASE_URL + "/posts/" + idpost + "/comments");
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

    public void getUserOpenTasksAndSaveToFile(int userId) throws IOException {
        String userTasksJson = getAllUserTasks(userId);
        String fileName = "user-" + userId + "-tasks.json";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(userTasksJson);
        }
        System.out.println("Tasks saved to file: " + fileName);
    }

    private String getAllUserTasks(int userId) throws IOException {
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