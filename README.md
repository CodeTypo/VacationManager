# VacationManager

A simple Java Enterprise Edition app utilising Tomcat servlets to deliver a suitable system
for employee vacation management.

# Login screen
![image](https://user-images.githubusercontent.com/61741336/123626682-64ac7700-d811-11eb-918c-07d63072b823.png)
User credentials are stored in a session object so as to keep them hidden and safe.

There is also an "I am an admin" checkbox which redirects admins to their main tab after a successfu login.

Users which are unregistered yet are given the ability to create an account by pressing the link.

# Registration form
![image](https://user-images.githubusercontent.com/61741336/123626753-7857dd80-d811-11eb-841d-1c3c0d4a2bd6.png)

Registration form consists of basic data inputs, as well as the "First day of work" date input. The date is then being processed to
estimate the appropriate amount of vacation days for each employee.

# User homepage tab
![image](https://user-images.githubusercontent.com/61741336/123626819-8a398080-d811-11eb-9ad1-bc531ace01f8.png)
After successful login, the user is being redirected to his / her main tab  consisting of a table of currently requested vacations and a statement informing the user
how many vacation days are left for him / her. There is also a navbar which allows the user to switch to one of the action tabs:

- Book vacations: a simple form allowing the user to request a vacation within given dates.
- My requests: a table displaying all the currently requested vacations, so that the user can check whether they are correct and edit / remove some vacations if needed.

# Vacation booking form
![image](https://user-images.githubusercontent.com/61741336/123626868-96254280-d811-11eb-9c76-43da0b25b0dd.png)

A simple form allowing the user to request a vacation between two given dates. After submitting, the form is being heavily validated to check if:
- the user has enough vacation days left
- the vacation requested is not overlaping with another vacation requested by this user

 # User homepage after submitting vacation request
![image](https://user-images.githubusercontent.com/61741336/123626901-a1786e00-d811-11eb-93cd-5c4bbfae7ee2.png)

After submitting a request, it appears on the user home tab, an "unapproved" state is being automatically assigned to it. It appears on admin-side as well allowing the admin to check the request and approve it, if everything is correct

# Admin-side elements 

![image](https://user-images.githubusercontent.com/61741336/123672899-267a7c00-d840-11eb-8151-b3f8db32b00e.png)

The admin, logs in using the same login form as ordinary users. She / he has to chcek the "I am an admin" checkbox to get the program to verify if the credentials provided 
are matching any admin account stored in database. After succesfull login, admin is being redirected to the tab which consists of all the records in the connected database. Records are divided into three categories: 
- Users: a table containing login data about each single user
- Details: a table contatining detailed information about every single employee, currently the details are a little limited but there is a possibility to expand the database table if necessary
- Vacations: a table containing all the vacations requested by every single employee, ever.

# Admin requests management
A tab that consists of a table containing all the vacation requests filled by employees. Every request row has 3 buttons attached allowing the admin to APPROVE the vacation request, DENY it, or completely DELETE it from the requests database.
