package org.online.polling.system.Questions;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/questions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QuestionResource {
    QuestionService questionService = new QuestionService();

    @GET
    public List<QuestionModel> getQuestions() {
        return questionService.getAllQuestions();
    }

    @PUT
    @Path("/{question_id}")
    public QuestionModel updateQuestion(@PathParam("question_id") long id, QuestionModel question) {
        return questionService.updateQuestion(id,question);
    }


    @POST
    public QuestionModel addQuestion(QuestionModel question)
    {
        return questionService.addQuestion(question);
    }


    @GET
    @Path("/{question_id}")
    public List<QuestionModel> getQuestion(@PathParam("question_id") long id) {
        return questionService.getQuestion(id);
    }

    @DELETE
    @Path("{question_id}")
    public void deleteQuestion(@PathParam("question_id") long id) {
        questionService.removeQuestion(id);
    }

}