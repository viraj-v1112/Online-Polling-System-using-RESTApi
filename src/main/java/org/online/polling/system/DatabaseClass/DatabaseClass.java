package org.online.polling.system.DatabaseClass;

import org.online.polling.system.Answers.AnswerModel;
import org.online.polling.system.Questions.QuestionModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseClass {
    private static Map<Long, QuestionModel> questions = new HashMap<>();
    private static Map<Long, AnswerModel> answers = new HashMap<>();

    public Connection getConnnection() {
        Connection connection = null;
        try {
            String connectionURL = "jdbc:mysql://localhost:3306/project";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(connectionURL, "root", "virajv1112");
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static Map<Long, QuestionModel> getQuestions() {
        return questions;
    }
    public static Map<Long, AnswerModel> getAnswers(){ return answers; }
}
