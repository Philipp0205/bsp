package com.philipp.vivent.views.admin;

import java.util.List;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

public class GenericGrid<T> extends Grid<T> {

	public GenericGrid(Class<T> beanType, List<T> items) {
		super(beanType);
		setSizeFull();
		addClassName("generic-grid");
		configureGrid();
		setItems(new ListDataProvider<>(items));
	}
	
	private void configureGrid() {
		addClassName("generic-grid");
		setSizeFull();
		getColumns().forEach(col -> col.setWidth("50px"));
	}
}
