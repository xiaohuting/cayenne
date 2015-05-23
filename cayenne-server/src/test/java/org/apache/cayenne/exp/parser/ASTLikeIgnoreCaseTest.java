/*****************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 ****************************************************************/
package org.apache.cayenne.exp.parser;

import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.testdo.testmap.Artist;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ASTLikeIgnoreCaseTest {

    @Test
    public void testToEJBQL() {
        Expression like = ExpressionFactory.likeIgnoreCaseExp("a", "%b%");
        assertEquals(like.toEJBQL("p"), "upper(p.a) like '%B%'");
    }
    
	@Test
	public void testEvaluateLIKE_IGNORE_CASE() {
		Expression like = new ASTLikeIgnoreCase(new ASTObjPath("artistName"), "aBcD");
		Expression notLike = new ASTNotLikeIgnoreCase(new ASTObjPath("artistName"), "aBcD");

		Artist noMatch1 = new Artist();
		noMatch1.setArtistName("dabc");
		assertFalse(like.match(noMatch1));
		assertTrue(notLike.match(noMatch1));

		Artist match1 = new Artist();
		match1.setArtistName("abcd");
		assertTrue("Failed: " + like, like.match(match1));
		assertFalse("Failed: " + notLike, notLike.match(match1));

		Artist match2 = new Artist();
		match2.setArtistName("ABcD");
		assertTrue("Failed: " + like, like.match(match2));
		assertFalse("Failed: " + notLike, notLike.match(match2));
	}
}
