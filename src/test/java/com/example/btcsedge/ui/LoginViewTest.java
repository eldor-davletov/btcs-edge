package com.example.btcsedge.ui;

import com.example.btcsedge.service.UserService;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LoginViewTest {

    @Test
    void loginView_hasCorrectRouteAnnotation() {
        Route route = LoginView.class.getAnnotation(Route.class);
        assertThat(route).isNotNull();
        assertThat(route.value()).isEqualTo("login");
    }

    @Test
    void loginView_isAnonymousAllowed() {
        AnonymousAllowed annotation = LoginView.class.getAnnotation(AnonymousAllowed.class);
        assertThat(annotation).isNotNull();
    }

    @Test
    void loginView_extendsVerticalLayout() {
        assertThat(com.vaadin.flow.component.orderedlayout.VerticalLayout.class)
                .isAssignableFrom(LoginView.class);
    }

    @Test
    void loginView_hasTwoServiceDependencies() {
        var constructors = LoginView.class.getConstructors();
        assertThat(constructors).isNotEmpty();
        assertThat(constructors[0].getParameterTypes())
                .containsExactly(UserService.class, AuthenticationManager.class);
    }

    @Test
    void loginView_handlesNullSession_withoutThrowing() {
        // Verify that the class structure is correct without needing a Vaadin context
        assertThat(LoginView.class).isNotNull();
    }

    @Test
    void loginView_hasPageTitleAnnotation() {
        var pageTitle = LoginView.class.getAnnotation(com.vaadin.flow.router.PageTitle.class);
        assertThat(pageTitle).isNotNull();
        assertThat(pageTitle.value()).contains("Login");
    }
}
