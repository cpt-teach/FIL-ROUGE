package fr.uv1.bettingServices;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.uv1.bettingServices.*;
import fr.uv1.competition.Competition;
import fr.uv1.competition.Competitor;

public class BetOnPodiumTest {
    public Competitor winner, second, third;
    public Subscriber bettor;
    public Competition competition;
	@Before
	public void setUp() throws Exception {
		String cName;
		Competitor winner, second, third;
		Competitor winnerTeam, secondTeam, thirdTeam;
		ArrayList<Competitor> competitors;
		ArrayList<Competitor> competitorTeams;
		Scanner sc = new Scanner(System.in);
		String c = "x";
	}
	
	public String getResponse() {
		Scanner sc = new Scanner(System.in);
		String s = "x";
		while (!s.equals("y") && !s.equals("n")) {
			s = sc.next();
		}
		return s;
	}
	@After
	public void tearDown() throws Exception {
	}


	private void testWithNullParameters() {
		// Tests "entries" : null
		try {
			Bet bet= new Bet(42, null, null, null, null,null,1);
			System.out.println("Add a bet On podium, parameters missing ");
		} catch (Exception tfou) {
		}
		try {
			Bet bet= new Bet(42, winner, null, null, null,null, 1);
			System.out
					.println("Adding a bet On podium..., only winner given");
		} catch (Exception tfou) {
		}
		try {
			Bet bet= new Bet(42, null, second, null, null,null, 1);
			System.out.println("Adding a bet On podium ...; only second given ");
		} catch (Exception tfou) {
		}
		try {
			Bet bet=new Bet(42, winner, second, third, null,null,1);
			System.out.println("Adding a bet On podium ...; only podium was given ");
		} catch (Exception tfou) {
		}
		try {
			Bet bet=new Bet(42, winner, second, third, null,bettor,1);
			System.out.println("Adding a bet On podium ...; only competition is missing ");
		} catch (Exception tfou) {

		}
		try {
			Bet bet=new Bet(42, null, null, null, null,bettor,1);
			System.out.println("Adding a bet On podium ...; only bettor is given");
		} catch (Exception tfou) {
		}
		try {
			Bet bet=new Bet(42, winner,second,third,competition,bettor,0);
			System.out.println("Adding a bet On podium ...; IfPodium isn't set");
		} catch (Exception tfou) {
		}
		try {
			Bet bet=new Bet(42, winner,winner,winner,competition,bettor,1);
			System.out.println("Adding a bet On podium ...; check competitors, it seems like it is a bet On winner");
		} catch (Exception tfou) {
		}
		try {
			Bet bet=new Bet(42, second,second,second,competition,bettor,1);
			System.out.println("Adding a bet On podium ...; check competitors, it seems like it is a bet On winner");
		} catch (Exception tfou) {
		}
		try {
			Bet bet=new Bet(42, third,third,third,competition,bettor,1);
			System.out.println("Adding a bet On podium ...; check competitors, it seems like it is a bet On winner");
		} catch (Exception tfou) {
		}

		try {
			Bet bet=new Bet(42, winner,null,null,competition,bettor,1);
			System.out.println("Adding a bet On podium ...; check competitors, it seems like it is a bet On winner");
		} catch (Exception tfou) {
		}

	
		try {
			Bet bet=new Bet(42, null,null,null,competition,bettor,1);
			System.out.println("Adding a bet On podium ...; Missing all competitors ");
		} catch (Exception tfou) {
		}
		try {
			Bet bet=new Bet(42, winner,null,null,competition,bettor,1);
			System.out.println("Adding a bet On podium ...; Missing second and third competitors ");
		} catch (Exception tfou) {
		}
		try {
			Bet bet=new Bet(42, null,second,null,competition,bettor,1);
			System.out.println("Adding a bet On podium ...; Missing winner and third competitors ");
		} catch (Exception tfou) {
		}
		try {
			Bet bet=new Bet(42, null,null,third,competition,bettor,1);
			System.out.println("Adding a bet On podium ...; Missing winner and second competitors ");
		} catch (Exception tfou) {
		}	}
	
	
	
	private void testWithInvalidParameters() {
		// Tests invalid parameters
		try {
			Bet bet=new Bet(-42, winner,second,third,competition,bettor,1);
			System.out.println("Adding a bet On podium ...; but invalid tokens number -42 ");
		} catch (Exception tfou) {
		}	
		try {
			Bet bet=new Bet(42, winner,second,third,null,bettor,1);
			System.out.println("Adding a bet On podium ...; but invalid tokens number -42 ");
		} catch (Exception tfou) {
		}
		
	}
	private void testBettorIsACompetitor() {
		System.out.println("wait for stuff to be maid");
		// Tests if Bettor is a Competitor 
		// Subscribe some one 
		/*subscribe(new String("Duranto"),
		new String("Miguel"), new String("titi"), new String("13-12-1983"),
		new String(increment.getManagerPassword()));
		String pwdTiti = null;*/

	}

	private void testNotEnoughTokens() {
		try {
			Bet bet=new Bet(420000, winner,second,third,competition,bettor,1);
			System.out.println("Adding a bet On podium ...; But Not enough Tokens ");
		} catch (Exception e) {
		}
	}

	
	
	@Test
	public void BetOnPodiumValidationTests() throws Exception {

			System.out.println("Bet On Podium?");
			String c = "x";
			c = getResponse();
			if (c.equals("y")) {
				this.setUp();

				this.testWithNullParameters();
				System.out.println("  >>>>> Fin tests paramètre non instancié\n");

				this.testWithInvalidParameters();
				System.out.println("  >>>>> Fin tests paramètre invalide\n");

				System.out
						.print("  ----- Parier podium par un compétiteur de la compétition ? (y/n)\n");
				String resp = getResponse();
				if (resp.equals("y")) {
					this.testBettorIsACompetitor();
					System.out
							.println("  >>>>> Fin tests parieur=compétiteur de la compétition\n");
				}

			    

				this.testNotEnoughTokens();
				System.out.println("  >>>>> Fin tests pas assez de jetons\n");
			}

			
	}
	
	
	
	

}
