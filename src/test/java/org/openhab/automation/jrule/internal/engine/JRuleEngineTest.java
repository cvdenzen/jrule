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

    //ComponentContext mockedComponentContext;
    // mockito ComponentContext mockedComponentContext = mock(ComponentContext.class);
    //when(mockedComponentContext.).thenReturn (null);

    @Mocked
    DateTimeItem mockedDateTimeItem;
    //@Mocked DateTimeItem anotherMockedDateTimeItem;
    ZonedDateTime myZonedDateTime = ZonedDateTime.parse("2022-10-11T23:11:09+02:00");

    @BeforeAll
    public void testBeforeAll() throws Exception {
        out.println("Start TestBeforeAll()");
        out.println("jf=" + jf);
    }

    public class JRuleTest1 extends JRule {
        @JRuleName("Test jRule 13 hours")
        // Test add with hour=13
        @JRuleWhen(hours = 13)
        public void TestruleInJUnitTestForJRuleEngineTest() {
            try {
                AnnotatedElement ae = JRuleEngineTest.class.getDeclaredMethod("add", JRuleEngineTest.class);
            } catch (Exception ex) {
                out.println("foutje");
            }
            //findAnnotation(jRule., JRuleName.class);
            //jRuleEngine.add(jRule);
        }
    }

    public class JRuleTest2 extends JRule {
        @JRuleName("TestTestDateTimeItem")
        // Test add with DateTimeItem
        @JRuleWhen(item = "_TestDateTimeItem", trigger = JRuleDateTimeItem.TRIGGER_AT)
        public void TestruleInJUnitTestForJRuleEngineTestAt() {
            JRule jRule = new JRule() {
                public String toString() {
                    return "Test jRulexx";
                }
            };
            Annotation[] annotations = jRule.getClass().getAnnotations();
            try {
                AnnotatedElement ae = JRuleEngineTest.class.getDeclaredMethod("add", JRuleEngineTest.class);
            } catch (Exception ex) {
                out.println("foutje");
            }
        }
    }

    /*
    Test the add function in JRuleEngine.
     */
    // disable this test @Test
    void addTest1() {
        out.println("Start addTest1()");
        //mockedItemRegistry.add(mockedDateTimeItem);
        out.println("mockedItemRegistry=" + mockedItemRegistry);
        out.println("JRuleEngine.get()=" + JRuleEngine.get());
        JRuleEngine jRuleEngine = JRuleEngine.get();
        out.println("After get(): JRuleEngine.get()=" + JRuleEngine.get());
        out.println("Start new JRuleTest1()");
        JRule jRule = new JRuleTest1();
        out.println("Start add(jRule)");
        // JRuleEventHandler must have an itemRegistry
        JRuleEventHandler.get().setItemRegistry(mockedItemRegistry);
        jRuleEngine.add(jRule);
        out.println("End of JRuleEngineTest.add() method");
    }

    @Test
    void addTest2() throws Exception {
        jRuleEngine = JRuleEngine.get();
        JRuleEngine.get().setConfig(jRuleConfig);
        new Expectations() {{
            mockedDateTimeItem.getState();
            result = new DateTimeType(myZonedDateTime.plusDays(800));
        }};
        jRuleDateTimeItem = JRuleDateTimeItem.forName("_TestDateTimeItem");
        out.println("Start (openhab) itemRegistry.add(dateTimeItem)");
        out.println("Start add()");
        out.println("JRuleEngine.get()=" + JRuleEngine.get());
        // Create JRule item
        //JRuleDateTimeItem jRuleDateTimeItem = JRuleDateTimeItem.forName("TestDateTimeItem");
        jRuleDateTimeItem.postUpdate(ZonedDateTime.parse("2022-10-11T23:11:09+02:00"));
        // JRuleEventHandler must have an itemRegistry
        JRuleEventHandler.get().setItemRegistry(mockedItemRegistry);
        out.println("Start new JRuleTest2()");
        JRule jRule = new JRuleTest2();
        out.println("Start add(jRule)");
        jRuleEngine.add(jRule);
        // check error
        new Verifications() {{
            mockedDateTimeItem.getState();
            times = 4;
        }};
        out.println("End of JRuleEngineTest.add() method");
    }
}