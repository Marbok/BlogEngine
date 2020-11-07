package org.blog.services.api;

import org.blog.model.Topic;

import java.util.Collection;
import java.util.Optional;

public interface TopicService {

    /**
     * @return all topic
     */
    Collection<Topic> findAllTopics();

    /**
     * @param topicId
     * @return
     */
    Optional<Topic> findTopicById(long topicId);
}
