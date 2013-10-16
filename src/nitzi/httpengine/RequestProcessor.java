//
// ::text
// Copyright 2013 Anurag Gautam
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nitzi.httpengine;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;


public class RequestProcessor {

    public static enum Method {

        GET, HEAD, POST, PUT, DELETE, OPTIONS
    };
    public HashMap<String, String> headers = new HashMap<>();
    public Method method;
    public URI requestResource;
    public String httpVersion;
    public String requestMessage;
    private Reply reply = new Reply();

    public Reply getReply() {
        return reply;
    }

    public void parse(byte[] bb) throws UnsupportedEncodingException {
        char previous = '\0';
        StringBuffer requestLine = new StringBuffer();
        int bbIndex = 0;
        boolean crlfFound = false;
        for (bbIndex = 0; bbIndex < bb.length; bbIndex++) {
            byte b = bb[bbIndex];
            char ch = (char) (b & 0xFF);
            if (ch == '\n' && previous == '\r') {
                if (crlfFound) {
                    //Possibly we reached the end of headers,
                    //Be carefull below might follow some binary data
                    break;
                }
                //One request line complete
                if (requestLine.capacity() > 0) {
                    parseRequestLine(requestLine.toString());
                    requestLine.delete(0, requestLine.capacity());
                }
                crlfFound = true;
            } else {
                //Ignore all the whitespaces
                if (ch != '\r' && ch != '\n') {
                    crlfFound = false;
                    requestLine.append(ch);
                }
            }
            previous = ch;
        }
        //For the time being we only support application/x-www-form-urlencoded for POST request
        //But support for multipart/form-data is arrving soon
        //Please go through following RFC for details http://tools.ietf.org/html/rfc1867
        if (headers.containsKey("Content-type")
                && headers.get("Content-type").equalsIgnoreCase("application/x-www-form-urlencoded")) {
            if (bbIndex < bb.length) {
                String message = new String(bb, bbIndex, bb.length);
                requestMessage = URLDecoder.decode(message, "UTF-8");
            } else {
                reply.code = 400; //Bad request
            }
        }
    }

    private void parseRequestLine(String raw) {
        int colon = raw.indexOf(':');
        if (colon > 0) {
            //It's possibly a header
            //Header-Title: Value|Value[, Value]
            String k = raw.substring(0, colon).trim();
            String v = raw.substring(colon + 1).trim();
            headers.put(k, v);
        } else {
            //Its certainly a request(first) line
            //Method /<URI> HTTP/1.x\r\n
            String[] tokens = raw.split(" ");
            if (tokens.length == 3) {
                String meth = tokens[0].toUpperCase();
                if (meth.equals("GET")) {
                    method = Method.GET;
                } else if (meth.equals("HEAD")) {
                    method = Method.HEAD;
                } else if (meth.equals("POST")) {
                    method = Method.POST;
                } else if (meth.equals("PUT")) {
                    method = Method.PUT;
                } else if (meth.equals("DELETE")) {
                    method = Method.DELETE;
                } else if (meth.equals("OPTIONS")) {
                    method = Method.OPTIONS;
                } else {
//                    throw new BadRequestException(405, "Method \"" + meth + "\" not allowed");
                    reply.code = 405;
                }
                try {
                    requestResource = new URI(tokens[1]);
                } catch (URISyntaxException ex) {
                    //throw BadHeaderException 400 Invalid resource specification
                    reply.code = 400;
                }
                if (tokens[2].indexOf('/') > 0) {
                    httpVersion = tokens[2].split("/")[1];
                } else {
                    //Assume small to be on safer side
                    httpVersion = "0.9";
                }
                reply.httpVersion = httpVersion;
            } else {
                //throw BadHeaderException 400 Invalid header line
                reply.code = 400;
            }
        }
    }
}
