/*****************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 ****************************************************************/

package org.apache.cayenne.remote.service;

import org.apache.cayenne.CayenneRuntimeException;
import org.apache.cayenne.MockDataChannel;
import org.apache.cayenne.map.EntityResolver;
import org.apache.cayenne.remote.BootstrapMessage;
import org.apache.cayenne.remote.ClientMessage;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

public class DispatchHelperTest {

    @Test
    public void testBootstrapMessage() {
        EntityResolver resolver = new EntityResolver();
        MockDataChannel channel = new MockDataChannel(resolver);
        assertSame(resolver.getClientEntityResolver(), DispatchHelper.dispatch(
                channel,
                new BootstrapMessage()));
    }

    @Test
    public void testUnknownMessage() {
        try {
            DispatchHelper.dispatch(new MockDataChannel(), mock(ClientMessage.class));
            fail("Unknown message must have failed");
        }
        catch (CayenneRuntimeException e) {
            // expected
        }
    }
}
