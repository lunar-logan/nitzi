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
public interface HeaderEvent {

//    public void onHeader(String key, String val);

//    public void onHeader(String k, String v, Reply reply);

    public void onHeader(String k, String v, Request request, Reply reply);
}
