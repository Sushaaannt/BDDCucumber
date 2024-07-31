## Types of constructor
``Note : we will refer constructorTypes class here as Book``

``1.Default Constructor :``
The default constructor sets default values for the attributes. This is useful when you don’t have specific information at the time of creating the Book object but still need to create an instance of it.


``2.Parameterized Constructor : ``
The parameterized constructor allows setting the attributes to specific values at the time of object creation. This is useful when you have all the necessary information and want to initialize the object with these values.

# Usage in Real-Time Scenario : 
### Default Constructor :
When a new book is added to the system, but some details are not available initially, you can still create a Book object and later update its details using setter methods.

### Parameterized Constructor:
When you have all the details of a book (title, author, and publication year), you can use the parameterized constructor to create a Book object with these details right away.

``Practical Application use in constructorTypesUsage class : ``

Imagine a library system where books are added to the system through a user interface (UI). The user might have the option to either add a book with all details or just add a placeholder book and fill in the details later.

In this library system, constructors help in creating Book objects in a flexible and efficient manner, allowing the system to handle both immediate and delayed data entry.

## Difference in method and constructor

### Purpose:
``Constructor :``Initializes a new object of a class. It sets up the initial state of the object.
``Method :`` Defines a behavior or functionality that an object of a class can perform.

### Name:
``Constructor :`` Must have the same name as the class.
``Method :`` Can have any name, typically descriptive of the action it performs.

### Return Type:
``Constructor :`` Does not have a return type, not even ``void``.
``Method :``  Must have a return type, which can be ``void`` if it does not return a value.

### Invocation:
``Constructor :`` Automatically called when an object is instantiated using the ``new`` keyword.
``Method :`` Explicitly called on an object using the dot (.) operator.
### Default Availability:
``Constructor :`` If no constructor is explicitly defined, Java provides a default no-argument constructor.
``Method :`` No default method is provided by Java; all methods must be explicitly defined.

### Inheritance:
``Constructor :``Not inherited by subclasses, but a subclass can call a superclass constructor using ⁠ super() ⁠.
``Method :`` Can be inherited and overridden by subclasses.

### Overloading:
``Constructor :`` Can be overloaded (multiple constructors with different parameters).
``Method :`` Can be overloaded (multiple methods with the same name but different parameters).

### Summary:
``Constructor :`` Used to initialize a new object.
``Method :``Used to perform actions or operations on an object after it has been created.

Understanding these differences is crucial for designing and implementing classes effectively in Java.