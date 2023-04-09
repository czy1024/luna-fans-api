# luna-fans-api

luna-fans-api-fans

<!-- PROJECT SHIELDS -->

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LOGO -->
<br />

<p align="center">
  <a href="https://github.com/lunasaw/luna-fans-api/">
    <img src="https://i.loli.net/2020/07/28/5MzIVArBZyp8NgX.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Api开放平台工具</h3>
  <p align="center">
    Api开放平台工具
    <br />
    <a href="https://github.com/lunasaw/luna-fans-api"><strong>探索本项目的文档 »</strong></a>
    <br />
    <br />
    <a href="">查看Demo</a>
    ·
    <a href="">报告Bug</a>
    ·
    <a href="https://github.com/lunasaw/luna-fans-api/issues">提出新特性</a>
  </p>

</p>

## 日志

2023.04.09 升级SpringBoot 2.7.10, 精简maven，升级到3.1.2
2022.10.03 增加阿里Oss平台，升级SpringBoot 2.7.0

### 2022-05-25

增加Smms图床

增加百度Api开放平台

增加腾讯Api开放平台

增加阿里Api开放平台

## 目录

- [安装步骤](#安装步骤)
- [文件目录说明](#文件目录说明)
- [部署](#部署)

###### **安装步骤**

引入项目依赖

```xml

<dependency>
    <groupId>io.github.lunasaw</groupId>
    <artifactId>luna-fans-api-fans</artifactId>
    <version>${last.version}</version>
</dependency>
```

在配置文件application.properties加入可选配置

| items          | items-src                        | items Guide                                        |
| -------------- | -------------------------------- | -------------------------------------------------- |
| ali            | [ali](./ali-spring-boot-starter) | ali-spring-boot-starter                                  |
| api            | [api](./api-spring-boot-starter) | api-spring-boot-starter                      |
| badiu          | [baidu](./baidu-spring-boot-starter) |baidu-spring-boot-starter                           |
| tencent        | [tencent](./tencent-spring-boot-starter) | tencent-spring-boot-starter

引用示例

```java
若采用SpringBoot构建项目可通过将第三方包中的 通过Spring配置文件注入Spring管理
```

[结果即刻得到配置数据,进而调用api里的静态方法完成调用]()

### 部署

暂无


<!-- links -->

[your-project-path]:lunasaw/luna-fans-api

[contributors-shield]: https://img.shields.io/github/contributors/lunasaw/luna-fans-api.svg?style=flat-square

[contributors-url]: https://github.com/lunasaw/luna-fans-api/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/lunasaw/luna-fans-api.svg?style=flat-square

[forks-url]: https://github.com/lunasaw/luna-fans-api/network/members

[stars-shield]: https://img.shields.io/github/stars/lunasaw/luna-fans-api.svg?style=flat-square

[stars-url]: https://github.com/lunasaw/luna-fans-api/stargazers

[issues-shield]: https://img.shields.io/github/issues/lunasaw/luna-fans-api.svg?style=flat-square

[issues-url]: https://img.shields.io/github/issues/lunasaw/luna-fans-api.svg

[license-shield]: https://img.shields.io/github/license/lunasaw/luna-fans-api.svg?style=flat-square

[license-url]: https://github.com/lunasaw/luna-fans-api/blob/master/LICENSE.txt

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555

[linkedin-url]: https://linkedin.com/in/luna-fans-api




