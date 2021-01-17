package com.woorifis.bc;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public final class SwiftBcTest {

    @Nested
    class PaymentOrderExists {
        @Test
        public void noProperPaymentOrder() {

            SwiftBc swiftBc = new SwiftBc();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("BC1TEST")).thenReturn(new byte[] {});
            boolean result = swiftBc.paymentOrderExists(ctx,"BC1TEST");

            assertFalse(result);
        }

        @Test
        public void paymentOrderExists() {

            SwiftBc swiftBc = new SwiftBc();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("BC1TEST")).thenReturn(new byte[] {42});
            boolean result = swiftBc.paymentOrderExists(ctx,"BC1TEST");

            assertTrue(result);
        }

        @Test
        public void noKey() {
            SwiftBc swiftBc = new SwiftBc();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("BC1TEST")).thenReturn(null);
            boolean result = swiftBc.paymentOrderExists(ctx,"BC1TEST");

            assertFalse(result);
        }
    }

    @Nested
    class PaymentOrderCreates {

        @Test
        public void newPaymentOrderCreate() {
            SwiftBc swiftBc = new SwiftBc();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            String json = new PaymentOrder("BC1TEST"
                    , "20210113"
                    , "100"
                    , "USD"
                    , "WONGI"
                    , "WOORIFIS9FC"
                    , "20201215"
                    , "88260204024002"
                    , "HYEONGGUN"
                    , "WOORIFIS9FD"
                    , "TEST"
                    , "EMPTY"
                    , "2").toJSONString();

            swiftBc.createPaymentOrder(ctx
                    , "BC1TEST"
                    , "20210113"
                    , "100"
                    , "USD"
                    , "WONGI"
                    , "WOORIFIS9FC"
                    , "20201215"
                    , "88260204024002"
                    , "HYEONGGUN"
                    , "WOORIFIS9FD"
                    , "TEST"
                    , "EMPTY"
                    , "2");

            verify(stub).putState("BC1TEST", json.getBytes(UTF_8));
        }

        @Test
        public void alreadyExists() {
            SwiftBc swiftBc = new SwiftBc();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);

            when(stub.getState("BC1TEST")).thenReturn(new byte[] { 42 });

            Exception thrown = assertThrows(RuntimeException.class, () -> swiftBc.createPaymentOrder(ctx
                    , "BC1TEST"
                    , "20210113"
                    , "100"
                    , "USD"
                    , "WONGI"
                    , "WOORIFIS9FC"
                    , "20201215"
                    , "88260204024002"
                    , "HYEONGGUN"
                    , "WOORIFIS9FD"
                    , "TEST"
                    , "EMPTY"
                    , "2"));
            assertEquals(thrown.getMessage(), "The PaymentOrder BC1TEST already exists");

        }

    }

    @Test
    public void assetRead() {
        SwiftBc swiftBc = new SwiftBc();
        Context ctx = mock(Context.class);
        ChaincodeStub stub = mock(ChaincodeStub.class);
        when(ctx.getStub()).thenReturn(stub);

        PaymentOrder paymentOrder = new PaymentOrder(
                "BC1TEST"
                , "20210113"
                , "100"
                , "USD"
                , "WONGI"
                , "WOORIFIS9FC"
                , "20201215"
                , "88260204024002"
                , "HYEONGGUN"
                , "WOORIFIS9FD"
                , "TEST"
                , "EMPTY"
                , "2");

        String json = paymentOrder.toJSONString();
        when(stub.getState("BC1TEST")).thenReturn(json.getBytes(StandardCharsets.UTF_8));

        PaymentOrder returnedPaymentOrder = swiftBc.readPaymentOrder(ctx, "BC1TEST");
        assertEquals(returnedPaymentOrder.getAmount(), paymentOrder.getAmount());
    }
}