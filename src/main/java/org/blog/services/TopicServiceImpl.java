package org.blog.services;

import org.blog.model.Topic;
import org.blog.repository.TopicRepository;
import org.blog.services.api.TopicService;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Collection<Topic> findAllTopics() {
        return (Collection<Topic>) topicRepository.findAll();
    }
}
