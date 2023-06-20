<p align="center" >
    <h3 align="center">Mockit</h3>
    <p align="center">
        Mockit, an Non-invasive mock framework.
        <br>
        一种非侵入性的Mock框架。
        <br>
        <a href="https://github.com/bombl/mockit/"><strong>-- Home Page --</strong></a>
        <br>
        <br>
    </p>
</p>

## Maven dependency
```xml
<dependency>
    <groupId>cn.thinkinginjava</groupId>
    <artifactId>mockit-spring-boot-starter</artifactId>
    <version>${lastest.version}</version>
</dependency>
```

## Quick Start
1. Add `mockit.plugin.enabled=true` to your project.</br>
   将配置：`mockit.plugin.enabled=true`添加到你的项目中。</br></br>
   
2. Mock the return value of a method by requesting the `http://IP:PORT/mock` path.</br>
   请求`http://IP:PORT/mock`路径对方法返回值进行Mock。

```
curl --location --request POST 'http://localhost:8080/mock' \
--header 'Content-Type: application/json' \
--data-raw '{
    "className":"cn.thinkinginjava.mockit.example.service.MockTestService",
    "methodName":"say",
    "mockValue":"{\"code\":\"111\",\"result\":\"aaa\"}"
}'
```

## Module Relationship Diagram
![](https://github.com/bombl/ImageHost/blob/main/Mockit.png?raw=true)

- mockit-core :Core dependency of the framework
- mockit-example :Example project
- mockit-spring-boot-starter :Integration entry point
- mockit-client :Communicates with mockit-admin
- mockit-admin :Management console

## Features
- Non-intrusive: Java probe-based mock frameworks offer a convenient, flexible, and powerful way to simulate and test code, helping developers build reliable and high-quality applications;
- 无侵入性：基于Java探针的mock框架提供了一种方便、灵活且强大的方式来模拟和测试代码，帮助开发人员构建可靠和高质量的应用程序；
- Persistence: Mock data persistence and support for canceling mocks (not yet implemented)；
- 持久化：Mock数据持久化，并支持取消Mock(暂未实现)；
- Unified Management: Unified management of mock data for multiple projects (not yet implemented)；
- 统一管理：多项目Mock数据统一管理(暂未实现)；

## Contributing
Contributions are welcome! Open a pull request to fix a bug, or open an [Issue](https://github.com/bombl/mockit/issues/) to discuss a new feature or change.

欢迎参与项目贡献！比如提交PR修复一个bug，或者新建 [Issue](https://github.com/bombl/mockit/issues/) 讨论新特性或者变更。


## Copyright and License
This product is open source and free, and will continue to provide free community technical support. Individual or enterprise users are free to access and use.

- Licensed under the GNU General Public License (GPL) v3.
- Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>.

产品开源免费，并且将持续提供免费的社区技术支持。个人或企业内部可自由的接入和使用。如有需要可邮件联系作者免费获取项目授权。