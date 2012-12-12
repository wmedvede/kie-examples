package org.kie.example6;

import java.io.File;
import java.io.PrintStream;

import org.kie.KieServices;
import org.kie.builder.KieBuilder;
import org.kie.builder.KieFileSystem;
import org.kie.builder.KieModuleModel;
import org.kie.builder.Message.Level;
import org.kie.builder.ReleaseId;
import org.kie.io.Resource;
import org.kie.runtime.KieContainer;
import org.kie.runtime.KieSession;

public class APIExample6 {
    
    public void go(PrintStream out) {
        KieServices ks = KieServices.Factory.get();          
        KieFileSystem kfs = ks.newKieFileSystem();
        
        Resource ex1Res = ks.getResources().newFileSystemResource( getFile("kie-api-example1") ) ;
        Resource ex2Res = ks.getResources().newFileSystemResource( getFile("kie-api-example2") ) ;             
        
        ReleaseId rid = ks.newReleaseId(  "org.kie", "kie-example6", "6.0.0-SNAPSHOT" );        
        kfs.generateAndWritePomXML( rid );
        
        KieModuleModel kModuleModel = ks.newKieModuleModel();
        kModuleModel.newKieBaseModel( "org.kie.example6" )
                    .addInclude( "org.kie.example1" )
                    .addInclude( "org.kie.example2") 
                    .newKieSessionModel( "ksession6" );
        
        kfs.writeKModuleXML( kModuleModel.toXML() );        
        kfs.write( "src/main/resources/org/kie/example6/HAL6.drl", getRule() );
        
        KieBuilder kb = ks.newKieBuilder( kfs );
        kb.setDependencies( ex1Res, ex2Res);
        kb.buildAll(); // kieModule is automatically deployed to KieRepository if successfully built.
        if ( kb.getResults().hasMessages( Level.ERROR ) ) {
            throw new RuntimeException( "Build Errors:\n" + kb.getResults().toString() );
        }

        KieContainer kContainer = ks.newKieContainer( rid );

        KieSession kSession = kContainer.newKieSession( "ksession6" );
        kSession.setGlobal( "out", out );
        
        Object msg1 = createMessage(kContainer, "Dave", "Hello, HAL. Do you read me, HAL?");        
        kSession.insert( msg1 );
        kSession.fireAllRules();              

        Object msg2 = createMessage(kContainer, "Dave", "Open the pod bay doors, HAL.");        
        kSession.insert( msg2 );
        kSession.fireAllRules();
        
        Object msg3 = createMessage(kContainer, "Dave", "What's the problem?");        
        kSession.insert( msg3 );
        kSession.fireAllRules(); 
    }
    
    public static void main( String[] args ) {
         new APIExample6().go( System.out );
    }
   
    
    private static String getRule() {
        String s = "" +
                "package org.kie.example6 \n\n" +
                "import org.kie.example1.Message \n\n" +
                "rule rule6 when \n" +
                "    Message(text == \"What's the problem?\") \n" +
                "then\n" +
                "    insert( new Message(\"HAL\", \"I think you know what the problem is just as well as I do.\" ) ); \n" +
                "end \n";
        
        return s;
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
    
    public static File getFile(String exampleName) {
        File folder = new File( "." ).getAbsoluteFile();
        File exampleFolder = null;
        while ( folder != null ) {
            exampleFolder = new File( folder,
                                      exampleName );
            if ( exampleFolder.exists() ) {
                break;
            }
            exampleFolder = null;
            folder = folder.getParentFile();
        }        

        if ( exampleFolder != null ) {
            
            File targetFolder = new File( exampleFolder,
                                          "target" );
            if ( !targetFolder.exists() ) {
                throw new RuntimeException("The target folder does not exist, please build project " + exampleName + " first");
            }
            
            for ( String str : targetFolder.list() ) {
                if ( str.startsWith( exampleName ) && !str.endsWith( "-sources.jar" ) && !str.endsWith( "-tests.jar" ) ) {
                    return new File( targetFolder, str );
                }
            }
        }
        
        throw new RuntimeException("The target jar does not exist, please build project " + exampleName + " first");
    }

}
