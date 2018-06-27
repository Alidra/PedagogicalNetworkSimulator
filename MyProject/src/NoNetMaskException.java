

public class NoNetMaskException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoNetMaskException( String message ) {
        super( message );
    }

    public NoNetMaskException( String message, Throwable cause ) {
        super( message, cause );
    }

    public NoNetMaskException( Throwable cause ) {
        super( cause );
    }
}
