package org.online.polling.system.Questions;

import org.online.polling.system.DatabaseClass.DatabaseClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionService {

    private Map<Long,QuestionModel> questionsMap= DatabaseClass.getQuestions();
    private DatabaseClass jdbcConnection = new DatabaseClass();

    public QuestionService() {
        questionsMap.put(1L,new QuestionModel(1L,"Q1","A","B","C","D"));
        questionsMap.put(2L,new QuestionModel(2L,"Q2","A","B","C","D"));
    }

    public List<QuestionModel> getAllQuestions()
    {
        QuestionModel question = new QuestionModel();
        List<QuestionModel> list=new ArrayList<>();
        try{
            Connection connection = jdbcConnection.getConnnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM questions;");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                question=new QuestionModel(rs.getLong("idquestions"),rs.getString("question"),
                        rs.getString("option1"),rs.getString("option2"),rs.getString("option3"),
                        rs.getString("option4"));
                list.add(question);
            }
            connection.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return list;
        //return new ArrayList<QuestionModel>(questionsMap.values());
    }

    public List<QuestionModel> getQuestion(long id)
    {
        QuestionModel question1 = new QuestionModel();
        List<QuestionModel> list1=new ArrayList<>();
        try{
            Connection connection = jdbcConnection.getConnnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM questions WHERE idquestions="+id+";");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                question1=new QuestionModel(rs.getLong("questions_id"),rs.getString("questions"),
                        rs.getString("option1"),rs.getString("option2"),rs.getString("option3"),
                        rs.getString("option4"));
                list1.add(question1);
            }
            connection.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return list1;
    }

    public QuestionModel addQuestion(QuestionModel question)
    {
        try{
            Connection connection = jdbcConnection.getConnnection();
            String sql = "insert into questions(question,option1,option2,option3,option4)values(?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,question.getQuestion());
            ps.setString(2,question.getOption1());
            ps.setString(3,question.getOption2());
            ps.setString(4,question.getOption3());
            ps.setString(5,question.getOption4());
            ps.executeUpdate();
            ps.close();

            String sql1 = "insert into answers (questions_id,question,A,B,C,D,option1,option2,option3,option4) values(last_insert_id(),?,?,?,?,?,0,0,0,0)";
            PreparedStatement s = connection.prepareStatement(sql1);
            s.setString(1,question.getQuestion());
            s.setString(2,question.getOption1());
            s.setString(3,question.getOption2());
            s.setString(4,question.getOption3());
            s.setString(5,question.getOption4());
            s.executeUpdate();
            s.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return question;
    }

    public QuestionModel updateQuestion(long id,QuestionModel question)
    {
        try{
            Connection connection = jdbcConnection.getConnnection();
            String s1="\"";
            String sql="UPDATE questions set question="+s1+question.getQuestion()+s1+",option1="+s1+question.getOption1()+s1+",option2="+s1+question.getOption2()+s1+",option3="+s1+question.getOption3()+s1+",option4="+s1+question.getOption4()+s1+"where idquestions="+id+";";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return question;
    }

    public QuestionModel removeQuestion(long id)
    {
        try{
            Connection connection = jdbcConnection.getConnnection();
            String s1="\"";
            String sql="DELETE from questions where idquestions="+id+";";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();

            String s2 = "\"";
            String sql2 = "DELETE from answers where questions_id ="+id+";";
            PreparedStatement ps1 = connection.prepareStatement(sql2);
            ps1.executeUpdate();
            ps1.close();
       }
        catch(SQLException e){
            e.printStackTrace();
        }
        return questionsMap.remove(id);
    }

}
