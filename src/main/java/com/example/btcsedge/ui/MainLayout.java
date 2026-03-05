package com.example.btcsedge.ui;

import com.example.btcsedge.service.ThemeService;
import com.example.btcsedge.ui.component.SidebarNavigation;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
public class MainLayout extends AppLayout {

    private final ThemeService themeService;

    public MainLayout(ThemeService themeService) {
        this.themeService = themeService;
        createNavbar();
        createDrawer();
    }

    private void createNavbar() {
        DrawerToggle drawerToggle = new DrawerToggle();

        H1 title = new H1("Traffic-control");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Button themeToggle = new Button(
                themeService.isDark() ? VaadinIcon.SUN_O.create() : VaadinIcon.MOON.create(),
                e -> {
                    themeService.toggleTheme(UI.getCurrent());
                    e.getSource().setIcon(themeService.isDark()
                            ? VaadinIcon.SUN_O.create()
                            : VaadinIcon.MOON.create());
                });
        themeToggle.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        themeToggle.setTooltipText("Toggle dark/light theme");

        HorizontalLayout navbar = new HorizontalLayout(drawerToggle, title, themeToggle);
        navbar.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        navbar.expand(title);
        navbar.setWidthFull();
        navbar.setPadding(false);

        addToNavbar(navbar);
    }

    private void createDrawer() {
        addToDrawer(new SidebarNavigation());
    }
}
