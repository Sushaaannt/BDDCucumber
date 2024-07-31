package javaDoc.constructors;

public class constructorTypesUsage {
    public static void main(String[] args) {
        // Adding a book with all details known
        constructorTypes book1 = new constructorTypes("To Kill a Mockingbird", "Harper Lee", 1960);
        book1.displayBookDetails();

        // Adding a book with details to be updated later
        constructorTypes book2 = new constructorTypes();
        book2.setTitle("The Great Gatsby");
        book2.setAuthor("F. Scott Fitzgerald");
        book2.setPublicationYear(1925);
        book2.displayBookDetails();
    }

}
