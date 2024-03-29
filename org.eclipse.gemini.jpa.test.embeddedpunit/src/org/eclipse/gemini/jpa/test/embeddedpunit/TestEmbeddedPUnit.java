/*******************************************************************************
 * Copyright (c) 2010 Oracle.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution. 
 * The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 * and the Apache License v2.0 is available at 
 *     http://www.opensource.org/licenses/apache2.0.php.
 * You may elect to redistribute this code under either of these licenses.
 *
 * Contributors:
 *     mkeith - Gemini JPA tests 
 ******************************************************************************/
package org.eclipse.gemini.jpa.test.embeddedpunit;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;

import model.embeddedaccount.*;

import org.junit.*;
import org.osgi.framework.BundleContext;

import org.eclipse.gemini.jpa.test.common.JpaTest;

/**
 * Test class to test looking up EMF Service of an embedded persistence unit
 * 
 * @author mkeith
 */
public class TestEmbeddedPUnit extends JpaTest {
        
    public static final String TEST_NAME = "TestEmbeddedPUnit";
    public static final String PERSISTENCE_UNIT_UNDER_TEST = "EmbeddedAccounts";

    protected static EntityManagerFactory emf;
    public static BundleContext ctx;
    
    /* === Test Methods === */

    @BeforeClass
    public static void classSetUp() {
        sdebug(TEST_NAME, "In setup");
        emf = lookupEntityManagerFactory(TEST_NAME, PERSISTENCE_UNIT_UNDER_TEST, ctx);
        sdebug(TEST_NAME, "Got EMF - " + emf);
    }

    @AfterClass
    public static void classCleanUp() {
        if (emf != null) {
            emf.close();
            emf = null;
        }
    }

    /* === Subclassed methods === */

    public EntityManagerFactory getEmf() { return emf; }

    public String getTestPersistenceUnitName() { return PERSISTENCE_UNIT_UNDER_TEST; }

    public Object newObject() {
        EmbAccount a = new EmbAccount();
        a.setBalance(100.0);
        return a;
    }
    
    public Object findObject() {
        EntityManager em = emf.createEntityManager();
        Object obj = em.find(EmbAccount.class, 1);
        em.close();
        return obj;
    }

    public String queryString() { 
        return "SELECT a FROM EmbAccount a";
    }
}
