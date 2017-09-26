package com.example.demo.repository;

import com.example.demo.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by janier on 20/09/17.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByApiKey(String apikey);
}
