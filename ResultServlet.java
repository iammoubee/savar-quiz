package com.savarquiz.servlet;

import java.io.IOException;
import java.util.List;

import com.savarquiz.dao.QuestionDAO;
import com.savarquiz.dao.ScoreDAO;
import com.savarquiz.model.Question;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/result")
public class ResultServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        QuestionDAO questionDAO =
                new QuestionDAO();

        List<Question> questions =
                questionDAO.getAllQuestions();

        int score = 0;

        for (Question question : questions) {

            String userAnswer =
                    request.getParameter(
                            "q" + question.getId()
                    );

            if (userAnswer != null &&
                    userAnswer.equals(
                            question.getCorrectAnswer()
                    )) {

                score++;
            }
        }

        int total = questions.size();

        request.setAttribute(
                "score",
                score
        );

        request.setAttribute(
                "total",
                total
        );

        // Save score to database
        HttpSession session = request.getSession();
        Object participantIdObj = session.getAttribute("participantId");

        if (participantIdObj != null) {
            int participantId = (Integer) participantIdObj;
            ScoreDAO scoreDAO = new ScoreDAO();
            scoreDAO.addScore(participantId, score, total);

            request.setAttribute("participantId", participantId);
            request.setAttribute("participantName", session.getAttribute("participantName"));
            request.setAttribute("participantRoll", session.getAttribute("participantRoll"));
        }

        request.getRequestDispatcher(
                "result.jsp"
        ).forward(request, response);
    }
}