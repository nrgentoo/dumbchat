package com.nrgentoo.dumbchat.data.features.bot;

import com.nrgentoo.dumbchat.domain.features.messages.entity.Message;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subscribers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link OfflineBotService}
 */
@RunWith(MockitoJUnitRunner.class)
public class OfflineBotServiceTest {

    private static final int MIN_PERIOD = 2;
    private static final int MAX_PERIOD = 4;
    private static final int MOCK_TEST_TIME = 20;   // 20 seconds

    private TestScheduler mTimerScheduler;

    private OfflineBotService mOfflineBotService;

    @Before
    public void setUp() throws Exception {
        mTimerScheduler = new TestScheduler();

        mOfflineBotService = new OfflineBotService();
        mOfflineBotService.mTimerScheduler = mTimerScheduler;
        mOfflineBotService.mMessageIdService = new MockMessageIdService();
        mOfflineBotService.setPeriods(MIN_PERIOD, MAX_PERIOD);
    }

    @After
    public void tearDown() throws Exception {
        mOfflineBotService = null;
    }

    @Test
    public void botMessages() throws Exception {
        TestSubscriber<Message> subscriber = new TestSubscriber<>();

        mOfflineBotService.messages()
                .subscribe(subscriber);

        mTimerScheduler.advanceTimeBy(MOCK_TEST_TIME, TimeUnit.SECONDS);
        subscriber.dispose();

        subscriber.assertNoErrors();
        subscriber.assertNotComplete();

        int minValues = MOCK_TEST_TIME / MAX_PERIOD;
        int maxValues = MOCK_TEST_TIME / MIN_PERIOD;

        Assertions.assertThat(subscriber.values()).isNotEmpty();
        assertThat(subscriber.valueCount()).isBetween(minValues, maxValues);
    }

    private static class MockMessageIdService implements MessageIdService {

        private AtomicInteger mLastId = new AtomicInteger(0);

        @Override
        public long getLastMessageId() {
            return mLastId.getAndIncrement();
        }
    }

}