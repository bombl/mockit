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
    <version>0.0.5</version>
</dependency>
```

## Quick Start
1. Add the mockit-spring-boot-starter Maven dependency to your project.
2. Add the following configuration to your project:
- mockit.plugin.enabled=true: Enable Mockit (true to enable, false to disable).
- mockit.plugin.alias=mockit-example: The project name registered in the console.
- mockit.plugin.addresses=127.0.0.1:8889: Console IP address and port.
3. Start the console (mockit-admin project) for mocking.

- **Running Report：** By running the report, you can intuitively see the status of service mocking.
  ![](https://github.com/bombl/ImageHost/blob/main/report.jpg?raw=true)
  &nbsp;
- **Service Management：** Through service management, you can mock or unmock services.
  ![](https://github.com/bombl/ImageHost/blob/main/alias.jpg?raw=true)
  &nbsp;
- **Service Class Management：** Through service class management, you can perform operations such as adding, modifying, deleting, enabling, and disabling service classes.
  ![](https://github.com/bombl/ImageHost/blob/main/class.jpg?raw=true)
  &nbsp;
- **Method Management：** Through method management, you can perform operations such as adding, modifying, deleting, enabling, and disabling methods.
  ![](https://github.com/bombl/ImageHost/blob/main/method.jpg?raw=true)
  &nbsp;
- **Data Management：** Through data management, you can perform operations such as adding, modifying, deleting, enabling, and disabling data.
  ![](https://github.com/bombl/ImageHost/blob/main/data.jpg?raw=true)
  &nbsp;
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
- Automated Mock Data Generation: Automatically generate mock data based on the return data type.
- 无侵入性：基于Java探针的mock框架提供了一种方便、灵活且强大的方式来模拟和测试代码，帮助开发人员构建可靠和高质量的应用程序；
- 持久化：Mock数据持久化；
- 统一管理：多项目Mock数据统一管理；
- 自动生成Mock数据：根据返回数据类型自动生成Mock数据。

## Contributing
Contributions are welcome! Open a pull request to fix a bug, or open an [Issue](https://github.com/bombl/mockit/issues/) to discuss a new feature or change.

欢迎参与项目贡献！比如提交PR修复一个bug，或者新建 [Issue](https://github.com/bombl/mockit/issues/) 讨论新特性或者变更。


## Copyright and License
This product is open source and free, and will continue to provide free community technical support. Individual or enterprise users are free to access and use.

- Licensed under the GNU General Public License (GPL) v3.
- Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>.

产品开源免费，并且将持续提供免费的社区技术支持。个人或企业内部可自由的接入和使用。如有需要可邮件联系作者免费获取项目授权。