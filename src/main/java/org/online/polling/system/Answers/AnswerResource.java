package org.online.polling.system.Answers;

import org.online.polling.system.Questions.QuestionModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnswerResource {
    AnswerService answerService = new AnswerService();

    @GET
    public List<AnswerModel> getAnswers(){return answerService.getAllAnswers();}

    @GET
    @Path("/{answer_id}")
    public AnswerModel getAnswer(@PathParam("answer_id") long id) { return answerService.getAnswer(id); }

    @PUT
    public List<AnswerModel> addAnswer(List<AnswerModel> answer) throws SQLException {
        return answerService.addAnswer(answer);
    }

    @DELETE
    @Path("/{answer_id}")
    public void deleteQuestion(@PathParam("answer_id") long id) throws SQLException {
//        question.setId(id);
        answerService.removeAnswer(id);
    }
}