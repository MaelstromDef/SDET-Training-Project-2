package com.skillstorm.testingComponents;

import java.util.List;

public interface Page {
    // Each page should enter the information into their own form.
    public void enterBadFormInformation();
    public void enterGoodFormInformation(); 

    public boolean submitForm(); // Returns success status of the attempt to submit the form (not necessarily success of the form's submission).
}
