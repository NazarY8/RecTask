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
   P.S. sorry about this responces, but docker container didn't understand \n , so that is the reason why the answers look a little strange, but the      normal answers can be seen below in the following pictures.
   
   ![docker_app](https://user-images.githubusercontent.com/73239084/232798122-8bb32865-a59b-4a26-b043-bd457083f65b.jpg)
   ![docker_divisor_case](https://user-images.githubusercontent.com/73239084/232798347-aaa46e1f-c6e0-4de5-8790-203d5d5ea73c.jpg)
   ![default_docker_case](https://user-images.githubusercontent.com/73239084/232798429-2c177021-ff99-4885-8fa5-a2f696a99187.jpg)
   ![Docker_BadRequest_case](https://user-images.githubusercontent.com/73239084/232798488-36b7a90e-52fb-4d0c-82de-b7eb49871e50.jpg)

   
###### For SBT Run:

1. Just use `sbt run` commannd and open localhost in browser

   As a result you should see:

   ![chrome_divisor_case](https://user-images.githubusercontent.com/73239084/232800242-42698ff1-6555-40fb-8465-e33d49af7906.jpg)
   ![chrome_default_case](https://user-images.githubusercontent.com/73239084/232800538-a270cdb4-e908-4889-8c4e-e1a76da36ab4.jpg)
   ![Chrome_badrequest_case](https://user-images.githubusercontent.com/73239084/232800613-8b5fdb7a-00cf-4ad6-a573-458ce82b7b71.jpg)

### Tests

I'm also created several simple scala test, for run it use `sbt test` command
