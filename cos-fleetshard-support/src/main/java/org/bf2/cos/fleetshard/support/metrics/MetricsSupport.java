package org.bf2.cos.fleetshard.support.metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.fabric8.kubernetes.api.model.HasMetadata;
import io.micrometer.core.instrument.Tag;

public final class MetricsSupport {
    private MetricsSupport() {
    }

    public static List<Tag> tags(MetricsRecorderConfig config, HasMetadata resource) {
        List<Tag> tags = new ArrayList<>();

        Map<String, String> labels = resource.getMetadata().getLabels();
        if (labels != null && !labels.isEmpty()) {
            config.tags().labels()
                .stream()
                .flatMap(List::stream)
                .forEach(key -> {
                    String val = labels.get(key);
                    if (val != null) {
                        tags.add(Tag.of(key.replace("/", "."), val));
                    }
                });
        }

        Map<String, String> annotations = resource.getMetadata().getAnnotations();
        if (annotations != null && !annotations.isEmpty()) {
            config.tags().annotations()
                .stream()
                .flatMap(List::stream)
                .forEach(key -> {
                    String val = annotations.get(key);
                    if (val != null) {
                        tags.add(Tag.of(key.replace("/", "."), val));
                    }
                });
        }

        return tags;
    }
}
