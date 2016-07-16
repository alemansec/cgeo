package cgeo.geocaching.connector;

import cgeo.geocaching.CgeoApplication;
import cgeo.geocaching.R;
import cgeo.geocaching.enumerations.StatusCode;
import cgeo.geocaching.network.Cookies;
import cgeo.geocaching.network.Network;
import cgeo.geocaching.settings.Credentials;
import cgeo.geocaching.settings.Settings;

import org.apache.commons.lang3.StringUtils;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class AbstractLogin {

    /**
     * {@code true} if logged in, {@code false} otherwise
     */
    private boolean actualLoginStatus = false;
    private String actualUserName = StringUtils.EMPTY;
    /**
     * Number of caches found. An unknown number is signaled by the value -1, while 0 really indicates zero caches found
     * by the user.
     */
    private int actualCachesFound = -1;
    private String actualStatus = StringUtils.EMPTY;

    public void setActualCachesFound(final int found) {
        actualCachesFound = found;
    }

    public String getActualStatus() {
        return actualStatus;
    }

    protected void setActualStatus(final String status) {
        actualStatus = status;
    }

    public boolean isActualLoginStatus() {
        return actualLoginStatus;
    }

    protected void setActualLoginStatus(final boolean loginStatus) {
        actualLoginStatus = loginStatus;
    }

    public String getActualUserName() {
        return actualUserName;
    }

    protected void setActualUserName(final String userName) {
        actualUserName = userName;
    }

    public int getActualCachesFound() {
        return actualCachesFound;
    }

    protected void resetLoginStatus() {
        Cookies.clearCookies();
        Settings.setCookieStore(null);

        setActualLoginStatus(false);
    }

    protected void clearLoginInfo() {
        resetLoginStatus();

        setActualCachesFound(-1);
        setActualStatus(CgeoApplication.getInstance().getString(R.string.err_login));
    }

    @NonNull
    public StatusCode login() {
        return login(null);
    }

    @NonNull
    public StatusCode login(@Nullable final Credentials credentials) {
        if (!Network.isConnected()) {
            return StatusCode.COMMUNICATION_ERROR;
        }
        if (credentials == null) {
            return login(true);
        }
        return login(true, credentials);
    }

    @NonNull
    protected abstract StatusCode login(final boolean retry);

    @NonNull
    protected abstract StatusCode login(final boolean retry, @NonNull final Credentials credentials);

}
