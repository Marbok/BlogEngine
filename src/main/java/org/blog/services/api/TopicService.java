package org.blog.services.api;

import org.blog.model.Topic;

import java.util.Collection;

public interface TopicService {

    /**
     * @return all topic
     */
    Collection<Topic> findAllTopics();
}
