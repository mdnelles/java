package com.pubnub.api.endpoints.push;

import com.pubnub.api.core.Pubnub;
import com.pubnub.api.core.PubnubException;
import com.pubnub.api.core.enums.PNOperationType;
import com.pubnub.api.core.enums.PushType;
import com.pubnub.api.endpoints.Endpoint;
import lombok.Setter;
import lombok.experimental.Accessors;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;
import java.util.Map;

@Accessors(chain = true, fluent = true)
public class RemoveAllPushChannelsForDevice extends Endpoint<List<Object>, Boolean> {

    @Setter private PushType pushType;
    @Setter private String deviceId;

    public RemoveAllPushChannelsForDevice(Pubnub pubnub) {
        super(pubnub);
    }

    @Override
    protected boolean validateParams() {
        if (pushType == null) {
            return false;
        }

        return true;
    }


    @Override
    protected Call<List<Object>> doWork(Map<String, String> params) throws PubnubException {
        params.put("type", pushType.name().toLowerCase());

        PushService service = this.createRetrofit().create(PushService.class);

        return service.removeAllChannelsForDevice(pubnub.getConfiguration().getSubscribeKey(), deviceId, params);

    }

    @Override
    protected Boolean createResponse(Response<List<Object>> input) throws PubnubException {
        return true;
    }

    protected int getConnectTimeout() {
        return pubnub.getConfiguration().getConnectTimeout();
    }

    protected int getRequestTimeout() {
        return pubnub.getConfiguration().getNonSubscribeRequestTimeout();
    }

    @Override
    protected PNOperationType getOperationType() {
        return null; // PNOperationType.PNPushNotificationModifiedChannelsOperations;
    }

}
