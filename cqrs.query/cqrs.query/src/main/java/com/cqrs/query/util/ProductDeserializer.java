package com.cqrs.query.util;
import com.cqrs.query.entities.Products;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ProductDeserializer implements Deserializer<Products> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Products deserialize(String topic, byte[] data) {
        try {
            JsonNode jsonNode = objectMapper.readTree(data);

            // Map 'id' to 'pId' if necessary
            if (jsonNode.has("id")) {
                ((ObjectNode) jsonNode).put("pId", jsonNode.get("id").asText());
                ((ObjectNode) jsonNode).remove("id");
            }

            return objectMapper.treeToValue(jsonNode, Products.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize Product object", e);
        }
    }

    @Override
    public void close() {
    }
}
