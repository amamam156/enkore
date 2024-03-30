project folder:
amamam156-enkore/


Brief description of submitted files:

/screenshots
    - Screenshot of the program running

/src/main/java
    - Backend source code

/src/main/resources/backend
    - Program backend resources

/src/main/java/resources/front
    - Program front resources


project description:

enkore is a software product specially customized for catering companies (restaurants, hotels), including system management backend and mobile application.
The system management backend is mainly provided for internal employees of catering companies to manage and maintain restaurant categories, dishes, packages, orders, employees, etc.
The mobile application is mainly provided to consumers, who can browse dishes online, add shopping carts, place orders, etc.

Development environment:

Environment (software): JDK + MySQL +IDEA + Git + Tomcat
Technologies: Spring Boot, SSM, Mybatis Plus, Spring Security, Redis, Spring Cache, Swagger, Linux, ShardingJDBC, Nginx

duty description:

Responsible for the preparation of staff, categories, dishes, and set meal modules in the system management backend.
Responsible for the development of mobile applications and implementing the SMS verification code login function.
Develop the menu browsing function of mobile applications so that users can browse menu information conveniently.
Implement the management function of user address book information in mobile applications to facilitate users to manage delivery addresses.
Develop the shopping cart function of the mobile application so that users can easily add dishes to the shopping cart and manage orders.
Implement the function of order submission in mobile applications to ensure that users can place orders successfully.
Optimize the data display cache of mobile applications to improve system response speed and user experience.
Responsible for database master-slave configuration and implementation of read-write separation to improve database performance and reliability.
Implement the project interface API document generation function and use Swagger to generate clear interface documents.

Technical Description:

Use Redis to cache high-frequency data, and use Spring Cache to optimize code to improve system performance and response speed.
Use the Linux system for deployment throughout the development process, taking full advantage of the stability and security of the Linux system.
Use Nginx to deploy front-end projects to achieve a front-end and back-end separation deployment method, improving the flexibility and maintainability of the system.
Use Nginx to implement reverse proxy and load balancing to improve the concurrent processing capability and availability of the system.
Use ShardingJDBC to separate database reading and writing and improve the reading and writing performance of the database.
Use the Spring Security framework to develop permission modules and adopt the classic RBAC model for fine-grained permission management.
Use YApi to achieve separate development of front-end and back-end, and use Swagger to generate interface API documents to improve team collaboration efficiency.
Use Git for project version control and code management, and make full use of the branch function for project optimization and problem repair.