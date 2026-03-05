package com.example.btcsedge.ui;

import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HomeViewTest {

    @Test
    void homeView_hasCorrectRouteAnnotation() {
        Route route = HomeView.class.getAnnotation(Route.class);
        assertThat(route).isNotNull();
        assertThat(route.value()).isEqualTo("");
        assertThat(route.layout()).isEqualTo(MainLayout.class);
    }

    @Test
    void homeView_isPermitAll() {
        PermitAll annotation = HomeView.class.getAnnotation(PermitAll.class);
        assertThat(annotation).isNotNull();
    }

    @Test
    void homeView_extendsVerticalLayout() {
        assertThat(HomeView.class.getSuperclass().getSimpleName())
                .isEqualTo("VerticalLayout");
    }

    @Test
    void homeView_hasPageTitleAnnotation() {
        var pageTitle = HomeView.class.getAnnotation(com.vaadin.flow.router.PageTitle.class);
        assertThat(pageTitle).isNotNull();
        assertThat(pageTitle.value()).isNotBlank();
    }

    @Test
    void homeView_hasNoArgConstructor() {
        var constructors = HomeView.class.getConstructors();
        assertThat(constructors).isNotEmpty();
        assertThat(constructors[0].getParameterCount()).isEqualTo(0);
    }
}
