package com.nrgentoo.dumbchat.data.features.bot;

import com.nrgentoo.dumbchat.data.features.messages.model.MessageDto;
import com.nrgentoo.dumbchat.data.features.users.model.UserDto;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Offline implementation of {@link BotService}
 */
public class OfflineBotService implements BotService {

    // minimal period between messages
    private static final int MIN_PERIOD = 2;

    // maximal period between messages
    private static final int MAX_PERIOD = 3;

    // Bot account
    private static final UserDto BOT_USER;
    static {
        String avatarUri = "";
        BOT_USER = UserDto.builder()
                .setId(1)
                .setName("Bot")
                .setAvatarUri(avatarUri)
                .build();
    }

    private int mMinPeriod = MIN_PERIOD;
    private int mMaxPeriod = MAX_PERIOD;

    private final Random mPeriodRandom;

    @Inject
    Scheduler mTimerScheduler;

    @Inject
    public OfflineBotService() {
        mPeriodRandom = new Random();
    }

    public void setPeriods(int min, int max) {
        this.mMinPeriod = min;
        this.mMaxPeriod = max;
    }

    @Override
    public Flowable<MessageDto> messages() {
        return Flowable.create(e -> {
            if (e.isCancelled()) return;

            BehaviorSubject<Integer> intervalSubject = BehaviorSubject.createDefault(getInterval());

            Disposable d = intervalSubject
                    .switchMap(interval -> Observable.timer(interval, TimeUnit.SECONDS,
                            mTimerScheduler))
                    .map(r -> getInterval())
                    .subscribe(newInterval -> {
                        MessageDto message = MessageDto.builder()
                                .setText(peekRandomQuote())
                                .setTimeStamp(System.currentTimeMillis())
                                .setAuthor(BOT_USER)
                                .build();

                        e.onNext(message);

                        intervalSubject.onNext(newInterval);
                    });

            e.setCancellable(d::dispose);
        }, BackpressureStrategy.BUFFER);
    }

    private int getInterval() {
        return mPeriodRandom.nextInt(mMaxPeriod - mMinPeriod) + mMinPeriod;
    }

    private String peekRandomQuote() {
        int index = mPeriodRandom.nextInt(BotDictionary.DICTIONARY.size());

        int i = 0;
        for (String quote : BotDictionary.DICTIONARY) {
            if (i == index) {
                return quote;
            }

            i++;
        }

        throw new IllegalStateException("Can't get random quote from dictionary");
    }
}
