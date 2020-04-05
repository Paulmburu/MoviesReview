package tk.paulmburu.moviesreview.util

import org.junit.Assert.*
import org.junit.Test

class BindingAdaptersTest{

    @Test
    fun getStringToBeDisplayed(){
        //GIVEN  a string
        var str = "Ssup"

        //WHEN you call helloWorld
        val result = helloWorld(str);

        // THEN the string given should match the expected
        assertEquals("Ssup", result)
    }


    @Test
    fun getString2ToBeDisplayed(){
        val result = helloWorld("Sup");
        assertEquals("Susp", result)
    }

}