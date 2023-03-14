package org.bf2.cos.fleetshard.support.strimzi;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;

public class StrimziProcessor {
    private static final String FEATURE = "cos-fleetshard-support-strimzi";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void addDependencies(BuildProducer<IndexDependencyBuildItem> dependencies) {
        dependencies.produce(new IndexDependencyBuildItem("io.strimzi", "api"));
    }
}
