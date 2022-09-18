package org.openhab.automation.jrule.internal.engine;

import org.eclipse.jdt.annotation.Nullable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openhab.automation.jrule.internal.JRuleConfig;
import org.openhab.automation.jrule.internal.JRuleFactory;
import org.openhab.automation.jrule.internal.events.JRuleEventSubscriber;
import org.openhab.automation.jrule.internal.handler.JRuleEventHandler;
import org.openhab.automation.jrule.items.JRuleDateTimeItem;
import org.openhab.automation.jrule.rules.JRule;
import org.openhab.automation.jrule.rules.JRuleName;
import org.openhab.automation.jrule.rules.JRuleWhen;
import org.openhab.core.audio.AudioManager;
import org.openhab.core.audio.internal.AudioManagerImpl;
import org.openhab.core.events.Event;
import org.openhab.core.events.EventPublisher;
import org.openhab.core.i18n.LocaleProvider;
import org.openhab.core.i18n.TranslationProvider;
import org.openhab.core.internal.items.MetadataRegistryImpl;
import org.openhab.core.items.ItemRegistry;
import org.openhab.core.items.MetadataRegistry;
import org.openhab.core.items.events.ItemStateEvent;
import org.openhab.core.library.items.DateTimeItem;
import org.openhab.core.library.types.DateTimeType;
import org.openhab.core.voice.VoiceManager;
import org.openhab.core.voice.internal.VoiceManagerImpl;
import org.osgi.framework.Bundle;
import org.osgi.service.component.ComponentContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.time.ZonedDateTime;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.System.out;

import mockit.*;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JRuleEngineTest {
    JRuleEngine jRuleEngine;
    @Injectable
    JRuleConfig jRuleConfig; // = new JRuleConfig(configMap);
    @Mocked
    JRule jRule;
    private final Logger logger = LoggerFactory.getLogger(JRuleEngine.class);
    Map<String, Object> configMap = new HashMap<>();
    JRuleDateTimeItem jRuleDateTimeItem;
    MetadataRegistry metadataRegistry = new MetadataRegistryImpl();
    @Mocked
    ItemRegistry mockedItemRegistry; // = mock(ItemRegistry.class); //new ItemRegistryImpl(metadataRegistry);
    @Mocked
    ComponentContext mockedComponentContext;
    @Injectable
    JRuleFactory jf;

    /**
     * A JRule. This JRule is used for testing the JRuleEngine
     */
    public class JRuleTest1 extends JRule {
        @JRuleName("Test jRule 13 hours")
        @JRuleWhen(hours = 13)
        public void TestruleInJUnitTestForJRuleEngineTest() {
            out.println("This is JRuleTest1");
        }
    }

    /**
     * Test method JRuleEngine.add().
     */
    @Test
    void addTest1() {
        out.println("Start addTest1()");
        JRuleEngine jRuleEngine = JRuleEngine.get();
        JRuleEngine.get().setConfig(jRuleConfig);
        JRule jRule = new JRuleTest1();
        JRuleEventHandler.get().setItemRegistry(mockedItemRegistry);
        jRuleEngine.add(jRule);
        out.println("End of JRuleEngineTest.add() method");
    }
}