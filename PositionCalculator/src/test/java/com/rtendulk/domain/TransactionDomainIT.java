package com.rtendulk.domain;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TransactionDomainIT extends TestCase {
    public void testTransactionDomainObjectCreation() {
        TransactionDomain transactionDomain = new TransactionDomain();
        Assert.assertNotNull(transactionDomain);
    }

    public void testTransactionDomainSetterGetter() {
        TransactionDomain transactionDomain = new TransactionDomain();
        Assert.assertNotNull(transactionDomain);

        transactionDomain.setTransactionId("1");
        transactionDomain.setTransactionQuantity(1000);
        transactionDomain.setInstrument("IBM");
        transactionDomain.setTransactionType("B");

        Assert.assertEquals("1",transactionDomain.getTransactionId());
        Assert.assertEquals("B",transactionDomain.getTransactionType());
        Assert.assertEquals("IBM",transactionDomain.getInstrument());
        Assert.assertEquals(Integer.valueOf(1000),transactionDomain.getTransactionQuantity());
    }
}
