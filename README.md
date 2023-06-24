<p align="center" >
    <h3 align="center">Mockit</h3>
    <p align="center">
        Mockit, an Non-invasive mock framework.
        <br>
        一个非侵入性的Mock框架。
        <br>
        <a href="https://github.com/bombl/mockit/"><strong>-- Home Page --</strong></a>
        <br>
        <br>
    </p>
</p>

## Introduction
Mockit is a non-invasive mocking framework that aims to provide a convenient, flexible, and powerful way to simulate and test code. Its core design objective is to assist developers in building reliable and high-quality applications.

Mockit是一个非侵入性的Mock框架，其核心设计目标是提供了一种方便、灵活且强大的方式来模拟和测试代码，帮助开发人员构建可靠和高质量的应用程序。

## Maven Dependency
```xml
<dependency>
    <groupId>cn.thinkinginjava</groupId>
    <artifactId>mockit-spring-boot-starter</artifactId>
    <version>${lastest.version}</version>
</dependency>
```

## Quick Start
1. Add the mockit-spring-boot-starter Maven dependency to your project.
2. Add the following configuration to your project:
- mockit.plugin.enabled=true: Enable Mockit (true to enable, false to disable).
- mockit.plugin.alias=mockit-example: The project name registered in the console.
- mockit.plugin.addresses=10.37.129.2:8889: Console IP address and port.
3. Start the console (mockit-admin project) for mocking. The console currently does not support page operations (under development). You can perform mocking through the API. The following API is provided:
* **Get all methods in a class** ：http://localhost:9999/mockit-admin/api/methodList
```
## Request Information：
curl --location --request POST 'http://localhost:9999/mockit-admin/api/methodList' \
--header 'Content-Type: application/json' \
--data-raw '{
    "alias":"mockit-example",
    "className":"cn.thinkinginjava.mockit.example.service.MockTestService"
}'
```


```
## Response Information：
{
    "code": 200,
    "message": null,
    "data": [
        {
            "accessModifier": "public",
            "returnType": "cn.thinkinginjava.mockit.example.model.ResultDTO",
            "methodName": "say",
            "parameters": [
                "java.lang.String"
            ]
        },
        {
            "accessModifier": "public",
            "returnType": "cn.thinkinginjava.mockit.example.model.ResultDTO",
            "methodName": "say2",
            "parameters": [
                "java.lang.String"
            ],
            "methodContent": "AbA="
        }
    ]
}
```
* **Mock a method** ：http://localhost:9999/mockit-admin/api/mock
```
## Request Information：
curl --location --request POST 'http://localhost:9999/mockit-admin/api/mock' \
--header 'Content-Type: application/json' \
--data-raw '{
    "alias": "mockit-example",
    "className": "cn.thinkinginjava.mockit.example.service.MockTestService",
    "methodMockDataList": [
        {
            "methodName": "say",
            "parameters": [
                "java.lang.String"
            ],
            "mockValue": "{\"code\":\"111\",\"result\":\"aaa\"}"
        },
        {
            "methodName": "say2",
            "parameters": [
                "java.lang.String"
            ],
            "mockValue": "{\"code\":\"444\",\"result\":\"aaa\"}"
        }
    ]
}'
```

```
## Response Information：
{
    "code": 200,
    "message": null,
    "data": null
}
```
* **Cancel Mock**  ：http://localhost:9999/mockit-admin/api/cancelMock
```
## Request Information：
curl --location --request POST 'http://localhost:9999/mockit-admin/api/cancelMock' \
--header 'Content-Type: application/json' \
--data-raw '{
    "alias": "mockit-example",
    "className": "cn.thinkinginjava.mockit.example.service.MockTestService"
}'
```

```
## Response Information：
{
    "code": 200,
    "message": null,
    "data": null
}
```

