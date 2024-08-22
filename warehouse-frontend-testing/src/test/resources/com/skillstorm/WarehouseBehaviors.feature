Feature: Warehouse Behaviors

    ### CREATE ###

    Scenario: W4 - Valid Create
        Given I Am On "Warehouses"
        And I Enter Correct Information
        When I Submit The Form
        Then A New "Warehouse" Is Created

    Scenario: W5 - Invalid Create
        Given I Am On "Warehouses"
        And I Entered Incorrect Information
        When I Submit The Form
        Then I See An Error Message

    ### READ ###

    Scenario: W2 - Valid Read
        Given I Am Logged "In"
        When I Attempt To Navigate To "Warehouses"
        Then I Can See "Warehouse" Information

    Scenario: W1 - Invalid Read
        Given I Am Logged "Out"
        When I Attempt To Navigate To "Warehouses"
        Then I "Can Not" See "Warehouse" Information

    ### UPDATE ###

    Scenario: W3 - Valid Update
        Given I Am On "Warehouses"
        When I "Correctly" Update “Warehouse”
        Then "Warehouse" Fields Have "Been" Changed

    ### DELETE ###

    Scenario: W6 - Valid Delete
        Given I Am On "Warehouses"
        When I Click "btnDeleteWarehouse" Button
        Then "Warehouse" No Longer Exists