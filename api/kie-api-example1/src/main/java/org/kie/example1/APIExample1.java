package org.kie.example1;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.PrintStream;

public class APIExample1  {
    
    public void go(PrintStream out) {
        KieServices ks = KieServices.Factory.get();        
        KieContainer kContainer = ks.getKieClasspathContainer();
        
        KieSession kSession = kContainer.newKieSession( "ksession1" );
        kSession.setGlobal( "out", out );
        kSession.insert( new Message("Dave", "Hello, HAL. Do you read me, HAL?") );
        kSession.fireAllRules();

        //kSession.startProcess("com.sample.bpmn.hello");
    }    
    
    
    public static void main( String[] args ) {
        new APIExample1().go( System.out );
    }

}