1. 将`mockit-spring-boot-starter`maven依赖添加到你的项目中;
2. 将下面的配置添加到你的项目中：
- mockit.plugin.enabled=true : 是否启用Mockit（ture.启用｜false.不启用）
- mockit.plugin.alias=mockit-example : 注册到控制台的项目名称
- mockit.plugin.addresses=10.37.129.2:8889 : 控制台IP、端口

3. 启动控制台（mockit-admin项目）进行mock，控制台暂时不支持页面操作（开发中...），可以通过接口进行mock，提供接口如下：
* **获取类中所有方法**  ：http://localhost:9999/mockit-admin/api/methodList
```
## 请求信息：
curl --location --request POST 'http://localhost:9999/mockit-admin/api/methodList' \
--header 'Content-Type: application/json' \
--data-raw '{
    "alias":"mockit-example",
    "className":"cn.thinkinginjava.mockit.example.service.MockTestService"
}'
```


```
## 响应信息：
{
    "code": 200,
    "message": null,
    "data": [
        {
            "accessModifier": "public",
            "returnType": "cn.thinkinginjava.mockit.example.model.ResultDTO",
            "methodName": "say",
            "parameters": [
                "java.lang.String"
            ]
        },
        {
            "accessModifier": "public",
            "returnType": "cn.thinkinginjava.mockit.example.model.ResultDTO",
            "methodName": "say2",
            "parameters": [
                "java.lang.String"
            ]
        }
    ]
}
```
* **对方法进行Mock**  ：http://localhost:9999/mockit-admin/api/mock
```
## 请求信息：
curl --location --request POST 'http://localhost:9999/mockit-admin/api/mock' \
--header 'Content-Type: application/json' \
--data-raw '{
    "alias": "mockit-example",
    "className": "cn.thinkinginjava.mockit.example.service.MockTestService",
    "methodMockDataList": [
        {
            "methodName": "say",
            "parameters": [
                "java.lang.String"
            ],
            "mockValue": "{\"code\":\"111\",\"result\":\"aaa\"}"
        },
        {
            "methodName": "say2",
            "parameters": [
                "java.lang.String"
            ],
            "mockValue": "{\"code\":\"444\",\"result\":\"aaa\"}"
        }
    ]
}'
```

```
## 响应信息：
{
    "code": 200,
    "message": null,
    "data": null
}
```
* **取消Mock**  ：http://localhost:9999/mockit-admin/api/cancelMock
```
## 请求信息：
curl --location --request POST 'http://localhost:9999/mockit-admin/api/cancelMock' \
--header 'Content-Type: application/json' \
--data-raw '{
    "alias": "mockit-example",
    "className": "cn.thinkinginjava.mockit.example.service.MockTestService"
}'
```

```
## 响应信息：
{
    "code": 200,
    "message": null,
    "data": null
}
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
- Persistence: Mock data persistence and support for canceling mocks (not yet implemented)；
- Unified Management: Unified management of mock data for multiple projects (not yet implemented)；
- 无侵入性：基于Java探针的mock框架提供了一种方便、灵活且强大的方式来模拟和测试代码，帮助开发人员构建可靠和高质量的应用程序；
- 持久化：Mock数据持久化(暂未实现)；
- 统一管理：多项目Mock数据统一管理(暂未实现)；

## Contributing
Contributions are welcome! Open a pull request to fix a bug, or open an [Issue](https://github.com/bombl/mockit/issues/) to discuss a new feature or change.

欢迎参与项目贡献！比如提交PR修复一个bug，或者新建 [Issue](https://github.com/bombl/mockit/issues/) 讨论新特性或者变更。


## Copyright and License
This product is open source and free, and will continue to provide free community technical support. Individual or enterprise users are free to access and use.

- Licensed under the GNU General Public License (GPL) v3.
- Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>.

产品开源免费，并且将持续提供免费的社区技术支持。个人或企业内部可自由的接入和使用。如有需要可邮件联系作者免费获取项目授权。