package org.kie.example1;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.drools.cdi.example.TestClass;
import org.jboss.weld.environment.se.StartMain;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import org.kie.cdi.KSession;
import org.kie.runtime.KieSession;

public class CDIExample1 {
    @Inject @KSession("ksession1")
    KieSession kSession;
    
    public void go() {
      kSession.insert( new Message("Dave", "Hello, HAL. Do you read me, HAL?") );
      kSession.fireAllRules();
    }    
    
    public static void main( String[] args ) {
        Weld w = new Weld();
        
        WeldContainer wc = w.initialize();
        CDIExample1 bean = wc.instance().select(CDIExample1.class).get();
        bean.go();
        
        w.shutdown();
    }
}
