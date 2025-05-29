package org.hohoho.cheer.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hohoho.cheer.component.NavBar;
import org.hohoho.cheer.model.Address;
import org.hohoho.cheer.model.Hobby;
import org.hohoho.cheer.model.Measurement;
import org.hohoho.cheer.model.Person;
import org.hohoho.cheer.repository.HobbyRepository;
import org.hohoho.cheer.repository.MeasurementRepository;
import org.hohoho.cheer.repository.PersonRepository;
import com.vaadin.flow.component.html.H1;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Route("")
@PageTitle("Henkilöhallinta")
public class MainView extends VerticalLayout {

    private final PersonRepository personRepo;
    private final HobbyRepository hobbyRepo;
    private final MeasurementRepository measurementRepo;

    private final Grid<Person> grid = new Grid<>(Person.class);

    private final TextField nameField = new TextField("Nimi");
    private final DatePicker birthdateField = new DatePicker("Syntymäaika");
    private final TextField katuosoiteField = new TextField("Katuosoite");
    private final TextField postinumeroField = new TextField("Postinumero");
    private final TextField kaupunkiField = new TextField("Kaupunki");

    private final CheckboxGroup<Hobby> hobbyGroup = new CheckboxGroup<>();

    private final Button saveButton = new Button("Tallenna");
    private final Button deleteButton = new Button("Poista");
    private final Button refreshButton = new Button("Päivitä");

    private final Grid<Measurement> measurementGrid = new Grid<>(Measurement.class);
    private final DatePicker measurementDateField = new DatePicker("Päivämäärä");
    private final TextField weightField = new TextField("Paino (kg)");
    private final TextField bloodPressureField = new TextField("Verenpaine");

    private final Button addMeasurementButton = new Button("Lisää mittaus");
    private final Button deleteMeasurementButton = new Button("Poista mittaus");

    private Person selectedPerson;
    private Measurement selectedMeasurement;

    public MainView(PersonRepository personRepo, HobbyRepository hobbyRepo, MeasurementRepository measurementRepo) {
        this.personRepo = personRepo;
        this.hobbyRepo = hobbyRepo;
        this.measurementRepo = measurementRepo;

        NavBar navBar = new NavBar();
        add(navBar);
        // Header
        HorizontalLayout header = createHeader();

        // Muu sisältö
        configureGrid();

        HorizontalLayout personForm = configureForm();
        VerticalLayout hobbySection = configureHobbySection();
        VerticalLayout measurementSection = configureMeasurementSection();

        // Lisää kaikki: header, grid, lomakkeet
        add(header, grid, personForm, hobbySection, measurementSection);

        // Päivitä lista
        refreshGrid();
    }

    private HorizontalLayout createHeader() {
        H1 title = new H1("Henkilöhallinta");
        title.getStyle().set("margin", "0");

        Button homeButton = new Button("Koti", e -> getUI().ifPresent(ui -> ui.navigate("")));
        homeButton.getStyle().set("margin-left", "auto");

        HorizontalLayout header = new HorizontalLayout(title, homeButton);
        header.setWidthFull();
        header.getStyle().set("background-color", "#333").set("color", "white").set("padding", "10px");

        return header;
    }

