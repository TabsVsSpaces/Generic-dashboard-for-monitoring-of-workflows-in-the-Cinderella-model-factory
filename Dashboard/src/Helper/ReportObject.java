/*
TODO
    -https://www.mkyong.com/java/jaxb-hello-world-example/
    -implement Inheritance: see structure below

    //better structure
   
        ReportObject{
            String reportName;
            int updateRate;
            {
                DisplayElement{
                    int id; //alternative String name;
                    String sqlStatement;
                    ...
                }
                DisplayElement{
                    int id;
                    ...
                }
                ...
            }
        }

 */
package Helper;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class ReportObject {

    //int id;
    String reportName;
    int updateRate;
    String sqlStatement;
    String measureXAxis;
    String measureYAxis;
    String columnXAxis;
    String columnYAxis;

    /*
    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }
     */
    public String getReportName() {
        return reportName;
    }

    @XmlElement
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public int getUpdateRate() {
        return updateRate;
    }

    @XmlElement
    public void setUpdateRate(int updateRate) {
        this.updateRate = updateRate;
    }

    public String getSqlStatement() {
        return sqlStatement;
    }

    @XmlElement
    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    public String getMeasureXAxis() {
        return measureXAxis;
    }

    @XmlElement
    public void setMeasureXAxis(String measureXAxis) {
        this.measureXAxis = measureXAxis;
    }

    public String getMeasureYAxis() {
        return measureYAxis;
    }

    @XmlElement
    public void setMeasureYAxis(String measureYAxis) {
        this.measureYAxis = measureYAxis;
    }

    public String getColumnXAxis(){
        return columnXAxis;
    }
    
    @XmlElement
    public void setColumnXAxis(String columnXAxis){
        this.columnXAxis = columnXAxis;
    }
    
    public String getColumnYAxis(){
        return columnYAxis;
    }
    
    @XmlElement
    public void setColumnYAxis(String columnYAxis){
        this.columnYAxis = columnYAxis;
    }
}
