package fr.uv1.bettingServices.Exceptions;

public class NotExistingCompetitorException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public NotExistingCompetitorException() {
        super();
     }
      public NotExistingCompetitorException(String reason) {
        super(reason);
     }
      

}