    private void configureGrid() {
        grid.removeAllColumns();
        grid.addColumn(Person::getNimi).setHeader("Nimi");
        grid.addColumn(Person::getSyntymaaika).setHeader("Syntymäaika");
        grid.addColumn(person -> {
            Address addr = person.getAddress();
            if (addr == null) return "";
            return addr.getKatuosoite() + ", " + addr.getPostinumero() + " " + addr.getKaupunki();
        }).setHeader("Osoite");

        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedPerson = event.getValue();
            if (selectedPerson != null) {
                nameField.setValue(selectedPerson.getNimi() != null ? selectedPerson.getNimi() : "");
                birthdateField.setValue(selectedPerson.getSyntymaaika());
                Address addr = selectedPerson.getAddress();
                if (addr != null) {
                    katuosoiteField.setValue(addr.getKatuosoite() != null ? addr.getKatuosoite() : "");
                    postinumeroField.setValue(addr.getPostinumero() != null ? addr.getPostinumero() : "");
                    kaupunkiField.setValue(addr.getKaupunki() != null ? addr.getKaupunki() : "");
                } else {
                    katuosoiteField.clear();
                    postinumeroField.clear();
                    kaupunkiField.clear();
                }
                if (selectedPerson.getHobbies() != null) {
                    hobbyGroup.setValue(selectedPerson.getHobbies());
                } else {
                    hobbyGroup.clear();
                }
                refreshMeasurementGrid();
            }
        });
    }

    private HorizontalLayout configureForm() {
        saveButton.addClickListener(e -> savePerson());
        deleteButton.addClickListener(e -> deletePerson());
        refreshButton.addClickListener(e -> refreshGrid());

        HorizontalLayout formLayout = new HorizontalLayout(
                nameField, birthdateField,
                katuosoiteField, postinumeroField, kaupunkiField,
                saveButton, deleteButton, refreshButton
        );
        formLayout.setSpacing(true);
        return formLayout;
    }

    private VerticalLayout configureHobbySection() {
        hobbyGroup.setLabel("Harrastukset");
        hobbyGroup.setItemLabelGenerator(Hobby::getNimi);
        hobbyGroup.setItems(hobbyRepo.findAll());

        VerticalLayout hobbyLayout = new VerticalLayout(hobbyGroup);
        hobbyLayout.setSpacing(true);
        return hobbyLayout;
    }

    private VerticalLayout configureMeasurementSection() {
        measurementGrid.removeAllColumns();
        measurementGrid.addColumn(Measurement::getDate).setHeader("Päivämäärä");
        measurementGrid.addColumn(Measurement::getWeight).setHeader("Paino (kg)");
        measurementGrid.addColumn(Measurement::getBloodPressure).setHeader("Verenpaine");

        measurementGrid.asSingleSelect().addValueChangeListener(event -> {
            selectedMeasurement = event.getValue();
            if (selectedMeasurement != null) {
                measurementDateField.setValue(selectedMeasurement.getDate());
                weightField.setValue(String.valueOf(selectedMeasurement.getWeight()));
                bloodPressureField.setValue(selectedMeasurement.getBloodPressure());
            }
        });

        addMeasurementButton.addClickListener(e -> saveMeasurement());
        deleteMeasurementButton.addClickListener(e -> deleteMeasurement());

        HorizontalLayout measurementForm = new HorizontalLayout(
                measurementDateField, weightField, bloodPressureField,
                addMeasurementButton, deleteMeasurementButton
        );
        measurementForm.setSpacing(true);

        return new VerticalLayout(measurementGrid, measurementForm);
    }

    private void savePerson() {
        if (selectedPerson == null) {
            selectedPerson = new Person();
        }

        selectedPerson.setNimi(nameField.getValue());
        selectedPerson.setSyntymaaika(birthdateField.getValue());

        Address addr = selectedPerson.getAddress();
        if (addr == null) {
            addr = new Address("Kaupunki");
        }
        addr.setKatuosoite(katuosoiteField.getValue());
        addr.setPostinumero(postinumeroField.getValue());
        addr.setKaupunki(kaupunkiField.getValue());
        selectedPerson.setAddress(addr);

        selectedPerson.setHobbies(hobbyGroup.getValue());

        personRepo.save(selectedPerson);
        clearForm();
        refreshGrid();
    }

    private void deletePerson() {
        if (selectedPerson != null && selectedPerson.getId() != null) {
            personRepo.deleteById(selectedPerson.getId());
            clearForm();
            refreshGrid();
        }
    }

    private void refreshGrid() {
        grid.setItems(personRepo.findAll());
    }

    private void clearForm() {
        selectedPerson = null;
        nameField.clear();
        birthdateField.clear();
        katuosoiteField.clear();
        postinumeroField.clear();
        kaupunkiField.clear();
        hobbyGroup.clear();
    }

    private void saveMeasurement() {
        if (selectedPerson == null) return;

        Measurement m = selectedMeasurement != null ? selectedMeasurement : new Measurement();
        m.setDate(measurementDateField.getValue());
        m.setWeight(Double.parseDouble(weightField.getValue()));
        m.setBloodPressure(bloodPressureField.getValue());
        m.setPerson(selectedPerson);

        measurementRepo.save(m);
        selectedMeasurement = null;
        clearMeasurementForm();
        refreshMeasurementGrid();
    }

    private void deleteMeasurement() {
        if (selectedMeasurement != null) {
            measurementRepo.deleteById(selectedMeasurement.getId());
            selectedMeasurement = null;
            clearMeasurementForm();
            refreshMeasurementGrid();
        }
    }

    private void refreshMeasurementGrid() {
        if (selectedPerson != null && selectedPerson.getId() != null) {
            measurementGrid.setItems(measurementRepo.findByPersonId(selectedPerson.getId()));
        } else {
            measurementGrid.setItems();
        }
    }

    private void clearMeasurementForm() {
        measurementDateField.clear();
        weightField.clear();
        bloodPressureField.clear();
    }
}
