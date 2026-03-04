package com.example.btcsedge.ui.component;

import com.example.btcsedge.service.ThemeService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * Top header component containing the application title and a dark/light theme toggle.
 */
public class HeaderComponent extends HorizontalLayout {

    private final ThemeService themeService;
    private final Button themeToggle;

    public HeaderComponent(ThemeService themeService) {
        this.themeService = themeService;
        addClassName("header-component");
        setWidthFull();
        setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        setPadding(false);

        H1 title = new H1("BTCS Edge");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        themeToggle = new Button(resolveIcon(), e -> handleThemeToggle());
        themeToggle.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        themeToggle.setTooltipText("Toggle dark/light theme");

        expand(title);
        add(title, themeToggle);
    }

    private void handleThemeToggle() {
        themeService.toggleTheme(UI.getCurrent());
        themeToggle.setIcon(resolveIcon());
    }

    private com.vaadin.flow.component.icon.Icon resolveIcon() {
        return themeService.isDark() ? VaadinIcon.SUN_O.create() : VaadinIcon.ADJUST.create();
    }
}
