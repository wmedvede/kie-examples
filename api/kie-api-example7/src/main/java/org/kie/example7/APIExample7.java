package org.kie.example7;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.PrintStream;

public class APIExample7 {

    public void go(PrintStream out) {
        KieServices ks = KieServices.Factory.get();

        // Install example1 in the local maven repo before to do this
        KieContainer kContainer = ks.newKieContainer( ks.newReleaseId( "org.kie", "kie-api-example1", "6.0.0-SNAPSHOT") );

        KieSession kSession = kContainer.newKieSession( "ksession1" );
        kSession.setGlobal( "out", out );
        
        Object msg1 = createMessage( kContainer,"Dave", "Hello, HAL. Do you read me, HAL?" );        
        kSession.insert( msg1 );
        kSession.fireAllRules();            
    }
    
    public static void main( String[] args ) {
        new APIExample7().go( System.out );
    }
    
    private static Object createMessage(KieContainer kContainer, String name, String text) {
        Object o = null;
        try {
            Class cl = kContainer.getClassLoader().loadClass( "org.kie.example1.Message" );
            o =  cl.getConstructor( new Class[] { String.class, String.class } ).newInstance( name, text );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return o;
    }

}
