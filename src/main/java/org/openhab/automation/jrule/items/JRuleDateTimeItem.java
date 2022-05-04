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
package org.openhab.automation.jrule.items;

import java.util.Date;

import org.openhab.automation.jrule.internal.handler.JRuleEventHandler;

/**
 * The {@link JRuleDateTimeItem} Items
 *
 * @author Joseph (Seaside) Hagberg - Initial contribution
 */
public class JRuleDateTimeItem extends JRuleItem {

    private final String itemName;

    private JRuleDateTimeItem(String itemName) {
        this.itemName = itemName;
    }

    public static JRuleDateTimeItem forName(String itemName) {
        return new JRuleDateTimeItem(itemName);
    }

    public void sendItemCommand(Date date) {
        JRuleEventHandler.get().sendCommand(itemName, date);
    }

    public void postItemUpdate(Date date) {
        JRuleEventHandler.get().postUpdate(itemName, date);
    }

    public Date getItemState() {
        return JRuleEventHandler.get().getStateFromItemAsDate(itemName);
    }

    public static void sendItemCommand(String itemName, Date date) {
        JRuleEventHandler.get().sendCommand(itemName, date);
    }

    public static void postItemUpdate(String itemName, Date date) {
        JRuleEventHandler.get().postUpdate(itemName, date);
    }

    public static Date getItemState(String itemName) {
        return JRuleEventHandler.get().getStateFromItemAsDate(itemName);
    }
}
