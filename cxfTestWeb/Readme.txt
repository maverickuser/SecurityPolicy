This example implements the below Scenario
http://cxf.apache.org/docs/how-to-define-policies.html
Dynamically via message property
It can be done for example in custom interceptor that fulfils the following:

1) Get policy from external location and build it for current message.
2) Parse WS-Policy XML using Neethi library.
3) Store result Policy object into PolicyConstants.POLICY_OVERRIDE message content property.
Custom policy interceptor is called before CXF PolicyInInterceptor or PolicyOutInterceptor. 
Than CXF will automatically recognize Policy stored into this property and use it with highest priority.


This project represents a Service protected by a runtime policy of usernameToken


