# Sample User Stories - Bank Application

## Product Backlog (priority order)

---

### Story 1 - User Login (8 points)
**As a** bank customer  
**I want to** log in with my username and password  
**So that** I can access my account securely

**Acceptance Criteria:**
- Given I am on login page, when I enter valid credentials, then I see my dashboard
- Given I enter wrong password, when I click login, then I see "Invalid credentials" message
- Given I fail 3 times, when I try again, then account is locked for 30 minutes

---

### Story 2 - View Account Balance (3 points)
**As a** bank customer  
**I want to** see my current account balance  
**So that** I know how much money I have

**Acceptance Criteria:**
- Given I am logged in, when I go to accounts, then I see my balance with date/time
- Given I have multiple accounts, when I view accounts, then I see all of them listed

---

### Story 3 - Transfer Funds (13 points)
**As a** bank customer  
**I want to** transfer money to another account  
**So that** I can pay people without going to branch

**Acceptance Criteria:**
- Given I have enough balance, when I transfer, then amount is debited from my account
- Given I enter an invalid account number, when I submit, then I see an error
- Given transfer is successful, when done, then I get a confirmation SMS

---

### Story 4 - View Transaction History (5 points)
**As a** bank customer  
**I want to** see my last 30 transactions  
**So that** I can track my spending

**Acceptance Criteria:**
- Given I am on history page, when it loads, then I see last 30 transactions
- Given I want to filter, when I select a date range, then I see only those transactions
- Given I want to download, when I click export, then I get a PDF

---

## Sprint 1 Plan (velocity: 20 points)

Selected for sprint:
- Story 1 - User Login (8 pts)
- Story 2 - View Account Balance (3 pts)
- Story 4 - View Transaction History (5 pts)
- Story 6 - User Profile Update (3 pts)

Total: 19 points

## Burndown Chart (Sprint 1)

```
Points
20 |*
18 |  *
16 |    *
14 |      *
12 |        *
10 |          *
 8 |            *
 6 |              *
 4 |                *
 2 |                  *
 0 |____________________*
   Day 1  5  7  9  11  14
```
