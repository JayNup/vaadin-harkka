package org.hohoho.cheer.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginView extends VerticalLayout {

    public LoginView() {
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(e -> {
            // Tähän lisäät kirjautumislogiikan
            if (e.getUsername().equals("admin") && e.getPassword().equals("password")) {
                getUI().ifPresent(ui -> ui.navigate(""));  // Navigoi pääsivulle, jos kirjautuminen onnistuu
            } else {
                loginForm.setError(true);  // Aseta virhe, jos kirjautuminen epäonnistuu
            }
        });

        add(loginForm);
    }
}
