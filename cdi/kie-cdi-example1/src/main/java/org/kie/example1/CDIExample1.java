package org.kie.example1;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;

import javax.inject.Inject;
import java.io.PrintStream;

public class CDIExample1 {

    @Inject @KSession("ksession1")
    KieSession kSession;
    
    public void go(PrintStream out) {
        kSession.setGlobal( "out", out );
        kSession.insert( new Message("Dave", "Hello, HAL. Do you read me, HAL?") );
        kSession.fireAllRules();
    }    
    
    public static void main( String[] args ) {
        Weld w = new Weld();
        
        WeldContainer wc = w.initialize();
        CDIExample1 bean = wc.instance().select(CDIExample1.class).get();
        bean.go( System.out );
        
        w.shutdown();
    }

}
