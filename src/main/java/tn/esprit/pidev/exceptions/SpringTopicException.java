package tn.esprit.pidev.exceptions;

public class SpringTopicException  extends RuntimeException {
    public SpringTopicException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringTopicException(String exMessage) {
        super(exMessage);
    }
}
