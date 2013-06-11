package org.kie.example2;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.example1.Message;

import java.io.PrintStream;

public class APIExample2 {
    
    public void go(PrintStream out) {
        KieServices ks = KieServices.Factory.get();        
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession( "ksession2" );
        kSession.setGlobal( "out", out );
        
        kSession.insert( new Message("Dave", "Hello, HAL. Do you read me, HAL?") );
        kSession.fireAllRules();
        
        kSession.insert( new Message("Dave", "Open the pod bay doors, HAL.") );
        kSession.fireAllRules();
    }
    
    public static void main( String[] args ) {
        new APIExample2().go( System.out );
    }

}
