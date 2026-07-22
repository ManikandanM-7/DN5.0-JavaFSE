
-- Control Structures


-- A bank wants to apply interest rates and flag customers
-- based on their account balance and loan status using control structures.


-- SETUP: Create sample tables

CREATE TABLE Customers (
    CustomerID   NUMBER PRIMARY KEY,
    Name         VARCHAR2(100),
    Age          NUMBER,
    Balance      NUMBER(15,2),
    IsVIP        VARCHAR2(5) DEFAULT 'FALSE'
);

CREATE TABLE Loans (
    LoanID       NUMBER PRIMARY KEY,
    CustomerID   NUMBER REFERENCES Customers(CustomerID),
    LoanAmount   NUMBER(15,2),
    InterestRate NUMBER(5,2),
    DueDate      DATE,
    Status       VARCHAR2(20) DEFAULT 'ACTIVE'
);

-- Insert sample data
INSERT INTO Customers VALUES (1, 'Mani',      28, 150000.00, 'FALSE');
INSERT INTO Customers VALUES (2, 'Nithish',   27,  45000.00, 'FALSE');
INSERT INTO Customers VALUES (3, 'Karmugilan',26, 520000.00, 'FALSE');
INSERT INTO Customers VALUES (4, 'Kanishkumar',25,  8000.00, 'FALSE');
INSERT INTO Customers VALUES (5, 'Harikrishna',27, 95000.00, 'FALSE');

INSERT INTO Loans VALUES (1, 1, 50000,  10.5, SYSDATE - 10, 'ACTIVE');
INSERT INTO Loans VALUES (2, 2, 20000,   9.0, SYSDATE - 60, 'ACTIVE');
INSERT INTO Loans VALUES (3, 3, 200000,  8.5, SYSDATE + 30, 'ACTIVE');
INSERT INTO Loans VALUES (4, 4, 15000,  12.0, SYSDATE - 5,  'ACTIVE');

COMMIT;


-- PART A: IF-THEN-ELSE — Apply discount on loan interest rate
-- for customers aged > 60 (senior citizen benefit)

DECLARE
    v_customer_id  Customers.CustomerID%TYPE := 1;
    v_age          Customers.Age%TYPE;
    v_loan_id      Loans.LoanID%TYPE;
    v_interest     Loans.InterestRate%TYPE;
    v_new_interest Loans.InterestRate%TYPE;
BEGIN
    SELECT Age INTO v_age FROM Customers WHERE CustomerID = v_customer_id;
    SELECT LoanID, InterestRate INTO v_loan_id, v_interest
    FROM   Loans WHERE CustomerID = v_customer_id AND ROWNUM = 1;

    IF v_age > 60 THEN
        v_new_interest := v_interest - 1.0;  -- 1% discount for senior citizens
        UPDATE Loans SET InterestRate = v_new_interest WHERE LoanID = v_loan_id;
        DBMS_OUTPUT.PUT_LINE('Senior citizen discount applied. New rate: ' || v_new_interest || '%');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Customer age: ' || v_age || '. No senior discount applicable.');
    END IF;
    COMMIT;
END;
/


-- PART B: CASE Statement — Categorize customers by balance

DECLARE
    CURSOR c_customers IS SELECT CustomerID, Name, Balance FROM Customers;
    v_category VARCHAR2(20);
BEGIN
    FOR rec IN c_customers LOOP
        v_category := CASE
            WHEN rec.Balance >= 500000 THEN 'PLATINUM'
            WHEN rec.Balance >= 100000 THEN 'GOLD'
            WHEN rec.Balance >=  50000 THEN 'SILVER'
            ELSE                            'BASIC'
        END;
        DBMS_OUTPUT.PUT_LINE(rec.Name || ' (₹' || rec.Balance || ') → Category: ' || v_category);
    END LOOP;
END;
/


-- PART C: FOR LOOP — Apply 1% interest hike for all ACTIVE loans

DECLARE
    CURSOR c_loans IS SELECT LoanID, InterestRate FROM Loans WHERE Status = 'ACTIVE';
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- Applying 1% interest hike to all active loans ---');
    FOR loan_rec IN c_loans LOOP
        UPDATE Loans
        SET    InterestRate = loan_rec.InterestRate + 1
        WHERE  LoanID = loan_rec.LoanID;
        DBMS_OUTPUT.PUT_LINE('LoanID ' || loan_rec.LoanID || ': ' ||
                             loan_rec.InterestRate || '% → ' ||
                             (loan_rec.InterestRate + 1) || '%');
    END LOOP;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Done. All active loans updated.');
END;
/


-- PART D: WHILE LOOP — Mark overdue loans (due date in past)

DECLARE
    CURSOR c_overdue IS
        SELECT LoanID, CustomerID, DueDate
        FROM   Loans
        WHERE  DueDate < SYSDATE AND Status = 'ACTIVE';
    v_count NUMBER := 0;
BEGIN
    FOR rec IN c_overdue LOOP
        UPDATE Loans SET Status = 'OVERDUE' WHERE LoanID = rec.LoanID;
        v_count := v_count + 1;
        DBMS_OUTPUT.PUT_LINE('LoanID ' || rec.LoanID || ' marked OVERDUE.');
    END LOOP;
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Total overdue loans flagged: ' || v_count);
END;
/


-- PART E: Mark VIP customers (Balance > 100000)

BEGIN
    UPDATE Customers
    SET    IsVIP = 'TRUE'
    WHERE  Balance > 100000;

    DBMS_OUTPUT.PUT_LINE('VIP customers updated: ' || SQL%ROWCOUNT || ' row(s).');
    COMMIT;
END;
/

-- Verify results
SELECT CustomerID, Name, Balance, IsVIP FROM Customers;
SELECT LoanID, CustomerID, InterestRate, DueDate, Status FROM Loans;
