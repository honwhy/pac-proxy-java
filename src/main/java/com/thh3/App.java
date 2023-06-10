package com.thh3;

import com.github.markusbernhardt.proxy.selector.pac.PacProxySelector;
import com.github.markusbernhardt.proxy.util.ProxyUtil;
import io.netty.handler.codec.http.HttpRequest;
import org.littleshoot.proxy.ChainedProxy;
import org.littleshoot.proxy.ChainedProxyManager;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Queue;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        HttpProxyServer server =
                DefaultHttpProxyServer.bootstrap()
                        .withPort(1080)
                        .withChainProxyManager(new ChainedProxyManager() {
                            @Override
                            public void lookupChainedProxies(HttpRequest httpRequest, Queue<ChainedProxy> queue) {
                                URL resource = this.getClass().getClassLoader().getResource("file.pac");
                                String file = resource.getFile();
                                PacProxySelector pacProxySelector = ProxyUtil.buildPacSelectorForUrl("file://" + file);
//                                UrlPacScriptSource pacScriptSource = new UrlPacScriptSource(file);
//                                PacProxySelector pacProxySelector = new PacProxySelector(pacScriptSource);
                                String uri = httpRequest.getUri();
                                try {
                                    URI uri1 = new URI(uri);
                                    List<Proxy> selected = pacProxySelector.select(uri1);
                                    UpstreamProxy.PROXY_HOLDER.set(selected);
                                } catch (URISyntaxException e) {
                                    //
                                }
                                queue.add(new UpstreamProxy());
                            }
                        })
                        .start();
    }
}
