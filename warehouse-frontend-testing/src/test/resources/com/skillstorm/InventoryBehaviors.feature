Feature: Inventory Behaviors
  
  ### CREATE ###

  Scenario: I3 
    Given I Am On “Item”
    And I Entered Correct Information
    When I Submit The Form
    Then Item Is Added To Warehouse
  
  Scenario: I10 
    Given I Am On “Item”
    And I Incorrectly Add “Item”
    When I Click “btnAddItem” Button
    Then I See An Error Message



  ### READ ###

  Scenario: I1 
    Given I Am On “Warehouse”
    When I Click “btnManageWarehouse” Button
    Then I Am Taken to “Item”
    And I Can See Only Those Warehouse Items
  
  Scenario: I8 
    Given I Am Logged Out
    When I Attempt To Navigate To “Item”
    Then I Can Not See “Item” Information



  ### UPDATE ###

  Scenario: I5 
    Given I Am On “Item”
    When I Correctly Modify “Item”
    Then “Item” Fields Have “Been” Changed

  Scenario: I9 
    Given I Am On “Item”
    And I Incorrectly Update “Item”
    When I Click “btnUpdateItem” Button
    Then “Item” Fields Have “Not Been” Changed

  
  ### DELETE ###

  Scenario: I7 
    Given I Am On “Item”
    When I Click “btnDeleteItem” Button
    Then “Item” No Longer Exists


  
  