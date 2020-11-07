package org.blog.services;

import org.blog.model.Topic;
import org.blog.repository.TopicRepository;
import org.blog.services.api.TopicService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

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

    @Override
    public Optional<Topic> findTopicById(long topicId) {
        return topicRepository.findById(topicId);
    }
}
