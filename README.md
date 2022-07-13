# Full Stack Developer Coding Challenge

You will be building a basic version of a Ground Resources Management (GRM) Dashboard. There are two JSON files in this repository, contacts.json and alerts.json. Contacts (satellites) is a list of satellites in orbit and includes pertinent information about each device. Alerts is a list of unrelated status alerts with varying levels of severity. You will need to take this JSON data and persist it in a backend database. You will then develop a backend API, to be called upon by your frontend, which will display the data. This dashboard should allow registration and login of a user, requiring a password, and the user's credentials should also be persisted in your database.

The result should be a dashboard with two pages (the application can be single-page (SPA), or multiple pages):

    A registration/login page
    A main page that displays contact and alert information in a clean, user-friendly format

Because this position requires familiarity with web components, we would like you to refer to our Astro UX Design site (https://astrouxds.com/) for this project. You may work with the web components directly, or build your own. We also encourage you to utilize our CSS-only library to better match Astro styling.

Refer to the section Astro Storybook and Sample Apps at this link to get more details about Astro components, and to see several sample apps that may provide you with inspiration (https://astrouxds.com/getting-started/developers/).
Requirements

Backend

    contacts.json, alerts.json, and user credentials data is persisted in a database.
        Each data point should have a corresponding column in the database.
        You may use any databse tool of your choice, such as PostgresQL, MongoDB, SQLite, etc.

Frontend

    The application utilizes either Astro UXDS components, or Astro UXDS styling
    Login Page Reuirements
        Authentication is required to access the application
        User must register for a new account, or login with an existing account, to proceed to the dashboard
    Main Page/Dashboard Requirements
        The dashboard page cleanly displays the data from contacts.json, alerts.json in two separate tables
        The following should be displayed in the Contacts pane:
            Display the total number of Contacts.
            Display the total different Contact states (contactState).
            For each Contact, display Name (contactName), Status (contactStatus), and Begin/End timestamp (contactBeginTimestamp/contactEndTimestamp).
            Allow sorting on the name.
        The following should be displayed in the Alerts pane:
            Display each Alert message (errorMessage).
            Display each Alert category (errorCategory).
            Display each Alert time (errorTime).
            Allow sorting on the category.
