package com.plug.common.plugservice;

import com.fanjun.processroute.annotation.ProcessId;
import com.fanjun.processroute.annotation.RemoteServiceImpl;
import com.fanjun.processroute.callback.CallbackProcessor;

@ProcessId("com.plug.plug2")
@RemoteServiceImpl("com.plug.plug2.application.RemoteService2Impl")
public interface RemoteService2 {
    /**
     * 注册
     * @param tel 手机号
     * @param flag 类型
     * @param callbackProcessor @String 回调返回token
     */
    void regist(CallbackProcessor<String> callbackProcessor, String tel, Integer flag);
}
