Feature: Usability

    Scenario: U2 - Simple Navigation with Buttons
        Given I Am On “<page1>”,
        When I Click “<button>” Button
        Then I Am Taken To "<page2>"
    
    Examples:
    | page1     | button             | page2     |
    | Landing   | btnSignup          | Signup    |
    | Landing   | btnLogin           | Login     |
    | Landing   | btnLanding         | Landing   | 
    | Signup    | btnSignup          | Signup    |
    | Signup    | btnLogin           | Login     |
    | Signup    | btnLanding         | Landing   |
    | Login     | btnSignup          | Signup    |
    | Login     | btnLogin           | Login     |
    | Login     | btnLanding         | Landing   |
    | Home      | btnHome            | Home      |
    | Home      | btnAccount         | Account   |
    | Home      | btnWarehouse       | Warehouse |
    | Account   | btnHome            | Home      |
    | Account   | btnAccount         | Account   |
    | Account   | btnWarehouse       | Warehouse |
    | Account   | btnDeleteAccount   | Landing   |
    | Warehouse | btnHome            | Home      |
    | Warehouse | btnAccount         | Account   |
    | Warehouse | btnWarehouse       | Warehouse |
    | Warehouse | btnManageWarehouse | Item      |
    | Item      | btnHome            | Home      |
    | Item      | btnAccount         | Account   |
    | Item      | btnWarehouse       | Warehouse |
    | Item      | btnDeleteItem      | Item      |



    Scenario: U3 - Navigation with Buttons During Action
        Given I Am On "<page1>"
        And I am "<performingAction>"
        When I Click “<button>”
        Then I Am Taken To “<page2>”

    Examples:
    | page1     | performingAction       | button             | page2     |
    | Account   | Updating Account       | btnHome            | Home      |
    | Account   | Updating Account       | btnAccount         | Account   |
    | Account   | Updating Account       | btnWarehouse       | Warehouse |
    | Account   | Updating Account       | btnUpdateAccount   | Account   |
    | Account   | Updating Account       | btnDeleteAccount   | Landing   |
    | Warehouse | Adding Warehouse       | btnHome            | Home      |
    | Warehouse | Adding Warehouse       | btnAccount         | Account   |
    | Warehouse | Adding Warehouse       | btnWarehouse       | Warehouse |
    | Warehouse | Adding Warehouse       | btnManageWarehouse | Item      |
    | Item      | Adding Item            | btnHome            | Home      |
    | Item      | Adding Item            | btnAccount         | Account   |
    | Item      | Adding Item            | btnWarehouse       | Warehouse |
    | Item      | Adding Item            | btnDeleteItem      | Item      |
    | Item      | Updating Item          | btnHome            | Home      |
    | Item      | Updating Item          | btnAccount         | Account   |
    | Item      | Updating Item          | btnWarehouse       | Warehouse |
    | Item      | Updating Item          | btnUpdateItem      | Item      |
    | Item      | Updating Item          | btnDeleteItem      | Item      |
    | Item      | Updating Item          | btnCancel          | Item      |
    | Signup    | Correctly Signing Up   | btnAttemptSignup   | Login     |
    | Signup    | Incorrectly Signing Up | btnAttemptSignup   | Signup    |
    | Login     | Correctly Logging In   | btnAttemptLogin    | Home      |
    | Login     | Incorrectly Logging In | btnAttemptLogin    | Login     |
    


    Scenario: U4 - Navigation with Button to Pages that Perform Actions
        Given I Am On <page1>
        When I Click <button>
        Then I Am Taken To <page2>
        And I Am <performingAction>
    
    Examples:
    | page1     | button           | page2     | performingAction |
    | Account   | btnUpdateAccount | Account   | Updating Account |
    | Warehouse | btnAddWarehouse  | Warehouse | Adding Warehouse |
    | Item      | btnAddItem       | Item      | Adding Item      |
    | Item      | btnUpdateItem    | Item      | Updating Item    |
    
    
    
    Scenario: U5 - Navigation During Action to Pages that Perform Actions
        Given I am on <page1>
        And I am <performingAction1>
        When I click <button>
        Then I am taken to <page2>
        And I am <performingAction2>'

    Examples:
    | page1     | performingAction | button          | page2     | performingAction2 |
    | Warehouse | Adding Warehouse | btnAddWarehouse | Warehouse | Adding Warehouse  |
    | Item      | Adding Item      | btnAddItem      | Item      | Adding Item       |
    | Item      | Adding Item      | btnUpdateItem   | Item      | Updating Item     |
    | Item      | Updating Item    | btnAddItem      | Item      | Adding Item       |


 
