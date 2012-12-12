package org.kie.example6;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class APIExample2Test {
    
    @Test
    public void testGo() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream( baos ); 
        new APIExample6().go( ps );
        ps.close();
        
        String actual = new String( baos.toByteArray() );
        String expected = "" +
        		"Dave: Hello, HAL. Do you read me, HAL?\n" +
        		"HAL: Dave. I read you.\n" +
        		"Dave: Open the pod bay doors, HAL.\n" +
        		"HAL: I'm sorry, Dave. I'm afraid I can't do that.\n" +
        		"Dave: What's the problem?\n" +
        		"HAL: I think you know what the problem is just as well as I do.\n";
        assertEquals(expected, actual );
    }
}
