package org.kie.example2;

import java.io.PrintStream;

import javax.inject.Inject;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.kie.api.cdi.KSession;
import org.kie.example1.Message;
import org.kie.api.runtime.KieSession;

/**
 * Hello world!
 *
 */
public class CDIExample2  {
    
    @Inject @KSession("ksession2")
    KieSession kSession;
    
    public void go(PrintStream out) {
        kSession.setGlobal( "out", out );
        kSession.insert( new Message("Dave", "Hello, HAL. Do you read me, HAL?") );
        kSession.fireAllRules();
        
        kSession.insert( new Message("Dave", "Open the pod bay doors, HAL.") );
        kSession.fireAllRules();
    }    
    
    public static void main( String[] args ) {
        Weld w = new Weld();
        
        WeldContainer wc = w.initialize();
        CDIExample2 bean = wc.instance().select(CDIExample2.class).get();
        bean.go( System.out );
        
        w.shutdown();
    }

}
