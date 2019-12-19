package ru.itpark.implementation.repository;

import lombok.var;
import ru.itpark.enums.TaskStatus;
import ru.itpark.model.Task;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private DataSource ds;

    public TaskRepository() {
        InitialContext context = null;
        try {
            context = new InitialContext();
            ds = (DataSource) context.lookup("java:comp/env/db.sqlite");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        try (var conn = ds.getConnection()) {
            try (var stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY, phrase TEXT NOT NULL, status TEXT NOT NULL, sessionId TEXT NOT NULL)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTask(Task task) {
        try (Connection connection = ds.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO tasks (phrase, status, sessionId) VALUES (?,?,?)");

            preparedStatement.setString(1, task.getPhrase());
            preparedStatement.setString(2, task.getStatus().toString());
            preparedStatement.setString(3, task.getSessionId());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Task> getTasksByStatusOneAndSetStatusTwo(TaskStatus status, TaskStatus newStatus) {
        try (Connection connection = ds.getConnection()) {
            connection.setAutoCommit(false);
            List<Task> tasksByStatus = getTasksByStatus(status);

            for (Task task : tasksByStatus) {
                task.setStatus(newStatus);
                updateTask(task);
            }

            connection.commit();
            return tasksByStatus;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public List<Task> getTasksByStatus(TaskStatus status) {
        try (Connection connection = ds.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT *  FROM tasks WHERE status=? LIMIT 5");
            preparedStatement.setString(1, status.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                Task task = new Task();
                task.setSessionId(resultSet.getString("sessionid"));
                task.setStatus(TaskStatus.valueOf(resultSet.getString("status")));
                task.setPhrase(resultSet.getString("phrase"));
                task.setId(resultSet.getInt("id"));
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void updateTask(Task task) {
        try (Connection connection = ds.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks set status=? where id=?");
            preparedStatement.setString(1, task.getStatus().toString());
            preparedStatement.setInt(2, task.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
