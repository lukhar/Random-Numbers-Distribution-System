package rnds.distribution;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;
import org.junit.Test;


public class RandomNumbersDistributorTest extends TestCase {

	@Test
	public void testShouldGenerateRandomBitSequenceAndPlaceItInResponseObject() throws ServletException, IOException {
		// given
		HttpServletRequest httpRequest = mock(HttpServletRequest.class);
		HttpServletResponse httpResponse = mock(HttpServletResponse.class);
		OutputStream out = new ByteArrayOutputStream();

		// when
		when(httpRequest.getParameter("sequenceLength")).thenReturn("400");
		when(httpResponse.getWriter()).thenReturn(new PrintWriter(out));

		new RandomNumbersDistributor().doPost(httpRequest, httpResponse);

		// then
		//assertEquals(400, out.toString().length());
	}

}
