@usability
Feature: Usability

    Scenario: U2 - Simple Navigation with Buttons
        Given I Am On "<page1>"
        When I Click "<button>"
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
    | Home      | btnWarehouse       | Warehouses |
    | Account   | btnHome            | Home      |
    | Account   | btnAccount         | Account   |
    | Account   | btnWarehouse       | Warehouses |
    | Account   | btnUpdateAccount   | Account   |
    | Warehouses | btnHome            | Home      |
    | Warehouses | btnAccount         | Account   |
    | Warehouses | btnWarehouse       | Warehouses |
    | Warehouses | btnManageWarehouse | Item      |
    | Item      | btnHome            | Home      |
    | Item      | btnAccount         | Account   |
    | Item      | btnWarehouse       | Warehouses |
    | Item      | btnDeleteItem      | Item      |


    @u3
    Scenario: U3 - Navigation with Buttons During Action
        Given I Am On "<page1>"
        And I Am Performing "<action>"
        When I Click "<button>"
        Then I Am Taken To "<page2>"

    @u3Account
    Examples:
    | page1     | action            | button             | page2     |
    | Account   | Account Delete    | btnHome            | Home      |
    | Account   | Account Delete    | btnAccount         | Account   |
    | Account   | Account Delete    | btnWarehouse       | Warehouses |
    | Account   | Account Delete    | btnCancelDelete    | Account   |
    | Account   | Account Delete    | btnConfirmDelete   | Landing   |

    @u3Warehouses
    Examples:
    | page1     | action            | button             | page2     |
    | Warehouses | New Warehouse     | btnHome            | Home      |
    | Warehouses | New Warehouse     | btnAccount         | Account   |
    | Warehouses | New Warehouse     | btnWarehouse       | Warehouses |
    | Warehouses | New Warehouse     | btnManageWarehouse | Item      |

    @u3Items
    Examples:
    | page1     | action            | button             | page2     |
    | Item      | New Item          | btnHome            | Home      |
    | Item      | New Item          | btnAccount         | Account   |
    | Item      | New Item          | btnWarehouse       | Warehouses |
    | Item      | New Item          | btnDeleteItem      | Item      |
    | Item      | Item Update       | btnHome            | Home      |
    | Item      | Item Update       | btnAccount         | Account   |
    | Item      | Item Update       | btnWarehouse       | Warehouses |
    | Item      | Item Update       | btnUpdateItem      | Item      |
    | Item      | Item Update       | btnDeleteItem      | Item      |
    | Item      | Item Update       | btnCancel          | Item      |
    


    Scenario: U4 - Navigation with Button to Pages that Perform Actions
        Given I Am On "<page1>"
        When I Click "<button>"
        Then I Am Taken To "<page2>"
        And I Will Be Performing "<action>"
    
    Examples:
    | page1     | button          | page2     | action         |
    | Account   | btnDeleteAccount | Account   | Account Delete |
    | Warehouses | btnAddWarehouse | Warehouses | New Warehouse  |
    | Item      | btnAddItem      | Item      | New Item       |
    | Item      | btnUpdateItem   | Item      | Item Update    |
    
    
    
    Scenario: U5 - Navigation During Action to Pages that Perform Actions
        Given I Am On "<page1>"
        And I Am Performing "<action1>"
        When I Click "<button>"
        Then I Am Taken To "<page2>"
        And I Will Be Performing "<action2>"

    Examples:
    | page1     | action1         | button           | page2     | action2        |
    | Account   | Account Delete | btnUpdateAccount | Account   | Account Delete |
    | Warehouses | New Warehouse  | btnAddWarehouse  | Warehouses | New Warehouse  |
    | Item      | New Item       | btnAddItem       | Item      | New Item       |
    | Item      | New Item       | btnUpdateItem    | Item      | Item Update    |
    | Item      | Item Update    | btnAddItem       | Item      | New Item       |


 
