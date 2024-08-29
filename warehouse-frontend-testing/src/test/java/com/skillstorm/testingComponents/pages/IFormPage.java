package com.skillstorm.testingComponents.pages;

public interface IFormPage extends IPage {
    // Each page should enter the information into their own form.
    public void clearFormInformation();
    public void enterWrongFormInformation();
    public void enterRightFormInformation(); 

    public boolean submitForm(); // Returns success status of the attempt to submit the form (not necessarily success of the form's submission).

    public boolean verifySubmissionFailure();
    public boolean verifySubmissionSuccess();
}
