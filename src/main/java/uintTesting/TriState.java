package uintTesting;

/**
 * The TriState is a subclass of NonResident that can have a discount based on the state the student live in
 *
 * @author Isaac Brukhman
 */
public class TriState extends NonResident {

    private Profile student;
    private Date lastPaid;
    public int creditHours;
    private double tuitionFee, financialAid, totalPaid;
    private String state;
    private boolean calculated = false;

    /**
     * empty constructor
     */
    public TriState() {
    }

    /**
     * constructor calculates the full time status amd makes a profile
     *
     * @param name        of the student
     * @param major       of the student
     * @param creditHours that will be taken
     * @param state       that the student lives in
     */
    public TriState(String name, Major major, int creditHours, String state) {
        super(name,major,creditHours);
        boolean fullTime = (creditHours - 12) >= 0;

        lastPaid = new Date("00/00/00");
        this.student = new Profile(name, major, fullTime);
        this.creditHours = creditHours;
        this.state = state;
        tuitionFee = 0;
        financialAid = 0;
        totalPaid = 0;
    }

    /**
     * will return a string with the variables and ends in non resident(tri-state):state
     *
     * @return String the formatted output
     */
    @Override
    public String toString() {
        String stateVal = state;

        if(state.equals("other"))
            stateVal = "";

        return String.format("%s(tri-state):%s"
                , super.toString(), stateVal);
    }

    /**
     * tells if the student is equal to another
     *
     * @param obj the student we want to compare
     * @return true if they are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TriState) {
            TriState newStudent = (TriState) obj;
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
                if (state.equals("NY")) {
                    tuitionFee -= 4000;
                } else if (state.equals("CT")) {
                    tuitionFee -= 5000;
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
    @Override
    public boolean hadPaid() {
        return totalPaid > 0;
    }
}
