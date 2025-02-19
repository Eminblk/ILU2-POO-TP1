package villagegaulois;

public class EtalNonOccupeException extends Exception {
	
	private static final long serialVersionUID=1L;
	public EtalNonOccupeException(String message) {
        super(message);
    }
	
	public EtalNonOccupeException(Throwable cause) {
        super(cause);
    }
	
	public EtalNonOccupeException(String message, Throwable cause) {
        super(message, cause);
    }
}
