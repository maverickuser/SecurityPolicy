
package com.maverick.security;

import javax.jws.WebService;

@WebService(endpointInterface = "com.maverick.security.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        return "Hello " + text;
    }
}

