# 📝 Technical test 
## 💻 Moussaoui Mohammed

### 🌎 API description
➡️  **/user POST**: Manages the creation of a new user<br>
 __return :__ <br>
   😀 200 ok -> user created successfully<br>
   😐 400 bad request -> user is not valid<br>
   😐 409 conflict -> user already exists<br>

➡️  **/user/{username} GET**: Manages the search of an existing user<br>
 __return :__ <br>
  😀 200 ok -> user found and returned<br>
  😐 404 not found -> user does not exist<br>


### 🔴 Maven commands
#### lunch the app
    mvn spring-boot:run

#### lunch only the tests
    mvn test

#### clean the project 
    mvn clean

#### build the project for server deploy
    mvn clean package
