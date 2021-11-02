package uintTesting;

/**
 * The International is a subclass of NonResident that can have a student study abroad
 *
 * @author Isaac Brukhman
 */
public class International extends NonResident {

    private Profile student;
    private Date lastPaid;
    public int creditHours;
    private double tuitionFee, financialAid, totalPaid;
    private boolean studyAbroad;
    private boolean calculated = false;

    /**
     * empty constructor
     */
    public International() {
    }

    /**
     * constructor with premade profile
     *
     * @param student     profile of the student
     * @param creditHours that will be taken
     * @param studyAbroad study abroad status
     */
    public International(Profile student, int creditHours, boolean studyAbroad) {
        super(student,creditHours);
        lastPaid = new Date("00/00/00");
        this.student = student;
        this.creditHours = creditHours;
        this.studyAbroad = studyAbroad;
        tuitionFee = 0;
        financialAid = 0;
        totalPaid = 0;
    }

    /**
     * will return a string with the variables and ends in :international:(abroad status)
     *
     * @return String the formatted output
     */
    @Override
    public String toString() {
        String s = String.format("%s:international"
                , super.toString());
        if (studyAbroad) {
            s += ":study abroad";
        }
        return s;
    }

    /**
     * tells if the student is equal to another
     *
     * @param obj the student we want to compare
     * @return true if they are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof International) {
            International newStudent = (International) obj;
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
            if (studyAbroad) {
                if (!student.getFullTime()) {
                    tuitionFee = (UNIVERSITY_FEE * 0.8); // 80% of the university fee
                } else {
                    tuitionFee = UNIVERSITY_FEE;
                    tuitionFee += 2650;
                }
            } else {
                if (!student.getFullTime()) {
                    tuitionFee = (UNIVERSITY_FEE * 0.8); // 80% of the university fee
                    tuitionFee += 966 * creditHours;
                } else {
                    tuitionFee = UNIVERSITY_FEE;
                    tuitionFee += 29737;
                    tuitionFee += 2650;
                    if (creditHours > 16) {
                        tuitionFee += (creditHours - 16) * 966;
                    }
                }
            }
            calculated = true;
            super.setTuitionFee(tuitionFee);
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
        boolean ret = super.pay(payment, datePaid);
        lastPaid = super.getLastPaid();
        totalPaid = super.getTotalPaid();
        return ret;
    }

    /**
     * sets the finanical aid of the student.
     *
     * @param financialAid the amount given
     * @return false if the financial aid was already given
     */
    @Override
    public boolean setFinancialAid(double financialAid) {
        return super.setFinancialAid(financialAid);
    }

    /**
     * sets the study abroad status of the student. If changed, the tuition is re evaluated.
     *
     * @param abroadState the boolean value that the status will be set to
     * @return true if the state was changed
     */
    public boolean setStudyAbroad(boolean abroadState) {
        if (studyAbroad == abroadState) {
            return false;
        }
        if (studyAbroad == false) {
            if (creditHours > 12) {
                creditHours = 12;
            }
            studyAbroad = true;
            totalPaid = 0;
            lastPaid = new Date("00/00/00");
        } else {
            studyAbroad = false;
        }
        calculated = false;
        tuitionDue();
        return true;
    }

    /**
     * gets the tuition fee that was calculated
     *
     * @return double the tuition fee
     */
    public double getTuitionFee() {
        return tuitionFee;
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
