# framework
自定义rpc框架
实现像调本地方法一样调用远程服务
流程：
1 client调用代理，代理按照协议将请求数据序列化成字节流，通过网络发送到server
2 server接收到数据后，反序列化数据，得到请求信息，proxy根据此信息调用响应的业务逻辑，执行完再将返回值返回给client

结构：
codec：编解码器
compress：压缩处理器
protocol：协议
serialization: 序列化与反序列化工具
