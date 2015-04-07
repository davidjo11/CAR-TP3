import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import site.graphe.SiteImpl;
import site.graphe.SiteItf;

/**
 * Classe de tests.
 * @author David JOSIAS et Thibaud VERBAERE
 *
 */
public class SiteTests {

	SiteItf s1;
	SiteItf s2;
	SiteItf s3;
	SiteItf s4;
	SiteItf s5;
	SiteItf s6;
	
	/**
	 * Initialisation des sites.
	 */
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
			s2.ajouterVoisin(s1);
			
			s3.ajouterVoisin(s4);
			s3.ajouterVoisin(s2);
			
			s4.ajouterVoisin(s2);
			s4.ajouterVoisin(s3);
			
			s5.ajouterVoisin(s6);
			s5.ajouterVoisin(s1);
			
			s6.ajouterVoisin(s5);
		}
		catch (Exception e) {
			// Ignore
		}
	}
	
	
	/**
	 * Test envoi d'un message depuis la racine : le site 1
	 * Tout les sites doivent avoir reçu le message.
	 */
	@Test
	public void BonDeroulementTest() {
		
		try {
			s1.envoyerMessage("Test".getBytes());
			
			s6.envoyerMessage("Test2".getBytes());
			
			assertTrue(s1.getDatas().contains("Test"));
			assertTrue(s2.getDatas().contains("Test"));
			assertTrue(s3.getDatas().contains("Test"));
			assertTrue(s4.getDatas().contains("Test"));
			assertTrue(s5.getDatas().contains("Test"));
			assertTrue(s6.getDatas().contains("Test"));
			
			assertTrue(s1.getDatas().contains("Test2"));
			assertTrue(s2.getDatas().contains("Test2"));
			assertTrue(s3.getDatas().contains("Test2"));
			assertTrue(s4.getDatas().contains("Test2"));
			assertTrue(s5.getDatas().contains("Test2"));
			assertTrue(s6.getDatas().contains("Test2"));
		}
		catch (Exception e) {
			//Ignore
		}
		
	}
		
	
	/**
	 * Test envoi d'un message après réinitialisation.
	 * On doit ainsi pouvoir effectuer des envois à la chaine.
	 */
	@After
	public void DeroulementSuccessifTest() {

		try {
			s1.reset("Test".getBytes());
			s1.reset("Test2".getBytes());
			
			assertTrue(s1.getDatas().isEmpty());
			assertTrue(s1.getDatas().isEmpty());
			assertTrue(s1.getDatas().isEmpty());
			assertTrue(s1.getDatas().isEmpty());
			assertTrue(s1.getDatas().isEmpty());
			assertTrue(s1.getDatas().isEmpty());
			
			s1.envoyerMessage("Test".getBytes());
			
			assertTrue(s1.getDatas().contains("Test"));
			assertTrue(s2.getDatas().contains("Test"));
			assertTrue(s3.getDatas().contains("Test"));
			assertTrue(s4.getDatas().contains("Test"));
			assertTrue(s5.getDatas().contains("Test"));
			assertTrue(s6.getDatas().contains("Test"));
		}
		catch (Exception e) {
			//Ignore
		}
		
	}
	
}
