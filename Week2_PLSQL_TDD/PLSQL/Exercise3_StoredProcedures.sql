
-- DN 5.0 - Week 2 | Module 3: PL/SQL Programming
-- Stored Procedures and Functions



-- PROCEDURE 1: ProcessMonthlyInterest
-- Adds monthly interest to all savings accounts

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest (
    p_interest_rate IN NUMBER DEFAULT 3.5   -- Annual rate in %
) AS
    v_monthly_rate NUMBER;
    v_count        NUMBER := 0;
BEGIN
    v_monthly_rate := p_interest_rate / 12 / 100;

    FOR rec IN (SELECT CustomerID, Balance FROM Customers WHERE Balance > 0) LOOP
        UPDATE Customers
        SET    Balance = Balance + (Balance * v_monthly_rate)
        WHERE  CustomerID = rec.CustomerID;

        v_count := v_count + 1;
        DBMS_OUTPUT.PUT_LINE('CustomerID ' || rec.CustomerID ||
                             ': ₹' || rec.Balance ||
                             ' → ₹' || ROUND(rec.Balance * (1 + v_monthly_rate), 2));
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Monthly interest applied to ' || v_count || ' accounts.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error in ProcessMonthlyInterest: ' || SQLERRM);
        ROLLBACK;
END ProcessMonthlyInterest;
/

-- Test
BEGIN ProcessMonthlyInterest(3.5); END;
/


-- PROCEDURE 2: UpdateEmployeeSalary (IN + OUT params)
-- Updates customer balance and returns updated amount

CREATE OR REPLACE PROCEDURE UpdateCustomerBalance (
    p_customer_id  IN  NUMBER,
    p_amount       IN  NUMBER,
    p_operation    IN  VARCHAR2,    -- 'CREDIT' or 'DEBIT'
    p_new_balance  OUT NUMBER,
    p_status       OUT VARCHAR2
) AS
    v_current_balance NUMBER;
BEGIN
    SELECT Balance INTO v_current_balance
    FROM   Customers WHERE CustomerID = p_customer_id
    FOR UPDATE;

    IF p_operation = 'CREDIT' THEN
        UPDATE Customers
        SET    Balance = Balance + p_amount
        WHERE  CustomerID = p_customer_id
        RETURNING Balance INTO p_new_balance;
        p_status := 'SUCCESS: Credited ₹' || p_amount;

    ELSIF p_operation = 'DEBIT' THEN
        IF v_current_balance < p_amount THEN
            p_new_balance := v_current_balance;
            p_status      := 'FAILED: Insufficient funds';
            RETURN;
        END IF;
        UPDATE Customers
        SET    Balance = Balance - p_amount
        WHERE  CustomerID = p_customer_id
        RETURNING Balance INTO p_new_balance;
        p_status := 'SUCCESS: Debited ₹' || p_amount;

    ELSE
        p_status      := 'FAILED: Invalid operation';
        p_new_balance := v_current_balance;
        RETURN;
    END IF;

    COMMIT;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        p_status      := 'FAILED: Customer not found';
        p_new_balance := 0;
    WHEN OTHERS THEN
        p_status      := 'ERROR: ' || SQLERRM;
        p_new_balance := 0;
        ROLLBACK;
END UpdateCustomerBalance;
/

-- Test with OUT params
DECLARE
    v_balance NUMBER;
    v_status  VARCHAR2(100);
BEGIN
    UpdateCustomerBalance(1, 20000, 'CREDIT', v_balance, v_status);
    DBMS_OUTPUT.PUT_LINE('Status: ' || v_status);
    DBMS_OUTPUT.PUT_LINE('New Balance: ₹' || v_balance);
END;
/


-- FUNCTION 1: CalculateAge — returns age from DOB

CREATE OR REPLACE FUNCTION CalculateAge (
    p_dob DATE
) RETURN NUMBER AS
BEGIN
    RETURN TRUNC(MONTHS_BETWEEN(SYSDATE, p_dob) / 12);
