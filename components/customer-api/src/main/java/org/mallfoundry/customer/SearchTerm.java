package org.mallfoundry.customer;

public interface SearchTerm {

    String getCustomerId();

    String getTerm();

    long getTimestamp();

    int getHits();

    int incrementHits();

    int decrementHits();
}
