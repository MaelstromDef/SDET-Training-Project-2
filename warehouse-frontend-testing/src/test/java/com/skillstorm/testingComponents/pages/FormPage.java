package com.skillstorm.testingComponents.pages;

public interface FormPage extends Page {
    // Each page should enter the information into their own form.
    public void enterWrongFormInformation();
    public void enterRightFormInformation(); 

    public boolean submitForm(); // Returns success status of the attempt to submit the form (not necessarily success of the form's submission).
}
