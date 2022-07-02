# Microservices Application (Survey Submission & Analysis)

## Components:
1. Enter Data (Web application)
	- Can be accessed through http://localhost:3000 when the service is running.
2. Show Results (Web application)
	- Can be accessed through http://localhost:3001 when the service is running.
3. Authentication Service
	- Server port : 8000
4. MySQL Database Service
	- Server port: 8001
5. Mongo Database Service
	- Server port: 8002
6. Analytics Service
	- Server port: 8003

# Front-end Services:
## Enter data Service:
A React application that provides the following functionalities:
1. Login / Register: Authentication is required for the user to be able to perform/utilize other functionalities, communicates with the authentication service to validate user submissions.
2. Take survey: Allow the authorized users to submit to surveys available.
	- In order to submit to a survey the user must fill out the whole survey then try to submit, filling half/ or missing surveys will not be pushed to the back-end services. 
3. Create new Surveys: Allow the authorized users to create new surveys.
	- In order to create a new Survey the user must navigate to the page responsible for it, and start filling the data in JSON format an editor with simple auto-complete and a small guide is set up on that page.

An example of the survey JSON that could be sent to the back-end with no issues:

```JSON
{
	"survey": {
		"surveyName":"Web Development Related Survey",
		"surveyDescription":"Survey for web-dev enthusiasts"
	},
	"questions": [
		{
			"prompt":"Preferred front-end framework?",
			"answers": [
				"React",
				"Angular",
				"Vue",
				"Other"
			]
		},
		{
			"prompt":"Preferred back-end language?",
			"answers": [
				"Java",
				"PHP",
				"JavaScript",
				"Python",
				"Ruby",
				"C#"
			]
		}
	]
}
```

The Dockerfile:

```Dockerfile
FROM node:alpine
WORKDIR /app
COPY ./build ./build
RUN npm install -g serve --silent
EXPOSE 3000
ENTRYPOINT ["serve","-s","build","-l","3000"]
```

1. I used (node:alpine) instead of (node:latest) since its much more lightweight and has higher performance.
	- node:latest's size is ~900MB
	- node:alpine's size is ~100MB
2. Moving the build file instead of the source code since the build version is much more optimized.
	- Source code requires node_modules to run which usually has a size of ~150MB 
	- Running the build requires no dependencies since most dependencies are converted into HTML/JS folders, so it only needs a way of serving the build (serve).
3. Such minor changes decreased the final size of the docker image from ~1.2GB to ~100/200MB.
4. Exposing port 3000 and Serving the build folder with "serve" on port 3000.

## Show data Service:
A React application that provides the following functionalities:
1. Login / Register: Authentication is required for the user to be able to perform/utilize other functionalities, communicates with the authentication service to validate user submissions.
2. View survey analysis: Provides the users with analyzed surveys, For each survey if it has any submissions, then the structure of the analyzed survey will be like this:
	- Number of submissions will be presented to the user.
	- For each Question:
		- The details of the Question and its answers.
		- A Pie chart displaying the choices of Users.
		- The most chosen and least chosen answers.

The Dockerfile: 

```Dockerfile
FROM node:alpine
WORKDIR /app
COPY ./build ./build
RUN npm install -g serve --silent
EXPOSE 3001
ENTRYPOINT ["serve","-s","build","-l","3001"]
```

## Notes about both Web Applications:
 Since both applications are connected to the same authentication service, the user can register once and login on both applications with the same credentials.

It's a bad practice to hardcode other APIs URI's, for this i made a .env file which i stored other URI's in.

A .env file is a file to store application configuration away from the code which is based on the Twelve-Factor App Methodology for (SaaS) applications.

```env
REACT_APP_AUTH_URL=http://localhost:8000
REACT_APP_SURVEYAPP_URL=http://localhost:8001
REACT_APP_MONGO_URL=http://localhost:8002
```

This way we can access those URIs through the process.env.

```JavaScript
try {
	fetch(`${process.env.REACT_APP_AUTH_URL}/auth/login`, options)
	.then(...);
} catch( ) {}
```

I Tried to validate data before sending it to the back-end services from all sources as much as possible to reduce the invalid requests & prevent as much errors as possible.

---
# Back-end Services
Using maven, i made a jar file for each service, which will be added to the docker image at build time.

## Authentication Service:
The service is a simple Spring boot REST API that follows the MVC architecture which has 2 endpoints:

- [POST]/auth/login : Used to validate user login credentials.
- [POST]/auth/register : Used to register new users.

```JSON
// Json Body example
{
	"username":"mohammad",
	"password":"test"
}
```

Both web applications (Enter data & Show data) will communicate with this service to validate user submissions/ store new user data (Login & Register).

The service communicates with a MySQL database called authentication, The Schema is also simple:

```SQL
CREATE DATABASE authentication;

USE authentication;

CREATE TABLE users(
	userId INT AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255) NOT NULL
);
```

The Dockerfile:

```Dockerfile
FROM openjdk:8  
WORKDIR /app  
ADD ./target/authentication_service.jar application.jar  
EXPOSE 8000  
ENTRYPOINT ["java","-jar","application.jar"]
```

