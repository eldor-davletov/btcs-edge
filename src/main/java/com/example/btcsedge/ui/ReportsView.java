package com.example.btcsedge.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "reports", layout = MainLayout.class)
@PageTitle("Reports – BTCS Edge")
@PermitAll
public class ReportsView extends VerticalLayout {

    public ReportsView() {
        setSizeFull();
        setPadding(true);
        add(new H2("Reports"));
    }
}
