Feature: Usability

    Scenario: U2 - Simple Navigation with Buttons
        Given I Am On "<page1>",
        When I Click "<button>" Button
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
        And I Am Performing "<action>"
        When I Click "<button>"
        Then I Am Taken To "<page2>"

    Examples:
    | page1     | action            | button             | page2     |
    | Account   | Account Update    | btnHome            | Home      |
    | Account   | Account Update    | btnAccount         | Account   |
    | Account   | Account Update    | btnWarehouse       | Warehouse |
    | Account   | Account Update    | btnUpdateAccount   | Account   |
    | Account   | Account Update    | btnDeleteAccount   | Landing   |
    | Warehouse | New Warehouse     | btnHome            | Home      |
    | Warehouse | New Warehouse     | btnAccount         | Account   |
    | Warehouse | New Warehouse     | btnWarehouse       | Warehouse |
    | Warehouse | New Warehouse     | btnManageWarehouse | Item      |
    | Item      | New Item          | btnHome            | Home      |
    | Item      | New Item          | btnAccount         | Account   |
    | Item      | New Item          | btnWarehouse       | Warehouse |
    | Item      | New Item          | btnDeleteItem      | Item      |
    | Item      | Item Update       | btnHome            | Home      |
    | Item      | Item Update       | btnAccount         | Account   |
    | Item      | Item Update       | btnWarehouse       | Warehouse |
    | Item      | Item Update       | btnUpdateItem      | Item      |
    | Item      | Item Update       | btnDeleteItem      | Item      |
    | Item      | Item Update       | btnCancel          | Item      |
    | Signup    | Correct Sign Up   | btnAttemptSignup   | Login     |
    | Signup    | InCorrect Sign Up | btnAttemptSignup   | Signup    |
    | Login     | Correct Login     | btnAttemptLogin    | Home      |
    | Login     | InCorrect Login   | btnAttemptLogin    | Login     |
    


    Scenario: U4 - Navigation with Button to Pages that Perform Actions
        Given I Am On "<page1>"
        When I Click "<button>"
        Then I Am Taken To "<page2>"
        And I Will Be Performing "<action>"
    
    Examples:
    | page1     | button           | page2     | action         |
    | Account   | btnUpdateAccount | Account   | Account Update |
    | Warehouse | btnAddWarehouse  | Warehouse | New Warehouse  |
    | Item      | btnAddItem       | Item      | New Item       |
    | Item      | btnUpdateItem    | Item      | Item Update    |
    
    
    
    Scenario: U5 - Navigation During Action to Pages that Perform Actions
        Given I Am On "<page1>"
        And I Am Performing "<action1>"
        When I Click "<button>"
        Then I Am Taken To "<page2>"
        And I Will Be Performing "<action2>"

    Examples:
    | page1     | action         | button          | page2     | action2        |
    | Warehouse | New Warehouse  | btnAddWarehouse | Warehouse | New Warehouse  |
    | Item      | New Item       | btnAddItem      | Item      | New Item       |
    | Item      | New Item       | btnUpdateItem   | Item      | Item Update    |
    | Item      | Item Update    | btnAddItem      | Item      | New Item       |


 
