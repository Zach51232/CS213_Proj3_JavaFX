package com.example.project3fx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import uintTesting.*;
import Exceptions.*;

/**
 * The MainController stores all the inputs for the TuitionManager application. Actions are coded for the inputs to make sure the functionality
 *
 * @author Isaac Brukhman, Zachary Goldman
 */
public class MainController {
    public static int INVALID = -1;
    public static int VALID = 1;

    @FXML
    private Roster roster;

    //The first tab

    @FXML
    private TextField name;
    @FXML
    private RadioButton cs;
    @FXML
    private RadioButton ee;
    @FXML
    private RadioButton me;
    @FXML
    private RadioButton it;
    @FXML
    private RadioButton ba;
    @FXML
    private RadioButton resident;
    @FXML
    private RadioButton nonResident;
    @FXML
    private RadioButton triState;
    @FXML
    private RadioButton ny;
    @FXML
    private RadioButton ct;
    @FXML
    private RadioButton international;
    @FXML
    private CheckBox abroad;
    @FXML
    private CheckBox resetCheck;
    @FXML
    private TextField creditHours;
    @FXML
    private TextField tuition;
    @FXML
    private Button addStudent;
    @FXML
    private Button removeStudent;
    @FXML
    private Button tuitionDue;
    @FXML
    private TextArea output;

    //The second tab
    @FXML
    private TextField name2;
    @FXML
    private RadioButton cs1;
    @FXML
    private RadioButton ee1;
    @FXML
    private RadioButton me1;
    @FXML
    private RadioButton it1;
    @FXML
    private RadioButton ba1;
    @FXML
    private CheckBox abroadStatus;
    @FXML
    private Button setStudyAbroad;
    @FXML
    private TextField paymentAmount;
    @FXML
    private DatePicker paymentDate;
    @FXML
    private Button setFinancialAid;
    @FXML
    private TextField financialAid;

    //The third tab
    @FXML
    private Button calculate;
    @FXML
    private Button print;
    @FXML
    private Button printByTuition;
    @FXML
    private Button printByName;

    /**
     * onResidentClick() sets the relevant things disabled
     */
    @FXML
    protected void onResidentClick() {
        triState.setDisable(true);
        ny.setDisable(true);
        ct.setDisable(true);
        international.setDisable(true);
        abroad.setDisable(true);
        triState.setSelected(false);
        ny.setSelected(false);
        ct.setSelected(false);
        international.setSelected(false);
        abroad.setSelected(false);
    }

    /**
     * onNonResidentClick() sets the relevant things disabled
     */
    @FXML
    protected void onNonResidentClick() {
        triState.setDisable(false);
        ny.setDisable(false);
        ct.setDisable(false);
        international.setDisable(false);
    }

    /**
     * onTriStateClick() sets the relevant things disabled
     */
    @FXML
    protected void onTriStateClick() {
        international.setSelected(false);
        abroad.setSelected(false);
        abroad.setDisable(true);
    }

    /**
     * onInternationalClick() sets the relevant things disabled
     */
    @FXML
    protected void onInternationalClick() {
        triState.setSelected(false);
        ny.setSelected(false);
        ct.setSelected(false);
        abroad.setDisable(false);
    }

    /**
     * onTuitionDueClick() displays the student currently
     * with the name and major selected tuition, after calculating it
     * Note: The student needs to have been added into the roster beforehand
     */
    @FXML
    protected void onTuitionDueClick() {
        if (null == roster) {
            roster = new Roster();
        }
        if (checkNameMajor(0) == -1) {
            return;
        }
        tuition.setText("");
        Major major = findMajor();
        Student newStudent = new Student(name.getText(), major, 0);
        Student rightStudent = roster.findStudent(newStudent);
        if (null == rightStudent) {
            tuition.appendText("N/A");
            return;
        }
        tuition.appendText(rightStudent.tuitionDueWithValue() + "\n");
    }

    /**
     * Checks if the roster is empty or not
     *
     * @return INVALID (-1) if it's empty, 0 (VALID) otherwise
     */
    protected int checkRoster() {
        if (null == roster) {
            output.appendText("Student roster is empty.\n");
            return INVALID;
        }
        return VALID;
    }

    /**
     * Calculates tuition for the roster
     */
    @FXML
    protected void onCalculateClick() {
        if (checkRoster() == INVALID) {
            return;
        }
        roster.calculateTuition();
        output.appendText("Calculation completed.\n");
    }

    /**
     * Prints roster in order students were added
     */
    @FXML
    protected void onPrintClick() {
        if (checkRoster() == INVALID) {
            return;
        }
        output.appendText(roster.print());
    }

    /**
     * Prints roster alphabetically
     */
    @FXML
    protected void onPrintByNameClick() {
        if (checkRoster() == INVALID) {
            return;
        }
        output.appendText(roster.printByName());
    }

