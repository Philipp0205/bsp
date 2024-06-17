package com.philipp.vivent.views.login;

import com.philipp.vivent.views.MainLayout;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "login", layout = MainLayout.class)
public class LoginView extends VerticalLayout {

	public LoginView() {
		constructUi();
	}

	private void constructUi() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidthFull();
		layout.setJustifyContentMode(JustifyContentMode.CENTER);
		LoginForm loginForm = new LoginForm();
		layout.add(loginForm);

		add(layout);
	}
}
