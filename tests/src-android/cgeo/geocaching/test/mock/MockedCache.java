package cgeo.geocaching.test.mock;

import junit.framework.Assert;

import org.apache.commons.lang3.StringUtils;
import android.support.annotation.NonNull;
import org.mapsforge.v3.core.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cgeo.geocaching.connector.gc.GCConstants;
import cgeo.geocaching.location.Geopoint;
import cgeo.geocaching.models.Geocache;
import cgeo.geocaching.models.Image;
import cgeo.geocaching.models.Trackable;
import cgeo.geocaching.utils.TextUtils;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class MockedCache extends Geocache {

    protected final Geopoint coords;
    private final String data;
    private String mockedDataUser;

    public static final List<MockedCache> MOCKED_CACHES;
    static {
        MOCKED_CACHES = Collections.unmodifiableList(Arrays.asList(new GC2CJPF(), new GC1ZXX2(), new GC2JVEH(), new GC3XX5J()));
    }

    protected MockedCache(final Geopoint coords) {
        this.coords = coords;
        this.data = MockedCache.readCachePage(getGeocode());
        // for mocked caches the user logged in is the user who saved the html file(s)
        this.mockedDataUser = TextUtils.getMatch(data, GCConstants.PATTERN_LOGIN_NAME, true, "");
    }

    public String getMockedDataUser() {
        assertThat(StringUtils.isNotBlank(this.mockedDataUser)).isTrue();
        return mockedDataUser;
    }

    public void setMockedDataUser(final String mockedDataUser) {
        this.mockedDataUser = mockedDataUser;
        assertThat(StringUtils.isNotBlank(this.mockedDataUser)).isTrue();
    }

    public static String getDateFormat() {
        return "yyyy-MM-dd";
    }

    /*
     * The data for the caches can be generated by entering the url
     * https://www.geocaching.com/seek/cache_details.aspx?log=y&wp=GCxxxx&numlogs=35&decrypt=y
     * into a browser and saving the file
     */
    public String getData() {
        return this.data;
    }

    public static String readCachePage(final String geocode) {
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = MockedCache.class.getResourceAsStream("/cgeo/geocaching/test/mock/" + geocode + ".html");
            br = new BufferedReader(new InputStreamReader(is), 150000);

            final StringBuilder buffer = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                buffer.append(line).append('\n');
            }

            return TextUtils.replaceWhitespace(buffer.toString());
        } catch (final IOException e) {
            Assert.fail(e.getMessage());
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(br);
        }
        return null;
    }

    @Override
    public boolean isArchived() {
        return false;
    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    @Override
    public boolean isPremiumMembersOnly() {
        return false;
    }

    @Override
    public boolean isOwner() {
        return false;
    }

    @Override
    public String getHint() {
        return "";
    }

    @Override
    public String getShortDescription() {
        return "";
    }

    @Override
    public String getPersonalNote() {
        return null;
    }

    @Override
    public boolean isFound() {
        return false;
    }

    @Override
    public boolean isFavorite() {
        return false;
    }

    @Override
    public int getFavoritePoints() {
        return 0;
    }

    @Override
    public boolean isOnWatchlist() {
        return false;
    }

    @Override
    public List<Trackable> getInventory() {
        return null;
    }

    @Override
    @NonNull
    public List<Image> getSpoilers() {
        return Collections.emptyList();
    }

    @Override
    public String getNameForSorting() {
        return getName();
    }

    @Override
    public Geopoint getCoords() {
        return coords;
    }
}
