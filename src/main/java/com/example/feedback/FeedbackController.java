package com.example.feedback;

import com.example.feedback.domain.Feedback;
import com.example.feedback.repos.FeedbackRepo;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "feedbackController")
@ELBeanName(value = "feedbackController")
@Join(path = "/feedback", to = "/feedback-form.jsf")
public class FeedbackController {

    @Autowired
    private FeedbackRepo feedbackRepo;

    private Feedback feedback = new Feedback();

    public String save() {
        feedbackRepo.save(feedback);
        feedback = new Feedback();
        return "/success.xhtml?faces-redirect=true";
    }

    public Feedback getFeedback() {
        return feedback;
    }
}
