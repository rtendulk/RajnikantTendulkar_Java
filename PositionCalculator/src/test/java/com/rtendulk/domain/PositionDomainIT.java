package com.rtendulk.domain;

import junit.framework.Assert;
import junit.framework.TestCase;

public class PositionDomainIT extends TestCase {

    public void testPositionDomainObjectCreation() {
        PositionDomain positionDomain = new PositionDomain();
        Assert.assertNotNull(positionDomain);
    }

    public void testPositionDomainSetterGetter() {
        PositionDomain positionDomain = new PositionDomain();
        Assert.assertNotNull(positionDomain);

        positionDomain.setAccount("101");
        positionDomain.setAccountType("B");
        positionDomain.setInstrument("IBM");
        positionDomain.setQuantity(-1000);
        positionDomain.setDelta(-20000);

        Assert.assertEquals("101",positionDomain.getAccount());
        Assert.assertEquals("B",positionDomain.getAccountType());
        Assert.assertEquals("IBM",positionDomain.getInstrument());
        Assert.assertEquals(Integer.valueOf(-1000),positionDomain.getQuantity());
        Assert.assertEquals(Integer.valueOf(-2000),positionDomain.getDelta());

    }
}
