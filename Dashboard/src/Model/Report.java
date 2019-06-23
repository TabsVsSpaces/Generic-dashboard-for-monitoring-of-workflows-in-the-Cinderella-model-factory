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

    private List<ViewElement> elementList;

    //Constructor
    public Report () {
        this.reportId = 0;
        this.reportName = " ";
        this.maxElements = 4;
        this.elementList = new ArrayList<>();
    }
    
    public Report(int reportId, String reportName) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.maxElements = 4;
        this.elementList = new ArrayList<>();
    }

    //setter
    public void setReportName(String name) {
        reportName = name;
    }

    public void setReportId(int id) {
        reportId = id;
    }

    //getter
    public String getReportName() {
        return reportName;
    }

    public int getReportId() {
        return reportId;
    }

    public int getListElementSize() {
        return this.elementList.size();
    }

    public ViewElement getViewEelementbyIndex(int index) {
        return this.elementList.get(index);
    }

    //Methods
    public boolean addViewElement(ViewElement newElement) {
        if (elementList.size() < maxElements) {
            newElement.setDiagramId(createViewElementID());
            elementList.add(newElement);
            return true;
        } else {
            LogHandler.add("Maximale Anzahl (" + Integer.toString(maxElements) + ") an Einzeigeelemente erreicht.");
            return false;
        }
    }

    public boolean removeViewElement(ViewElement removeElement) {
        if (elementList.size() > 0) {
            elementList.remove(removeElement);
            return true;
        } else {
            LogHandler.add("Keine Einzeigeelemente vorhanden.");
            return false;
        }
    }

    @Override
    public String toString() {
        return reportId + "/"
                + reportName + "/"
                + elementList;
    }

    private int createViewElementID() {
        int id = 1;
        boolean search = true;

        while (search) {
            for (int i = 0; i < getListElementSize(); i++) {
                if (getViewEelementbyIndex(i).getDiagramId() == id) {
                    id++;
                    break;
                };
            }
            search = false;
        }
        return id;
    }
}