END CalculateAge;
/

-- Test
SELECT CalculateAge(TO_DATE('1997-06-15', 'YYYY-MM-DD')) AS Age FROM DUAL;


-- FUNCTION 2: GetCustomerCategory — returns tier

CREATE OR REPLACE FUNCTION GetCustomerCategory (
    p_customer_id IN NUMBER
) RETURN VARCHAR2 AS
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance FROM Customers WHERE CustomerID = p_customer_id;

    RETURN CASE
        WHEN v_balance >= 500000 THEN 'PLATINUM'
        WHEN v_balance >= 100000 THEN 'GOLD'
        WHEN v_balance >=  50000 THEN 'SILVER'
        ELSE                          'BASIC'
    END;
EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN 'NOT FOUND';
END GetCustomerCategory;
/

-- Test all customers
SELECT CustomerID, Name, Balance, GetCustomerCategory(CustomerID) AS Category
FROM   Customers;


-- PACKAGE: CustomerManagement
-- Groups related procedures and functions


-- Package Specification (interface)
CREATE OR REPLACE PACKAGE CustomerManagement AS
    PROCEDURE ApplyVIPStatus(p_min_balance IN NUMBER DEFAULT 100000);
    FUNCTION  GetTotalDeposits RETURN NUMBER;
    PROCEDURE PrintCustomerReport;
END CustomerManagement;
/

-- Package Body (implementation)
CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

    PROCEDURE ApplyVIPStatus(p_min_balance IN NUMBER DEFAULT 100000) AS
    BEGIN
        UPDATE Customers SET IsVIP = 'TRUE'  WHERE Balance >= p_min_balance;
        UPDATE Customers SET IsVIP = 'FALSE' WHERE Balance <  p_min_balance;
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('VIP status updated for threshold ₹' || p_min_balance);
    END ApplyVIPStatus;

    FUNCTION GetTotalDeposits RETURN NUMBER AS
        v_total NUMBER;
    BEGIN
        SELECT SUM(Balance) INTO v_total FROM Customers;
        RETURN NVL(v_total, 0);
    END GetTotalDeposits;

    PROCEDURE PrintCustomerReport AS
    BEGIN
        DBMS_OUTPUT.PUT_LINE('=== CUSTOMER REPORT ===');
        FOR rec IN (SELECT CustomerID, Name, Balance, IsVIP FROM Customers ORDER BY Balance DESC) LOOP
            DBMS_OUTPUT.PUT_LINE(
                RPAD(rec.Name, 20) ||
                ' Balance: ₹' || LPAD(rec.Balance, 10) ||
                ' VIP: ' || rec.IsVIP
            );
        END LOOP;
        DBMS_OUTPUT.PUT_LINE('Total Deposits: ₹' || GetTotalDeposits());
    END PrintCustomerReport;

END CustomerManagement;
/

-- Test package
BEGIN
    CustomerManagement.ApplyVIPStatus(100000);
    CustomerManagement.PrintCustomerReport;
END;
/


-- TRIGGER: Auto-log balance changes

CREATE TABLE AuditLog (
    LogID      NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    CustomerID NUMBER,
    OldBalance NUMBER(15,2),
    NewBalance NUMBER(15,2),
    ChangedAt  TIMESTAMP DEFAULT SYSTIMESTAMP,
    Action     VARCHAR2(20)
);

CREATE OR REPLACE TRIGGER trg_balance_audit
AFTER UPDATE OF Balance ON Customers
FOR EACH ROW
BEGIN
    INSERT INTO AuditLog (CustomerID, OldBalance, NewBalance, Action)
    VALUES (:OLD.CustomerID, :OLD.Balance, :NEW.Balance,
            CASE WHEN :NEW.Balance > :OLD.Balance THEN 'CREDIT' ELSE 'DEBIT' END);
END;
/

-- Test trigger
UPDATE Customers SET Balance = Balance + 5000 WHERE CustomerID = 2;
COMMIT;
SELECT * FROM AuditLog;
