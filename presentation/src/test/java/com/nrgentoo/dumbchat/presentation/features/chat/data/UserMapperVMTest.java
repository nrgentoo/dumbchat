package com.nrgentoo.dumbchat.presentation.features.chat.data;

import com.nrgentoo.dumbchat.domain.features.users.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nrg on 22.02.2017.
 */
public class UserMapperVMTest {

    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "user";
    private static final String USER_AVATAR_URI = "avatar uri";

    private User mUser;

    @Before
    public void setUp() throws Exception {
        mUser = User.builder()
                .id(USER_ID)
                .name(USER_NAME)
                .avatarUri(USER_AVATAR_URI)
                .build();
    }

    @Test
    public void transform() throws Exception {
        UserVM userVM = UserMapperVM.transform(mUser);

        assertThat(userVM.id()).isEqualTo(USER_ID);
        assertThat(userVM.name()).isEqualTo(USER_NAME);
        assertThat(userVM.avatarUri()).isEqualTo(USER_AVATAR_URI);
    }

}