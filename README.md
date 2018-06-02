# cccx

## 项目介绍

基于Spring + SpringMVC + Mybatis开发的登录、注册项目，该项目前后端分离，使用restful风格，可基于此项目快速开发简单的web系统

### 功能

- 注册
- 登录
    - 找回密码
    - 重置密码

### 业务逻辑

[项目业务逻辑](/project-docs/businessLogicAnalysis.txt)

### 组织结构

```
├── cccx-common -- ssm框架公共模块
├── cccx-dao -- 代码生成模块，无需开发
├── cccx-service -- 服务的接口及实现
├── cccx-web -- 网站前台
├── cccx-admin -- 网站后台
├── project-datamodel -- 项目数据库相关文件
└── project-docs -- 项目文档
```

### 技术选型

#### 后端技术

- Spring Framework
- SpringMVC
- MyBatis
- MyBatis Generator
- Apache Shiro
- Velocity
- Swagger2
- FluentValidator
- com.github.penggle.kaptcha
- Log4J2
- Maven
- javax.mail

#### 前端技术

- Bootstrap
- AngularJS

### 模块依赖

```
cccx-web & cccx-admin -> cccx-service -> cccx-dao -> cccx-common
```

### 编程规约

后台参照`阿里巴巴Java开发手册`

## 项目运行

1. 下载项目并构建： git clone xxx; mvn clean install

2. 新建数据库: project-datamodel/cccx.sql

3. 修改数据库配置文件: cccx-service/src/main/resources/jdbc-config.properties

4. 修改邮件配置文件： cccx-common/src/main/resources/email.properties.template

5. mvn jetty run

6. 打开页面: http://localhost:9999/login.html & http://localhost:9999/swagger-ui.html

## 项目预览

### 数据模型
