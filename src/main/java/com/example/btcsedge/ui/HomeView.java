package com.example.btcsedge.ui;

import com.example.btcsedge.service.UserService;
import com.example.btcsedge.ui.component.DataTableLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Dashboard – BTCS Edge")
@PermitAll
public class HomeView extends VerticalLayout {

    public HomeView(UserService userService) {
        setSizeFull();
        setPadding(true);

        H2 heading = new H2("Dashboard");
        DataTableLayout dataTable = new DataTableLayout(userService);

        add(heading, dataTable);
        expand(dataTable);
    }
}
