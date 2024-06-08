package com.philipp.vivent.views.admin;

import java.util.List;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;

public class GridBuilder<T> {
    private final Grid<T> grid;
    private FormLayout form;
    private Class<T> beanType;

    public GridBuilder(Class<T> beanType) {
        this.grid = new Grid<>(beanType);
        this.beanType = beanType;
    }

    public GridBuilder<T> withSelectionMode(Grid.SelectionMode selectionMode) {
        this.grid.setSelectionMode(selectionMode);
        return this;
    }
   
    public GridBuilder<T> withClassName(String className) {
        this.grid.addClassName(className);
        return this;
    }
    
    public GridBuilder<T> withData(List<T> items) {
		this.grid.setItems(items);
		return this;
    }

    public GridBuilder<T> fullSize() {
        this.grid.setSizeFull();
        return this;
    }

    public GridBuilder<T> setFlexGrow() {
		return this;
    }
    
	public <F extends FormLayout> GridBuilder<T> withForm(F form) {
		this.form = form;
		return this;
	}
	
    public Grid<T> build() {
        return this.grid;
    }
}
