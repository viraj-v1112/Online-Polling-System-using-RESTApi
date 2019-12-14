package org.online.polling.system.Answers;

import org.online.polling.system.DatabaseClass.DatabaseClass;
import org.online.polling.system.Questions.QuestionService;
import org.online.polling.system.Questions.QuestionModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerService {

    private Map<Long,AnswerModel> answersMap= DatabaseClass.getAnswers();
    private DatabaseClass jdbcConnection = new DatabaseClass();

    public List<AnswerModel> getAllAnswers()
    {
        AnswerModel answer = new AnswerModel();
        List<AnswerModel> list=new ArrayList<>();
        try{
            Connection connection = jdbcConnection.getConnnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM answers;");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                answer =new AnswerModel(rs.getLong("answers_id"),rs.getString("question"),rs.getString("A"),rs.getString("B"),rs.getString("C"),rs.getString("D"),rs.getInt("questions_id"),
                        rs.getInt("option1"),rs.getInt("option2"),rs.getInt("option3"),rs.getInt("option4"));
                list.add(answer);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return list;
        //return new ArrayList<QuestionModel>(questionsMap.values());
    }

    public AnswerModel getAnswer(long id){ return answersMap.get(id); }

    public List<AnswerModel> addAnswer(List<AnswerModel> answer) throws SQLException {
        Connection connection = jdbcConnection.getConnnection();
        String s1="\"";
        for(AnswerModel ans:answer)
        {
            int o1,o2,o3,o4;
            String q;
            long id=ans.getId();
            o1=ans.getOption1();
            o2=ans.getOption2();
            o3=ans.getOption3();
            o4=ans.getOption4();
            q=ans.getQuestion();
            String sql="UPDATE answers set option1= IFNULL(option1,0) + "+o1+",option2= IFNULL(option2,0) + "+o2+",option3= IFNULL(option3,0) + "+o3+",option4= IFNULL(option4,0) + "+o4+" WHERE question="+s1+q+s1+";";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        }

        return answer;
    }

    public void removeAnswer(long id) throws SQLException {
        Connection connection = jdbcConnection.getConnnection();
        String sql = "DELETE from answers Where questions_id ="+id;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
        //return questionsMap.remove(id);
    }
}
