package javaDoc.constructors;

public class constructorTypes {
    private String title,author;
    private int publicationYear;

    // Default constructor
    public constructorTypes(){
        this.author="unknown";
        this.title="unknown";
        this.publicationYear=0;
    }

    // ==============Parameterized constructor==========
    public  constructorTypes(String title,String author,int publicationYear){
        this.author=title;
        this.title=author;
        this.publicationYear=publicationYear;
    }

    // ==============Getters and Setters=================

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author=author;
    }
    public int getPublicationYear(){
        return publicationYear;
    }
    public void setPublicationYear(int publicationYear){
        this.publicationYear=publicationYear;
    }

    //=================Method to display book details=================
    public void displayBookDetails(){
        System.out.println("Title:  "+title+", Author : "+author+", publicationYear : "+publicationYear);
    }

    //===================Main Method to test constructor=============
    public static void main(String [] args){
        constructorTypes ct1 = new constructorTypes();

        ct1.displayBookDetails();

        constructorTypes ct2 = new constructorTypes("1984","George orwell",1949);
        ct2.displayBookDetails();
    }
}
