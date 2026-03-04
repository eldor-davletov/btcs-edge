package com.example.btcsedge.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "users", layout = MainLayout.class)
@PageTitle("Users – BTCS Edge")
@PermitAll
public class UsersView extends VerticalLayout {

    public UsersView() {
        setSizeFull();
        setPadding(true);
        add(new H2("Users"));
    }
}
