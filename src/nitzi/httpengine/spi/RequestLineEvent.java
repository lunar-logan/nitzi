/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nitzi.httpengine.spi;

import nitzi.httpengine.Reply;
import nitzi.httpengine.Request;

/**
 *
 * @author ANURAG
 */
public interface RequestLineEvent {

//    public void onRequestLine(String requestLine);

    public void onRequestLine(String raw, Request request, Reply reply);
}
