package com.philipp.vivent.views.quiz;

import com.philipp.vivent.data.Category;
import com.philipp.vivent.services.CategoryService;
import com.philipp.vivent.views.admin.CategoryForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

public class CategoryCard extends ListItem {
	Image image;
	Span header;
	CategoryService service;

	public CategoryCard(Category category, CategoryService service) {
		this.service = service;
		addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
				BorderRadius.LARGE);

		Div div = new Div();
		div.addClassNames(Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
				Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
		div.setHeight("160px");

		image = new Image(category.getImgUrl(), category.getImgAlt());
		image.setWidth("30%");
		
		div.add(image);
		header = new Span();
		header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
		header.setText(category.getName());

		Button edit = new Button(new Icon(VaadinIcon.EDIT));
		edit.getElement().setAttribute("theme", "icon small");
        edit.getElement().addEventListener("click", e -> openEditDialog(category)).addEventData("event.stopPropagation()");
		add(div, header, edit);
	}

	private void openEditDialog(Category category) {
		Dialog dialog = new Dialog();
		CategoryForm form = new CategoryForm();
		form.setCategory(category);
		form.addSaveListener(event -> {
			Category savedCategory = event.getCategory();
			header.setText(savedCategory.getName());
			image.setSrc(savedCategory.getImgUrl());
			image.setAlt(savedCategory.getImgAlt());
			dialog.close();
		});
		form.addCloseListener(event -> dialog.close());
		form.addSaveListener(e -> service.saveCategory(category));
		dialog.add(form);
		dialog.open();
	}
}
