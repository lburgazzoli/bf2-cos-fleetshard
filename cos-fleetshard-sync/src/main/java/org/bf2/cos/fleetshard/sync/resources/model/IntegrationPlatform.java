package org.bf2.cos.fleetshard.sync.resources.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Buildable(builderPackage = "io.fabric8.kubernetes.api.builder", refs = @BuildableReference(CustomResource.class),
    editableEnabled = false)
@Version(IntegrationPlatform.RESOURCE_VERSION)
@Group(IntegrationPlatform.RESOURCE_GROUP)
public class IntegrationPlatform
    extends CustomResource<IntegrationPlatformSpec, IntegrationPlatformStatus>
    implements Namespaced {

    public static final String RESOURCE_GROUP = "camel.apache.org";
    public static final String RESOURCE_VERSION = "v1";
    public static final String RESOURCE_API_VERSION = RESOURCE_GROUP + "/" + RESOURCE_VERSION;
    public static final String RESOURCE_KIND = "IntegrationPlatform";

    public IntegrationPlatform() {
        super();
    }

    @Override
    protected IntegrationPlatformSpec initSpec() {
        return new IntegrationPlatformSpec();
    }

    @Override
    protected IntegrationPlatformStatus initStatus() {
        return new IntegrationPlatformStatus();
    }
}
