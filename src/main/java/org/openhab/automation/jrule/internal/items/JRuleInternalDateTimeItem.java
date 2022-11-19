/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.automation.jrule.internal.items;

import java.time.ZonedDateTime;
import java.util.Date;

import org.openhab.automation.jrule.internal.handler.JRuleEventHandler;
import org.openhab.automation.jrule.items.JRuleDateTimeItem;
import org.openhab.automation.jrule.rules.value.JRuleDateTimeValue;

/**
 * The {@link JRuleInternalDateTimeItem} Items
 *
 * @author Joseph (Seaside) Hagberg - Initial contribution
 */
public class JRuleInternalDateTimeItem extends JRuleInternalItem<JRuleDateTimeValue> implements JRuleDateTimeItem {

    public JRuleInternalDateTimeItem(String name, String label, String type, String id) {
        super(name, label, type, id);
    }

    public void sendCommand(Date date) {
        JRuleEventHandler.get().sendCommand(name, new JRuleDateTimeValue(date));
    }

    public void sendCommand(ZonedDateTime value) {
        JRuleEventHandler.get().sendCommand(name, new JRuleDateTimeValue(value));
    }

    public void postUpdate(Date date) {
        JRuleEventHandler.get().postUpdate(name, new JRuleDateTimeValue(date).asStringValue());
    }

    public void postUpdate(ZonedDateTime value) {
        JRuleEventHandler.get().postUpdate(name, new JRuleDateTimeValue(value).asStringValue());
    }
}