## MySQL Service
The service is a Spring Boot REST API, This service is a bit more compolicated unlike other services.

Endpoints:
- Survey related:
	- [POST] /surveys/new: create new survey.
	- [GET] /surveys/: fetch all surveys.
	- [GET] /surveys/survey?surveyId={}: fetch specific survey with its details.
- Question related:
	- [POST] /questions/new: for creating new questions.
- Answers related:
	- [POST] /answers/new: for creating new answers.
- Submissions related:
	- [POST] /submissions/new: for creating a new submission to a survey.
	- [GET] /submissions/survey?surveyId={}: fetch submissions of a specific survey.

Some endpoints perform other logic under the hood:

1. Sending a request to [/submissions/new] will notify the analytics service to update the analysis data in the mongo service, more details on this will be found in the Analysis Service Section.
2. Sending a request to [/surveys/new] will forward the request to the mongo service to create a new survey in the mongodb.

For the communication from the server side i made 2 Utilities (GlobalStorage, RequestUtility):

Same with the web applications its a bad practice to hard code API URLs, so i made a GlobalStorge  configuration class that contains 2 beans, the Urls to other services.

```Java
@Configuration  
public class GlobalStorage {  
    /*  
    * The GlobalStorage is a configuration class which is used to retrieve values    
	* that are shared between components, such as other service urls.    
	* Values will be regarded as Beans, and whenever a value is needed it will be    
	* accessed through the ApplicationContext provided by Spring.
	**/
	
    @Bean  
    public String mongoApiUrl() {  
        return "http://localhost:8002";  
    }  
  
    @Bean  
    public String analyticsApiUrl() {  
        return "http://localhost:8003";  
    }  
}
```

The request utility contains 2 functions one to forward a survey to mongo service and the other to notify the analytics service.

1. forwardSurveyToMongo():
	- Sends a POST request to the MongoDB service to create and store a new survey
2. notifyAnalyticsService():
	- Sends a GET request to the Analytics Service as a notification to create new analysis.  

```Java
@Component  
public class RequestUtility {  
    ApplicationContext applicationContext;  
    RestTemplate restTemplate;  
  
    @Autowired  
    public RequestUtility(ApplicationContext applicationContext) {  
        this.applicationContext = applicationContext;  
        this.restTemplate = new RestTemplate();  
    }  
  
    public void forwardSurveyToMongo(Survey survey) {  
    	...
    }  
  
    public void notifyAnalyticsService(Integer surveyId) {  
        ...  
    }  
}
```

This service also communicates with a MySQL database under the hood.

```SQL
CREATE DATABASE surveyapp;

USE surveyapp;

CREATE TABLE surveys (
	surveyId INT AUTO_INCREMENT PRIMARY KEY,
	surveyDescription VARCHAR(255) NOT NULL,
	surveyName VARCHAR(255) NOT NULL
);

CREATE TABLE questions (
	questionId INT AUTO_INCREMENT PRIMARY KEY,
	surveyId INT NOT NULL,
	prompt VARCHAR(255) NOT NULL,
	FOREIGN KEY (surveyId) REFERENCES surveys(surveyId)
);

CREATE TABLE answers (
	answerId INT AUTO_INCREMENT PRIMARY KEY,
	questionId INT NOT NULL,
	value VARCHAR(255) NOT NULL,
	FOREIGN KEY (questionId) REFERENCES questions(questionId)
);

CREATE TABLE submissions (
	submissionId INT AUTO_INCREMENT PRIMARY KEY,
	surveyId INT NOT NULL,
	questionId INT NOT NULL,
	answer VARCHAR(255) NOT NULL,
	FOREIGN KEY (surveyId) REFERENCES surveys(surveyId),
	FOREIGN KEY (questionId) REFERENCES questions(questionId)
);
```


In my API, i implemented 2 types of models, the core models which each model corresponds to a table in the database, and composed models:	

- FullQuestion: Which will contain a Question Object and an ArrayList of Answer objects.
- FullSurvey: Which will contain a Survey object and an ArrayList of FullQuestion objects.

This is to make it easier to send data from the server.

```Java
public class FullSurvey {  
    private Survey survey;  
    private ArrayList<FullQuestion> questions;  
  
    public Survey getSurvey() {  
        return survey;  
    }  
  
    public void setSurvey(Survey survey) {  
        this.survey = survey;  
    }  
  
    public ArrayList<FullQuestion> getQuestions() {  
        return questions;  
    }  
      
    public void setQuestions(ArrayList<FullQuestion> questions) {  
        this.questions = questions;  
    }  
  
    @Override  
    public String toString() {  
        return "FullSurvey{" +  
                "survey=" + survey +  
                ", questions=" + questions +  
                '}';  
    }  
}

public class FullQuestion {  
    private Question question;  
    private ArrayList<Answer> answers;  
  
    public Question getQuestion() {  
        return question;  
    }  
  
    public void setQuestion(Question question) {  
        this.question = question;  
    }  
  
    public ArrayList<Answer> getAnswers() {  
        return answers;  
    }  
  
    public void setAnswers(ArrayList<Answer> answers) {  
        this.answers = answers;  
    }  
  
    @Override  
    public String toString() {  
        return "FullQuestion{" +  
                "question=" + question +  
                ", answers=" + answers +  
                '}';  
    }  
}
```

