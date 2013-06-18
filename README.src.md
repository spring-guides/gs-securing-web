# Getting Started: Creating a Login Page

What You'll Build
-----------------

This guide walks you through creating a simple web application that has resources that are protected with Spring Security and require login through a login form.

What You'll Need
----------------

- About 15 minutes
- A favorite text editor or IDE
- {!include#prereq-editor-jdk-buildtools}

## {!include#how-to-complete-this-guide}

<a name="scratch"></a>
Set up the project
------------------

{!include#build-system-intro}

{!include#create-directory-structure-hello}

### Create a Maven POM

    {!include:initial/pom.xml}

{!include#bootstrap-starter-pom-disclaimer}

### Create an unsecured web application

Before you can apply security to a web application, you'll need a web application to secure. The steps in this section will walk you through creating a very simple web application. Then you'll secure it with Spring Security in the next section.

The web application will include two simple views: A home page and a "Hello World" page. The home page is defined in the following Thymeleaf template:

    {!include:initial/src/main/resources/templates/home.html}

As you can see, this simple view include a link to the page at "/hello". That is defined in the following Thymeleaf template:

    {!include:initial/src/main/resources/templates/hello.html}

The web application will be based on Spring MVC. Therefore, you'll need to configure Spring MVC and setup view controllers to expose these templates. Here's a configuration class for configuring Spring MVC in the application.

    {!include:initial/src/main/java/hello/MvcConfig.java}

The `@EnableWebMvc` annotation configures much of Spring MVC. Meanwhile, the `addViewControllers()` method (overriding the method of the same name in `WebMvcConfigurerAdapter`) adds four view controllers. Two of the view controllers reference the view whose name is "home" (defined in `home.html`) and another references the view named "hello" (defined in `hello.html`). The fourth view controller references another view named "login". You'll create that view in the next section.

At this point, you could jump ahead to the _[Run the application](#run)_ section and run the application. It will work near-perfectly (the logout link won't work, but otherwise it's a functioning Spring MVC application).

With the base simple web application created, now it's time to add security to it.

<a name="initial"></a>
Setup Spring Security
---------------------

Suppose that you want to prevent unauthorized users from viewing the greeting page at "/hello". As it is now, if a user clicks the link on the home page, they'll be shown the greeting with no barriers to stop them. Therefore, you'll need to add a barrier that forces the user to sign in before seeing that page.

To do that, you'll need to configure Spring Security in the application. Here's a security configuration that will ensure that only authenticated users can see the secret greeting:

    {!include:complete/src/main/java/hello/WebSecurityConfig.java}

The `WebSecurityConfig` class is annotated with `@EnableWebSecurity` to enable Spring Security's web security support. It also extends `WebSecurityConfigurerAdapter` and overrides a couple of its methods to set some specifics of the web security configuration.

The `configure()` method defines which URL paths should be secured and which should not. Specifically, the "/hello" path is configured to require that the user have "USER" role. If not then it could mean that the user hasn't signed in yet and the user will be automatically taken to the login page. Meanwhile, the "/**" path (using Ant-style wildcarding to indicate all paths not previously constrained) is configured to permit access to all users, authenticated or not. 

THe `configure()` method goes on to indicate that after a successful login the user's browser should be redirected to "/hello". And, if the user logs out, then they should be redirected to "/".

As for the `registerAuthentication()` method, it sets up an in-memory user store with a single user. That user is given a username of "user", a password of "password", and a role of "USER".

All that's left to do is create the login page. There's already a view controller for the "login" view, so you'll only need to create the login view itself:

    {!include:complete/src/main/resources/templates/login.html}

As you can see, this Thymeleaf template simply presents a form that captures a username and password and posts them to "/login". As configured, Spring Security provides a filter that intercepts that request and authenticates the user.

Make the application executable
-------------------------------

Although it is possible to package this service as a traditional _web application archive_ or [WAR][u-war] file for deployment to an external application server, the simpler approach demonstrated below creates a _standalone application_. You package everything in a single, executable JAR file, driven by a good old Java `main()` method. And along the way, you use Spring's support for embedding the [Tomcat][u-tomcat] servlet container as the HTTP runtime, instead of deploying to an external instance.

### Create a main class

    {!include:complete/src/main/java/hello/Application.java}

The `main()` method defers to the [`SpringApplication`][] helper class, providing `Application.class` as an argument to its `run()` method. This tells Spring to read the annotation metadata from `Application` and to manage it as a component in the _[Spring application context][u-application-context]_.

The `@ComponentScan` annotation tells Spring to search recursively through the `hello` package and its children for classes marked directly or indirectly with Spring's [`@Component`][] annotation. This directive ensures that Spring finds and registers the `WebConfig` and `WebSecurityConfig`, because they are marked with `@Configuration`, which in turn is a kind of `@Component` annotation. In effect, those configuration classes will also be used to configure Spring.

The [`@EnableAutoConfiguration`][] annotation switches on reasonable default behaviors based on the content of your classpath. For example, because the application depends on the embeddable version of Tomcat (tomcat-embed-core.jar), a Tomcat server is set up and configured with reasonable defaults on your behalf. And because the application also depends on Spring MVC (spring-webmvc.jar), a Spring MVC [`DispatcherServlet`][] is configured and registered for you — no `web.xml` necessary! Auto-configuration is a powerful, flexible mechanism. See the [API documentation][`@EnableAutoConfiguration`] for further details.

### {!include#build-an-executable-jar}

<a name="run"></a>
Run the application
-------------------

Now you can run the application from the jar as well, and distribute that as an executable artifact:
```
$ java -jar target/gs-securing-web-0.1.0.jar

... app starts up ...
```

Once the application starts up, point your browser to http://localhost:8080. You should be greeted by the home page:

![The application's home page](images/home.png)

When you click on the link, it should attempt to take you to the greeting page at `/hello`. But because that page is secured and you have not yet logged in, it will instead take you to the login page:

![The login page](images/login.png)

At the login page, you can sign in as the test user by entering "user" and "password" for the username and password fields, respectively. Once you submit the login form, you'll be authenticated and then taken to the greeting page:

![The secured greeting page](images/greeting.png)

If you decide to click on the "logout" link, your authentication will be revoked and you'll be taken back to the home page where you'll need to login again before seeing the greeting page.

Summary
-------
Congratulations! You have developed a simple web application that is secured with Spring Security.


[zip]: https://github.com/springframework-meta/gs-accessing-facebook/archive/master.zip
[u-war]: /understanding/war
[u-tomcat]: /understanding/tomcat
[u-application-context]: /understanding/application-context
[`SpringApplication`]: http://static.springsource.org/spring-bootstrap/docs/0.5.0.BUILD-SNAPSHOT/javadoc-api/org/springframework/bootstrap/SpringApplication.html
[`@Component`]: http://static.springsource.org/spring/docs/current/javadoc-api/org/springframework/stereotype/Component.html
[`@EnableAutoConfiguration`]: http://static.springsource.org/spring-bootstrap/docs/0.5.0.BUILD-SNAPSHOT/javadoc-api/org/springframework/bootstrap/context/annotation/SpringApplication.html
[`DispatcherServlet`]: http://static.springsource.org/spring/docs/current/javadoc-api/org/springframework/web/servlet/DispatcherServlet.html
