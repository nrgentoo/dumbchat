package com.nrgentoo.dumbchat.domain.features.users.usecase;

import com.nrgentoo.dumbchat.domain.core.executor.PostExecutionThread;
import com.nrgentoo.dumbchat.domain.core.executor.ThreadExecutor;
import com.nrgentoo.dumbchat.domain.features.users.entity.User;
import com.nrgentoo.dumbchat.domain.features.users.repository.UserRepo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.observers.TestObserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test {@link GetMyselfUseCase}
 */

@RunWith(MockitoJUnitRunner.class)
public class GetMyselfUseCaseTest {

    @Mock
    ThreadExecutor mockThreadExecutor;

    @Mock
    PostExecutionThread mockPostExecutionThread;

    @Mock
    UserRepo mockUserRepo;

    @Mock
    User mockMyselfUser;

    private GetMyselfUseCase mGetMyselfUseCase;

    @Before
    public void setUp() throws Exception {
        mGetMyselfUseCase = new GetMyselfUseCase(mockThreadExecutor, mockPostExecutionThread);
        mGetMyselfUseCase.mUserRepo = mockUserRepo;

        when(mockUserRepo.getMyself()).thenReturn(mockMyselfUser);
    }

    @After
    public void tearDown() throws Exception {
        mGetMyselfUseCase = null;
    }

    @Test
    public void execute() throws Exception {
        TestObserver<User> observer = new TestObserver<>();

        mGetMyselfUseCase.execute(null).subscribeWith(observer);

        observer.assertNoErrors();
        observer.assertComplete();
        observer.assertValueCount(1);

        User user = observer.values().get(0);
        assertThat(user).isEqualTo(mockMyselfUser);
    }

}