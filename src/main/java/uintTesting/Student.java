package uintTesting;

/**
 * The Student class holds the information of a student and declares some methods that will be used in subclasses
 *
 * @author Isaac Brukhman
 */
public class Student {
    //Should keep track of the amount they owe and have paid as well as the last date -> i.e. the last T pay command's date

    public final int UNIVERSITY_FEE = 3268;    // University Fee for all students

    private Profile student;
    private Date lastPaid;
    public int creditHours;
    private double tuitionFee, financialAid, totalPaid;

    /**
     * Empty constructor
     */
    public Student() {

    }

    /**
     * Constructor creates a profile after finding the fullTime status, sets all the private variables, and calculates tuitionDue
     *
     * @param name        of the student
     * @param major       the student is studying
     * @param creditHours that the student is taking
     */
    public Student(String name, Major major, int creditHours) {
        boolean fullTime = (creditHours - 12) >= 0;
        lastPaid = new Date("00/00/00");
        this.creditHours = creditHours;
        this.student = new Profile(name, major, fullTime);
        totalPaid = 0;
    }

    /**
     * Constructor passes the inputs to the private variables and calculates the tuition due
     *
     * @param student     profile with name, major and full time status
     * @param creditHours that the student is taking
     */
    public Student(Profile student, int creditHours) {
        this.creditHours = creditHours;
        this.student = student;
        totalPaid = 0;
    }

    /**
     * toString will return the student Profile toString
     *
     * @return a string of the name and major
     */
    @Override
    public String toString() {
        return student.toString() + ":" + creditHours +" credit hours";
    }

    /**
     * tells if the student is equal to another
     *
     * @param obj the student we want to compare
     * @return true if they are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            Student newStudent = (Student) obj;
            System.out.println("profile: " + newStudent.getProfile());
            return (newStudent.getProfileP()).equals(this.getProfileP());
        }
        return false;
    }

    /**
     * gets the string representation of the profile
     *
     * @return String the profile as a string
     */
    public String getProfile() {
        return student.toString();
    }

    /**
     * gets the Profile of this student
     *
     * @return Profile of the student
     */
    public Profile getProfileP() {
        return student;
    }

    /**
     * does nothing here since the subclasses determine the tuition amount.
     */
    public void tuitionDue() {
    }
    /**
     * does nothing here since the subclasses determine the tuition amount.
     * @return the tuitionFee
     */
    public double tuitionDueWithValue() {
        tuitionDue();
        return tuitionFee;
    }

    /**
     * returns false since the subclasses determine if a payment is possible
     *
     * @param payment the amount to be paid
     * @param datePaid the date that a payment was attempted
     * @return false
     */
    public boolean pay(double payment, Date datePaid) {
        return false;
    }

    /**
     * returns false since the subclasses determine the financial
     *
     * @param financialAid the amount given
     * @return false
     */
    public boolean setFinancialAid(double financialAid) {
        return false;
    }

    /**
     * Getter for creditHours
     *
     * @return int the credit hours
     */
    public int getCredit() {
        return creditHours;
    }

    /**
     * Getter for lastPaid
     *
     * @return Date the date that the student last paid
     */
    public Date getLastPaid() {
        return lastPaid;
    }

    /**
     * finds if the account has paid once
     *
     * @return true if the total paid is more than 0
     */
    public boolean hadPaid() {
        return totalPaid > 0;
    }

    } //end Student
