# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./mvnw clean package

# Run
./mvnw spring-boot:run

# Test (all)
./mvnw test

# Test (single class)
./mvnw test -Dtest=ApplicationTests

# Clean build
./mvnw clean install
```

## Architecture

Spring Boot 4 + Vaadin 25 web application (Java 21).

- **Entry point:** `Application.java` — `@SpringBootApplication` + Vaadin's `AppShellConfigurator` (registers `styles.css`)
- **Views:** `de.mkn.training.views` — Vaadin components annotated with `@Route`. `HomeView` maps to `"/"`.
- **Styles:** `src/main/resources/META-INF/resources/styles.css`
- **Config:** `src/main/resources/application.properties` (currently sets `vaadin.launch-browser=true`)

Vaadin handles routing and UI rendering server-side; no separate frontend build step is needed during development — the `vaadin-maven-plugin` runs automatically during `package`.

## Core Features (MVP)
- Feature DICE: I want to select a subject (eg. Strength exercise, Guitar, Vocabulary, etc. ) and a time (e.g. single exercies, 5 min, 10 min, 30 min) and the training app should give me a random exercise plan that takes my settings into account
- Feature Add Exercise: I want to be able to add different types of exercises to my Database. A Exercise can have a description, a type (for physical exercise part of body that is under exercise, for musical exercise it can be a song, a lick, technical training), it can have a picture that shows the training and it can have a link to a video or a app that opens with the exercies
- Feature Exercise Support: During an exercise, the Training app should support me with a interval timer for phisical exercises and a metronom for specific guitar exercise
- Feature Statistics: I want to be able to keep track of my finished exercies and the relevant achievements (e.g. repetitions at exercies, speed etc.)