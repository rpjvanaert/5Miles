package five.miles.failurism.QuoteManagement;

public interface QuoteListener {
    void onQuoteAvailable(String quote);
    void onQuoteError(Error error);
}
