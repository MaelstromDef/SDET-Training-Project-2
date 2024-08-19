Feature: Administrator Behaviors

    ### CREATE ###

    Scenario: A1 - Valid account creation.
        Given a user is on the signup page
        And has entered their information
        When the user clicks the signup button
        Then the user is created
        And the user receives a successful alert
        And the user is redirected to the login page.

    Scenario: A2 - Invalid account creation.
        Given a user is on the signup page
        And has entered invalid information
        When the user clicks the signup button
        Then an error message appears
        And an account is not created.

    ### READ ###

    Scenario: A3 - Valid login attempt.
        Given a user is logged out
        And is on the login page
        And has entered valid credentials
        When the user clicks the login button
        Then they are logged in.

    Scenario: A4 - Invalid login attempt.
        Given a user is logged out
        And is on the login page
        And has entered invalid credentials
        When the user clicks the login button
        Then an error message appears
        And they are not logged in.

    Scenario: A7 - Invalid admin read.
        Given a user is logged out
        When the user attempts to navigate to the account page
        Then they can not see their account information.

    Scenario: A8 - Valid admin read.
        Given a user is logged in
        When the user attempt to navigate to the account page
        Then they can see their account information.

    ### UPDATE ###

    Scenario: A5 - Valid account update.
        Given a user is on their account page
        When the user clicks to update their account information
        Then their information is updated.

    Scenario: A9 - Invalid admin update.
        Given a user is on their account page
        And has entered invalid information
        When the user clicks to update their account information
        Then an error message appears.

    ### DELETE ###

    Scenario: A6 - Valid account deletion.
        Given a user is on their account page
        When the user clicks to delete their account
        Then their account is deleted
        And the user is redirected to the landing page.