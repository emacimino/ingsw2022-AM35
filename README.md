# Ingsw2022-AM35
![alt text](src/main/resource/EriantysMenu.jpeg)

Implementation of the Game [Eriantys](https://www.craniocreations.it/prodotto/eriantys/)
Rules of the Game can be found [here](https://www.craniocreations.it/wp-content/uploads/2021/11/Eriantys_ITA_bassa.pdf)

# Documents

### UML
These two diagrams describes how the project was initially thought
and the last implementation of the project, modified to overcome some critical issues
during the development

- [Early UML](Deliveries/Model_Uml.png)
- [Latest UML](//to be added)

### Coverage 

- [Here](//to be added) can be found all the tests with their respective coverage

### Javadoc

- [Here](//to be added) can be found all the documentation about most of the project,
includes classes and methods used

### Plugins and libraries
| Plugin/Library| Description                                        |
|----------------|----------------------------------------------------|
| __Maven__      | software project management and comprehension tool |
| __JavaFx__     | Graphics library used to create user interfaces    |
| __JUnit__      | Unit Testing Framework                           |


### Implemented functionalities

| Functionality     |                       State                        |
|:------------------|:--------------------------------------------------:|
| Basic rules       | 🟢 |
| Complete rules    | 🟢 |
| Socket            | 🟢 |
| GUI               | 🟢 |
| CLI               | 🟢 |
| Multiple games    | 🟢 |
| 4 Player basicMatch    | 🟢 |
| 12 Character Card            | 🟢 |

## Eryantis: How to start

### Compiling

There will be only one jar either for server, CLI or GUI

- [Here](//to be added) can be found the pre-compiled jar

Or if you want to create your own jar executable you have to get on 
the path of your project and use the command:
```
mvn clean package
```
This jar will be found in the ```target/``` folder with the name```AM35-Eriantys.jar``` 

### Execution 

To launch the executable digit the command:
```
java -jar AM35-Eriantys.jar
```
You can launch either server or the user interfaces by digit the command:
- ```server```
- ```-cli```
- ```-gui```

### Students:

[Camilla Andiloro](camilla.andiloro@mail.polimi.it)   
[Emanuele Cimino](emanuele.cimino@mail.polimi.it)   
[Marco Crisafulli](marco.crisafulli@mail.polimi.it)  
