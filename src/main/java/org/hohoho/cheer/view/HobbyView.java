package org.hohoho.cheer.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.hohoho.cheer.component.NavBar;
import org.hohoho.cheer.model.Hobby;
import org.hohoho.cheer.repository.HobbyRepository;

@Route("hobbies")
public class HobbyView extends VerticalLayout {

    private final HobbyRepository hobbyRepo;

    private final Grid<Hobby> grid = new Grid<>(Hobby.class);
    private final TextField hobbyNameField = new TextField("Harrastuksen nimi");

    private final Button saveButton = new Button("Tallenna");
    private final Button deleteButton = new Button("Poista");

    private Hobby selectedHobby;

    public HobbyView(HobbyRepository hobbyRepo) {
        this.hobbyRepo = hobbyRepo;

        NavBar navBar = new NavBar();
        add(navBar);

        configureGrid();
        configureForm();

        refreshGrid();
    }

    private void configureGrid() {
        grid.setColumns("nimi");
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedHobby = event.getValue();
            if (selectedHobby != null) {
                hobbyNameField.setValue(selectedHobby.getNimi());
            }
        });
        add(grid);
    }

    private void configureForm() {
        saveButton.addClickListener(e -> saveHobby());
        deleteButton.addClickListener(e -> deleteHobby());

        HorizontalLayout formLayout = new HorizontalLayout(hobbyNameField, saveButton, deleteButton);
        add(formLayout);
    }

    private void saveHobby() {
        if (selectedHobby == null) {
            selectedHobby = new Hobby();
        }
        selectedHobby.setNimi(hobbyNameField.getValue());
        hobbyRepo.save(selectedHobby);
        clearForm();
        refreshGrid();
    }

    private void deleteHobby() {
        if (selectedHobby != null && selectedHobby.getId() != null) {
            hobbyRepo.deleteById(selectedHobby.getId());
            clearForm();
            refreshGrid();
        }
    }

    private void refreshGrid() {
        grid.setItems(hobbyRepo.findAll());
    }

    private void clearForm() {
        selectedHobby = null;
        hobbyNameField.clear();
    }
}
