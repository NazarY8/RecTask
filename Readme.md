### Short description

**Simple scala app which is created web-socket using http4s and cats and relying on the divisor (integer parameter within our route) works with streams using core+io fs2 and fs2-kafka.**

Within application, we have 3 possible cases 🚀️ 🚀️ 🚀️ :

1. http://localhost:8080/ws?divisor=3 (provide divisor)
2. http://localhost:8080/ws?divisor (Didn't provide a divisor,
   then we will take default divisor using pure-config and read default value from yaml file)
3. http://localhost:8080/ws?divisor=12 (provide a divisor greater than rows in .csv file, as a result we will see message)

### Application run

We have two different run options:

1. Run app using Docker
2. Run app using simple sbt

###### For Docker Run:

1. Download project
2. Create .jar file using sbt assembly plugin next name

   ```
   ICEOTask-assembly-0.2.jar
   ```
3. From project folder create Docker image `docker build -t my-application .`
4. Run docker container `docker run -p 8080:8080 my-application`
5. Сheck with Curl the cases I gave above.

   As a result you should see:

###### For SBT Run:

1. Just run sbt run

   As a result you should see:

### Tests

I'm also created several simple scala test, for run it use `sbt test` command
