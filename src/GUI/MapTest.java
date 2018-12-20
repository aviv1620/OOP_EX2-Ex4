/**
 * 
 */
package GUI;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

/**
 * @author Aviv Vexler
 * Test the map.
 */
class MapTest {

	@Test
	void test() throws IOException {
		new MyFrame((int)(1433/1.5),(int)(642/1.5),true);
		
		//ask user if is see the map.
		System.out.println("\n\n\nare you see the map?y=yse,n=no.");
		Scanner reader = new Scanner(System.in);
		char c = reader.next().charAt(0);
		
		//if user see is god.
		if(c == 'y')
			assertTrue(true);
		else
			assertTrue(false);
		
		reader.close();

	}

}
