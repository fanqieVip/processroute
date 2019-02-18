package com.plug.common.plugservice;

import com.fanjun.processroute.annotation.ProcessId;
import com.fanjun.processroute.annotation.RemoteServiceImpl;
import com.fanjun.processroute.callback.CallbackProcessor;

/**
 * Plug1的远程协议类
 * 参数禁止使用基本类型，可用包装类代替，防止反射无法找不到该方法
 */
@ProcessId("com.plug.plug1")
@RemoteServiceImpl("com.plug.plug1.application.PlugRemoteService")
public interface RemoteService {
    /**
     * 登录
     * @param username 姓名
     * @param password 密码
     * @param callbackProcessor @String 回调返回token
     */
    void login(CallbackProcessor<String> callbackProcessor, String username, String password);
}
