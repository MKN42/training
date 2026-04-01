package de.mkn.training.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.mkn.training.model.Exercise;
import de.mkn.training.model.ExerciseSupportType;
import de.mkn.training.model.Subject;
import de.mkn.training.model.SubjectCategory;
import de.mkn.training.repository.ExerciseRepository;
import de.mkn.training.repository.SubjectRepository;

@Route("add-exercise")
@PageTitle("Add Exercise")
public class AddExerciseView extends VerticalLayout {

    private final ExerciseRepository exerciseRepository;
    private final SubjectRepository subjectRepository;

    private final ComboBox<Subject> subjectField = new ComboBox<>("Subject");
    private final TextField nameField = new TextField("Name");
    private final TextArea descriptionField = new TextArea("Description");
    private final TextField subtypeField = new TextField("Type");
    private final Select<ExerciseSupportType> supportTypeField = new Select<>();
    private final IntegerField defaultDurationField = new IntegerField("Default Duration (min)");
    private final TextField videoUrlField = new TextField("Video URL");
    private final TextField appUrlField = new TextField("App URL");

    private final IntegerField workSecondsField = new IntegerField("Work (sec)");
    private final IntegerField restSecondsField = new IntegerField("Rest (sec)");
    private final IntegerField setsField = new IntegerField("Sets");
    private final VerticalLayout intervalSection = new VerticalLayout();

    private final IntegerField bpmField = new IntegerField("BPM");
    private final VerticalLayout metronomeSection = new VerticalLayout();

    public AddExerciseView(ExerciseRepository exerciseRepository, SubjectRepository subjectRepository) {
        this.exerciseRepository = exerciseRepository;
        this.subjectRepository = subjectRepository;

        setPadding(true);
        setMaxWidth("800px");
        getStyle().set("margin", "0 auto");

        buildIntervalSection();
        buildMetronomeSection();

        add(new H2("Add Exercise"));
        add(buildForm());
        add(intervalSection);
        add(metronomeSection);
        add(buildButtons());
    }

    private FormLayout buildForm() {
        subjectField.setItemLabelGenerator(Subject::getName);
        subjectField.setItems(subjectRepository.findAll());
        subjectField.setRequired(true);
        subjectField.addValueChangeListener(e -> onSubjectChanged(e.getValue()));

        nameField.setRequired(true);

        descriptionField.setMinHeight("80px");

        supportTypeField.setLabel("Support Type");
        supportTypeField.setItems(ExerciseSupportType.values());
        supportTypeField.setValue(ExerciseSupportType.NONE);
        supportTypeField.addValueChangeListener(e -> updateSupportSections(e.getValue()));

        defaultDurationField.setMin(1);
        workSecondsField.setMin(1);
        restSecondsField.setMin(0);
        setsField.setMin(1);
        bpmField.setMin(20);
        bpmField.setMax(300);

        FormLayout form = new FormLayout();
        form.setResponsiveSteps(
            new FormLayout.ResponsiveStep("0", 1),
            new FormLayout.ResponsiveStep("600px", 2)
        );
        form.add(subjectField, nameField, descriptionField, subtypeField,
                 supportTypeField, defaultDurationField, videoUrlField, appUrlField);
        form.setColspan(descriptionField, 2);
        return form;
    }

    private void buildIntervalSection() {
        FormLayout layout = new FormLayout(workSecondsField, restSecondsField, setsField);
        intervalSection.add(new H4("Interval Timer"), layout);
        intervalSection.setPadding(false);
        intervalSection.setVisible(false);
    }

    private void buildMetronomeSection() {
        metronomeSection.add(new H4("Metronome"), bpmField);
        metronomeSection.setPadding(false);
        metronomeSection.setVisible(false);
    }

    private HorizontalLayout buildButtons() {
        Button saveButton = new Button("Save", e -> save());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button cancelButton = new Button("Cancel", e -> getUI().ifPresent(ui -> ui.navigate("")));

        return new HorizontalLayout(saveButton, cancelButton);
    }

    private void onSubjectChanged(Subject subject) {
        if (subject == null) {
            subtypeField.setLabel("Type");
            subtypeField.setPlaceholder("");
            return;
        }
        switch (subject.getCategory()) {
            case PHYSICAL -> {
                subtypeField.setLabel("Body Part");
                subtypeField.setPlaceholder("e.g. Chest, Legs, Core");
                supportTypeField.setValue(ExerciseSupportType.INTERVAL_TIMER);
            }
            case MUSICAL -> {
                subtypeField.setLabel("Exercise Kind");
                subtypeField.setPlaceholder("e.g. Song, Lick, Technical");
                supportTypeField.setValue(ExerciseSupportType.METRONOME);
            }
            case COGNITIVE -> {
                subtypeField.setLabel("Drill Type");
                subtypeField.setPlaceholder("e.g. Flashcard, Translation");
                supportTypeField.setValue(ExerciseSupportType.NONE);
            }
            default -> {
                subtypeField.setLabel("Type");
                subtypeField.setPlaceholder("");
                supportTypeField.setValue(ExerciseSupportType.NONE);
            }
        }
    }

    private void updateSupportSections(ExerciseSupportType type) {
        intervalSection.setVisible(type == ExerciseSupportType.INTERVAL_TIMER);
        metronomeSection.setVisible(type == ExerciseSupportType.METRONOME);
    }

    private void save() {
        if (subjectField.isEmpty() || nameField.getValue().isBlank()) {
            Notification n = Notification.show("Subject and Name are required.");
            n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        Exercise exercise = new Exercise(subjectField.getValue(), nameField.getValue().trim());
        exercise.setDescription(descriptionField.getValue());
        exercise.setSubtype(subtypeField.getValue().isBlank() ? null : subtypeField.getValue().trim());
        exercise.setSupportType(supportTypeField.getValue());
        exercise.setDefaultDurationMinutes(defaultDurationField.getValue());
        exercise.setVideoUrl(videoUrlField.getValue().isBlank() ? null : videoUrlField.getValue().trim());
        exercise.setAppUrl(appUrlField.getValue().isBlank() ? null : appUrlField.getValue().trim());

        if (supportTypeField.getValue() == ExerciseSupportType.INTERVAL_TIMER) {
            exercise.setIntervalWorkSeconds(workSecondsField.getValue());
            exercise.setIntervalRestSeconds(restSecondsField.getValue());
            exercise.setIntervalSets(setsField.getValue());
        } else if (supportTypeField.getValue() == ExerciseSupportType.METRONOME) {
            exercise.setMetronomeBpm(bpmField.getValue());
        }

        exerciseRepository.save(exercise);

        Notification n = Notification.show("Exercise \"" + exercise.getName() + "\" saved!");
        n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        clearForm();
    }

    private void clearForm() {
        subjectField.clear();
        nameField.clear();
        descriptionField.clear();
        subtypeField.clear();
        subtypeField.setLabel("Type");
        subtypeField.setPlaceholder("");
        supportTypeField.setValue(ExerciseSupportType.NONE);
        defaultDurationField.clear();
        videoUrlField.clear();
        appUrlField.clear();
        workSecondsField.clear();
        restSecondsField.clear();
        setsField.clear();
        bpmField.clear();
    }
}
