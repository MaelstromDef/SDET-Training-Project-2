Feature: Direct Navigation States

  Scenario Outline: Directly Going From One Page to Another
    Given I Am Logged "<InOut>"
    And I Am On "<Page1>"
    When I Attempt To Navigate to "<Page2>"
    Then I Am Taken To "<Page3>"

| InOut | page1      | page2      | page3      |
| In    | Home       | Landing    | Home       |
| In    | Home       | Signup     | Home       |
| In    | Home       | Login      | Home       |
| In    | Home       | Account    | Account    |
| In    | Home       | Warehouses | Warehouse  |
| In    | Home       | Item       | Home       |
| In    | Account    | Landing    | Home       |
| In    | Account    | Signup     | Home       |
| In    | Account    | Login      | Home       | 
| In    | Account    | Home       | Home       |
| In    | Account    | Warehouses | Warehouses |
| In    | Account    | Item       | Home       |
| In    | Warehouses | Landing    | Landing    |
| In    | Warehouses | Signup     | Home       |
| In    | Warehouses | Login      | Home       |
| In    | Warehouses | Home       | Home       |
| In    | Warehouses | Account    | Account    |
| In    | Warehouses | Item       | Home       |
| In    | Item       | Landing    | Home       |
| In    | Item       | Signup     | Home       |
| In    | Item       | Login      | Home       |
| In    | Item       | Home       | Home       |
| In    | Item       | Account    | Account    |
| In    | Item       | Warehouses | Warehouses |
| Out   | Landing    | Signup     | Signup     |
| Out   | Landing    | Login      | Login      |
| Out   | Landing    | Home       | Landing    |
| Out   | Landing    | Account    | Landing    |
| Out   | Landing    | Warehouses | Landing    |
| Out   | Landing    | Item       | Landing    |
| Out   | Signup     | Landing    | Landing    |
| Out   | Signup     | Login      | Login      |
| Out   | Signup     | Home       | Landing    |
| Out   | Signup     | Account    | Landing    |
| Out   | Signup     | Warehouses | Landing    |
| Out   | Signup     | Item       | Landing    |
| Out   | Login      | Landing    | Landing    |
| Out   | Login      | Signup     | Signup     |
| Out   | Login      | Home       | Landing    |
| Out   | Login      | Account    | Landing    |
| Out   | Login      | Warehouses | Landing    |
| Out   | Login      | Login      | Landing    |

