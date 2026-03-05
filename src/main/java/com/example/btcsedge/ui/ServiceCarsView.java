package com.example.btcsedge.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "service-cars", layout = MainLayout.class)
@PageTitle("Service Cars – Traffic-control")
@PermitAll
public class ServiceCarsView extends VerticalLayout {

    public ServiceCarsView() {
        setSizeFull();
        setPadding(true);
        add(new H2("Service Cars"));
    }
}
