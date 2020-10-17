package org.blog.controller;

import org.blog.controller.mapper.TopicMapper;
import org.blog.controller.response.TopicsResponse;
import org.blog.services.api.TopicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TopicController {

    private final TopicService topicService;
    private final TopicMapper topicMapper;

    public TopicController(TopicService topicService, TopicMapper topicMapper) {
        this.topicService = topicService;
        this.topicMapper = topicMapper;
    }

    @GetMapping("/topics")
    public List<TopicsResponse> getTopics() {
        return topicService.findAllTopics()
                .stream()
                .map(topicMapper::modelToTopicsResponse)
                .collect(Collectors.toList());
    }
}
