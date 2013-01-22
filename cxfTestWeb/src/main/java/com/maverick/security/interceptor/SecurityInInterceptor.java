package com.maverick.security.interceptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.ws.policy.PolicyConstants;
import org.apache.cxf.ws.policy.PolicyInInterceptor;
import org.apache.neethi.Policy;
import org.xml.sax.SAXException;

//public class SecurityInInterceptor  extends AbstractPolicyInterceptor {
	public class SecurityInInterceptor  extends AbstractPhaseInterceptor<SoapMessage> {
    public static final SecurityInInterceptor INSTANCE = new SecurityInInterceptor();
    private static final Logger LOG = LogUtils.getL7dLogger(SecurityInInterceptor.class);
	public SecurityInInterceptor() {
		super(Phase.RECEIVE);
        addBefore(PolicyInInterceptor.class.getName());
	}

	@Override
	public void handleMessage(SoapMessage msg) {
		ListIterator<Interceptor<? extends Message>> i =  msg.getInterceptorChain().getIterator();
		   
		   while(i.hasNext()){
			   System.out.println(i.next().getClass().getName());
			   
		   }
		   InputStream is=null;
		try {
		//change to location of the policy ..can be anywhere in a registery, hard drive etc
			is = new FileInputStream(new File("F:/eclipse/EclipseWorkspace/cxfTestWeb/src/main/java/usertoken_policy.xml"));
		} catch (FileNotFoundException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		org.apache.cxf.ws.policy.PolicyBuilder builder = msg.getExchange().getBus() 
                .getExtension(org.apache.cxf.ws.policy.PolicyBuilder.class); 
		   Policy p=null;
		try {
			p = builder.getPolicy(is);
			System.out.println("POLLICYY ------> " + p.getAssertions().get(0));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParserConfigurationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SAXException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		   
		try {
			msg.setContextualProperty(PolicyConstants.POLICY_OVERRIDE, p);
			msg.put(PolicyConstants.POLICY_OVERRIDE, p);
		} catch (Exception e6) {
			e6.printStackTrace();
		}
		System.out.println("msg  property ******** " + msg.getContextualProperty(PolicyConstants.POLICY_OVERRIDE));
		System.out.println("msg  property ******** " + msg.get(PolicyConstants.POLICY_OVERRIDE));
		
	}
		
}
