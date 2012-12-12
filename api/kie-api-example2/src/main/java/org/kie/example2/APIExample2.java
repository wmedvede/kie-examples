package org.kie.example2;

import java.io.PrintStream;

import org.kie.KieServices;
import org.kie.runtime.KieContainer;
import org.kie.runtime.KieSession;

import org.kie.example1.Message;

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
