package com.cognizant.junit;

import com.cognizant.tdd.BankService;
import com.cognizant.tdd.Customer;
import com.cognizant.tdd.CustomerRepository;
import com.cognizant.tdd.ExternalPaymentApi;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BankServiceJUnitTest {

    private BankService bankService;
    private ExternalPaymentApi mockPaymentApi;
    private CustomerRepository mockCustomerRepo;
    private Customer mani;
    private Customer nithish;

    @BeforeAll
    static void initAll() {
        System.out.println("starting bank service tests");
    }

    @BeforeEach
    void setUp() {
        mockPaymentApi = mock(ExternalPaymentApi.class);
        mockCustomerRepo = mock(CustomerRepository.class);
        bankService = new BankService(mockPaymentApi, mockCustomerRepo);

        mani = new Customer(1L, "Mani", 150000.00, "mani@email.com");
        nithish = new Customer(2L, "Nithish", 45000.00, "nithish@email.com");

        when(mockCustomerRepo.findById(1L)).thenReturn(mani);
        when(mockCustomerRepo.findById(2L)).thenReturn(nithish);
        when(mockCustomerRepo.findById(999L)).thenReturn(null);
    }

    @AfterEach
    void tearDown() {
        System.out.println("test done");
    }

    @AfterAll
    static void cleanUp() {
        System.out.println("all tests finished");
    }

    @Test
    @Order(1)
    void testAddition() {
        int result = bankService.add(5, 7);
        assertEquals(12, result);
    }

    @Test
    @Order(2)
    void testIsEven() {
        assertTrue(bankService.isEven(4));
        assertFalse(bankService.isEven(7));
        assertTrue(bankService.isEven(0));
    }

    @Test
    @Order(3)
    void testGetBalance() {
        double balance = bankService.getBalance(1L);
        assertNotNull(balance);
        assertEquals(150000.00, balance, 0.01);
    }

    @Test
    @Order(4)
    void testCustomerNotFound() {
        Customer c = mockCustomerRepo.findById(999L);
        assertNull(c);
    }

    @Test
    @Order(5)
    void testGetBalanceThrowsForUnknown() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> bankService.getBalance(999L)
        );
        assertTrue(ex.getMessage().contains("Customer not found"));
    }

    @Test
    @Order(6)
    void testCustomerDataAssertAll() {
        assertAll("mani details",
            () -> assertEquals(1L, mani.getId()),
            () -> assertEquals("Mani", mani.getName()),
            () -> assertEquals(150000.00, mani.getBalance(), 0.01),
            () -> assertNotNull(mani.getEmail())
        );
    }

    @Test
    @Order(7)
    void testNegativeAmountThrows() {
        assertThrows(
            IllegalArgumentException.class,
            () -> bankService.processPayment(1L, -500, "INR")
        );
    }

    @Test
    @Order(8)
    void testTransferSuccess() {
        boolean result = bankService.transferFunds(1L, 2L, 50000.00);
        assertTrue(result);
        verify(mockCustomerRepo).updateBalance(eq(1L), anyDouble());
        verify(mockCustomerRepo).updateBalance(eq(2L), anyDouble());
    }

    @Test
    @Order(9)
    void testTransferFailsInsufficientFunds() {
        // nithish only has 45000
        boolean result = bankService.transferFunds(2L, 1L, 100000.00);
        assertFalse(result);
        verify(mockCustomerRepo, never()).updateBalance(anyLong(), anyDouble());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 100, 0})
    void testEvenNumbers(int n) {
        assertTrue(bankService.isEven(n));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 99})
    void testOddNumbers(int n) {
        assertFalse(bankService.isEven(n));
    }

    @ParameterizedTest
    @CsvSource({"1,2,3", "10,20,30", "0,0,0", "100,200,300"})
    void testAddMultipleInputs(int a, int b, int expected) {
        assertEquals(expected, bankService.add(a, b));
    }

    @Test
    @Order(10)
    @Timeout(1)
    void testGetBalanceTimeout() {
        double balance = bankService.getBalance(1L);
        assertEquals(150000.00, balance, 0.01);
    }
}
