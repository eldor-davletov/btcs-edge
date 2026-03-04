package com.example.btcsedge.service;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {

    private static final String THEME_ATTR = "app-theme";
    private static final String DARK = "dark";

    public boolean isDark() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session == null) return false;
        return DARK.equals(session.getAttribute(THEME_ATTR));
    }

    public void toggleTheme(com.vaadin.flow.component.UI ui) {
        VaadinSession session = VaadinSession.getCurrent();
        if (session == null || ui == null) return;
        if (isDark()) {
            session.setAttribute(THEME_ATTR, "light");
            ui.getPage().executeJs("document.documentElement.removeAttribute('theme')");
        } else {
            session.setAttribute(THEME_ATTR, DARK);
            ui.getPage().executeJs("document.documentElement.setAttribute('theme', 'dark')");
        }
    }

    public void applyTheme(com.vaadin.flow.component.UI ui) {
        if (ui == null) return;
        if (isDark()) {
            ui.getPage().executeJs("document.documentElement.setAttribute('theme', 'dark')");
        } else {
            ui.getPage().executeJs("document.documentElement.removeAttribute('theme')");
        }
    }
}
