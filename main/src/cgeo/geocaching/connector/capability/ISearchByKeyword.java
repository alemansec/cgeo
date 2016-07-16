package cgeo.geocaching.connector.capability;

import cgeo.geocaching.SearchResult;
import cgeo.geocaching.connector.IConnector;
import cgeo.geocaching.loaders.RecaptchaReceiver;

import android.support.annotation.NonNull;

/**
 * Connector capability of searching online for a cache by keyword.
 *
 */
public interface ISearchByKeyword extends IConnector {
    // TODO: The recaptcha receiver is only needed for GC. Would be good to refactor this away from the generic interface.
    SearchResult searchByKeyword(@NonNull final String keyword, @NonNull final RecaptchaReceiver recaptchaReceiver);
}