The Dockerfile:

```Dockerfile
FROM openjdk:8  
WORKDIR ./app  
ADD ./target/analytics_service.jar application.jar  
EXPOSE 8001  
ENTRYPOINT ["java","-jar","application.jar"]
```

## Analytics Service
The service is a Spring boot REST API, this service doesn't communicate with a database and its purpose is to act as a middleware between the MySQL Service & the MongoDB Service.

I had many Ideas when it comes to the analytics service such as CRON jobs, or Refresh requests from the Show Data Web App but i settled for the "Notification" from the mysql service.

It has only one endpoint:

- [GET] /updates/new?surveyId={} : "Notifications" from the MySQL Service end up here, the analysis begins then it is sent to the Mongo Service.

I Also did the same thing A GlobalStorage utility:

```Java
@Configuration  
public class GlobalStorage {  
    /*  
     * The GlobalStorage is a configuration class which is used to retrieve values     * that are shared between components, such as other service urls.     * Values will be regarded as Beans, and whenever a value is needed it will be     * accessed through the ApplicationContext provided by Spring.     * */  
    @Bean  
    public String mongoApiUrl() {  
        return "http://localhost:8002";  
    }  
  
    @Bean  
    public String mysqlApiUrl() {  
        return "http://localhost:8001";  
    }  
}
```

And a RequestUtility:

```Java
@Component  
public class RequestUtility {  
    ApplicationContext applicationContext;  
    RestTemplate restTemplate = new RestTemplate();  
  
    @Autowired  
    public RequestUtility(ApplicationContext applicationContext) {  
        this.applicationContext = applicationContext;  
    }  
  
    public FullSurvey fetchFullSurvey(Integer surveyId) {  
		...
    }  
  
    public List<Submission> fetchSurveySubmissions(Integer surveyId) {  
		...
    }  
  
    public void sendUpdatesToMongoService(AnalyzedSurvey analyzedSurvey) {  
		...
    }  
}
```

The requests are:
1. fetchFullSurvey(): sends a get request to the MySQL service and retrieves the survey the user has submitted to.
2. fetchSurveySubmissions(): sends a get request to the MySQL Service and retrieves 
3. sendUpdatesToMongoService(): sends newly analyzed surveys to mongo service.

There is a set of new Models related to analysis, with the composed/core models.

1. Analyzed Answer
2. Analyzed Question
3. Analyzed Survey

The Analyzed Survey contains the original survey and an ArrayList of Analyzed Questions
Each analyzed question has these properties:
- question: the question itself
- mostChosen: most chosen answer.
- leastChosen: least chosen answer.
- answers: ArrayList of analyzed answers.

Each Analyzed Answer contains 2 properties:
- title: the answer it self
- value: number of times people chose this answer

The Dockerfile:

```Dockerfile
FROM openjdk:8  
WORKDIR ./app  
ADD ./target/analytics_service.jar application.jar  
EXPOSE 8003  
ENTRYPOINT ["java","-jar","application.jar"]
```

## MongoDB Service
The service is a Spring boot REST API, this service communicates with a mongodb cluster.

Endpoints:
- Survey related:
	- [POST] /surveys/new: create new survey.
	- [GET] /surveys/: fetch all surveys.
- Analysis related:
	- [POST] /analysis/new: create new analysis
	- [GET] /analysis/survey?surveyId={}: fetch analysis of a specific survey.

This service communicates with a MongoDB, the documents are stored based on 2 models

1. AnalyzedSurvey
2. Survey

The Dockerfile:

```Dockerfile
FROM openjdk:8  
WORKDIR ./app  
ADD ./target/mongodb_service.jar application.jar  
EXPOSE 8002  
ENTRYPOINT ["java","-jar","application.jar"]
```

# Docker & Docker Compose
The docker-compose.yaml file can be found in the root directory alongside the project files.

In my implementations servers require communication with local databases, for simplicity i configured the docker-compose file to run the back-end services on the host's network.

- --net=host -> When using docker run command
- network_mode: "host" -> in docker-compose 

The front-end services will have the ports published.

```yaml
version: "3.3"
services:
    submission:
        build: ./submissions
        ports:
            - "3000:3000"
    analysis:
        build: ./analysis
        ports:
            - "3001:3001"
    authentication:
        build: ./authentication
        network_mode: "host"
    mysql_service:
        build: ./mysql_service
        network_mode: "host"
    analytics_service:
        build: ./analytics_service
        network_mode: "host"
    mongo_service:
        build: ./mongodb_service
        network_mode: "host"
```

Running commands:

```bash
# Docker compose
# Building the images with compose
docker-compose up --build
# To run all the services
docker-compose up
# To stop all the services
docker-compose down

# If you want to run a specific service using docker
# navigate to the Dockerfile location for the service you want to run
docker build -t <image-name> .
docker run -it <image-name>

# if it's a back-end application use this instead
docker run -it --net=host <image-name>
```
