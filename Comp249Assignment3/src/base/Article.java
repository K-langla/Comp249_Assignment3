/*------------------------------------------------------------------------------
    Assignment 2
    Part 2
    Written by: Jason Brennan 27793928
                Kelly Langlais 26919855
------------------------------------------------------------------------------*/
package base;

/**
 *
 * @author Manhands
 */
public class Article {
    
    String author,title,journal,volume,pages, DOIaddress,month;
    final static String DOI = "https://doi.org/";
    int year, number;
    
    public Article(String author,String title,String journal,String volume,String pages,String DOIaddress,String month,int year, int number ){
        
        this.author = author;
        this.title= title;
        this.journal = journal;
        this.volume = volume;
        this.pages = pages;
        this.DOIaddress= DOIaddress;
        this.month= month;
        this.year=year;
        this.number=number;
                
    }
    
    
}
