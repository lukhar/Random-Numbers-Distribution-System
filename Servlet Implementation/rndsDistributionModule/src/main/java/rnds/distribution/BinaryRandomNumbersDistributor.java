package rnds.distribution;

import rnds.generator.model.FileRandomBitGenerator;
import rnds.generator.model.RandomBitGenerator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/14/11
 * Time: 9:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class BinaryRandomNumbersDistributor extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/octet-stream");
        ServletOutputStream out = resp.getOutputStream();
        String source = getServletContext().getInitParameter("randomNumbersSource");

        byte[] output;

        try {
            int sequenceSize = Integer.valueOf(req.getParameter("sequenceSize"));
            int packagesAmount = Integer.valueOf(req.getParameter("packagesAmount"));

            RandomBitGenerator randomBitGenerator = new FileRandomBitGenerator(source);

            for (int i = 0; i < packagesAmount; ++i) {
                output = randomBitGenerator.generateSequence(sequenceSize).getBytes();
                out.write(output);
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
