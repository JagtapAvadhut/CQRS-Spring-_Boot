package com.cqrs.command.kafka;


import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    public static final String PRODUCT_SAVE = "PRODUCT-SAVE";
    public static final String PRODUCT_UPDATE = "PRODUCT-UPDATE";
    public static final String PRODUCT_DELETE = "PRODUCT-DELETE";
    public static final String GROUP_ID = "group-1";


    @Bean
    public NewTopic saveProduct() {
        return TopicBuilder
                .name(PRODUCT_SAVE)
//               .partitions()
//               .replicas()
                .build();
    }
    @Bean
    public NewTopic updateProduct() {
        return TopicBuilder
                .name(PRODUCT_UPDATE)
//               .partitions()
//               .replicas()
                .build();
    }
    @Bean
    public NewTopic deleteProduct() {
        return TopicBuilder
                .name(PRODUCT_DELETE)
//               .partitions()
//               .replicas()
                .build();
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
