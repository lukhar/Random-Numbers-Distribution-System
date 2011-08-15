package rnds.distribution;

import rnds.generator.model.FileRandomBitGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RandomNumbersDistributor extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = -4717002943963557755L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        String source = getServletContext().getInitParameter("randomNumbersSource");

        String output;

        try {
            int sequenceSize = Integer.valueOf(req.getParameter("sequenceSize"));
            int packagesAmount = Integer.valueOf(req.getParameter("packagesAmount"));

            FileRandomBitGenerator randomBitGenerator = new FileRandomBitGenerator(source);

            for (int i = 0; i < packagesAmount; ++i) {
                output = randomBitGenerator.generateSequence(sequenceSize);

                out.write(output);
            }

        } catch (NumberFormatException e) {
            output = "Invalid value submitted !";
        } finally {
            out.flush();
            out.close();
        }
    }
}
