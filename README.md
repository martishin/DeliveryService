CLI tool used to model delivery service operations, built using:
* Kotlin + coroutines
* Vert.x - toolkit for building reactive applications
* Koin - dependency injection 
* JUnit 5, mockk, assertj - testing
* Jackson - JSON processing
* Docker - containerization

This project utilizes Hexagonal Architecture (Ports and Adapters) and has 80+% test coverage.

To run the project:  
`./gradlew run`

To build the image:  
`make build`
