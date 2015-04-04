import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import site.graphe.SiteImpl;
import site.graphe.SiteItf;

public class SiteTests {

	SiteItf s1;
	SiteItf s2;
	SiteItf s3;
	SiteItf s4;
	SiteItf s5;
	SiteItf s6;
	
	@Before
	public void setUp() {
		try {
			s1 = new SiteImpl("Site 1");
			s2 = new SiteImpl("Site 2");
			s3 = new SiteImpl("Site 3");
			s4 = new SiteImpl("Site 4");
			s5 = new SiteImpl("Site 5");
			s6 = new SiteImpl("Site 6");
			
			s1.ajouterVoisin(s2);
			s1.ajouterVoisin(s5);
			
			s2.ajouterVoisin(s3);
			s2.ajouterVoisin(s4);
			
			s3.ajouterVoisin(s4);
			
			s5.ajouterVoisin(s6);
		}
		catch (Exception e) {
			// Ignore
		}
	}
	
	
	@Test
	public void BonDeroulementTest() {
		
		try {
			s1.envoyerMessage("Test".getBytes());
			
			assertTrue(s1.isVisited());
			assertTrue(s2.isVisited());
			assertTrue(s3.isVisited());
			assertTrue(s4.isVisited());
			assertTrue(s5.isVisited());
			assertTrue(s6.isVisited());
		}
		catch (Exception e) {
			//Ignore
		}
		
	}
	
	@After
	public void DeroulementSuccessifTest() {

		try {
			s1.reset();
			
			assertFalse(s1.isVisited());
			assertFalse(s2.isVisited());
			assertFalse(s3.isVisited());
			assertFalse(s4.isVisited());
			assertFalse(s5.isVisited());
			assertFalse(s6.isVisited());
			
			s1.envoyerMessage("Test".getBytes());
			
			assertTrue(s1.isVisited());
			assertTrue(s2.isVisited());
			assertTrue(s3.isVisited());
			assertTrue(s4.isVisited());
			assertTrue(s5.isVisited());
			assertTrue(s6.isVisited());
		}
		catch (Exception e) {
			//Ignore
		}
		
	}
	
}
