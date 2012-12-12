package org.kie.example1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;
import org.kie.example1.APIExample1;

import static org.junit.Assert.assertEquals;

public class APIExample1Test {
    
    @Test
    public void testGo() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream( baos ); 
        new APIExample1().go( ps );
        ps.close();
        
        String actual = new String( baos.toByteArray() );
        String expected = "" +
        		"Dave: Hello, HAL. Do you read me, HAL?\n" +
        		"HAL: Dave. I read you.\n";
        assertEquals(expected, actual );
    }
}
