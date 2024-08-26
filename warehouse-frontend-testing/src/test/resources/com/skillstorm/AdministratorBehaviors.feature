Feature: Administrator Behaviors

    ### CREATE ###

    Scenario: A1 - Valid account creation.
        Given I Am On "Signup"
        And I Enter "Correct" Information
        When I Submit The Form
        Then A New "Account" Is Created

    Scenario: A2 - Invalid account creation.
        Given I Am On "Signup"
        And I Enter "Incorrect" Information
        When I Submit The Form
        Then I See An Error Message

    ### READ ###

    Scenario: A3 - Valid login attempt.
        Given I Am On "Login"
        And I Enter "Correct" Information
        When I Submit The Form
        Then I "Am" Logged In

    Scenario: A4 - Invalid login attempt.
        Given I Am On "Login"
        And I Enter "Incorrect" Information
        When I Submit The Form
        Then I "Am Not" Logged In

    Scenario: A7 - Invalid admin read.
        Given I Am Logged "Out"
        When I Attempt To Navigate To "Account"
        Then I "Can Not" See "Account" Information

    Scenario: A8 - Valid admin read.
        Given I Am Logged "In"
        When I Attempt To Navigate To "Account"
        Then I "Can" See "Account" Information

    ### UPDATE ###

    Scenario: A5 - Valid account update.
        Given I Am On "Account"
        And I "Correctly" Update "Account"
        When I Click "btnUpdateAccount"
        Then "Account" Fields Have "Been" Changed

    Scenario: A9 - Invalid admin update.
        Given I Am On "Account"
        And I "Incorrectly" Update "Account"
        When I Click "btnUpdateAccount"
        Then "Account" Fields Have "Not Been" Changed

    ### DELETE ###

    Scenario: A6 - Valid account deletion.
        Given I Am On "Account"
        When I Click "btnDeleteAccount"
        Then "Account" No Longer Exists