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
    
    private String author,title,journal,volume,pages, DOIaddress,month;
    final static String DOI = "https://doi.org/", DELIM ="[ and ]";
    private int year, number;
    private String[] authors;
    
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
        authors =author.split(DELIM);
                
    }
    
    public String IEEEtoString(){
        
        return IEEEformat(getAuthors()) +", "+getTitle()+", "+getJournal()+", vol. "+getVolume()+", "+getNumber()+", "+getPages()+", "+
                getMonth()+", "+getYear()+".";
        
    }
    
    public String ACMtoString(){
        
        return ACMformat(getAuthors())+getYear()+". "+getTitle()+". "+getJournal+". "+getVolume()+", "+
                getNumber()+". ("+getYear()+"), "+getPages()+". "+DOI+getDOIaddress()+".";
        
    }
    
    public String NJtoString(){
        
        return NJformat(getAuthors())+". "+getTitle()+". "+getJournal+". "+ getVolume()+", "+getPages+"("+getYear()+").";
    }
    
    public String IEEEformat(String[] authors){
        
        StringBuilder format = new StringBuilder();
        
        for(int i=0; i<authors.length-1;i++){
            
            format.append(authors[i]);
            
            if (i<authors.length-1)
                format.append(", ");
        }
        return format.toString();
    }
    
    public String ACMformat(String[] authors){
        
        return authors[0]+" et al. ";
    }
    
    public String NJformat(String[] authors){
        
        StringBuilder format = new StringBuilder();
        
        for(int i=0; i<authors.length-1;i++){
            
            format.append(authors[i]);
            
            if (i<authors.length-1)
                format.append(" & ");
        }
        return format.toString();
        
    }
    
    public String getAuthor(){
        return author;
    }
    public String getTitle(){
        return title;
    }    
    public String getJournal(){
        return journal;
    }    
    public String getVolume(){
        return volume;
    }
    public String getPages(){
        return pages;
    }
    public String getDOIaddress(){
        return DOIaddress;
    }
    public String getMonth(){
        return month;
    }
    public int getYear(){
        return year;
    }
    public int getNumber(){
        return number;
    }   
    
    public String[] getAuthors(){
        return authors;
    }
    
}
