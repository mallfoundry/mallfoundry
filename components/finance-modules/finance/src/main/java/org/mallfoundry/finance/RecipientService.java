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

import org.mallfoundry.keygen.PrimaryKeyHolder;

import java.util.Objects;
import java.util.Optional;

public class RecipientService {

    private static final String RECIPIENT_ID_VALUE_NAME = "finance.recipient.id";

    private final RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    private Optional<Recipient> findRecipient(RecipientType type, String number) {
        var recipient = this.recipientRepository.create(null).toBuilder().type(type).number(number).build();
        return this.recipientRepository.find(recipient);
    }

    Recipient getRecipient(Recipient recipient) throws RecipientException {
        if (Objects.isNull(recipient.getType())) {
            throw new RecipientException("Recipient type cannot be empty");
        }
        return this.findRecipient(recipient.getType(), recipient.getNumber())
                .orElseGet(() -> this.saveRecipient(recipient));
    }

    private Recipient saveRecipient(Recipient recipient) {
        recipient.setId(PrimaryKeyHolder.next(RECIPIENT_ID_VALUE_NAME));
        return this.recipientRepository.save(recipient);
    }
}
