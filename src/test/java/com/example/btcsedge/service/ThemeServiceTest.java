package com.example.btcsedge.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ThemeServiceTest {

    private final ThemeService themeService = new ThemeService();

    @Test
    void isDark_whenSessionIsNull_returnsFalse() {
        try (MockedStatic<VaadinSession> sessionStatic = mockStatic(VaadinSession.class)) {
            sessionStatic.when(VaadinSession::getCurrent).thenReturn(null);

            assertThat(themeService.isDark()).isFalse();
        }
    }

    @Test
    void isDark_whenThemeAttributeIsDark_returnsTrue() {
        try (MockedStatic<VaadinSession> sessionStatic = mockStatic(VaadinSession.class)) {
            VaadinSession session = mock(VaadinSession.class);
            when(session.getAttribute("app-theme")).thenReturn("dark");
            sessionStatic.when(VaadinSession::getCurrent).thenReturn(session);

            assertThat(themeService.isDark()).isTrue();
        }
    }

    @Test
    void isDark_whenThemeAttributeIsLight_returnsFalse() {
        try (MockedStatic<VaadinSession> sessionStatic = mockStatic(VaadinSession.class)) {
            VaadinSession session = mock(VaadinSession.class);
            when(session.getAttribute("app-theme")).thenReturn("light");
            sessionStatic.when(VaadinSession::getCurrent).thenReturn(session);

            assertThat(themeService.isDark()).isFalse();
        }
    }

    @Test
    void isDark_whenThemeAttributeIsNull_returnsFalse() {
        try (MockedStatic<VaadinSession> sessionStatic = mockStatic(VaadinSession.class)) {
            VaadinSession session = mock(VaadinSession.class);
            when(session.getAttribute("app-theme")).thenReturn(null);
            sessionStatic.when(VaadinSession::getCurrent).thenReturn(session);

            assertThat(themeService.isDark()).isFalse();
        }
    }

    @Test
    void toggleTheme_whenSessionIsNull_doesNothing() {
        try (MockedStatic<VaadinSession> sessionStatic = mockStatic(VaadinSession.class)) {
            sessionStatic.when(VaadinSession::getCurrent).thenReturn(null);
            UI ui = mock(UI.class);

            // Should not throw
            themeService.toggleTheme(ui);
        }
    }

    @Test
    void toggleTheme_whenUiIsNull_doesNothing() {
        try (MockedStatic<VaadinSession> sessionStatic = mockStatic(VaadinSession.class)) {
            VaadinSession session = mock(VaadinSession.class);
            sessionStatic.when(VaadinSession::getCurrent).thenReturn(session);

            // Should not throw
            themeService.toggleTheme(null);
        }
    }

    @Test
    void toggleTheme_fromDarkToLight_setsLightAttribute() {
        try (MockedStatic<VaadinSession> sessionStatic = mockStatic(VaadinSession.class)) {
            VaadinSession session = mock(VaadinSession.class);
            when(session.getAttribute("app-theme")).thenReturn("dark");
            sessionStatic.when(VaadinSession::getCurrent).thenReturn(session);

            UI ui = mock(UI.class);
            com.vaadin.flow.component.page.Page page = mock(com.vaadin.flow.component.page.Page.class);
            when(ui.getPage()).thenReturn(page);

            themeService.toggleTheme(ui);

            verify(session).setAttribute("app-theme", "light");
            verify(page).executeJs("document.documentElement.removeAttribute('theme')");
        }
    }

    @Test
    void toggleTheme_fromLightToDark_setsDarkAttribute() {
        try (MockedStatic<VaadinSession> sessionStatic = mockStatic(VaadinSession.class)) {
            VaadinSession session = mock(VaadinSession.class);
            when(session.getAttribute("app-theme")).thenReturn("light");
            sessionStatic.when(VaadinSession::getCurrent).thenReturn(session);

            UI ui = mock(UI.class);
            com.vaadin.flow.component.page.Page page = mock(com.vaadin.flow.component.page.Page.class);
            when(ui.getPage()).thenReturn(page);

            themeService.toggleTheme(ui);

            verify(session).setAttribute("app-theme", "dark");
            verify(page).executeJs("document.documentElement.setAttribute('theme', 'dark')");
        }
    }
}
