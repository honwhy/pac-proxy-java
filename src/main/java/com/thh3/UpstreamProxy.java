package com.thh3;

import org.littleshoot.proxy.ChainedProxyAdapter;
import org.littleshoot.proxy.TransportProtocol;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UpstreamProxy extends ChainedProxyAdapter {
    static ThreadLocal<List<Proxy>> PROXY_HOLDER = ThreadLocal.withInitial(() -> {
        List<Proxy> proxyList = new ArrayList<>();
        proxyList.add(Proxy.NO_PROXY);
        return proxyList;
    });
    public UpstreamProxy() {
    }
    @Override
    public InetSocketAddress getChainedProxyAddress() {
        System.out.println("getChainedProxyAddress");
        List<Proxy> proxyList = PROXY_HOLDER.get();
        Proxy proxy = proxyList.get(0);
        if (proxy.equals(Proxy.NO_PROXY)) {
            return null;
        }
        //return new InetSocketAddress("127.0.01", 8080);
        String sa = proxy.toString();
        String[] split = sa.split("@");
        String[] hostPort = split[1].split(":");
        return new InetSocketAddress(hostPort[0].trim(), Integer.parseInt(hostPort[1]));
    }



    @Override
    public TransportProtocol getTransportProtocol() {
        return TransportProtocol.TCP;
    }

    @Override
    public void connectionFailed(Throwable cause) {
        PROXY_HOLDER.set(Collections.singletonList(Proxy.NO_PROXY));
    }
    @Override
    public void disconnected() {
        PROXY_HOLDER.set(Collections.singletonList(Proxy.NO_PROXY));
    }
}
