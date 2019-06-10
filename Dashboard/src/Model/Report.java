/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Helper.LogHandler;
import java.util.ArrayList;
import java.util.List;


public class Report {
    private final int maxElements;
    private String reportName;
    private int reportId;
    private List<ViewElement> viewElement = new ArrayList<>();

    //Constructor
    public Report() {
        this.maxElements = 4;
    }

    //setter
    public void setReportName(String name){
        reportName= name;
    }

    public void setReportId(int id){
        reportId = id;
    }

    //getter
    public String getReportName(){
        return reportName;
    }

    public int getReportId(int id){
        return reportId;
    }
    
    //Methods
    public boolean addViewElement(ViewElement newElement){
        if (viewElement.size() < maxElements) {
            viewElement.add(newElement);
            return true;
        } else {
            LogHandler.add("Maximale Anzahl ("+ Integer.toString(maxElements)+ ") an Einzeigeelemente erreicht.");
            return false;
        }
    }
    
    public boolean removeViewElement(ViewElement removeElement){
        if (viewElement.size() > 0) {
            viewElement.remove(removeElement);
            return true;
        } else {
            LogHandler.add("Keine Einzeigeelemente vorhanden.");
            return false;
        }
        
    }
}
