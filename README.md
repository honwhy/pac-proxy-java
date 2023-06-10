# pac-proxy-java

Implementation of http proxy with configuration of pac, in order to forward http request to next proxy server determined by pac, build on LittleProxy and proxy-vole project.

# example
## star launcher of LittleProxy 
```
$ git clone git://github.com/adamfisk/LittleProxy.git
```
run **org.littleshoot.proxy.Launcher**, default port is 8080
- try one level of proxy
```bash
curl -x 127.0.0.1:8080 http://linuxtect.com
```

## start pac-proxy-java
```
$ git clone git://github.com/honwhy/pac-proxy-java.git
```
run **com.thh3.App**, default port is 1080
- try two level of proxy
```bash
curl -x 127.0.0.1:1080 http://linuxtect.com
```
- explain,
```md
curl -> http proxy(1080) -> http proxy(8080) -> linuxtect.com
```


Acknowledgments
---------------

[proxy-vole](https://github.com/MarkusBernhardt/proxy-vole)

[LittleProxy](https://github.com/adamfisk/LittleProxy)
