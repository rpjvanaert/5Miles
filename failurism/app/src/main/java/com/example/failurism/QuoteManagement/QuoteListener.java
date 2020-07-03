package com.example.failurism.QuoteManagement;

import com.example.failurism.ApiManagement.ApiImage;

public interface QuoteListener {
    void onQuoteAvailable(String quote);
    void onQuoteError(Error error);
}
