# Event Creator Api

Event Creator Api is the third and the last part of a desing and creation series of smaller projects.

- Event calendar
- Events Creator Frontend
- Events Creator Api

---

## Introduction

The goal of this project is to provide Spring Boot backend functionality for your events app. It should allow users perform CRUD operations.

---

## MVP

The user should be able to do the following:
• Create a new event
• Update an existing future event
• Delete an event
• Retrieve all events from the database
• Retrieve a single event from the database
• Filter events by labels
• Filter events by location
All data coming from the user should be validated. Tests are required.

---

## Build Steps

- how to build / run project

1. Clone the repo and run

```bash

```

2. Run

```bash

```

3. And visit the provided url.

---

## Design Goals / Approach

- Version control using GitHub
- Project management using GitHub Issues and Linear
- API-First approach using OpenAPI and SwaggerHub
- Test-driven development TDO: Red, Green, Refactor development cycle
- Continuous integration/ continuous deployment using GitHub Actions

- I chose to use ModelMapper in this project.
- I chose to use JavaFaker for data seeding
- I am using Abstract class as a BaseEntity class holding ID, createdAt and updatedAt, which is then inherited by the child
- For testing I chose RestAssure for end to end testing and Mockito for unit testing

---

## Features

---

## Known issues

---

## Future Goals

---

## What did you struggle with?

---

## Licensing Details

- Public, free
