# Opel Exam – Data Access

> **Exam project** for the **Data Access** (*Acceso a Datos*) subject of the higher vocational training
> programme in **Cross-Platform Application Development (DAM – Desarrollo de Aplicaciones Multiplataforma)**,
> completed in **2023**.

A web application for an Opel car catalogue. It lets users browse models by type, view the trim levels
of each one with their equipment, and book a test-drive appointment. The goal of the exam was to practise
data persistence with **JPA** on a relational database, including one-to-many and many-to-many relationships.

**Author:** Iván Gómez – Azarquiel

## Features

- Model catalogue with images, filterable by type from the navigation bar
  (Saloon, SUV, Commercial, Electric, Hybrid).
- Modal window showing each model's trim levels (Edition, Elegance, GS Line, Ultimate) and their equipment,
  loaded via AJAX without reloading the page.
- User registration and login in a single form: if the user does not exist, it is created.
- Test-drive appointment booking, available only when logged in.

## Technologies

- Java EE 7: Servlets and JSP, running on Apache Tomcat
- JPA 2.1 with EclipseLink
- H2 database
- Bootstrap 4 and jQuery
- NetBeans (Ant web project)

## Data model

| Table | Description |
|---|---|
| `TIPO` | Body or powertrain types |
| `MODELO` | Car models with their image |
| `TIPOMODELO` | Many-to-many relation between types and models |
| `ACABADO` | Trim levels |
| `MODELOACABADO` | Trim levels available for each model |
| `ITEMACABADO` | Equipment items |
| `MODELOACABADOITEM` | Many-to-many relation between a model's trim level and its equipment |
| `USUARIO` | Registered users |
| `CITA` | Test-drive appointments |

## How to run

1. Open the project in NetBeans.
2. Check the database URL in `src/conf/persistence.xml`
   (default `jdbc:h2:C:\Users\Usuario\bd\opel`) and change it to a valid path on your machine.
3. Run **Clean and Build** and deploy it on Tomcat. The entry page is `index.html`,
   which redirects to `Controller?op=inicio`.

The database is recreated on every deployment (`drop-and-create`) and automatically populated from
`util.JPAUtil` with types, models, trim levels, equipment, users and sample appointments.
This means users and appointments created from the web are lost when the application is redeployed.

Test users: `admin/admin`, `ivan/1234`, `navarrata/navarrata`.

## Project structure

```
src/java/controller/Controller.java   Main servlet (op=inicio | vatipo | acabados | login | logout | cita)
src/java/entities/                    JPA entities
src/java/util/JPAUtil.java            EntityManagerFactory and initial data
src/conf/persistence.xml              JPA and H2 configuration
web/home.jsp                          Main page
web/acabados.jsp                      Trim levels fragment (loaded via AJAX)
web/myjs.js                           Modal windows logic
web/img/                              Model images
```
