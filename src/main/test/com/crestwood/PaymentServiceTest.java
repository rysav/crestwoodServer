package com.crestwood;

import com.crestwood.persistance.PaymentPlanRepository;
import com.crestwood.persistance.TransactionRepository;
import com.crestwood.persistance.UserRepository;
import com.crestwood.service.PaymentServiceImpl;
import com.crestwood.service.TransactionService;
import com.crestwood.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.format.datetime.DateFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PaymentServiceTest {

    private PaymentServiceImpl paymentService;

    @Mock
    UserRepository userRepository;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    PaymentPlanRepository paymentPlanRepository;
    @Mock
    UserService userService;
    @Mock
    TransactionService transactionService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        paymentService = new PaymentServiceImpl(userRepository, transactionRepository, paymentPlanRepository,
                userService, transactionService);
    }

    @Test
    public void calcMessageTest() {
        assertEquals("Rent Charge", paymentService.calcMessage(100, 100));
        assertEquals("", paymentService.calcMessage(0, 100));
        assertEquals("Late Rent Fee", paymentService.calcMessage(5, 100));
    }

    @Test
    public void calcFeeTest() {
        double rentAmount = 525;
        double nothingOwed = 0;
        double somethingOwed = 525;

        assertEquals(0, paymentService.calcFee(-1, rentAmount, nothingOwed), .0001);
        assertEquals(rentAmount, paymentService.calcFee(0, rentAmount, nothingOwed), .0001);
        assertEquals(0, paymentService.calcFee(1, rentAmount, nothingOwed), .0001);
        assertEquals(0, paymentService.calcFee(2, rentAmount, nothingOwed), .0001);
        assertEquals(0, paymentService.calcFee(3, rentAmount, nothingOwed), .0001);
        assertEquals(0, paymentService.calcFee(4, rentAmount, nothingOwed), .0001);
        assertEquals(0, paymentService.calcFee(8, rentAmount, nothingOwed), .0001);
        assertEquals(0, paymentService.calcFee(9, rentAmount, nothingOwed), .0001);

        assertEquals(0, paymentService.calcFee(-11, rentAmount, somethingOwed), .0001);
        assertEquals(rentAmount, paymentService.calcFee(0, rentAmount, somethingOwed), .0001);
        assertEquals(0, paymentService.calcFee(1, rentAmount, somethingOwed), .0001);
        assertEquals(10, paymentService.calcFee(2, rentAmount, somethingOwed), .0001);
        assertEquals(5, paymentService.calcFee(3, rentAmount, somethingOwed), .0001);
        assertEquals(5, paymentService.calcFee(4, rentAmount, somethingOwed), .0001);
        assertEquals(5, paymentService.calcFee(8, rentAmount, somethingOwed), .0001);
        assertEquals(0, paymentService.calcFee(9, rentAmount, somethingOwed), .0001);
    }

    @Test
    public void calcDiffTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date dueDate = formatter.parse("15-NOV-2018");
        Date today = formatter.parse("04-NOV-2018");
        int diff = paymentService.calcDifference(today, dueDate);
        assertTrue(diff < 0);

        assertEquals(0, paymentService.calcDifference(formatter.parse("15-NOV-2018"), dueDate));
        assertEquals(1, paymentService.calcDifference(formatter.parse("16-NOV-2018"), dueDate));
        assertEquals(2, paymentService.calcDifference(formatter.parse("17-NOV-2018"), dueDate));
        assertEquals(3, paymentService.calcDifference(formatter.parse("18-NOV-2018"), dueDate));

        Date due = formatter.parse("30-DEC-2018");
        assertEquals(2, paymentService.calcDifference(formatter.parse("01-JAN-2019"), due));
        assertEquals(3, paymentService.calcDifference(formatter.parse("02-JAN-2019"), due));
        assertEquals(5, paymentService.calcDifference(formatter.parse("04-JAN-2019"), due));
        assertEquals(10, paymentService.calcDifference(formatter.parse("09-JAN-2019"), due));

        due = formatter.parse("29-JAN-2019");
        assertEquals(-20, paymentService.calcDifference(formatter.parse("09-JAN-2019"), due));
        assertEquals(0, paymentService.calcDifference(formatter.parse("29-JAN-2019"), due));
        assertEquals(1, paymentService.calcDifference(formatter.parse("30-JAN-2019"), due));
        assertEquals(2, paymentService.calcDifference(formatter.parse("31-JAN-2019"), due));
        assertEquals(3, paymentService.calcDifference(formatter.parse("01-FEB-2019"), due));
        assertEquals(4, paymentService.calcDifference(formatter.parse("02-FEB-2019"), due));
        assertEquals(12, paymentService.calcDifference(formatter.parse("10-FEB-2019"), due));

    }

    @Test
    public void modTest() {
        assertEquals(11, (((0 - 1) % 12) + 12) % 12);
    }
}
