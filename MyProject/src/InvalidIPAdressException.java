

public class InvalidIPAdressException extends Exception {
	 
	private static final long serialVersionUID = 1L;
	
	public InvalidIPAdressException( String message ) {
	        super( message );
	    }
	    public InvalidIPAdressException( String message, Throwable cause ) {
	        super( message, cause );
	    }

	    public InvalidIPAdressException( Throwable cause ) {
	        super( cause );
	    }
}
