package org.hohoho.cheer.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.Route;
import org.hohoho.cheer.component.NavBar;
import org.hohoho.cheer.model.Person;
import org.hohoho.cheer.model.Hobby;
import org.hohoho.cheer.repository.PersonRepository;
import org.hohoho.cheer.repository.HobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.button.Button;
import java.awt.*;
import java.util.List;


@Route("personview")
public class PersonView extends VerticalLayout {

    private final PersonRepository personRepo;
    private final HobbyRepository hobbyRepo;
    private Grid<Person> grid;
    private TextField filterName;
    private TextField filterCity;
    private ComboBox<Hobby> filterHobby;

    @Autowired
    public PersonView(PersonRepository personRepo, HobbyRepository hobbyRepo) {
        this.personRepo = personRepo;
        this.hobbyRepo = hobbyRepo;

        this.grid = new Grid<>(Person.class, false);

        NavBar navBar = new NavBar();
        add(navBar);

        filterName = new TextField("Suodata nimen mukaan");
        filterCity = new TextField("Suodata kaupungin mukaan");
        filterHobby = new ComboBox<>("Suodata harrastuksen mukaan");
        Button clearButton = new Button("Tyhjennä suodattimet", e -> {
            filterName.clear();
            filterCity.clear();
            filterHobby.clear();
        });

        configureGrid();
        configureFilters();

        add(new HorizontalLayout(filterName, filterCity, filterHobby), grid);
        updateGrid();
    }

    private void configureGrid() {
        grid.addColumn(Person::getNimi).setHeader("Nimi");
        grid.addColumn(person -> {
            if (person.getAddress() != null) {
                return person.getAddress().getKaupunki();
            }
            return "";
        }).setHeader("Kaupunki");
        grid.addColumn(Person::getSyntymaaika).setHeader("Syntymäaika");
        grid.addColumn(person -> person.getHobbies().toString()).setHeader("Harrastukset");
    }


    private void configureFilters() {
        filterName.setPlaceholder("Kirjoita nimi...");
        filterCity.setPlaceholder("Kirjoita kaupunki...");

        filterHobby.setItems(hobbyRepo.findAll());
        filterHobby.setItemLabelGenerator(Hobby::getNimi);

        filterName.addValueChangeListener(e -> updateGrid());
        filterCity.addValueChangeListener(e -> updateGrid());
        filterHobby.addValueChangeListener(e -> updateGrid());
    }

    private void updateGrid() {
        List<Person> persons = personRepo.findAll();

        List<Person> filtered = persons.stream()
                .filter(p -> filterName.isEmpty() ||
                        (p.getNimi() != null && p.getNimi().toLowerCase().contains(filterName.getValue().toLowerCase())))
                .filter(p -> filterCity.isEmpty() ||
                        (p.getAddress() != null && p.getAddress().getKaupunki() != null &&
                                p.getAddress().getKaupunki().toLowerCase().contains(filterCity.getValue().toLowerCase())))
                .filter(p -> filterHobby.isEmpty() ||
                        (p.getHobbies() != null && p.getHobbies().contains(filterHobby.getValue())))
                .toList();

        grid.setItems(filtered);
    }
}
