package wykopapi.api.extractor;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

public class ExtractorTest {
    private Extractor extractor;

    @Before
    public void setUp() throws Exception {
        extractor = new Extractor();
    }

    @Test
    public void extractFormBody() throws Exception {
        RequestBody requestBody = new FormBody.Builder()
                .add("key1", "value1")
                .add("embed", "value2")
                .build();

        Map<String, String> parameters = extractor.extract(requestBody);

        assertEquals(2, parameters.size());
        assertEquals("value1", parameters.get("key1"));
        assertEquals("value2", parameters.get("embed"));
    }

    @Test
    public void extractMultipartBody() throws Exception {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("formkey", "formvalue")
                .addFormDataPart("formkey2", "formvalue2")
                .addFormDataPart("embed", "filename.jpg", RequestBody.create(MediaType.parse("image/jpeg"), new File("file.jpg")))
                .build();

        Map<String, String> parameters = extractor.extract(requestBody);

        assertEquals(2, parameters.size());
        assertEquals("formvalue", parameters.get("formkey"));
        assertEquals("formvalue2", parameters.get("formkey2"));
    }

    @Test
    public void extractAnonymousBody() throws Exception {
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

            }
        };

        Map<String, String> parameters = extractor.extract(requestBody);

        assertEquals(0, parameters.size());
    }

    @Test
    public void extractNullBody() throws Exception {
        Map<String, String> parameters = extractor.extract(null);

        assertEquals(0, parameters.size());
    }
}