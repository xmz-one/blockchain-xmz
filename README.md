# blockchain-xmz
一. 介绍：
blockChain-XMZ 是一个全球区块链开源项目，是一个高度可定制的模块化区块链基础设施。它由微内核和功能模块组成。
blockChain-XMZ 后期提供智能合约,多链机制和跨链共识。
旨在打破区块链技术壁垒，降低开发成本，促进区块链技术在商业领域的应用。
项目特色：
1.模块化设计
2.平行链--待开发
3.智能合约--待开发
4.POS共识机制
二. 入门
要求
1.操作系统
blockChain-XMZ 基于Java最新的spring-boot架构，您可以选择自己喜欢的操作系统。
2.依赖
JDK：JDK 1.8
Maven：Maven 3.3+
IDE：任何你喜欢的
3.技术架构
1.spring-boot 2.5.0
2.RocksDB 6.8.1
3.httpclient 4.5.3
4.kryo 4.0.1
5.guava 20.0
6.bcprov-jdk15on 1.59
三. 技术讲解
1.SpringBoot基于Spring4.0设计，不仅继承了Spring框架原有的优秀特性，而且还通过简化配置来进一步简化了Spring应用的整个搭建和开发过程。另外SpringBoot通过集成大量的框架使得依赖包的版本冲突，以及引用的不稳定性等问题得到了很好的解决。
2.RocksDB 是一个来自 facebook 的可嵌入式的支持持久化的 key-value 存储系统，也可作为 C/S 模式下的存储数据库，但主要目的还是嵌入式。RocksDB 基于 LevelDB 构建。
3.HttpClient 是Apache Jakarta Common 下的子项目，可以用来提供高效的、最新的、功能丰富的支持 HTTP 协议的客户端编程工具包
4.kryo是一个高性能的序列化/反序列化工具，由于其变长存储特性并使用了字节码生成机制，拥有较高的运行速度和较小的体积。
5.Guava是一种基于开源的Java库，Google Guava源于2007年的"Google Collections Library"。这个库是为了方便编码，并减少编码错误。这个库用于提供集合，缓存，支持原语句，并发性，常见注解，字符串处理，I/O和验证的实用方法。
6.,bcprov-jdk15on是,SM2加密必要加密工具包