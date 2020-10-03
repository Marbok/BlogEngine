package org.blog.dao;

import org.blog.model.Topic;
import org.springframework.data.repository.CrudRepository;

public interface TopicDao extends CrudRepository<Topic, Long> {
}
