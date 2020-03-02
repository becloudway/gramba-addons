package ${package}.configuration;

import be.cloudway.easy.reflection.dependency.configuration.proxy.ProxyConfigurationInterface;
import be.cloudway.easy.reflection.dependency.configuration.reflection.ReflectionConfigurationInterface;
import be.cloudway.easy.reflection.dependency.configuration.reflection.model.ReflectionConfigurationBuilder;
import be.cloudway.easy.reflection.model.ReflectedJson;
import be.cloudway.gramba.annotations.GrambaConfigurationTarget;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.partitions.model.*;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@GrambaConfigurationTarget
public class AwsSdkConfiguration implements ReflectionConfigurationInterface, ProxyConfigurationInterface {
    @Override
    public List<ReflectedJson> reflectionConfiguration() {
        return new ReflectionConfigurationBuilder()
                .reflect(Partitions.class)
                .reflect(Partition.class)
                .reflect(Endpoint.class)
                .reflect(Region.class)
                .reflect(Service.class)
                .reflect(CredentialScope.class)
                .reflect(AWS4Signer.class)
                .reflect(APIGatewayProxyRequestEvent.class)
                .reflect(APIGatewayProxyRequestEvent.ProxyRequestContext.class)
                .reflect(APIGatewayProxyRequestEvent.RequestIdentity.class)
                .reflect(APIGatewayProxyResponseEvent.class)
                .reflect(HashSet.class)
                .build();
    }

    @Override
    public List<List<String>> proxyConfiguration(List<List<String>> outerList) {
        List<String> innerList = new ArrayList<>();

        innerList.add("org.apache.http.conn.HttpClientConnectionManager");
        innerList.add("org.apache.http.pool.ConnPoolControl");
        innerList.add("com.amazonaws.http.conn.Wrapped");

        outerList.add(innerList);

        return outerList;
    }
}
