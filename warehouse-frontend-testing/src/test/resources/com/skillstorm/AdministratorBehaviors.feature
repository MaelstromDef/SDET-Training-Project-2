Feature: Administrator Behaviors

    ### CREATE ###

    Scenario: A1 - Valid account creation.
        Given I Am On “Signup”
        And I Entered Correct Information
        When I Submit The Form
        Then I Am Signed Up

    Scenario: A2 - Invalid account creation.
        Given I Am On “Signup”
        And I Entered Incorrect Information
        When I Submit The Form
        Then I Am Not Signed Up

    ### READ ###

    Scenario: A3 - Valid login attempt.
        Given I am on “Login”
        And I Entered Correct Information
        When I Submit The Form
        Then I Am Logged In

    Scenario: A4 - Invalid login attempt.
        Given I Am On “Login”
        And I Entered Incorrect Information
        When I Submit The Form
        Then I Am Not Logged In

    Scenario: A7 - Invalid admin read.
        Given I Am Logged Out
        When I Attempt To Navigate To “Account”
        Then I Can Not See Account Information

    Scenario: A8 - Valid admin read.
        Given I Am Logged In
        When I Attempt To Navigate To “Account”
        Then I Can See Account Information

    ### UPDATE ###

    Scenario: A5 - Valid account update.
        Given I Am On “Account”
        When I Submit The Form
        Then My Information Is Updated

    Scenario: A9 - Invalid admin update.
        Given I Am On “Account”
        And I Entered Incorrect Information
        When I Submit The Form
        Then My Information Is Not Updated

    ### DELETE ###

    Scenario: A6 - Valid account deletion.
        Given I Am On “Account”
        When I Delete My Account
        Then My Account Is Deleted