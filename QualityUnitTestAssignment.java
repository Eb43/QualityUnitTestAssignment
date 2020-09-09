/**
 * Test assignment for Quality Unit
 * 
 */
import java.util.*;
import java.io.*;
import java.nio.*;
import java.text.*; 

public class QualityUnitTestAssignment {
    
        public void RunToGetResult() {
          /*
                PRINT NAME OF THE FILE
            in the string variable fileName
            as full path to the file
            like fileName = "c:/temporary_downloads/QualityUnitTestAssignment/testfile.txt"
          */
            String fileName = "c:/temporary_downloads/QualityUnitTestAssignment/testfile.txt"; //<<< PRINT HERE NAME OF THE FILE
         
            /*
                PRINT NAME OF THE FILE
            in the string variable fileName
          */ 
            
          ArrayList scannedLines = new ArrayList();
          //System.out.println("-------------"); //For more eye pleasuring output
       
         try {
             //create object for the file with queries
             File myObj = new File(fileName);  
             Scanner myReader = new Scanner(myObj);
             //read each line in file and add it to ArrayList
             int i = 0;
             while (myReader.hasNextLine()) {
                 String line = myReader.nextLine();
                 scannedLines.add(line);
                 //Check if scanner reached D line and start analysys of queries
                 if (line.substring(0,1).equals("D")){
                    Analysis(scannedLines, i);
                    }
                 i=i+1;
             }
             
             myReader.close();
             }
             
         catch (FileNotFoundException e) {
             System.out.println("Error with file. Probably no file given");
             e.printStackTrace();
            }
                 
          //System.out.println("-------------"); //Closing eye pleasuring output
    }
    
    
    //class for checking if C date is inside D date
    public boolean DateCompare (String D_date, String C_date) throws Exception {  
        Boolean boolean_date_check  = false;
        
        if (D_date.indexOf("-")!= -1){
            String [] D_DayMonthYearArray = D_date.split("-");
            String D_date1 = D_DayMonthYearArray[0];
            Date D_start_date=new SimpleDateFormat("dd.MM.yyyy").parse(D_date1); 
            
            String  D_date2 = D_DayMonthYearArray[1];
            Date D_end_date=new SimpleDateFormat("dd.MM.yyyy").parse(D_date2); 
            
            String C_sDate = C_date;
            Date dateC=new SimpleDateFormat("dd.MM.yyyy").parse(C_sDate);  
 
            if ((dateC.compareTo(D_start_date) == 1) || (dateC.compareTo(D_start_date) == 0)) {
               if ((dateC.compareTo(D_end_date) == -1) || (dateC.compareTo(D_end_date) == 0)) {
                boolean_date_check  = true;
               }
            }
                
        }// closing for if (D_date.indexOf("-")!= -1)

        if (D_date.indexOf("-") == -1){
            String [] D_DayMonthYearArray1 = D_date.split("\\.");
            String [] C_DayMonthYearArray = C_date.split("\\.");
            if ((D_DayMonthYearArray1[2].equals(C_DayMonthYearArray[2])) && (D_DayMonthYearArray1[1].equals(C_DayMonthYearArray[1])) && (D_DayMonthYearArray1[0].equals(C_DayMonthYearArray[0]))){
                boolean_date_check = true;
            }

        }//closing for if (D_date.indexOf("-") == -1)
        return boolean_date_check;
    }  
    
    
    //class for C and D lines analysis
    public void Analysis(ArrayList scannedLines, int i) {
        
            /*
          * 
          *Structure of query line   
          *C service_id[.variation_id] question_type_id[.category_id.[sub-category_id]] P/N date time
          *D service_id[.variation_id] question_type_id[.category_id.[sub-category_id]] P/N date_from[-date_to]
          *
          *Exmple of query line
          *C 1.1 8.15.1 P 15.10.2012 83
          *D 1.1 8 P 01.01.2012-01.12.2012
          */
         
        int avgTime = 0; // variable for average waiting time
       
        //Breaking D query line into parameters 
        String[] lineD = (scannedLines.get(i)+"").split(" ");

        //Performing analysys of queries
         
            //Creating variables for query parameters for readnig convinience 
            String D_service_id = lineD[1]; //service. service 9.1 represent service 9 of variation 1
            String D_variation_id = ""; //service variation
            String D_question_type_id = lineD[2]; //question type 7.14.4 represent question type 7 category 14 and sub-category 4
            String D_category_id = ""; //question category
            String D_sub_category_id = ""; //question sub-category
            String D_responseType = lineD[3]; //P/N - response type ‘P’ (first answer) or ‘N’ (next answer)
            String D_date = lineD[4]; //response date format is DD.MM.RRRR, for example, 27.11.2012 (27.november 2012)  
        
         //Analyzing C queries in accordance to D queries
         int counter = 0; 
         int count = 0;
         for (int k=0;k<i;k=k+1){
              
            if ((scannedLines.get(k)+"").substring(0,1).equals("C")){  
             String[] lineC = (scannedLines.get(k)+"").split(" ");
             
             String C_service_id = lineC[1];
             String C_variation_id = "";
             String C_question_type_id = lineC[2]; 
             String C_category_id = "";
             String C_sub_category_id = ""; 
             String C_responseType = lineC[3]; 
             String C_date = lineC[4]; 
             int C_time = Integer.parseInt(lineC[5]);//time in minutes represent waiting time 
             
             
             // variables to cut analysis when parameters of C and D don't match
             Boolean boolean_service_id = false;
             Boolean boolean_question_type_id = false;
             Boolean boolean_responseType = false;
             Boolean boolean_date = false;
             // Searching in C and D queries for similar service_id

             if (!D_service_id.equals("*")){
                 if ((D_service_id.length()>1) && (D_service_id.contains("."))){
                     //System.out.println(lineD[1].substring(0,lineD[1].indexOf(".")));
                     String tempD = D_service_id.substring(0,D_service_id.indexOf("."));
                     if (!C_service_id.equals("*")){
                         if ((C_service_id.length()>1) && (C_service_id.contains("."))){
                             String tempC = C_service_id.substring(0,C_service_id.indexOf("."));
                             if (tempD.equals(tempC)){
                                 boolean_service_id = true;
                              }
                         } 
                         if ((C_service_id.length()==1) || (!C_service_id.contains("."))){
                            String tempC = C_service_id.substring(0,C_service_id.length());
                             if (tempD.equals(tempC)){
                                 boolean_service_id = true;
                              }
                         }
                         
                     }//closing for if (!C_service_id.equals("*"))
                    
                    }//closing for if (D_service_id.length()>1)
                    
                 if ((D_service_id.length()==1) || (!D_service_id.contains("."))){
                     //System.out.println(lineD[1].substring(0,lineD[1].indexOf(".")));
                     String tempD = D_service_id.substring(0,D_service_id.length());
                     if (!C_service_id.equals("*")){
                         if ((C_service_id.length()>1)&& (C_service_id.contains("."))){
                             String tempC = C_service_id.substring(0,C_service_id.indexOf("."));
                             if (tempD.equals(tempC)){
                                 boolean_service_id = true;
                              }
                         } 
                         if ((C_service_id.length()==1) || (!C_service_id.contains("."))){                            String tempC = C_service_id.substring(0,C_service_id.length());
                             if (tempD.equals(tempC)){
                                 boolean_service_id = true;
                              }
                         }
                         
                     }//closing for if (!C_service_id.equals("*"))
                    
                    }//closing for if (D_service_id.length()==1)
                  
               }// closing for if (!D_service_id.equals("*"))  
              
             if (D_service_id.equals("*")){
                    boolean_service_id = true;
               }
             
             // Searching in C and D queries for similar question_type_id  
             if (boolean_service_id == true){
                 
               if (!D_question_type_id.equals("*")){
                 if ((D_question_type_id.length()>1) && (D_question_type_id.contains("."))){
                     //System.out.println(lineD[1].substring(0,lineD[1].indexOf(".")));
                     String tempD = D_question_type_id.substring(0,D_question_type_id.indexOf("."));
                     if (!C_question_type_id.equals("*")){
                         if (C_question_type_id.length()>1){
                             String tempC = C_question_type_id.substring(0,C_question_type_id.indexOf("."));
                             if (tempD.equals(tempC)){
                                 boolean_question_type_id = true;
                              }
                         } 
                         if (C_question_type_id.length()==1){
                            String tempC = C_question_type_id.substring(0,C_question_type_id.length());
                             if (tempD.equals(tempC)){
                                 boolean_question_type_id = true;
                              }
                         }
                         
                     }//closing for if (!C_question_type_id.equals("*"))
                    
                 }//closing for  if (D_question_type_id.length()>1)
                    
                 if ((D_question_type_id.length()==1) || (!D_question_type_id.contains("."))){
                     String tempD = D_question_type_id.substring(0,D_question_type_id.length());
                     if (!C_question_type_id.equals("*")){
                         if (C_question_type_id.length()>1){
                             String tempC = C_question_type_id.substring(0,C_question_type_id.indexOf("."));
                             if (tempD.equals(tempC)){
                                 boolean_question_type_id = true;
                    
                              }
                         } 
                         if (C_question_type_id.length()==1){
                            String tempC = C_question_type_id.substring(0,C_question_type_id.length());
                             if (tempD.equals(tempC)){
                                 boolean_question_type_id = true;
                              }
                         }
                         
                     }//if (!C_question_type_id.equals("*"))
                    
                    }//closing for if (D_question_type_id.length()==1)
                  
               }// closing for if (!D_question_type_id.equals("*"))
              
               if (D_question_type_id.equals("*")){
                    boolean_question_type_id = true;
               }
                             
                 
             }//closing for if (boolean_service_id == true)
             
             
             // Searching in C and D queries for similar responseType 
             if ((boolean_service_id.equals(true)) && (boolean_question_type_id.equals(true))) {
                 if (D_responseType.equals(C_responseType)){
                                 boolean_responseType = true;
                 }
                 
                }// closing for if ((boolean_service_id == true) && (boolean_question_type_id == true))
             
             //Search in C and D queries by date
             if ((boolean_service_id.equals(true)) && (boolean_question_type_id.equals(true)) && (boolean_responseType.equals(true))){                   //boolean_date = false;
                 try {
                     boolean_date = DateCompare (D_date, C_date);
                    }
                    catch (Exception e){
                        System.out.println("Error with comparing dates");
                        e.printStackTrace();
                    }
                 
                }//closing for if ((boolean_service_id == true) && (boolean_question_type_id == true) && (boolean_responseType == true))
             
             if ((boolean_service_id.equals(true)) && (boolean_question_type_id.equals(true)) && (boolean_responseType.equals(true)) && (boolean_date.equals(true))){
                 avgTime=avgTime+C_time;
                 count = count +1;
                }
               
            }//closing for if ((scannedLines.get(k)+"").substring(0,1).equals("C"))
            counter = count;
          }// closing the for loop which scans C lines and compares them with D lines
         
          //Cheking if average time exists and calculating it
          if (counter != 0){System.out.println(avgTime/counter);}
          else {System.out.println("-");}

    }
    

}
