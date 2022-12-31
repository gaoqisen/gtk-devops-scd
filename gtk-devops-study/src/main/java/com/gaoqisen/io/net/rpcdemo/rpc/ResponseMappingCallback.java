package com.gaoqisen.io.net.rpcdemo.rpc;

import com.gaoqisen.io.net.rpcdemo.rpc.protocol.PackageMsg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 响应映射回调
 */
public class ResponseMappingCallback {

    static ConcurrentHashMap<Long, CompletableFuture<Object>> mapping = new ConcurrentHashMap<>();

    public static void addCallBack(long requestId, CompletableFuture<Object> cb) {
        mapping.putIfAbsent(requestId, cb);
    }
    public static void runCallRack(PackageMsg msg) {
        CompletableFuture<Object> cf = mapping.get(msg.getHeader().getRequestId());
        // runnable.run();
        cf.complete(msg.getContent().getRes());
        removeCB(msg.getHeader().getRequestId());
    }

    private static void removeCB(long requestId) {
        mapping.remove(requestId);
    }

}