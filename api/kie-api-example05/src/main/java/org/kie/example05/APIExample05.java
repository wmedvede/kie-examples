package org.kie.example05;

import java.io.PrintStream;

import org.kie.KieServices;
import org.kie.runtime.KieContainer;
import org.kie.runtime.KieSession;

public class APIExample05  {
    
    public void go(PrintStream out) {
        KieServices ks = KieServices.Factory.get();        
        KieContainer kContainer = ks.getKieClasspathContainer();
        
        KieSession kSession = kContainer.newKieSession( );
        kSession.setGlobal( "out", out );
        kSession.insert( new Message("Dave", "Hello, HAL. Do you read me, HAL?") );
        kSession.fireAllRules();
    }    
    
    
    public static void main( String[] args ) {
        new APIExample05().go( System.out );
    }

}
