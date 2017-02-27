package com.nrgentoo.dumbchat.domain.features.users.usecase;

import com.nrgentoo.dumbchat.domain.core.executor.PostExecutionThread;
import com.nrgentoo.dumbchat.domain.core.executor.ThreadExecutor;
import com.nrgentoo.dumbchat.domain.core.usecase.SingleUseCase;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;
import com.nrgentoo.dumbchat.domain.features.users.repository.UserRepo;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Use case to get user's {@link User} object
 */

public class GetMyselfUseCase extends SingleUseCase<User, Void> {

    @Inject
    UserRepo mUserRepo;

    @Inject
    public GetMyselfUseCase(ThreadExecutor threadExecutor,
                            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<User> buildSingle(Void params) {
        return Single.create(e -> {
            if (e.isDisposed()) return;

            e.onSuccess(mUserRepo.getMyself());
        });
    }
}
