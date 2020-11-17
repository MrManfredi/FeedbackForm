package com.example.feedback.repos;

import com.example.feedback.domain.Feedback;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackRepo extends CrudRepository<Feedback, Integer> {
}
