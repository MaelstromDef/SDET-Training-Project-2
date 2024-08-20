package com.skillstorm.testingComponents;

import java.util.List;

public interface Page {
    public void enterFormInformation(List<String> information); // Each page should enter the information into their own form.
    public boolean submitForm(); // Returns success status of the attempt to submit the form (not necessarily success of the form's submission).
}
