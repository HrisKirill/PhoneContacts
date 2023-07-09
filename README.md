### Phone contacts application allows adding/editing and deleting contacts data. Single contact is represented by the following data:
- Contact name

- Contact emails. One contact may have multiple emails

- Contact phone number. One contact may have multiple phone numbers

### User have a possibility to:

- Register in the app, login and password should be provided during registration

- Login to the app

- Add new contact

- Edit existing contact

- Delete existing contact

- Get list of existing contacts


### The app is implemented with:


- Spring Boot, Spring Web, Spring Security and Spring Data + Hibernate


- access only to authorized users, so each user has his own list of phone contacts


-  emails and phone numbers validation, it is not possible to add phone number like “+38-asdas” or email like “aa@”. Every phone number and email is unique per contact, so it not be possible to add already existing email, the same for phone numbers


- unique contact


- unit and integration tests


- swagger documentation


- uploading image for each contact when create/update contact

