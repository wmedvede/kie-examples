package org.kie.example2;

import javax.inject.Inject;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.kie.cdi.KSession;
import org.kie.example1.Message;
import org.kie.runtime.KieSession;

/**
 * Hello world!
 *
 */
public class Example2  {
    
    @Inject @KSession("ksession2")
    KieSession kSession;
    
    public void go() {
        kSession.insert( new Message("Dave", "Hello, HAL. Do you read me, HAL?") );
        kSession.fireAllRules();
        
        kSession.insert( new Message("Dave", "Open the pod bay doors, HAL.") );
        kSession.fireAllRules();
    }    
    
    public static void main( String[] args ) {
        Weld w = new Weld();
        
        WeldContainer wc = w.initialize();
        Example2 bean = wc.instance().select(Example2.class).get();
        bean.go();
        
        w.shutdown();
    }    
}
