package org.kie.example8;

import org.kie.builder.KieContainer;
import org.kie.builder.KieFactory;
import org.kie.builder.KieServices;
import org.kie.runtime.KieSession;

public class APIExample8 {

    public static void main( String[] args ) {
        KieServices ks = KieServices.Factory.get();
        KieFactory kf = KieFactory.Factory.get();

        // Install example1 in the local maven repo before to do this
        KieContainer kContainer = ks.newKieContainer( kf.newGav("org.kie", "kie-api-example2", "6.0.0-SNAPSHOT") );

        KieSession kSession = kContainer.newKieSession( "ksession1" );
        Object msg1 = createMessage( kContainer,"Dave", "Hello, HAL. Do you read me, HAL?" );        
        kSession.insert( msg1 );
        kSession.fireAllRules();
        
        Object msg2 = createMessage( kContainer, "Dave", "Open the pod bay doors, HAL." );        
        kSession.insert( msg2 );
        kSession.fireAllRules();
        
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
