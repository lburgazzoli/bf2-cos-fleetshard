package org.bf2.cos.fleetshard.operator.debezium.model;

import org.bf2.cos.fleetshard.operator.debezium.DebeziumConstants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.Plural;
import io.fabric8.kubernetes.model.annotation.Singular;
import io.fabric8.kubernetes.model.annotation.Version;
import io.strimzi.api.kafka.model.Constants;
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
@Singular(io.strimzi.api.kafka.model.KafkaConnect.RESOURCE_SINGULAR)
@Plural(io.strimzi.api.kafka.model.KafkaConnect.RESOURCE_PLURAL)
@Kind(io.strimzi.api.kafka.model.KafkaConnect.RESOURCE_KIND)
@Version(Constants.V1BETA2)
@Group(DebeziumConstants.RESOURCE_GROUP_NAME)
public class DebeziumKafkaConnect
    extends CustomResource<io.strimzi.api.kafka.model.KafkaConnectSpec, io.strimzi.api.kafka.model.status.KafkaConnectStatus>
    implements Namespaced {

    public DebeziumKafkaConnect() {
        super();
    }

    @Override
    protected io.strimzi.api.kafka.model.KafkaConnectSpec initSpec() {
        return new io.strimzi.api.kafka.model.KafkaConnectSpec();
    }

    @Override
    protected io.strimzi.api.kafka.model.status.KafkaConnectStatus initStatus() {
        return new io.strimzi.api.kafka.model.status.KafkaConnectStatus();
    }

    @Override
    @JsonIgnore
    public String getPlural() {
        return HasMetadata.getPlural(io.strimzi.api.kafka.model.KafkaConnect.class);
    }

    @Override
    @JsonIgnore
    public String getSingular() {
        return HasMetadata.getSingular(io.strimzi.api.kafka.model.KafkaConnect.class);
    }
}
