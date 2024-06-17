package com.philipp.vivent.views.quiz;

import java.util.List;

import com.philipp.vivent.data.Category;
import com.philipp.vivent.services.CategoryService;
import com.philipp.vivent.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

import jakarta.annotation.security.PermitAll;

@PageTitle("Event Overview")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class CategoryView extends Main implements HasComponents, HasStyle {
	
	private OrderedList imageContainer;
	private CategoryService categoryService;
	
	public CategoryView(CategoryService categoryService) {
		this.categoryService = categoryService;
		constructUI();
		addImageContainers();
	}
	
	private void addImageContainers() {
		List<Category> allCategories = categoryService.findAllCategories("");
		allCategories.forEach(category -> {
			CategoryCard card = new CategoryCard(category, categoryService);

			card.addClickListener(e -> {
				card.getUI().ifPresent(ui -> ui.navigate(QuizView.class, category.getId().toString()));
			});
			imageContainer.add(card);
		});
	}
	
	private void constructUI() {
		addClassNames("event-overview-view");
		addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

		HorizontalLayout container = new HorizontalLayout();
		container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

		VerticalLayout headerContainer = new VerticalLayout();
		H2 header = new H2("Events in Stuttgart");
		header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
		Paragraph description = new Paragraph("Wine events near you.");
		description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
		headerContainer.add(header, description);

		Select<String> sortBy = new Select<>();
		sortBy.setLabel("Sort by");
		sortBy.setItems("Popularity", "Newest first", "Oldest first");
		sortBy.setValue("Popularity");

		imageContainer = new OrderedList();
		imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

		container.add(headerContainer, sortBy);
		add(container, imageContainer);
	}
}
