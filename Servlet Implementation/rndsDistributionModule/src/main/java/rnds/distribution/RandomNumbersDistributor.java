package rnds.distribution;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rnds.generator.model.RandomBitGeneratorManagerImpl;

public class RandomNumbersDistributor extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4717002943963557755L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String sequenceLength = req.getParameter("sequenceLength");

		String response = null;

		try {
			response = new RandomBitGeneratorManagerImpl()
					.getSequenceFromDefaultGenerator(Integer
							.valueOf(sequenceLength));
		} catch (NumberFormatException e) {
			response = "Invalid value submitted !";
		}
		
		PrintWriter out = resp.getWriter();
		out.println(response);

		/*req.setAttribute("response", response);
		RequestDispatcher requestDispatcher = req
				.getRequestDispatcher("generatedBits.jsp");
		requestDispatcher.forward(req, resp);*/
	}

}
