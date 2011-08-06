package rnds.distribution;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rnds.generator.model.NativeRandomBitGenerator;

public class RandomNumbersDistributor extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -4717002943963557755L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String output;

        try {
            int sequenceLength = Integer.valueOf(req.getParameter("sequenceLength"));
            int packagesAmount = Integer.valueOf(req.getParameter("packagesAmount"));


            NativeRandomBitGenerator generatorManager = new NativeRandomBitGenerator();

            for (int i = 0; i < packagesAmount; ++i) {
                output = generatorManager.generateSequence(sequenceLength);

                sendData(resp, output);
            }

        } catch (NumberFormatException e) {
            output = "Invalid value submitted !";
        }


    }

    private void sendData(HttpServletResponse resp, String output) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println(output);


//        req.setAttribute("response", response);
//        RequestDispatcher requestDispatcher = req
//                .getRequestDispatcher("generatedBits.jsp");
//        requestDispatcher.forward(req, resp);
    }
}
