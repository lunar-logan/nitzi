//
// ::text
// Copyright 2014 Anurag Gautam
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

import java.nio.channels.FileChannel;
import java.util.HashMap;

/**
 *
 * @author ANURAG
 */
public class Reply {

    public int code;
    public String reasonPhrase;
    public String httpVersion;
    public HashMap<String, String> responseHeaders;
    public FileChannel responseMessage;
    public String responseMessageStr;
    private static String CRLF = "\r\n";

    public String toString() {
        StringBuffer res = new StringBuffer();
        res.append("HTTP/").append(httpVersion).append(" ").append(code).append(" ").append(reasonPhrase).append(CRLF);
        for (String key : responseHeaders.keySet()) {
            res.append(key).append(": ").append(responseHeaders.get(key)).append(CRLF);
        }
        res.append(CRLF);
        if (responseMessageStr != null) {
            res.append(responseMessageStr);
        }
        return res.toString();
    }
}
