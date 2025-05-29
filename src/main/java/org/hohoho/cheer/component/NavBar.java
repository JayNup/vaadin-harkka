package org.hohoho.cheer.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;
import org.hohoho.cheer.view.PersonView;
import org.hohoho.cheer.view.HobbyView;
import org.hohoho.cheer.view.MainView;

public class NavBar extends HorizontalLayout {

    public NavBar() {
        // Luo navigointipainikkeet
        RouterLink mainLink = new RouterLink("Pääsivu", MainView.class);
        RouterLink hobbyLink = new RouterLink("Harrastukset", HobbyView.class);
        RouterLink personLink = new RouterLink("Henkilöt", PersonView.class);

        // Lisää painikkeet navigointipalkkiin
        add(mainLink, hobbyLink, personLink);

        // Tyylittele navigointipalkki
        setSpacing(true);
        setAlignItems(Alignment.CENTER);
        setWidthFull();
        getStyle().set("background-color", "#333").set("color", "white").set("padding", "10px");

    }
}
