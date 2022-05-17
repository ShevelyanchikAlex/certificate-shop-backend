package com.epam.esm.repository.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
