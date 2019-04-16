// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.doris.optimizer.operator;

import org.apache.doris.optimizer.base.OptMaxcard;

public class OptLogicalFullOuterJoin extends OptLogicalJoin {
    public OptLogicalFullOuterJoin() {
        super(OptOperatorType.OP_LOGICAL_FULL_OUTER_JOIN);
    }
    //------------------------------------------------------------------------
    // Used to get operator's derived property
    //------------------------------------------------------------------------
    @Override
    public OptMaxcard getMaxcard(OptExpressionHandle exprHandle) {
        OptMaxcard leftMaxcard = exprHandle.getChildLogicalProperty(0).getMaxcard();
        OptMaxcard rightMaxcard = exprHandle.getChildLogicalProperty(1).getMaxcard();
        if (leftMaxcard.getValue() > 0 && rightMaxcard.getValue() > 0) {
            OptMaxcard maxcard = new OptMaxcard(leftMaxcard);
            maxcard.multiply(rightMaxcard);
            return maxcard;
        }
        if (leftMaxcard.lessThan(rightMaxcard)) {
            return rightMaxcard;
        }
        return leftMaxcard;
    }
}