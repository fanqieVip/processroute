package com.plug.plug1.application;

import com.fanjun.messagecenter.MessageCenter;
import com.fanjun.messagecenter.Msg;
import com.fanjun.processroute.annotation.Processor;
import com.fanjun.processroute.callback.CallbackProcessor;
import com.plug.common.plugservice.RemoteService;
import com.plug.plug1.processors.LoginProcessor;
import com.plug.plug1.processors.RegistProcessor;

public class PlugRemoteService implements RemoteService {

    @Override
    @Processor({LoginProcessor.class, RegistProcessor.class})
    public void login(CallbackProcessor<String> callbackProcessor, String username, String password) {
        System.out.print("username=>"+ username);
        System.out.print("password=>"+ password);
        MessageCenter.sendMessage(Msg.ini("nihao", username+password));
        callbackProcessor.callback("我好喜欢你啊");
    }
}
