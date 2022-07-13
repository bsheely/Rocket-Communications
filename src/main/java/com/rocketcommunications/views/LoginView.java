package com.rocketcommunications.views;

import com.rocketcommunications.models.User;
import com.rocketcommunications.services.DataService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Log in")
@Route(value = "")
public class LoginView extends HorizontalLayout {
    private final DataService dataService;

    public LoginView(DataService dataService) {
        this.dataService = dataService;
        H2 header = new H2("Sign in");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        password.getStyle().set("padding-top", "0");
        Button login = new Button("Sign in", event -> {
            User user = dataService.getUser(username.getValue());
            if (user != null && password.getValue().equals(user.getPassword())) {
                username.clear();
                password.clear();
                UI.getCurrent().navigate("main");
            }
            else {
                Button closeButton = new Button("Invalid username/password", new Icon(VaadinIcon.CLOSE));
                closeButton.setIconAfterText(true);
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                Notification notification = new Notification(closeButton);
                closeButton.addClickListener(e -> notification.close());
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            }
        });
        login.setWidthFull();
        login.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button registration = new Button("Register", event -> createDialog());
        registration.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        VerticalLayout loginLayout = new VerticalLayout(header, username, password, login, registration);
        loginLayout.getElement().getThemeList().add("dark");
        loginLayout.setAlignSelf(Alignment.CENTER, registration);
        loginLayout.setSizeUndefined();
        Image logo = new Image("img/rocket-logo.png","logo");
        Image text = new Image("img/rocket-type.png", "Rocket Communications");
        VerticalLayout logoLayout = new VerticalLayout(logo, text);
        logoLayout.setSizeUndefined();
        logoLayout.getElement().getThemeList().add("dark");
        HorizontalLayout layout = new HorizontalLayout(loginLayout, logoLayout);
        layout.setSpacing(false);
        add(layout);
        setHeightFull();
        setDefaultVerticalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        getElement().getStyle().set("background-image","url('img/background.png')");
    }

    private void createDialog() {
        VerticalLayout dialogLayout = new VerticalLayout();
        Dialog dialog = new Dialog(dialogLayout);
        dialog.getElement().getThemeList().add("dark");
        H2 header = new H2("Create new user");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        password.getStyle().set("padding-top", "0");
        Button cancel = new Button("Cancel", event -> dialog.close());
        cancel.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button save = new Button("Save", event -> {
            if (dataService.getUser(username.getValue()) == null) {
                //NOTE adding the spring-boot-starter-security dependency causes Vaadin to not render correctly
                User user = new User(username.getValue(), password.getValue());
                dataService.saveUser(user);
                dialog.close();
            }
            else {
                Button closeButton = new Button("Username is not unique", new Icon(VaadinIcon.CLOSE));
                closeButton.setIconAfterText(true);
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
                Notification notification = new Notification(closeButton);
                closeButton.addClickListener(e -> notification.close());
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            }
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new HorizontalLayout(cancel, save);
        dialogLayout.add(header, username, password, buttonLayout);
        dialog.setCloseOnOutsideClick(false);
        dialog.setDraggable(false);
        dialog.setResizable(true);
        dialog.open();
    }
}
