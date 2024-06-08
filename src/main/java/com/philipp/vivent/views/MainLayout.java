package com.philipp.vivent.views;

import org.vaadin.lineawesome.LineAwesomeIcon;

import com.philipp.vivent.views.about.AboutView;
import com.philipp.vivent.views.quiz.CategoryView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

	public MainLayout() {
		setPrimarySection(Section.DRAWER);
		addHeaderContent();
		addDrawerContent();
	}

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }
    
	private Header addLogo() {
		H1 logo = new H1("\uD83C\uDF77"); 
		logo.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.Margin.NONE); // Adjusting logo size if needed

		H1 appName = new H1("Vivent!");
		appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

		HorizontalLayout layout = new HorizontalLayout(logo, appName);
		layout.setDefaultVerticalComponentAlignment(Alignment.CENTER); // Aligns children vertically center
		layout.setSpacing(true); // Adds space between components
		layout.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.JustifyContent.CENTER); // Centering items

		Header header = new Header(layout);
		return header;
	}
	
    private void addDrawerContent() {
        H1 appName = new H1("My App");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();
        nav.addItem(new SideNavItem("Quiz", CategoryView.class, LineAwesomeIcon.TH_LIST_SOLID.create()));
        nav.addItem(new SideNavItem("About", AboutView.class, LineAwesomeIcon.INFO_CIRCLE_SOLID.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }
//
//    @Override
//    protected void afterNavigation() {
//        super.afterNavigation();
//        viewTitle.setText(getCurrentPageTitle());
//    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
