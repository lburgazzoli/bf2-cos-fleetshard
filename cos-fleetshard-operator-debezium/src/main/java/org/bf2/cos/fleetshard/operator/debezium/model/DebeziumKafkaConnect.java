package org.bf2.cos.fleetshard.operator.debezium.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import io.strimzi.api.kafka.model.Constants;
import io.strimzi.api.kafka.model.KafkaConnectSpec;
import io.strimzi.api.kafka.model.status.KafkaConnectStatus;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Buildable(
    builderPackage = "io.fabric8.kubernetes.api.builder",
    refs = @BuildableReference(CustomResource.class),
    editableEnabled = false)
@Version(Constants.V1BETA2)
@Group("strimzi.rhoc.bf2.dev")
public class DebeziumKafkaConnect
    extends CustomResource<KafkaConnectSpec, KafkaConnectStatus>
    implements Namespaced {

    public DebeziumKafkaConnect() {
        super();
    }

    @Override
    protected KafkaConnectSpec initSpec() {
        return new KafkaConnectSpec();
    }

    @Override
    protected KafkaConnectStatus initStatus() {
        return new KafkaConnectStatus();
    }
}
