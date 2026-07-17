# Agile and Scrum - Module 14 Notes

## Agile Manifesto

4 values:
1. **Individuals and interactions** over processes and tools
2. **Working software** over comprehensive documentation
3. **Customer collaboration** over contract negotiation
4. **Responding to change** over following a plan

12 principles - main ones i remember:
- deliver working software frequently (weeks not months)
- welcome changing requirements even late in development
- business people and developers work together daily
- face to face conversation is best communication
- sustainable development - consistent pace
- continuous attention to technical excellence
- simplicity - maximising work NOT done
- self-organizing teams

## Agile vs Waterfall

| | Waterfall | Agile |
|--|-----------|-------|
| Planning | all upfront | iterative |
| Delivery | end of project | frequent releases |
| Changes | hard to change | welcome changes |
| Testing | at the end | continuous |
| Risk | high - found late | low - found early |

## Scrum Framework

### Roles
- **Scrum Master** - removes blockers, facilitates ceremonies, protects team from distractions
- **Product Owner** - owns the product backlog, prioritizes features, speaks for customer
- **Dev Team** - 3-9 people, self-organizing, cross-functional

### Artifacts
- **Product Backlog** - prioritized list of all features/requirements
- **Sprint Backlog** - subset of product backlog selected for current sprint
- **Increment** - working software delivered at end of sprint

### Ceremonies
- **Sprint Planning** - team picks items from backlog, estimates effort, plans the sprint
- **Daily Scrum** - 15 min standup: what did i do, what will i do, any blockers
- **Sprint Review** - demo to stakeholders at end of sprint
- **Sprint Retrospective** - team reflects: what went well, what didnt, how to improve

### Sprint
- timeboxed to 1-4 weeks (usually 2 weeks)
- no scope changes during sprint
- potentially shippable product at end

## Story Points and Estimation

story points measure complexity not time

fibonacci sequence used: 1, 2, 3, 5, 8, 13, 21

### Planning Poker
1. PO reads user story
2. everyone picks a card privately
3. all reveal at same time
4. discuss if big differences
5. vote again until consensus

### Velocity
- how many story points team completes per sprint
- used to predict how much can be done in future sprints
- takes 3-4 sprints to get stable velocity

## User Stories

format: **As a [user], I want [goal] so that [benefit]**

examples:
- As a student, I want to view my grades online so that I don't have to visit college
- As an admin, I want to export reports to excel so that I can share with management

### INVEST principle (good user story should be)
- **I**ndependent - can be developed on its own
- **N**egotiable - details can change
- **V**aluable - delivers value to user
- **E**stimable - team can estimate effort
- **S**mall - fits in one sprint
- **T**estable - can write acceptance criteria

### Acceptance Criteria - Given When Then format
```
Given: I am on the login page
When: I enter valid credentials and click login
Then: I should be redirected to dashboard
And: My name should appear in the top right

Given: I enter wrong password
When: I click login
Then: I should see "Invalid credentials" error
```

## Definition of Done
checklist before story can be called done:
- [ ] code written and reviewed
- [ ] unit tests written and passing
- [ ] no sonarqube issues
- [ ] tested manually
- [ ] merged to develop branch
- [ ] demo ready for sprint review
