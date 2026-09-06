package com.savarquiz.servlet;

import com.savarquiz.dao.QuestionDAO;
import com.savarquiz.model.Question;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        QuestionDAO questionDAO = new QuestionDAO();

        List<Question> questions =
                questionDAO.getAllQuestions();

        request.setAttribute(
                "questions",
                questions
        );

        request.getRequestDispatcher(
                "quiz.jsp"
        ).forward(request, response);
    }
}