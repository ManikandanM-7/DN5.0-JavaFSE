
-- DN 5.0 - Week 2 | Module 3: PL/SQL Programming
-- Exercise 4: Cursors (Implicit + Explicit)



-- PART A: Implicit Cursor — SQL%ROWCOUNT, SQL%FOUND

BEGIN
    UPDATE Customers SET Balance = Balance * 1.05 WHERE Balance < 50000;

    IF SQL%FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Updated ' || SQL%ROWCOUNT || ' low-balance accounts with 5% bonus.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('No accounts qualified for bonus.');
    END IF;
    COMMIT;
END;
/


-- PART B: Explicit Cursor — OPEN, FETCH, CLOSE

DECLARE
    CURSOR c_vip_customers IS
        SELECT CustomerID, Name, Balance
        FROM   Customers
        WHERE  IsVIP = 'TRUE'
        ORDER BY Balance DESC;

    v_id      Customers.CustomerID%TYPE;
    v_name    Customers.Name%TYPE;
    v_balance Customers.Balance%TYPE;
BEGIN
    OPEN c_vip_customers;
    DBMS_OUTPUT.PUT_LINE('=== VIP CUSTOMERS ===');

    LOOP
        FETCH c_vip_customers INTO v_id, v_name, v_balance;
        EXIT WHEN c_vip_customers%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('ID: ' || v_id || ' | Name: ' || v_name || ' | Balance: ₹' || v_balance);
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('Total VIP rows: ' || c_vip_customers%ROWCOUNT);
    CLOSE c_vip_customers;
END;
/


-- PART C: Cursor FOR LOOP (cleaner syntax)

BEGIN
    DBMS_OUTPUT.PUT_LINE('=== ALL LOANS WITH CUSTOMER NAMES ===');
    FOR rec IN (
        SELECT c.Name, l.LoanID, l.LoanAmount, l.InterestRate, l.Status
        FROM   Customers c JOIN Loans l ON c.CustomerID = l.CustomerID
        ORDER BY l.LoanAmount DESC
    ) LOOP
        DBMS_OUTPUT.PUT_LINE(
            RPAD(rec.Name, 15) ||
            ' | Loan: ₹' || LPAD(rec.LoanAmount, 10) ||
            ' | Rate: ' || rec.InterestRate || '%' ||
            ' | Status: ' || rec.Status
        );
    END LOOP;
END;
/


-- PART D: Parameterized Cursor

DECLARE
    CURSOR c_loans_by_status (p_status IN VARCHAR2) IS
        SELECT l.LoanID, c.Name, l.LoanAmount, l.DueDate
        FROM   Loans l JOIN Customers c ON l.CustomerID = c.CustomerID
        WHERE  l.Status = p_status;
BEGIN
    DBMS_OUTPUT.PUT_LINE('=== OVERDUE LOANS ===');
    FOR rec IN c_loans_by_status('OVERDUE') LOOP
        DBMS_OUTPUT.PUT_LINE('LoanID ' || rec.LoanID || ' | ' || rec.Name ||
                             ' | ₹' || rec.LoanAmount ||
                             ' | Due: ' || TO_CHAR(rec.DueDate, 'DD-MON-YYYY'));
    END LOOP;

    DBMS_OUTPUT.PUT_LINE(CHR(10) || '=== ACTIVE LOANS ===');
    FOR rec IN c_loans_by_status('ACTIVE') LOOP
        DBMS_OUTPUT.PUT_LINE('LoanID ' || rec.LoanID || ' | ' || rec.Name ||
                             ' | ₹' || rec.LoanAmount);
    END LOOP;
END;
/