    /**
     * Prints roster by tuition payments
     */
    @FXML
    protected void onPrintByTuitionClick() {
        if (checkRoster() == INVALID) {
            return;
        }
        output.appendText(roster.printByTuition());
    }
    /**
     * checkNameMajor(int tab) takes in the tab we're dealing with and tells if name or major is missing
     * @param tab takes either tab = 0 (1st tab), or tab =1 (2nd tab)
     * @return either INVALID if the name or major is missing, or VALID if not
     */
    protected int checkNameMajor(int tab) {
        if (tab == 0) {
            resetMajors1();
            if (name.getText().equals("")) {
                output.appendText("Set the name!\n");
                return INVALID;
            }
        } else if (tab == 1) {
            resetMajors();
            if (name2.getText().equals("")) {
                output.appendText("Set the name!\n");
                return INVALID;
            }
        }
        if (null == findMajor()) {
            output.appendText("Pick a major!\n");
            return INVALID;
        }
        return VALID;
    }

    /**
     * Sets an international student's study abroad status
     */
    @FXML
    protected void onSetStudyAbroadClick() {
        if (checkNameMajor(1) == INVALID) {
            return;
        }
        Major major = findMajor();
        boolean studyAbroad = abroadStatus.isSelected();
        Profile intProfile = new Profile(name2.getText(), major);
        International newStudent = new International(intProfile, 0, false);
        boolean updated = roster.setStudyAbroad(newStudent, studyAbroad);
        if (updated) {
            output.appendText("Tuition updated.\n");
        } else {
            output.appendText("Couldn't find international student.\n");
        }
        resetMajors1();
    }

    /**
     * Adds financial aid to a resident student
     */
    @FXML
    protected void onFinancialAidClick() {
        if (checkNameMajor(1) == INVALID) {
            return;
        }
        Major major = findMajor();

        if (financialAid.getText().equals("")) {
            output.appendText("Put in a finAid amount.\n");
            return;
        }
        try {
            Double finAid = Double.parseDouble(financialAid.getText());
            if (finAid <= 0 || finAid > 10000) {
                output.appendText("Invalid amount.\n");
                return;
            }
            Student newStudent = new Student(name2.getText(), major, 0);
            //Check if student is in roster
            Student s = roster.findStudent(newStudent);
            if (null == s) {
                output.appendText("Student not in roster.\n");
                return;
            }
            //Check if student is Resident
            boolean isResident = (s instanceof Resident);
            if (!isResident) {
                output.appendText("Not a resident student.\n");
                return;
            }
            //Check if student is full-time
            boolean fullTime = s.getCredit() >= 12;
            if (!fullTime) {
                output.appendText("Part-time student doesn't qualify for the award.\n");
                return;
            }
            boolean addedAid = s.setFinancialAid(finAid);
            if (addedAid) {
                output.appendText("Tuition updated.\n");
            } else {
                output.appendText("Awarded once already.\n");
            }
            resetMajors1();
        } catch (NumberFormatException e) {
            output.appendText("Put in a number for financialAid.\n");
        }
    }

    /**
     * Processes a payment towards tuition due for a student
     */
    @FXML
    protected void onPayTuitionClick() {
        if (null == paymentDate.getValue()) {
            output.appendText("Missing date in command line.\n");
            return;
        }
        //Check it's a valid payment amount
        try {
            double payment = Double.parseDouble(paymentAmount.getText());
            if (payment <= 0) {
                output.appendText("Invalid amount.\n");
                return;
            }
            //Check date is valid
            String day = paymentDate.getValue().toString().substring(8);
            String month = paymentDate.getValue().toString().substring(5, 7);
            String year = paymentDate.getValue().toString().substring(0, 4);
            Date realDate = new Date(month + "/" + day + "/" + year);
            if (!realDate.isValid()) {
                output.appendText("Payment date invalid.\n");
                return;
            }
            //Send the payment
            //Check that the student is in the roster
            resetMajors();
            Major major = findMajor();
            if (null == major) {
                output.appendText("Pick a major!\n");
                return;
            }
            Student newStudent = new Student(name2.getText(), major, 0);
            Student s = roster.findStudent(newStudent);
            if (null == s) {
                output.appendText("Student not in roster.\n");
                return;
            }
            boolean paidDone = roster.pay(s, payment, realDate);
            //Check if payment <= tuition due
            if (paidDone) {
                output.appendText("Payment applied.\n");
            } else {
                output.appendText("Amount is greater than amount due.\n");
            }
            resetMajors1();
        } catch (NumberFormatException e) {
            output.appendText("Put in a number for paymentAmount!\n");
        }
    }

