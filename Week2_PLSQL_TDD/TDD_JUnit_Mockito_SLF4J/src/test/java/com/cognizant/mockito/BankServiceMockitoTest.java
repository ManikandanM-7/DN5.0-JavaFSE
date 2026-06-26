package com.cognizant.mockito;

import com.cognizant.tdd.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class BankServiceMockitoTest {

    @Mock
    private ExternalPaymentApi mockPaymentApi;

    @Mock
    private CustomerRepository mockCustomerRepo;

    @InjectMocks
    private BankService bankService;

    private Customer mani;
    private Customer nithish;

    @BeforeEach
    void setUp() {
        mani = new Customer(1L, "Mani", 150000.00, "mani@test.com");
        nithish = new Customer(2L, "Nithish", 45000.00, "nithish@test.com");
    }

    @Test
    void testMockingStubbing() {
        when(mockCustomerRepo.findById(1L)).thenReturn(mani);
        when(mockPaymentApi.sendPayment(anyString(), eq(5000.0), eq("INR")))
            .thenReturn("TXN-SUCCESS-001");

        String result = bankService.processPayment(1L, 5000.0, "INR");
        assertEquals("TXN-SUCCESS-001", result);
    }

    @Test
    void testStubbingGetBalance() {
        when(mockCustomerRepo.findById(1L)).thenReturn(mani);
        double balance = bankService.getBalance(1L);
        assertEquals(150000.00, balance, 0.01);
    }

    @Test
    void testStubbingThrowsException() {
        when(mockCustomerRepo.findById(999L)).thenReturn(null);
        assertThrows(IllegalArgumentException.class,
            () -> bankService.getBalance(999L));
    }

    @Test
    void testMultipleReturnValues() {
        // first call returns mani, second returns updated mani
        when(mockCustomerRepo.findById(1L))
            .thenReturn(mani)
            .thenReturn(new Customer(1L, "Mani", 200000.00, "mani@test.com"));

        double first = bankService.getBalance(1L);
        double second = bankService.getBalance(1L);

        assertEquals(150000.00, first, 0.01);
        assertEquals(200000.00, second, 0.01);
    }

    @Test
    void testVerifyPaymentCalled() {
        when(mockPaymentApi.sendPayment(anyString(), anyDouble(), anyString()))
            .thenReturn("TXN-001");

        bankService.processPayment(1L, 1000.0, "INR");

        verify(mockPaymentApi, times(1)).sendPayment(eq("1"), eq(1000.0), eq("INR"));
    }

    @Test
    void testVerifyNeverCalledOnFailure() {
        when(mockCustomerRepo.findById(2L)).thenReturn(nithish);

        boolean result = bankService.transferFunds(2L, 1L, 100000.0);

        assertFalse(result);
        verify(mockCustomerRepo, never()).updateBalance(anyLong(), anyDouble());
    }

    @Test
    void testVerifyBothAccountsUpdated() {
        when(mockCustomerRepo.findById(1L)).thenReturn(mani);
        when(mockCustomerRepo.findById(2L)).thenReturn(nithish);

        boolean result = bankService.transferFunds(1L, 2L, 50000.0);

        assertTrue(result);
        verify(mockCustomerRepo, times(2)).updateBalance(anyLong(), anyDouble());
    }

    @Test
    void testNoMoreInteractions() {
        when(mockCustomerRepo.findById(1L)).thenReturn(mani);
        bankService.getBalance(1L);

        verify(mockCustomerRepo).findById(1L);
        verifyNoMoreInteractions(mockCustomerRepo);
        verifyNoInteractions(mockPaymentApi);
    }

    @Test
    void testVoidMethodStub() {
        doNothing().when(mockPaymentApi).notifyCustomer(anyString(), anyString());
        mockPaymentApi.notifyCustomer("1", "Payment done");
        verify(mockPaymentApi).notifyCustomer("1", "Payment done");
    }

    @Test
    void testInOrderVerification() {
        when(mockCustomerRepo.findById(1L)).thenReturn(mani);
        when(mockCustomerRepo.findById(2L)).thenReturn(nithish);

        bankService.transferFunds(1L, 2L, 10000.0);

        InOrder order = inOrder(mockCustomerRepo);
        order.verify(mockCustomerRepo).findById(1L);
        order.verify(mockCustomerRepo).updateBalance(eq(1L), anyDouble());
        order.verify(mockCustomerRepo).findById(2L);
        order.verify(mockCustomerRepo).updateBalance(eq(2L), anyDouble());
    }
}
