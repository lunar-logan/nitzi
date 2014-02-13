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
