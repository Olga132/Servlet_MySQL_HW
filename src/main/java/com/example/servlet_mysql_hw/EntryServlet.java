package com.example.servlet_mysql_hw;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.servlet_mysql_hw.dao.EntryDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/")
public class EntryServlet extends HttpServlet {

    private static EntryDAO entryDAO;

    public void init() {
        entryDAO = new EntryDAO();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertEntry(request, response);
                    break;
                case "/delete":
                    deleteEntry(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateEntry(request, response);
                    break;
                case "/search":
                    searchEntry(request, response);
                    break;
                default:
                    listEntry(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void searchEntry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String searchValue = request.getParameter("searchValue");
        List<Entry> searchEntries = entryDAO.getEntriesByCondition(searchValue);
        request.setAttribute("listEntry", searchEntries);
        RequestDispatcher dispatcher = request.getRequestDispatcher("entry-list.jsp");
        dispatcher.forward(request, response);
    }

    private void listEntry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List <Entry> listEntries = entryDAO.selectAllEntries();
        request.setAttribute("listEntry", listEntries);
        request.setAttribute("listSum",entryDAO.getSumEntries());
        RequestDispatcher dispatcher = request.getRequestDispatcher("entry-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("entry-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Entry existingEntry = entryDAO.selectEntry(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("entry-form.jsp");
        request.setAttribute("entry", existingEntry);
        dispatcher.forward(request, response);
    }

    private void insertEntry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {
        java.util.Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
        java.sql.Date date =  new java.sql.Date(selectedDate.getTime());

        String specification = request.getParameter("specification");
        double sum = Double.parseDouble(request.getParameter("sum"));
        String movementTypeString = request.getParameter("movementTypeString");
        MovementType movementType;
        if (movementTypeString.equals("coming")){
           movementType = MovementType.ПРИХОД;
        } else { movementType = MovementType.РАСХОД;}

        Entry newEntry = new Entry(date,specification,sum,movementType);
        entryDAO.insertEntry(newEntry);
        response.sendRedirect("list");
    }

    private void updateEntry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ParseException {

        int id = Integer.parseInt(request.getParameter("id"));
        java.util.Date selectedDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date"));
        java.sql.Date date =  new java.sql.Date(selectedDate.getTime());
        String specification = request.getParameter("specification");
        double sum = Double.parseDouble(request.getParameter("sum"));
        String movementTypeString = request.getParameter("movementTypeString");
        MovementType movementType;
        if (movementTypeString.equals("coming")){
            movementType = MovementType.ПРИХОД;
        } else { movementType = MovementType.РАСХОД;}
        Entry updateEntry = new Entry(id,date,specification,sum,movementType);

        entryDAO.updateEntry(updateEntry);
        response.sendRedirect("list");
    }

    private void deleteEntry(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        entryDAO.deleteEntry(id);
        response.sendRedirect("list");
    }
}