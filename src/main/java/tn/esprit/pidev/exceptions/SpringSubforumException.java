package tn.esprit.pidev.exceptions;

public class SpringSubforumException extends RuntimeException {
    public SpringSubforumException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringSubforumException(String exMessage) {
        super(exMessage);
    }
}
