package org.bf2.cos.fleetshard.strimzi.deployment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationTarget;
import org.jboss.jandex.AnnotationValue;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.logging.Logger;

import io.fabric8.kubernetes.model.annotation.Group;
import io.quarkus.arc.deployment.AnnotationsTransformerBuildItem;
import io.quarkus.arc.processor.AnnotationsTransformer;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;

public class StrimziProcessor {
    private static final Logger LOGGER = Logger.getLogger(StrimziProcessor.class);
    private static final DotName API_GROUP_NAME = DotName.createSimple(Group.class.getName());
    private static final String FEATURE = "cos-fleetshard-strimzi";

    private static final Map<String, String> MAPPING = Map.of(
        "kafka.strimzi.io", "strimzi.rhoc.bf2.dev",
        "core.strimzi.io", "core.strimzi.rhoc.bf2.dev");

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void additionalDependenciesToIndex(BuildProducer<IndexDependencyBuildItem> dependencies) {
        dependencies.produce(new IndexDependencyBuildItem("io.strimzi", "api"));
    }

    @BuildStep
    void transformStrimziApiGroup(BuildProducer<AnnotationsTransformerBuildItem> transformers) {
        transformers.produce(new AnnotationsTransformerBuildItem(new StrimziApiGroupTransformer()));
    }

    public static final class StrimziApiGroupTransformer implements AnnotationsTransformer {
        @Override
        public boolean appliesTo(AnnotationTarget.Kind kind) {
            return kind == AnnotationTarget.Kind.CLASS;
        }

        @Override
        public void transform(TransformationContext context) {
            ClassInfo target = context.getTarget().asClass();
            List<AnnotationInstance> instances = new ArrayList<>(context.getAnnotations());

            if (!target.name().packagePrefix().equals("io.strimzi.api.kafka.model")) {
                return;
            }

            LOGGER.infof("Process '%s'", target.name().toString());

            for (int i = 0; i < instances.size(); i++) {
                AnnotationInstance ai = instances.get(i);

                if (!ai.name().equals(API_GROUP_NAME)) {
                    continue;
                }

                String from = ai.value().toString();
                String to = MAPPING.get(from);

                if (to != null) {
                    LOGGER.infof("Transform '%s' annotation to '%s' on class: %s",
                        ai.value().toString(),
                        to,
                        target.name().toString());

                    instances.set(i, AnnotationInstance.create(
                        API_GROUP_NAME,
                        target,
                        new AnnotationValue[] {
                            AnnotationValue.createStringValue("value", to)
                        }));
                }
            }

            context.transform().removeAll();
            context.transform().addAll(instances);
        }
    }
}
