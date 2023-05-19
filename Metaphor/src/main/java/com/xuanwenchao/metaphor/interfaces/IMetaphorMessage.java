package com.xuanwenchao.metaphor.interfaces;

/**
 * @author Xuan Wenchao
 * @Package com.xuanwenchao.metaphor.interfaces
 * @Description:
 * @date Dec 08,2022
 */
public interface IMetaphorMessage {
    /**
     * @param from the source of message
     * @param code user customize code of message
     * @param msg user customize object of message
     * @Description: receive Metaphor message callback
     * @return: void
     */
    void OnMessageReceive(Object from, int code, Object msg);
}
