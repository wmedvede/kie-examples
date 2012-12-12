package org.kie.example35;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;
import org.kie.example35.APIExample35;

import static org.junit.Assert.assertEquals;

public class APIExample35Test {
    
    @Test
    public void testGo() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream( baos ); 
        new APIExample35().go( ps );
        ps.close();
        
        String actual = new String( baos.toByteArray() );
        String expected = "" +
        		"Dave: Hello, HAL. Do you read me, HAL?\n" +
        		"HAL: Dave. I read you.\n";
        assertEquals(expected, actual );
    }
}
