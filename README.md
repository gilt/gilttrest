## Welcome
You are on your way to learn about all the awesomeness that is [Apidoc](http://www.apidoc.me/doc/) and [Play](https://www.playframework.com/documentation/2.5.x/Home).  Both are core frameworks we use at Gilt to build services.

## What is GiltTrest?
GiltTrest is a starter project that aims to introduce you to core concepts in Apidoc and Play.  It is a "play"ful app that allows uers to "Pin" their favorite active sales on Gilt.

![Gilt Trest Screenshot](https://raw.githubusercontent.com/gilt/gilttrest/master/public/images/screen_shot.png)

By no means is this meant to be a thorough walkthrough of either framework, but it should give you a good basic understanding of how services are created at Gilt.

This app comes pre-bundled with a frontend built on AngularJS.  This lab does not cover those topics but feel free to reach out to Kevin Li or Kyle Dorman if you want to learn more about it.

## Development Environment Setup

The following need to be setup on your local machine to run Gilttrest locally.  You may already have some of these installed, in which case move on to the next step.

1. Install [Brew](http://brew.sh/) ("The missing package manager for OS X")

2. Install Postgres using `brew`.  A good guide can be found [here](https://launchschool.com/blog/how-to-install-postgresql-on-a-mac). This is the relational database we will be using

3. Install [IntelliJ](https://www.jetbrains.com/idea/download/).  This is the IDE we'll be using.

4. Install the IntelliJ [Scala Plugin](https://www.jetbrains.com/help/idea/2016.1/creating-and-running-your-scala-application.html?origin=old_help).  IntelliJ does not come with scala support out of the box.

5. Install `activator`.  Instructions can be found [here](https://www.playframework.com/documentation/2.5.x/Installing)

6. Register and login to [apidoc.me](www.apidoc.me).  Afterwards join the "Gilt" organization and ask a Gilt engineer to approve your request.

7. Install and configure the [apidoc-cli](https://github.com/mbryzek/apidoc-cli).  This is the `apidoc` command that lets you interact with Apidoc thorugh a terminal.

8. Sign up for access to Gilt's public api using your Gilt credentials: [api.gilt.com](https://dev.gilt.com/).  This will give us an API token to fetch active sales on Gilt.com.  Save the token for later

9. Install [Postman](https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en), a graphical curl client. (Or you can just use curl if you're confortable with it)


## Configuring the Database
Now that you have postgres installed, lets create our first user and database.  Postgres has some handy shell commands to accomplish this.

First lets create a user named "gilt-trest-user" as a Superuser: `createuser --interactive gilt-trest-user`

Next lets create the gilt-trest database: `createdb gilt-trest-development`

You should now be able to connect to your database: `psql -U gilt-trest-user -d gilt-trest-develpment`.  If this is successful, you can disconnect with `\q`

## Git Clone the Starter Project

Clone the GiltTrest repo located [here](https://github.com/gilt/gilttrest/tree/implement).  This is a standard Play framework file structure created with the `activator new` command.  Take a minute to read through [Anatomy of a Play Application](https://www.playframework.com/documentation/2.5.x/Anatomy).

Unique to Gilt is the `api.json` and `.apidoc` files.  This first is used by api-doc to describe the API and generate documentation/clients.  You can see the documented created by this api.json file [here](http://apidoc.me/gilt/gilt-trest/latest).  Take a moment to familiarize yourself with the syntax.  It is pretty self explanatory.

The `.apidoc` file is used by the apidoc cli.  This file contains the configuration settings neccessary to easily download the latest client.

Go ahead and try it now with the `apidoc update` command.  You should see 3 new files added to the repo

* `conf/api.routes` This is auto generated file mapping routes to controllers
* `GiltGiltPublicApiV1Client.scala` This is the Gilt public API auto generated client/models
* `GiltGiltTrestV1Client.scala` GiltTrest's auto generated client/models

## Compiling and Testing and Running
We can easily download all dependencies and compile the project with 1 command `activator compile`.  On your first run, it will take some time to download all dependencies.  Verify that your project builds successfully.

You can run unit tests via the `activator test` command.  Note, there will be failing tests. It'll be your job to fix them!

Finally, you can run the application with `activator run`.  Go ahead and hit the health check endpoint now at `curl localhost:9000/healthcheck/ping`.  You should see a simple "Pong" response.

## Import the Project into IntelliJ

Open the root directory with IntelliJ.  IntelliJ will notice the presence of a `build.sb` file and recommend creating an sbt project.  Click Ok.  Finally finish the import using the settings below:

![Gilt Trest Screenshot](https://raw.githubusercontent.com/gilt/gilttrest/master/public/images/intelliJ_settings.png)

Phew!  We can finally get coding!

## Evolutions!
That was all setup, now we can actually begin coding :D  First thing we need to do is create the DB schema.  Play has a built in [evolution manager](https://www.playframework.com/documentation/2.5.x/Evolutions) that we are going to leverage.  The basic concept here is we define schema evolutions that will get applied to the database one after another.  I've created the first one for you at `conf/evolutions/default/1.sql`.  This is the user schema.  We also need to create a "Pin" schema.  A pin has a many to one relationship with users.  Take a crack at implementing the `2.sql` file.  Create a "pins" table with the following schema:

Name | Type | Constraint
--- | --- | ---
id | bigserial | primary key
user_id | bigint | not null, references user(id), on delete cascade
sale_key | text | not null


How do you apply the schema change?  Just run the app and hit the healthcheck endpoint again.  I recommend you do it in a browser this time because if you made an error in you schema evolution it will alert you.  If you made an error, I like to just connect to the db through `psql` and drop the tables to start from square 1 again.

If everything went successfully you should have these three tables in your DB using the `\dt` command in `psql`:

![Gilt Trest Screenshot](https://raw.githubusercontent.com/gilt/gilttrest/master/public/images/schemas.png)

## Implement the Users Controllers

Oh snap!  Finally time to play with scala. We're gonna implement the controllers for our app.  Go ahead and navigate to `app/controllers/Users.scala` for instructions on what to implement.  You can test your implementation at any time using `activator test`.  You can also test your progress by using `activator run` and playing around in the user interface.

## Finishing off with Pins & Sales Controller

First you need to update your `gilt-api-key` in the `conf/application.conf` with the API key provided to you when you signed up for the public API.

Next implement the Pins and Sales controllers located at `app/controllers/Pins.scala` and `app/controllers/Users.scala` respectivly.  Both these controllers  will require you to make both database and API calls.  Remember you can always check your progress by running `activator test`.

## Sweet App Bro!!!
Go show off how awesome your new Apidoc/Play/Sclal app is! 👏👏👏