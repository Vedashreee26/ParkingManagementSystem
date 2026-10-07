package project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@SuppressWarnings("serial")
@WebServlet("/exit")
public class ExitServlet extends HttpServlet {

    private static final int RATE_PER_HOUR = 20;

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String vNo = request.getParameter("vNo");

        try {

            EntryExitDAO entryDAO = new EntryExitDAO();
            ParkingDAO parkingDAO = new ParkingDAO();

            // 1. Find the active parking record
            EntryRecordBean record =
                    entryDAO.getActiveRecord(vNo);

            if (record == null) {

                request.setAttribute(
                        "message",
                        "Vehicle is not currently parked!"
                );

                request.getRequestDispatcher(
                        "exit.html"
                ).forward(request, response);

                return;
            }

            // 2. Calculate parking duration
            long milliseconds =
                    System.currentTimeMillis()
                    - record.getEntryTime().getTime();

            long hours =
                    milliseconds / (1000 * 60 * 60);

            // Minimum charge = 1 hour
            if (hours < 1) {
                hours = 1;
            }

            // 3. Calculate parking amount
            int amount =
                    (int) hours * RATE_PER_HOUR;

            // 4. Update exit time
            entryDAO.updateExit(
                    record.getRecordid()
            );

            // 5. Make parking space available again
            parkingDAO.updateSpaceStatus(
                    record.getSpace_no(),
                    "AVAILABLE"
            );

            // 6. Generate transaction ID
            String transactionId =
                    "T" + System.currentTimeMillis();

            transactionId =
                    transactionId.substring(
                            transactionId.length() - 9
                    );

            // 7. Create transaction
            entryDAO.createTransaction(
                    transactionId,
                    record.getRecordid(),
                    amount,
                    "PAID"
            );

            // 8. Send information to success page
            request.setAttribute(
                    "vNo",
                    vNo
            );

            request.setAttribute(
                    "spaceNo",
                    record.getSpace_no()
            );

            request.setAttribute(
                    "amount",
                    amount
            );

            request.getRequestDispatcher(
                    "exit-success.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            throw new ServletException(
                    "Vehicle exit failed.",
                    e
            );
        }
    }
}