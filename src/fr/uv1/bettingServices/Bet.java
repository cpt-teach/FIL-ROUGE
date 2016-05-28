package fr.uv1.bettingServices;


import java.sql.SQLException;
import fr.uv1.competition.*;
import fr.uv1.bettingServices.Subscriber;




	public class Bet {
		protected int id;
		private long numberTokens; 
	    private Competitor first;
	    private Competitor second; 
	    private Competitor third;
	    private Competition competition;
	    private Subscriber bettor;
	    private int ifPodium;
	    
	    // Constructor
		public Bet(long bettorbet, Competitor first,Competitor second
				,Competitor third, Competition competition, Subscriber bettor, int ifPodium){
			this.numberTokens=bettorbet;
		    this.first=first;
		    this.second=second;
		    this.third=third;
		    this.competition=competition;
		    this.bettor=bettor;
		    this.ifPodium=ifPodium;
		}
	//getter	
		
		
		public int getId(){
			return this.id;
		}
		public long getBettorBet(){
			return this.numberTokens;
		}
		public Competitor getfirst(){
			return this.first;
		}
		public Competitor getsecond(){
			return this.second;
		}
		public Competitor getthird(){
			return this.third;
		}
		public Competition getCompetition(){
			return this.competition;
		}
		public Subscriber getBettor(){
			return this.bettor;
		}
				
		public int getifPodium(){
			return this.ifPodium;
		}
	//Setter
		public void setId(int id){
			this.id=id;
		}
		public void setBettorbet(long tokens){
			this.numberTokens=tokens;
		}
		public void setfirst(Competitor first){
			this.first=first;
		}
		public void setsecond(Competitor second){
			this.first=second;
		}
		public void setthird(Competitor third){
			this.first=third;
		}
		public void setSubscriber(Subscriber bettor){
			this.bettor=bettor;
		}
		
		public void setifPodium(int ifPodium){
			this.ifPodium=ifPodium;
		}
	    		
	    		
		// checks if the podium is valid
		public boolean validPodium(int firstId, int secondId,
				int thirdId) throws SQLException {
			return (this.first.getId()==(firstId) && 
					this.second.getId()==(secondId) && 
					this.third.getId()==(thirdId));
		}

        public void toString (Bet bet){ //à voir son utilité!!
        	if (bet.getifPodium()==1){
        	System.out.print("this bet is made by"+bet.getBettor().getFirstName()
        			+""+bet.getBettor().getLastName()+"on the podium:/n"+"winner:"+bet.getfirst().toString()+"/n"+
        			bet.getsecond().toString()+"/n"+"third:"+bet.getthird().toString()+"/n"
        			+"on the competition"+bet.getCompetition().toString()+"betting"+bet.getBettorBet());
        		}
        	else {
        		System.out.print("this bet is made by"+bet.getBettor().getFirstName()
            			+""+bet.getBettor().getLastName()+"on the winner:/n"+"winner:"+bet.getfirst().toString()+"/n"+
            			"on the competition"+bet.getCompetition().toString()+"betting"+bet.getBettorBet());
        	}
        }
	
}

