package com.philipp.vivent.views.admin;

import com.philipp.vivent.data.Question;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class QuestionForm extends FormLayout {
    private TextField questionText = new TextField("Title");
    private TextField correctAnswer = new TextField("Correct answer");
    private TextField wrongAnswer1 = new TextField("Wrong answer 1");
    private TextField wrongAnswer2 = new TextField("Wrong answer 2");
    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private Binder<Question> binder = new Binder<>(Question.class);

    public QuestionForm() {
        binder.bindInstanceFields(this);
        add(questionText, correctAnswer, wrongAnswer1, wrongAnswer2, createButtonsLayout());
    }

    private FormLayout createButtonsLayout() {
        save.addClickListener(eevent -> validateAndSave());
        cancel.addClickListener(eevent -> fireEvent(new CloseEvent(this)));
        return new FormLayout(save, cancel);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public void setQuestion(Question question) {
        binder.setBean(question);
    }

    // Custom events for save and close actions
    public static abstract class CategoryFormEvent extends ComponentEvent<QuestionForm> {
        private Question question;

        protected CategoryFormEvent(QuestionForm source, Question question) {
            super(source, false);
            this.question = question;
        }

        public Question getQuestion() {
            return question;
        }
    }

    public static class SaveEvent extends CategoryFormEvent {
        SaveEvent(QuestionForm source, Question category) {
            super(source, category);
        }
    }

    public static class CloseEvent extends CategoryFormEvent {
        CloseEvent(QuestionForm source) {
            super(source, null);
        }
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}
