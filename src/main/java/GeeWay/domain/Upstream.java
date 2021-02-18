package GeeWay.domain;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.json.JsonObject;

import java.net.MalformedURLException;
import java.net.URL;

public class Upstream {

    String url;
    String path;
    int weight;

    public String getPath() {
        return path;
    }

    public String getUrl() {
        return url;
    }

    public HttpClient getClient() {
        return client;
    }

    public int getWeight() {
        return weight;
    }

    HttpClient client;

    public Upstream(JsonObject json, Vertx vertx) {
        this.url = json.getString("url");
        this.weight = json.getInteger("weight");

        try {
            URL _url = new URL(this.url);
            String host = _url.getHost();
            int port = _url.getPort();
            this.path = _url.getPath();

            HttpClientOptions clientOptions = new HttpClientOptions();
            clientOptions.setDefaultHost(host);
            clientOptions.setDefaultPort(port);
            clientOptions.setKeepAlive(true);

            if (_url.getProtocol().equals("https")) {
                clientOptions.setSsl(true);
                clientOptions.setTrustAll(true);
            }

            this.client = vertx.createHttpClient(clientOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }
}