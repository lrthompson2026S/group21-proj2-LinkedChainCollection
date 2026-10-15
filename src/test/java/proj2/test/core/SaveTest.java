package proj2.test.core;

import proj2.collections.Collection;
import proj2.core.Save;
import student.TestCase;

public class SaveTest extends TestCase{


    public void setUp(){

    }

    public void testLoadObject(){
        assertNull(Save.load("NameDoesNotExistOnPurpose"));
        assertTrue((Collection) Save.load("Test1.ser") instanceof Collection);
    }

    public void testValidateFilename(){
        String validName = "myCollection";
        String emptyName = "";
        String invalidCharacterName = "???";
        String tooLongName = "";
        while(tooLongName.length() <= 50){
            tooLongName += "a";
        }

        assertTrue(Save.validateFilename(validName));
        assertFalse(Save.validateFilename(emptyName));
        assertFalse(Save.validateFilename(invalidCharacterName));;
        assertFalse(Save.validateFilename(tooLongName));
    }
}
