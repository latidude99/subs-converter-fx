package test.java;

import com.meridian99.subsconverter.app.MainController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Test replaceAll meyhod that is supposed to replace all the Polish spacific characters
 */

public class MainControllerTests {
    MainController mainController;
    String input;
    String expected;
    String actual;


    @BeforeEach
    public void init(){
        mainController = new MainController();
    }

    @AfterEach
    public void tearDown(){
        mainController = null;
        input = null;
        expected = null;
        actual = null;
    }

    /*
     * Tests all the diacritic characters at the same time and multiple other
     * characters to see if they stay intact
     */
    @Test
    @DisplayName("replaceAll method test")
    public void test1(){
        input = "ąęśćółżźń ĄĘŚĆÓŁŻŹŃ" +
                "LLMDVAL'DFJEBcmvdmb;g'/dfb'ldb'b'bnklbdfn'lfnjfb " +
                "fD:ls'jkjh;;jvn'lf/f,;dfvk;dfd/f, f'm df;dfb/dsa" +
                ".,m/x,cmvn.x   /,bnjndkerds;a/WK;FA349O[WVN/,cfd";
        expected = "aescolzzn AESCOLZZN" +
                "LLMDVAL'DFJEBcmvdmb;g'/dfb'ldb'b'bnklbdfn'lfnjfb " +
                "fD:ls'jkjh;;jvn'lf/f,;dfvk;dfd/f, f'm df;dfb/dsa" +
                ".,m/x,cmvn.x   /,bnjndkerds;a/WK;FA349O[WVN/,cfd";
        List<String> inputAsList = new ArrayList<>();
        inputAsList.add(input);

        List<String> actualAsList;
        actualAsList = mainController.replaceAll(inputAsList);
        actual = actualAsList.get(0);
        assertEquals(expected, actual, "one or more of the 18 characters failed to be replaced");

    }

    /*
     * Tests all characters one at the time to pinpoint the failed one
     */
    @ParameterizedTest
    @ValueSource(strings = {"ąescolzzn AESCOLZZN", "aęscolzzn AESCOLZZN", "aeścolzzn AESCOLZZN",
            "aesćolzzn AESCOLZZN", "aescólzzn AESCOLZZN", "aescołzzn AESCOLZZN", "aescolżzn AESCOLZZN",
            "aescolzźn AESCOLZZN", "aescolzzń AESCOLZZN", "aescolzzn ĄESCOLZZN", "aescolzzn AĘSCOLZZN",
            "aescolzzn AEŚCOLZZN", "aescolzzn AESĆOLZZN", "aescolzzn AESCÓLZZN", "aescolzzn AESCOŁZZN",
            "aescolzzn AESCOLŻZN", "aescolzzn AESCOLZŹN", "aescolzzn AESCOLZZŃ"})
    @DisplayName("Parametrized Input, char by char")
    public void parseInputTest_7(String input){
        expected = "aescolzzn AESCOLZZN";

        List<String> inputAsList = new ArrayList<>();
        inputAsList.add(input);

        List<String> actualAsList;
        actualAsList = mainController.replaceAll(inputAsList);
        actual = actualAsList.get(0);

        assertEquals(expected, actual, "a character failed to be replaced");

    }



}






























