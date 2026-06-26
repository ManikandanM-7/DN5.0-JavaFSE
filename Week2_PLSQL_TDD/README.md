# Week 2 - PL/SQL, JUnit, Mockito, SLF4J

## plsql

**exercise 1 - control structures**
- IF-THEN-ELSE to apply interest discounts
- CASE statement to categorize customers by balance
- FOR loop to apply rate hike on all active loans
- WHILE loop to flag overdue loans

**exercise 3 - stored procedures**
- ProcessMonthlyInterest procedure with IN param
- UpdateCustomerBalance with IN and OUT params
- GetCustomerCategory function returning tier
- CustomerManagement package with spec and body
- Audit trigger on balance changes

## junit tests

- setting up junit5 with maven
- assertEquals, assertTrue, assertFalse, assertNull, assertNotNull
- assertThrows for exception testing
- assertAll for grouping multiple assertions
- @BeforeEach and @AfterEach for setup and teardown
- @ParameterizedTest with @ValueSource and @CsvSource
- @Timeout test

## mockito

- mock() and @Mock annotation
- when().thenReturn() for stubbing
- verify() to check method was called
- times(), never() for call count verification
- InOrder for sequence verification
- doNothing() and doThrow() for void methods

## slf4j + logback

- all log levels: trace debug info warn error
- parameterized logging with {}
- logback.xml with console, file, error-only appenders

## how to run
```bash
cd TDD_JUnit_Mockito_SLF4J
mvn clean test
```
