package com.example.btcsedge.ui.component;

import com.example.btcsedge.ui.HomeView;
import com.example.btcsedge.ui.ReportsView;
import com.example.btcsedge.ui.ServiceCarsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class SidebarNavigation extends VerticalLayout {

    private boolean expanded = true;
    private final VerticalLayout navLinks = new VerticalLayout();
    private final Button toggleButton;

    public SidebarNavigation() {
        addClassName("sidebar-navigation");
        setWidth("220px");
        setPadding(false);
        setSpacing(false);

        toggleButton = new Button(VaadinIcon.MENU.create(), e -> toggle());
        toggleButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        toggleButton.addClassName("sidebar-toggle");

        navLinks.setPadding(true);
        navLinks.setSpacing(false);
        navLinks.add(
                createLink("Home", VaadinIcon.HOME, HomeView.class),
                createLink("Service cars", VaadinIcon.CAR, ServiceCarsView.class),
                createLink("Reports", VaadinIcon.CHART, ReportsView.class)
        );

        add(toggleButton, new Nav(navLinks));
    }

    private <T extends com.vaadin.flow.component.Component> RouterLink createLink(
            String label, VaadinIcon icon, Class<T> navigationTarget) {
        RouterLink link = new RouterLink(navigationTarget);
        link.addClassName("nav-link");

        HorizontalLayout content = new HorizontalLayout(icon.create(), new Span(label));
        content.setSpacing(true);
        content.setAlignItems(com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment.CENTER);
        link.add(content);

        return link;
    }

    private void toggle() {
        expanded = !expanded;
        navLinks.setVisible(expanded);
        setWidth(expanded ? "220px" : "52px");
    }

    public boolean isExpanded() {
        return expanded;
    }
}
