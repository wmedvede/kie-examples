package org.kie.example1;

import java.io.PrintStream;

import org.kie.builder.KieContainer;
import org.kie.builder.KieServices;
import org.kie.runtime.KieSession;

/**
 * Hello world!
 *
 */
public class APIExample1  {

    
    public void go(PrintStream out) {
        KieServices ks = KieServices.Factory.get();        
        KieContainer kContainer = ks.newKieClasspathContainer();
        KieSession kSession = kContainer.newKieSession( "ksession1" );
        kSession.setGlobal( "out", out );
        kSession.insert( new Message("Dave", "Hello, HAL. Do you read me, HAL?") );
        kSession.fireAllRules();
    }    
    
    
    public static void main( String[] args ) {
        new APIExample1().go( System.out );
    }

}
