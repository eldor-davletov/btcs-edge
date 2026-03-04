package com.example.btcsedge.config;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.context.annotation.Configuration;

@Configuration
@Theme(themeClass = Lumo.class)
public class VaadinConfig implements AppShellConfigurator {
}
