package com.example.demo.web.rest;

import com.example.demo.domain.ApiKey;
import com.example.demo.domain.Project;
import com.example.demo.domain.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.HeaderUtil;
import com.example.demo.util.RequestUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by janier on 20/09/17.
 */
@RestController
@RequestMapping("/api")
public class ProjectResource {

    private static final String ENTITY_NAME = "Project";
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Value("${kong.address}")
    private String kongAddress;

    @Value("${kong.consumers}")
    private String kongConsumers;

    public ProjectResource(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/project")
    public ResponseEntity<Project> createUser(@RequestBody Project project) throws URISyntaxException {

        User loggedUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        if (project.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "A new Project cannot have an ID")).body(null);
        }

        String url = kongConsumers + "/" + loggedUser.getUsername() + "/key-auth";
        String response = null;
        try {
            response = RequestUtil.sendPost(url);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
        Map json = JsonParserFactory.getJsonParser().parseMap(response);
        ApiKey key = new ApiKey();
        key.setUser(loggedUser);
        key.setKey(json.get("key").toString());

        project.setApiKey(key);
        project.setOwner(loggedUser);
        projectRepository.save(project);
        return ResponseEntity.created(new URI("/api/projects/" + project.getId())).body(project);
    }
}
