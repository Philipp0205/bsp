package com.philipp.vivent.views.admin;

import com.philipp.vivent.data.Category;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class CategoryForm extends FormLayout {
    private TextField name = new TextField("Name");
    private TextField imgAlt = new TextField("Image Alt");
    private TextField imgUrl = new TextField("Image URL");

    private TextField color = new TextField("Color");

    private Button save = new Button("Save");
    private Button cancel = new Button("Cancel");

    private Binder<Category> binder = new Binder<>(Category.class);

    public CategoryForm() {
        binder.bindInstanceFields(this);
        add(name, imgAlt, imgUrl, color, createButtonsLayout());
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

    public void setCategory(Category category) {
        binder.setBean(category);
    }

    // Custom events for save and close actions
    public static abstract class CategoryFormEvent extends ComponentEvent<CategoryForm> {
        private Category category;

        protected CategoryFormEvent(CategoryForm source, Category category) {
            super(source, false);
            this.category = category;
        }

        public Category getCategory() {
            return category;
        }
    }

    public static class SaveEvent extends CategoryFormEvent {
        SaveEvent(CategoryForm source, Category category) {
            super(source, category);
        }
    }

    public static class CloseEvent extends CategoryFormEvent {
        CloseEvent(CategoryForm source) {
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
