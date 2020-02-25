package be.cloudway.gramba.configuration.aws.sdk;

import be.cloudway.easy.reflection.dependency.configuration.proxy.ProxyConfigurationInterface;
import be.cloudway.easy.reflection.dependency.configuration.reflection.ReflectionConfigurationInterface;
import be.cloudway.easy.reflection.dependency.configuration.reflection.model.ReflectionConfigurationBuilder;
import be.cloudway.easy.reflection.model.ReflectedJson;
import be.cloudway.gramba.annotations.GrambaConfigurationTarget;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@GrambaConfigurationTarget
public class Configuration implements ReflectionConfigurationInterface, ProxyConfigurationInterface {
    @Override
    public List<ReflectedJson> reflectionConfiguration() {
        return new ReflectionConfigurationBuilder()
            .prefix("com.amazonaws.partitions.model")
                .reflect("Partitions")
                .reflect("Partition")
                .reflect("Endpoint")
                .reflect("Region")
                .reflect("Service")
                .reflect("CredentialScope")
            .prefix("com.amazonaws.auth")
                .reflect("AWS4Signer")
            .prefix("com.amazonaws.services.lambda.runtime.events")
                .reflect("APIGatewayProxyRequestEvent")
                .reflect("APIGatewayProxyRequestEvent$ProxyRequestContext")
                .reflect("APIGatewayProxyRequestEvent$RequestIdentity")
                .reflect("APIGatewayProxyResponseEvent")
            .reflect(HashSet.class)
            .build();
    }

    @Override
    public List<List<String>> proxyConfiguration() {
        List<List<String>> outerList = new ArrayList<>();
        List<String> innerList = new ArrayList<>();

        innerList.add("org.apache.http.conn.HttpClientConnectionManager");
        innerList.add("org.apache.http.pool.ConnPoolControl");
        innerList.add("com.amazonaws.http.conn.Wrapped");

        outerList.add(innerList);

        return outerList;
    }
}
