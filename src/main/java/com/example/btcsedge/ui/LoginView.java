package com.example.btcsedge.ui;

import com.example.btcsedge.dto.UserDto;
import com.example.btcsedge.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Route("login")
@PageTitle("Login – BTCS Edge")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    private final EmailField emailField = new EmailField("Email");
    private final PasswordField passwordField = new PasswordField("Password");

    public LoginView(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        emailField.setRequired(true);
        emailField.setWidth("300px");
        passwordField.setRequired(true);
        passwordField.setWidth("300px");

        Button loginButton = new Button("Login", e -> handleLogin());
        loginButton.setWidth("300px");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(new H1("BTCS Edge"), emailField, passwordField, loginButton);
    }

    private void handleLogin() {
        String email = emailField.getValue();
        String password = passwordField.getValue();

        if (email.isEmpty() || password.isEmpty()) {
            Notification.show("Please enter email and password", 3000, Notification.Position.MIDDLE);
            return;
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            VaadinServletRequest request = (VaadinServletRequest) VaadinRequest.getCurrent();
            VaadinServletResponse response = (VaadinServletResponse) VaadinResponse.getCurrent();
            new HttpSessionSecurityContextRepository()
                    .saveContext(context, request.getHttpServletRequest(), response.getHttpServletResponse());

            userService.getUserByEmail(email).map(UserDto::from).ifPresent(userDto ->
                    VaadinSession.getCurrent().setAttribute("user", userDto));

            getUI().ifPresent(ui -> ui.navigate(""));
        } catch (AuthenticationException e) {
            passwordField.clear();
            Notification.show("Invalid email or password", 3000, Notification.Position.MIDDLE);
        }
    }
}
