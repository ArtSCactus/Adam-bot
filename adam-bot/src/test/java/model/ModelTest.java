package model;

import exception.DataServerConnectionException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


/**
 * @author ArtSCactus
 * @version 1.0
 */
public class ModelTest extends Mockito {
    private static final String DATA_SERVER_HOST = "http://localhost:8080";
    private static final String CITIES_LIST_JSON = "[{" +
            "\"name\":Minsk," +
            "\"description\":Description}," +
            "{" +
            "\"name\":Vitebsk," +
            "\"description\":Description" +
            "}]";
    private static final String TEST_CITY_DESCRIPTION = "Description";
    private HttpClient client;
    private Model model;

    @BeforeClass
    public static void globalInit() {
        BasicConfigurator.configure();
    }

    @Before
    public void init() {
        client = mock(HttpClient.class);
        model = new Model(DATA_SERVER_HOST, client);
    }


    @Test
    public void should_return_json_from_http_response_if_everything_ok() throws IOException {

        HttpGet get = mock(HttpGet.class);
        HttpResponse response = mock(HttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);

        InputStream stream = new ByteArrayInputStream(CITIES_LIST_JSON.getBytes());
        BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContent(stream);
        stream.close();
        response.setEntity(entity);

        when(statusLine.getStatusCode()).thenReturn(200);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(get.getURI()).thenReturn(URI.create(DATA_SERVER_HOST + "/city/all"));
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(get)).thenReturn(response);

        String json = model.executeRequestAndExcludeJson(get);
        Assert.assertEquals(CITIES_LIST_JSON, json);
    }

    @Test(expected = DataServerConnectionException.class)
    public void should_throw_exception_if_data_server_is_not_respond() throws IOException {
        HttpGet get = mock(HttpGet.class);
        when(client.execute(get)).thenThrow(IOException.class);
        model.executeRequestAndExcludeJson(get);

    }

    @Test
    public void should_return_correct_city_description() throws IOException {
        HttpGet get = mock(HttpGet.class);
        HttpResponse response = mock(HttpResponse.class);
        InputStream stream = new ByteArrayInputStream(TEST_CITY_DESCRIPTION.getBytes());
        StatusLine statusLine = Mockito.mock(StatusLine.class);
        BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContent(stream);
        stream.close();
        response.setStatusLine(statusLine);
        response.setEntity(entity);

        when(client.execute(get)).thenReturn(response);
        when(response.getEntity()).thenReturn(entity);
        when(response.getStatusLine()).thenReturn(statusLine);
        when(statusLine.getStatusCode()).thenReturn(200);

        String methodResult = model.requestCityDescription(get);
        Assert.assertEquals(TEST_CITY_DESCRIPTION, methodResult);

    }

}
