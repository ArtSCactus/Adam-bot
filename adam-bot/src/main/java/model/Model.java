package model;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exception.DataServerConnectionException;
import model.entity.City;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Provides data handling functionality using {@link CloseableHttpClient} library to retrieve data
 * from the server, which is presented in JSON form.
 *
 * @author ArtSCactus
 * @version 1.1
 */
public class Model implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Model.class);
    private static final int HTTP_NOT_FOUND = 404;
    private static final String DESCRIPTION_NOT_FOUND_MSG = "Эм, кажется, я не знаю такого города.";
    private final String HOST_URL;
    private CloseableHttpClient client;
    private Gson gson;

    public Model(String dataServerHost) {
        client = HttpClientBuilder.create().build();
        gson = new GsonBuilder().create();
        HOST_URL = dataServerHost;
    }

    public Model(String HOST_URL, CloseableHttpClient client) {
        this.HOST_URL = HOST_URL;
        this.client = client;
    }

    /**
     * Basic method to retrieve data from data-server. Executes given {@link HttpUriRequest} and returns json,
     * that was received.
     *
     * @param uri - {@link HttpUriRequest} object.
     * @return json as String object.
     * @throws DataServerConnectionException if attempt to retrieve data was failed.
     */
    public String executeRequestAndExcludeJson(HttpUriRequest uri) throws DataServerConnectionException {
        try {
            try (CloseableHttpResponse response = client.execute(uri)) {
                LOGGER.debug("Request to " + uri.getURI() + " executed.\nResponse status: " + response.getStatusLine().getStatusCode());
                return EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            LOGGER.error("Failed to execute request to data-server");
            throw new DataServerConnectionException("Failed to send request to data server", e);
        }
    }

    public List<City> getAllCities() {
        HttpUriRequest request = new HttpGet(HOST_URL + "/city/all");
        List<City> cities;
        request.addHeader("Content-type", "application/json");
        try {
            String json = executeRequestAndExcludeJson(request);
            Type list = new TypeToken<List<City>>() {
            }.getType();
            cities = gson.fromJson(json, list);
            return cities;
        } catch (DataServerConnectionException e) {
            LOGGER.error("Failed to load all cities.", e);
            throw new DataServerConnectionException(e);
        }
    }

    /**
     * This method is almost same as {@link  Model#executeRequestAndExcludeJson(HttpUriRequest)} method.
     * It's doing 404 status handling, that's needed to send a custom message, that description for this
     * city wasn't found, but not json.
     *
     * @return City description from database or prepared message ({@code DESCRIPTION_NOT_FOUND_MSG})
     * if this wasn't found.
     */
    public String requestCityDescription(HttpUriRequest uri) {
        try {
            try (CloseableHttpResponse response = client.execute(uri)) {
                if (response.getStatusLine().getStatusCode() == HTTP_NOT_FOUND) {
                    return DESCRIPTION_NOT_FOUND_MSG;
                } else {
                    return EntityUtils.toString(response.getEntity());
                }
            }
        } catch (IOException e) {
            throw new DataServerConnectionException("Failed to send request to data server", e);
        }
    }

    public String getCityDescription(String name) {
        HttpUriRequest request = new HttpGet(HOST_URL + "/city/description?name=" + name);
        request.addHeader("Content-type", "application/json");
        return requestCityDescription(request);
    }

    /**
     * A simple method to test data-server availability.
     * <p>
     * FOR DEBUGGING PURPOSE ONLY.
     *
     * @return true if data server is available, false otherwise.
     */
    public boolean testConnectionToDataServer() {
        HttpUriRequest request = new HttpGet(HOST_URL + "/test");
        request.addHeader("Content-type", "application/json");
        try {
            try (CloseableHttpResponse response = client.execute(request)) {
                return response != null && response.getStatusLine().getStatusCode() == 200;
            }

        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Needs to close Http client, that creates at constructor stage.
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        client.close();
    }

    @Override
    protected void finalize() throws Throwable {
        client.close();
    }
}
