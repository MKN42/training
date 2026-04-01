package de.mkn.training.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class HomeView extends VerticalLayout {

    public HomeView() {
        add(new H1("Training App"));

        Button addExerciseButton = new Button("Add Exercise", e -> getUI().ifPresent(ui -> ui.navigate("add-exercise")));
        addExerciseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(addExerciseButton);
    }
}
