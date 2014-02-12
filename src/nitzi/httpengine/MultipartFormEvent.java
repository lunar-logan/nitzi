/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nitzi.httpengine;

/**
 *
 * @author ANURAG
 */
public interface MultipartFormEvent {
//    public void onMultipartFormData(Request request, Reply reply);

    public void onMultipartFormData(Request request, Reply reply, byte[] bb, int bbIndex);

    public void onMultipartFormData(byte[] bb, int bbIndex, Request request, Reply reply);
}
