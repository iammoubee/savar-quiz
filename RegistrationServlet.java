package com.savarquiz.servlet;

import java.io.IOException;

import com.savarquiz.dao.ParticipantDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String roll = request.getParameter("roll");

        // Validate inputs
        if (name == null || name.trim().isEmpty() ||
            roll == null || roll.trim().isEmpty()) {

            request.setAttribute("error", "Name and Roll are required!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        ParticipantDAO participantDAO = new ParticipantDAO();
        int participantId = participantDAO.addParticipant(
                name.trim(),
                roll.trim()
        );

        if (participantId == -1) {
            request.setAttribute("error", "Error registering participant. Please try again.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Store participant info in session
        HttpSession session = request.getSession();
        session.setAttribute("participantId", participantId);
        session.setAttribute("participantName", name.trim());
        session.setAttribute("participantRoll", roll.trim());

        // Redirect to quiz
        response.sendRedirect("quiz");
    }
}
