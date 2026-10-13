package project;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ParkingServlet")
public class ParkingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ParkingDAO parkingDAO = new ParkingDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        loadSpaces(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            String spaceNo = request.getParameter("spaceNo");

            if (spaceNo == null || spaceNo.trim().isEmpty()) {
                request.setAttribute("error",
                        "Please enter a parking slot number.");
            } else {
                try {
                    boolean success = parkingDAO.addParkingSpace(
                            spaceNo.trim().toUpperCase()
                    );

                    if (success) {
                        request.setAttribute("msg",
                                "Slot " + spaceNo.trim().toUpperCase()
                                + " added successfully!");
                    } else {
                        request.setAttribute("error",
                                "Slot already exists or could not be added.");
                    }
                } catch (Exception e) {
                    throw new ServletException(
                            "Error adding parking space", e);
                }
            }
        }

        loadSpaces(request, response);
    }

    private void loadSpaces(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ArrayList<ParkingSpaceBean> allSpaces =
                    parkingDAO.getAllSpaces();

            int total = parkingDAO.getTotalSpaces();
            int occupied = parkingDAO.getOccupiedSpaces();
            int available = parkingDAO.getAvailableSpaces();

            request.setAttribute("spaceList", allSpaces);
            request.setAttribute("totalCapacity", total);
            request.setAttribute("occupiedSpaces", occupied);
            request.setAttribute("availableSpaces", available);

            request.getRequestDispatcher("/parkingSpaces.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            throw new ServletException(
                    "Unable to load parking spaces from database", e);
        }
    }
}