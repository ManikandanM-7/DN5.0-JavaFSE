
-- Exercise 2: Error Handling (Exception Handling)



-- PART A: Predefined Exceptions — NO_DATA_FOUND, TOO_MANY_ROWS

DECLARE
    v_balance  Customers.Balance%TYPE;
    v_cust_id  NUMBER := 999;  -- Does not exist
BEGIN
    SELECT Balance INTO v_balance
    FROM   Customers
    WHERE  CustomerID = v_cust_id;

    DBMS_OUTPUT.PUT_LINE('Balance: ' || v_balance);

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: No customer found with ID ' || v_cust_id);
    WHEN TOO_MANY_ROWS THEN
        DBMS_OUTPUT.PUT_LINE('ERROR: Multiple rows returned — use a cursor instead.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Unexpected error: ' || SQLERRM);
END;
/


-- PART B: User-Defined Exception — Insufficient funds transfer

DECLARE
    insufficient_funds EXCEPTION;
    v_sender_id    NUMBER := 4;   -- Kanishkumar (balance 8000)
    v_amount       NUMBER := 15000;
    v_sender_bal   NUMBER;
BEGIN
    SELECT Balance INTO v_sender_bal
    FROM   Customers WHERE CustomerID = v_sender_id;

    IF v_sender_bal < v_amount THEN
        RAISE insufficient_funds;
    END IF;

    UPDATE Customers SET Balance = Balance - v_amount WHERE CustomerID = v_sender_id;
    DBMS_OUTPUT.PUT_LINE('Transfer of ₹' || v_amount || ' successful.');
    COMMIT;

EXCEPTION
    WHEN insufficient_funds THEN
        DBMS_OUTPUT.PUT_LINE('TRANSACTION FAILED: Insufficient funds.');
        DBMS_OUTPUT.PUT_LINE('Available: ₹' || v_sender_bal || ' | Requested: ₹' || v_amount);
        ROLLBACK;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Unexpected error: ' || SQLERRM);
        ROLLBACK;
END;
/


-- PART C: RAISE_APPLICATION_ERROR — Custom error codes

CREATE OR REPLACE PROCEDURE transfer_funds (
    p_sender_id   IN NUMBER,
    p_receiver_id IN NUMBER,
    p_amount      IN NUMBER
) AS
    v_sender_bal NUMBER;
BEGIN
    SELECT Balance INTO v_sender_bal FROM Customers WHERE CustomerID = p_sender_id;

    IF v_sender_bal < p_amount THEN
        RAISE_APPLICATION_ERROR(-20001, 'Insufficient balance. Available: ₹' || v_sender_bal);
    END IF;

    IF p_amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Transfer amount must be positive.');
    END IF;

    UPDATE Customers SET Balance = Balance - p_amount WHERE CustomerID = p_sender_id;
    UPDATE Customers SET Balance = Balance + p_amount WHERE CustomerID = p_receiver_id;

    DBMS_OUTPUT.PUT_LINE('Transfer of ₹' || p_amount || ' completed.');
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END;
/

-- Test the procedure
BEGIN transfer_funds(1, 2, 10000); END; /   -- Should succeed
BEGIN transfer_funds(4, 2, 50000); END; /   -- Should fail: insufficient funds
