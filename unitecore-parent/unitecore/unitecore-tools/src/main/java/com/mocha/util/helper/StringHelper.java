/**
 * 
 *Copyright 2014 The Darks Codec Project (Liu lihua)
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */

package com.mocha.util.helper;

/**
 * 
 * StringHelper.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public final class StringHelper
{

    /**
     * Buffer string objects.
     * 
     * @param objs Objects.
     * @return string
     */
    public static String buffer(Object... objs)
    {
        StringBuilder buf = new StringBuilder(64);
        for (Object obj : objs)
        {
            buf.append(obj);
        }
        return buf.toString();
    }
}
