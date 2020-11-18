package com.example.feedback;

import com.example.feedback.domain.Feedback;
import com.example.feedback.repos.FeedbackRepo;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

@Scope(value = "session")
@Component(value = "feedbackController")
@ELBeanName(value = "feedbackController")
@Join(path = "/feedback", to = "/feedback-form.jsf")
public class FeedbackController {

    @Autowired
    private FeedbackRepo feedbackRepo;

    private Feedback feedback = new Feedback();

    /**
     * This method is used to save feedback
     *
     * @return redirect to {@code success.xhtml} page
     */
    public String save() {
        feedbackRepo.save(feedback);
        feedback = new Feedback();
        return "/success.xhtml?faces-redirect=true";
    }

    public Feedback getFeedback() {
        return feedback;
    }

    /**
     * This method is used to validate mail field
     *
     * @param event event
     */
    public void validateMail(ComponentSystemEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();

        // get mail
        UIInput uiInputMail = (UIInput) components.findComponent("mail");
        String mail = uiInputMail.getLocalValue() == null ? ""
                : uiInputMail.getLocalValue().toString();
        String mailId = uiInputMail.getClientId();

        // get repeat mail
        UIInput uiInputMailRepeat = (UIInput) components.findComponent("mailRepeat");
        String mailRepeat = uiInputMailRepeat.getLocalValue() == null ? ""
                : uiInputMailRepeat.getLocalValue().toString();

        // Let required="true" do its job.
        if (mail.isEmpty() || mailRepeat.isEmpty()) {
            return;
        }

        if (!mail.equals(mailRepeat)) {
            FacesMessage msg = new FacesMessage("E-mail address must be the same!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(mailId, msg);
            context.renderResponse();
        }
    }
}
