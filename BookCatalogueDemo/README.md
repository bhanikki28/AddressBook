# Book Catalogue Service Application

Run the application using command :
	mvn clean spring-boot:run



# API Controller to create/remove/search book

	1. To get the list of books (METHOD : GET )
	   http://localhost:9090/book/list
		
	2. To remove/delete the book from Catalogue ( Method : DELETE )
		 http://localhost:9090/book/delete?title=sample
		 
	3. To search for a book in Catalogue ( Method : POST )
		 http://localhost:9090/book/search ( filter can be on isbn,title,author )
		 Sample Payload: 
		 {
    			"title" :"Hibernate in Action"
		 }
		 
    4. To create a book  ( Method : POST )
		 http://localhost:9090/book/create
		 Sample Payload:
		 {
		  	"title": "sample",
		    "isbn": "1234567891234",
		  	"authorList": [{
		  		"name": "Bharathy",
		  		"emailId": "nikki.bharathy@gmail.com"
		  	}]
		}
# Kafka 
	To create topic for sending the events : ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 100 --topic books .

# Tools

	Java 1.8
	Kafka
	Maven
	Jacoco Plugin ( JUnit Code Coverage : /target/site/jacaco/index.html )
	JUnit
	Eclipse/IntelliJ 
