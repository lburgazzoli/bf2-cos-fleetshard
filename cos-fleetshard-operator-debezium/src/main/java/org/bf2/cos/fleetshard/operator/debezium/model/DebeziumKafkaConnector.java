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
@Singular(io.strimzi.api.kafka.model.KafkaConnector.RESOURCE_SINGULAR)
@Plural(io.strimzi.api.kafka.model.KafkaConnector.RESOURCE_PLURAL)
@Kind(io.strimzi.api.kafka.model.KafkaConnector.RESOURCE_KIND)
@Version(Constants.V1BETA2)
@Group(DebeziumConstants.RESOURCE_GROUP_NAME)
public class DebeziumKafkaConnector
    extends
    CustomResource<io.strimzi.api.kafka.model.KafkaConnectorSpec, io.strimzi.api.kafka.model.status.KafkaConnectorStatus>
    implements Namespaced {

    public DebeziumKafkaConnector() {
        super();
    }

    @Override
    protected io.strimzi.api.kafka.model.KafkaConnectorSpec initSpec() {
        return new io.strimzi.api.kafka.model.KafkaConnectorSpec();
    }

    @Override
    protected io.strimzi.api.kafka.model.status.KafkaConnectorStatus initStatus() {
        return new io.strimzi.api.kafka.model.status.KafkaConnectorStatus();
    }

    @Override
    @JsonIgnore
    public String getPlural() {
        return HasMetadata.getPlural(io.strimzi.api.kafka.model.KafkaConnector.class);
    }

    @Override
    @JsonIgnore
    public String getSingular() {
        return HasMetadata.getSingular(io.strimzi.api.kafka.model.KafkaConnector.class);
    }
}
