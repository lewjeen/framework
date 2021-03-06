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

package com.mocha.util.exceptions;

/**
 * 
 * DecodingException.java
 * @version 1.0.0
 * @author Liu lihua
 */
public class DecodingException extends OCException
{

    /**
     * 
     */
    private static final long serialVersionUID = 3829543977230228041L;

    public DecodingException()
    {
        super();
    }

    public DecodingException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DecodingException(String message)
    {
        super(message);
    }

    public DecodingException(Throwable cause)
    {
        super(cause);
    }
    
}
