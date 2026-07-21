# GitHub Copilot - Practice Session Notes

## setup

1. installed GitHub Copilot extension in VS Code
2. signed in with github account
3. opened the spring boot project from week 4

---

## things i tried

### generating a method from a comment

typed this comment in CountryService.java:
```java
// find all countries with population greater than given value and sort by name
```
copilot suggested:
```java
public List<Country> findByPopulationGreaterThanSortedByName(Long population) {
    return countryRepository.findAll()
        .stream()
        .filter(c -> c.getPopulation() != null && c.getPopulation() > population)
        .sorted(Comparator.comparing(Country::getName))
        .collect(Collectors.toList());
}
```
reviewed it, looked fine, accepted it

---

### generating test cases

opened CountryServiceTest.java and typed:
```java
// test that findByPopulationGreaterThan returns correct results
```
copilot suggested the full test method with setup, call, and assertion
had to tweak the expected values to match my test data

---

### using chat feature (Copilot Chat)

asked: "explain what @Transactional(readOnly = true) does"
it explained:
- tells spring this method only reads data
- allows database optimizations
- prevents accidental writes
- better performance for read operations

also asked it to explain my LoggingAspect.java
it gave a clear explanation of each annotation

---

### refactoring

selected a messy method, right clicked → Copilot → Refactor
it cleaned up the null checks and made it more readable

---

## what i noticed

**good at:**
- boilerplate code (getters, setters, constructors)
- test case structure
- common patterns (singleton, builder etc)
- SQL queries from comments

**not so good at:**
- understanding my specific business logic
- always getting variable names right
- sometimes suggests outdated APIs

**important takeaway:**
always read the suggestion before accepting
copilot doesn't know your full codebase context
treat it like a smart autocomplete not a senior developer
