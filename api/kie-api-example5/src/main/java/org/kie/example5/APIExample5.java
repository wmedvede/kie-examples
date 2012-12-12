package org.kie.example5;

import java.io.PrintStream;

import org.kie.builder.KieBuilder;
import org.kie.builder.KieContainer;
import org.kie.builder.KieFileSystem;
import org.kie.builder.KieRepository;
import org.kie.builder.KieServices;
import org.kie.builder.Message.Level;
import org.kie.runtime.KieSession;


public class APIExample5 {
    
    public void go(PrintStream out) {
        KieServices ks = KieServices.Factory.get();          
        KieRepository kr = ks.getRepository();
        KieFileSystem kfs = ks.newKieFileSystem();
        
        kfs.write( "src/main/resources/org/kie/example5/HAL5.drl", getRule() );
 
        KieBuilder kb = ks.newKieBuilder( kfs );
        
        kb.buildAll(); // kieModule is automatically deployed to KieRepository if successfully built.
        if ( kb.getResults().hasMessages( Level.ERROR ) ) {
            throw new RuntimeException( "Build Errors:\n" + kb.getResults().toString() );
        }

        KieContainer kContainer = ks.newKieContainer( kr.getDefaultGAV() );

        KieSession kSession = kContainer.newKieSession();
        kSession.setGlobal( "out", out );
                
        kSession.insert( new Message( "Dave", "Hello, HAL. Do you read me, HAL?") );
        kSession.fireAllRules();            
    }
    public static void main( String[] args ) {
        new APIExample5().go( System.out );
    }
   
    
    private static String getRule() {
        String s = "" +
                   "package org.kie.example5 \n\n" +
                   "import org.kie.example5.Message \n\n" +
                   "global java.io.PrintStream out \n\n" +
                   "rule \"rule 1\" when \n" +
                   "    m : Message( ) \n" +
                   "then \n" +
                   "    out.println( m.getName() + \": \" +  m.getText() ); \n" +
                   "end \n" +
                   "rule \"rule 2\" when \n" +
                   "    Message( text == \"Hello, HAL. Do you read me, HAL?\" ) \n" +
                   "then \n" +
                   "    insert( new Message(\"HAL\", \"Dave. I read you.\" ) ); \n" +
                   "end";

        return s;
    }
  
}
