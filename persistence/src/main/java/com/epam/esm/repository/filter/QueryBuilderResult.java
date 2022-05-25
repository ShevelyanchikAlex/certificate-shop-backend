package com.epam.esm.repository.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * {@link QueryBuilderResult} contains resulting Query and Set with parameters
 */
@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class QueryBuilderResult {
    private String query;
    private Map<String, String> parameters;
}
