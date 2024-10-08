/**
 * By: Aaron Huggins
 * 
 * Description:
 *      A page that contains a modifiable object.
 */

package com.skillstorm.testingComponents.pages;

public interface IObjectPage extends IFormPage {
    // Each page should only have one item
    public void modifyObjectRight();
    public void modifyObjectWrong();

    public void deleteObject(); // Can delete the object to modify

    public boolean verifyListExistence();

    public boolean verifyObjectExistence();
    public boolean verifyObjectUpdated();

    void performAction(String action);
    boolean isUserPerformingAction(String action);
}
