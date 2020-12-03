 /*
  * Copyright (C) 2019-2020 the original author or authors.
  *
  * This program is free software; you can redistribute it and/or
  * modify it under the terms of the GNU General Public License
  * as published by the Free Software Foundation; either version 2
  * of the License, or (at your option) any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with this program; if not, write to the Free Software
  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  */

 package org.mallfoundry.finance;

 import org.apache.commons.lang3.StringUtils;
 import org.mallfoundry.keygen.PrimaryKeyHolder;
 import org.mallfoundry.security.SubjectHolder;

 public class TopupIdentityProcessor implements TopupProcessor {

     private static final String RECHARGE_ID_VALUE_NAME = "finance.recharge.id";

     @Override
     public Topup preProcessAfterCreateTopup(Topup recharge) {
         if (StringUtils.isBlank(recharge.getId())) {
             recharge.setId(this.nextRechargeId());
             recharge.setOperatorId(SubjectHolder.getSubject().getId());
             recharge.setOperator(SubjectHolder.getSubject().getNickname());
         }
         return recharge;
     }

     private String nextRechargeId() {
         return "1004" + PrimaryKeyHolder.next(RECHARGE_ID_VALUE_NAME);
     }
 }
