/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nitzi.httpengine;

import java.net.URI;
import java.util.HashMap;

/**
 *
 * @author ANURAG
 */
public class Request {
    public HashMap<String, String> headers = new HashMap<>();
    public Method method;
    public URI requestResource;
    public String httpVersion;
    public String requestMessage;
}
