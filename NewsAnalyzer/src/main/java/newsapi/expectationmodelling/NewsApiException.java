package newsapi.expectationmodelling;

public class NewsApiException extends Exception {
    public NewsApiException(String ErrorMessage) {
        super(ErrorMessage);
    }
}