    /**
     * Removes a student when the add removes is clicked if they're in there.
     */
    @FXML
    protected void onRemoveStudentClick() {
        Major major = findMajor();
        Student newStudent = new Student(name.getText(), major, 0);
        boolean removed = roster.remove(newStudent);
        //The roster is not null here when it should be
        if (removed) {
            output.appendText("Student removed from the roster.\n");
        } else {
            output.appendText("Student is not in the roster.\n");
        }
        if (resetCheck.isSelected()) {
            resetButtonsAfterAdding();
        }
        if (roster.getRoster() == null) {
            roster = null;
        }
    }

    /**
     * Adds a student when the add button is clicked. Makes sure that the appropriate errors are displayed if any before creating the student.
     */
    @FXML
    protected void onAddClick() {
        Major major = findMajor(); //Set the major
        int credits;
        boolean added = false;
        if (checkNameMajor(0) == INVALID) {
            return;
        }
        if (!(resident.isSelected() || nonResident.isSelected())) {
            output.appendText("Select resident or nonResident!\n");
            return;
        }
        //Make a roster
        if (null == roster) {
            roster = new Roster();
        }
        try {
            if (creditHours.getText().equals("")) {
                throw new EmptyCreditHours();
            }
            credits = Integer.parseInt(creditHours.getText());
            if (credits < 0) {
                throw new NegativeCredits();
            }
            if (credits < 3) {
                throw new MinimumCredits();
            }
            if (credits > 24) {
                throw new MaximumCredits();
            }
            if (resident.isSelected() || nonResident.isSelected()) {  // checks if the fields are empty
                if (resident.isSelected()) {
                    Resident s = new Resident(name.getText(), major, credits);
                    added = roster.add(s);
                }
                //Non-resident
                else {
                    if (international.isSelected()) {
                        if (credits < 12) {
                            output.appendText("International students must enroll at least 12 credits.\n");
                            return;
                        }
                        //They're full time
                        boolean fullTime = true;
                        boolean studyAbroad = abroad.isSelected();
                        Profile inter = new Profile(name.getText(), major, fullTime);
                        International s = new International(inter, credits, studyAbroad);
                        added = roster.add(s);
                    }
                    else if (triState.isSelected() || ny.isSelected() || ct.isSelected()) {
                        String state = "other";
                        if (ny.isSelected()) {
                            state = "NY";
                        } else if (ct.isSelected()) {
                            state = "CT";
                        }
                        TriState s = new TriState(name.getText(), major, credits, state);
                        added = roster.add(s);
                    } else {
                        NonResident s = new NonResident(name.getText(), major, credits);
                        added = roster.add(s);
                    }
                }
                if (!added) {
                    output.appendText("Student already in the roster.\n");
                } else {
                    output.appendText("Student added.\n");
                }
                if (resetCheck.isSelected()) {
                    resetButtonsAfterAdding();
                }
            }
        } catch (NumberFormatException e) {
            output.appendText("Invalid credit hours.\n");   // if it's not a number
        } catch (EmptyCreditHours e) {
            output.appendText("Credit hours missing.\n");
        } catch (MinimumCredits e) {
            output.appendText("Minimum credit hours is 3.\n");
        } catch (MaximumCredits e) {
            output.appendText("Credit hours exceed the maximum 24.\n");
        } catch (NegativeCredits e) {
            output.appendText("Credit hours cannot be negative.\n");
        }
    }

    /**
     * findMajor() picks the major selected and returns it
     *
     * @return major, or null if didn't pick a major
     */
    private Major findMajor() {
        Major major = null;

        if (cs.isSelected() || cs1.isSelected()) {
            major = Major.CS;
        }
        if (ee.isSelected() || ee1.isSelected()) {
            major = Major.EE;
        }
        if (me.isSelected() || me1.isSelected()) {
            major = Major.ME;
        }
        if (it.isSelected() || it1.isSelected()) {
            major = Major.IT;
        }
        if (ba.isSelected() || ba1.isSelected()) {
            major = Major.BA;
        }
        return major;
    }

    /**
     * resetButtonsAfterAdding() resets everything on the 1st tab (tab 0)
     */
    private void resetButtonsAfterAdding() {
        name.setText("");
        resident.setSelected(false);
        nonResident.setSelected(false);
        resident.setSelected(false);
        triState.setSelected(false);
        ny.setSelected(false);
        ct.setSelected(false);
        international.setSelected(false);
        abroad.setSelected(false);
        triState.setDisable(true);
        ny.setDisable(true);
        ct.setDisable(true);
        creditHours.setText("");
    }

    /**
     * resetMajors() resets the majors on the 1st tab (tab 0)
     */
    private void resetMajors() {
        cs.setSelected(false);
        ee.setSelected(false);
        me.setSelected(false);
        it.setSelected(false);
        ba.setSelected(false);
    }

    /**
     * resetMajors1() resets the majors on the 2nd tab (tab 1)
     */
    private void resetMajors1() {
        cs1.setSelected(false);
        ee1.setSelected(false);
        me1.setSelected(false);
        it1.setSelected(false);
        ba1.setSelected(false);
    }
}