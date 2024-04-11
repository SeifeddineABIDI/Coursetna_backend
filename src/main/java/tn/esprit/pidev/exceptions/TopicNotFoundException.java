package tn.esprit.pidev.exceptions;

public class TopicNotFoundException extends RuntimeException{
    public TopicNotFoundException(String message) {
        super(message);
    }
}
