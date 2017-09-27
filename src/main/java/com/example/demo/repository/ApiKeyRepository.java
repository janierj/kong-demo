package com.example.demo.repository;

import com.example.demo.domain.ApiKey;
import com.example.demo.domain.Project;
import io.swagger.annotations.Api;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by janier on 20/09/17.
 */
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    ApiKey findByKey(String key);
}
