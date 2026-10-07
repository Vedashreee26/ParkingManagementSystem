package project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/entry")
public class EntryServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String vNo = request.getParameter("vNo");
        String name = request.getParameter("uName");
        String type = request.getParameter("vType");

        try {

            EntryExitDAO entryDAO = new EntryExitDAO();
            ParkingDAO parkingDAO = new ParkingDAO();
            VehicleDAO vehicleDAO = new VehicleDAO();

            // 1. Check whether vehicle is already parked
            if (entryDAO.isVehicleCurrentlyParked(vNo)) {

                request.setAttribute(
                    "message",
                    "Vehicle is already parked!"
                );

                request.getRequestDispatcher("entry.html")
                       .forward(request, response);

                return;
            }

            // 2. Find an available parking space
            String spaceNo = parkingDAO.getAvailableSpace();

            if (spaceNo == null) {

                request.setAttribute(
                    "message",
                    "Parking is full!"
                );

                request.getRequestDispatcher("entry.html")
                       .forward(request, response);

                return;
            }

            // 3. Add vehicle if it does not already exist
            if (!vehicleDAO.vehicleExists(vNo)) {

                VehicleBean vehicle =
                    new VehicleBean(vNo, name, type);

                vehicleDAO.addVehicle(vehicle);
            }

            // 4. Generate Record ID
            String recordId =
                "R" + System.currentTimeMillis();

            recordId =
                recordId.substring(
                    recordId.length() - 9
                );

            // 5. Create entry record
            entryDAO.createEntry(
                recordId,
                vNo,
                spaceNo
            );

            // 6. Mark parking space as occupied
            parkingDAO.updateSpaceStatus(
                spaceNo,
                "OCCUPIED"
            );

            // 7. Send information to success page
            request.setAttribute(
                "vNo",
                vNo
            );

            request.setAttribute(
                "spaceNo",
                spaceNo
            );

            request.setAttribute(
                "recordId",
                recordId
            );

            request.getRequestDispatcher(
                "entry-success.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            throw new ServletException(
                "Vehicle entry failed.",
                e
            );
        }
    }
}