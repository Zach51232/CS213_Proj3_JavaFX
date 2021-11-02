package uintTesting;

import java.text.DecimalFormat;

/**
 * The NonResdident is a subclass of Student that cannot have financial aid
 *
 * @author Isaac Brukhman
 */
public class NonResident extends Student {

    private Profile student;
    private Date lastPaid;
    public int creditHours;
    private double tuitionFee, financialAid, totalPaid;
    private boolean calculated = false;

    /**
     * empty constructor
     */
    public NonResident() {
    }

    /**
     * constructor with premade profile
     *
     * @param student     the profile of the student
     * @param creditHours that will be taken
     */
    public NonResident(Profile student, int creditHours) {
        super(student,creditHours);
        lastPaid = new Date("00/00/00");
        this.student = student;
        this.creditHours = creditHours;
        tuitionFee = 0;
        financialAid = 0;
        totalPaid = 0;
    }

    /**
     * constructor that makes the profile and calculates the full time status
     *
     * @param name        of the student
     * @param major       of the student
     * @param creditHours that will be taken
     */
    public NonResident(String name, Major major, int creditHours) {
        super(name,major,creditHours);
        boolean fullTime = (creditHours - 12) >= 0;
        lastPaid = new Date("00/00/00");
        this.student = new Profile(name, major, fullTime);
        this.creditHours = creditHours;
        tuitionFee = 0;
        financialAid = 0;
        totalPaid = 0;
    }

    /**
     * will return a string with the variables and ends in non resident
     *
     * @return String the formatted output
     */
    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

        String tF = decimalFormat.format(tuitionFee);
        String tP = decimalFormat.format(totalPaid);

        return String.format("%s:tuition due:%s:total payment:%s:last payment date: %s:non-resident"
                , super.toString(), tF, tP, lastPaid.getDate());
    }

    /**
     * tells if the student is equal to another
     *
     * @param obj the student we want to compare
     * @return true if they are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NonResident) {
            NonResident newStudent = (NonResident) obj;
            return (newStudent.getProfileP()).equals(this.getProfileP());
        }
        return false;
    }

    /**
     * gets the string representation of the profile
     *
     * @return String the profile as a string
     */
    @Override
    public String getProfile() {
        return student.toString();
    }

    /**
     * gets the Profile of this student
     *
     * @return Profile of the student
     */
    @Override
    public Profile getProfileP() {
        return student;
    }

    /**
     * will calculate the amount of tuition this student will need to pay
     */
    @Override
    public void tuitionDue() {
        if (!calculated) {
            if (!student.getFullTime()) {
                tuitionFee = (UNIVERSITY_FEE * 0.8); // 80% of the university fee
                tuitionFee += 966 * creditHours;
            } else {
                tuitionFee = UNIVERSITY_FEE;
                tuitionFee += 29737;
                if (creditHours > 16) {
                    tuitionFee += (creditHours - 16) * 966;
                }
            }
            calculated = true;
        }
    }
    /**
     * Calls tuitionDue() but this returns the tuitionFee after
     * calculating it
     *
     * @return how much the student owes
     */
    @Override
    public double tuitionDueWithValue(){
        tuitionDue();
        return tuitionFee;
    }
    /**
     * will make a payment so long as the payment isn't greater than the tuitionfee.
     *
     * @param payment the amount to be paid
     * @return false if the payment is too large.
     */
    @Override
    public boolean pay(double payment, Date datePaid) {

        if (payment <= tuitionFee) {   // Check that the payment is less than the tuition fee
            totalPaid += payment;
            tuitionFee -= payment;
            lastPaid = datePaid;
            return true;
        }

        return false;
    }

    /**
     * returns false since non-residential students get no aid.
     *
     * @param financialAid the amount given
     * @return false if the financial aid was already given
     */
    @Override
    public boolean setFinancialAid(double financialAid) {
        return false;
    }

    /**
     * will set the tuition fee for NonResident. Used mostly by subclasses so that they can call super for toString().
     *
     * @param tuitionFee the amount given
     *
     */
    public void setTuitionFee(double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }
    /**
     * will get the payment for NonResident. Used mostly by subclasses so that they can call super for toString().
     *
     * @return totalPaid the total paid payment
     *
     */
    protected double getTotalPaid() {
        return totalPaid;
    }
    /**
     * Getter for lastPaid
     *
     * @return Date the date that the student last paid
     */
    @Override
    public Date getLastPaid() {
        return lastPaid;
    }

    /**
     * finds if the account has paid once
     *
     * @return true if the total paid is more than 0
     */
    @Override
    public boolean hadPaid() {
        return totalPaid > 0;
    }
}
