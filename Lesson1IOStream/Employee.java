import java.io.Serializable;

/**
 * 
 */

/**
 * @author bglogowski
 *
 */
public class Employee implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private double salary;
    private int hireYear;
    private int hireMonth;
    private int hireDay;
    
    public Employee(String name, double salary, int hireYear, int hireMonth, int hireDay) {
        setName(name);
        setSalary(salary);
        setHireYear(hireYear);
        setHireMonth(hireMonth);
        setHireDay(hireDay);
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }


    /**
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }


    /**
     * @return the hireYear
     */
    public int getHireYear() {
        return hireYear;
    }


    /**
     * @param hireYear the hireYear to set
     */
    public void setHireYear(int hireYear) {
        this.hireYear = hireYear;
    }


    /**
     * @return the hireMonth
     */
    public int getHireMonth() {
        return hireMonth;
    }


    /**
     * @param hireMonth the hireMonth to set
     */
    public void setHireMonth(int hireMonth) {
        this.hireMonth = hireMonth;
    }


    /**
     * @return the hireDay
     */
    public int getHireDay() {
        return hireDay;
    }


    /**
     * @param hireDay the hireDay to set
     */
    public void setHireDay(int hireDay) {
        this.hireDay = hireDay;
    }
    
    public String toString() {
        return getName() + "|" + Double.toString(getSalary()) + "|" + getHireYear() + "-"
                + getHireMonth() + "-" + getHireDay() + "\n";
    }


}
