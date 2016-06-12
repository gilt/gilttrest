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

5. Install sbt: `brew install sbt`.  SBT is scala's preferred build tool.  Although it stands for "simple build tool", it is rather complex and (arguably) one of the pain points when working in the scala ecosystem.

6. Register and login to [apidoc.me](www.apidoc.me).  Afterwards join the "Gilt" organization and ask a Gilt engineer to approve your request.

7. Install and configure the [apidoc-cli](https://github.com/mbryzek/apidoc-cli).  This is the `apidoc` command that lets you interact with Apidoc thorugh a terminal.

8. Sign up for access to Gilt's public api using your Gilt credentials: [api.gilt.com](https://dev.gilt.com/).  This will give us an API token to fetch active sales on Gilt.com


## Configuring the Database
Now that you have postgres installed, lets create our first user and database.  Postgres has some handy shell commands to accomplish this.

First lets create a user named "gilt-trest-user" as a Superuser: `createuser --interactive gilt-trest-user`

Next lets create the gilt-trest database: `createdb gilt-trest-development`

You should now be able to connect to your database: `psql -U gilt-trest-user -d gilt-trest-develpment`

## Git Clone the Starter Project


