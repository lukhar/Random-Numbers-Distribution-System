package rnds.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by IntelliJ IDEA.
 * User: lukash
 * Date: 8/18/11
 * Time: 9:59 PM
 */

@WebService
public interface RandomNumbersService {
    @WebMethod
    String generateSequence(@WebParam int sequenceSize) throws Throwable;
}
