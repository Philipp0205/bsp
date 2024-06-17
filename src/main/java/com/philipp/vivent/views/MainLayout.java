package com.philipp.vivent.views;

import com.philipp.vivent.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * The main view is a top-level placeholder for other views.
 */
@StyleSheet("context://style.css")
public class MainLayout extends AppLayout {

	private H2 viewTitle;

	public MainLayout() {
		setPrimarySection(Section.DRAWER);
		addHeaderContent();
	}

	private void addHeaderContent() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.addClassName("header");
		layout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
		layout.setSizeFull();
		
		H2 title = new H2("BSP Quiz");
		layout.add(title, profilePicture());

		addToNavbar(true, layout);
	}

	private Button profilePicture() {
		Button button = new Button(new Icon(VaadinIcon.USER));
		button.addClassName("profile-button");
		ContextMenu contextMenu = new ContextMenu(button);
		contextMenu.setOpenOnClick(true); // This will make the menu open on a left-click

		contextMenu.addItem("Profile", e -> {
			// Handle the click on the "Profile" item
		});

		contextMenu.addItem("Settings", e -> {
			// Handle the click on the "Settings" item
		});

		contextMenu.addItem("Login", e -> {
			UI.getCurrent().navigate(LoginView.class);
		});

		return button;
	}
}
