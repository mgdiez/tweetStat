package repository.datasource;

import android.content.Context;

import com.mgdiez.data.bean.dto.tweet.mapper.UserDtoMapper;
import com.mgdiez.data.bean.vo.tweet.UserVo;
import com.mgdiez.domain.bean.UserBo;
import com.twitter.sdk.android.core.models.User;

import net.TwitterApiService;

import io.realm.Realm;
import okhttp3.logging.HttpLoggingInterceptor;
import repository.RealmHelper;
import rx.Observable;

public class CloudUserDatastore implements UserDatastore {

    private Context context;
    private TwitterApiService twitterApiService;

    public CloudUserDatastore(Context context) {
        this.context = context;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        twitterApiService = new TwitterApiService();
    }


    @Override
    public Observable<UserBo> getUserData() {
        return twitterApiService.getUserData().doOnNext(new UserPersistAction()).map(UserDtoMapper::toBo);
    }

    private class UserPersistAction implements rx.functions.Action1<User> {
        @Override
        public void call(User user) {
            Realm realm = RealmHelper.getInstance(context);
            UserVo userVo = UserDtoMapper.toVo(user);

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(userVo);
            realm.commitTransaction();

            realm.close();
        }
    }
}
