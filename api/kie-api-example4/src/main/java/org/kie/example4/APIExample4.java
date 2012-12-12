package org.kie.example4;

import java.io.File;
import java.io.PrintStream;

import org.kie.KieServices;
import org.kie.builder.KieModule;
import org.kie.builder.KieRepository;
import org.kie.io.Resource;
import org.kie.runtime.KieContainer;
import org.kie.runtime.KieSession;


public class APIExample4  {
    
    public void go(PrintStream out) {
        KieServices ks = KieServices.Factory.get();  
        
        KieRepository kr = ks.getRepository();
        
        Resource ex1Res = ks.getResources().newFileSystemResource( getFile("kie-api-example1") ) ;
        Resource ex2Res = ks.getResources().newFileSystemResource( getFile("kie-api-example2") ) ;
        
        KieModule kModule = kr.addKieModule(ex1Res,  ex2Res);                
        KieContainer kContainer = ks.newKieContainer( kModule.getReleaseId() );

        KieSession kSession = kContainer.newKieSession( "ksession2" );
        kSession.setGlobal( "out", out );
        
        Object msg1 = createMessage(kContainer, "Dave", "Hello, HAL. Do you read me, HAL?");        
        kSession.insert( msg1 );
        kSession.fireAllRules();              

        Object msg2 = createMessage(kContainer, "Dave", "Open the pod bay doors, HAL.");        
        kSession.insert( msg2 );
        kSession.fireAllRules();  
    }
    
    public static void main( String[] args ) {
        new APIExample4().go( System.out );        
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
