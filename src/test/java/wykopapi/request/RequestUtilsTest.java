package wykopapi.request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestUtilsTest {
    private RequestUtils requestUtils;

    @Before
    public void setUp() {
        requestUtils = new RequestUtils();
    }

    @Test
    public void getMD5() throws Exception {
        assertEquals("098F6BCD4621D373CADE4E832627B4F6", requestUtils.getMD5("test"));
        assertEquals("AD0234829205B9033196BA818F7A872B", requestUtils.getMD5("test2"));
        assertEquals("2B53C83565E5AE675F0E0FA67D1CFB0B", requestUtils.getMD5("http://a.wykop.pl"));
    }
}