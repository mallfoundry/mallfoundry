# Mallfoundry

Mallfoundry 是一个完全开源的使用 Spring Boot 开发的多商户电商平台。它可以嵌入到已有的 Java 程序中，或者作为服务器、集群、云中的服务运行。

## 领域模型（Interface）

领域模型采用接口化设计，将模型（Model）设计成接口（Interface）。

```java
public interface Product extends ProductBody, StoreOwnership {

    ProductId toId();

    String getName();

    void setName(String name);

    ProductType getType();

    void setType(ProductType type);

    // ...
}
```

将领域模型设计成接口有以下优点：

* 模型设计与编码分离。
* 调用者使用 API 编程。
* 与运行环境解耦，可以运行在独立进程、微服务之上。
* 与持久技术解耦，业务代码与持久化的技术代码横向切分。

## 业务模块（Package）

![Modules](https://gitee.com/mallfoundry/mallfoundry/raw/master/docs/images/modules.png)

Mallfoundry 整体由共享内核、支撑域、业务域和核心域四个部分组成。

* Commons 模块：公用组件（Commons）、共享内核（Shared）组成。
* Keystone 模块：身份（Identity）、安全（Security）组成。
* Finance 模块：Payment（支付）、提现（Withdrawal）、充值（Recharge）、转账（Transfer）等组成。
* Catalog 模块：商品（Product）、商品类目（Category）、商品品牌（Brand）、商品集合（Collection）等组成。
* Trade 模块：购物车（Cart）、结算台（Checkout）、订单（Order）等组成。
* Store 模块：商家（Store）、行业（Industry）、店铺员工（Staff）、店铺角色（Role）、店铺会员（Member）等组成。
* Customer 模块：顾客（Customer）、收货地址（Address）、顾客积分（Point）、搜索词条（SearchTerm）等组成。
* Marketing 模块：优惠券（Coupon）、广告（Banner）等组成。
* Analytics 模块：数据仓库（EDW）、报表（Report）组成。
* Shipping 模块：物流信息（Track）、运费模板（Rate）等组成。
* Storage 模块：提供对象存储（OSS），集成 Aliyun OSS、Qiniu OSS、FTP 等存储服务。
* Test 模块：提供一个总体测试环境。

## 环境要求（Requirements）

* JDK11+ — 我们使用 JDK11 或以上版本。
* Spring Boot — 我们使用 Spring Boot 作为后端的服务。
* Spring Data JPA — 我们使用 JPA 作为主要的持久化框架。
* PostgreSQL — 默认使用 PostgreSQL 作为数据源，也可以使用 MySQL 作为数据源。

## 开发（Development）

使用 Git 克隆到本地进行开发。

```
$ git clone https://gitee.com/mallfoundry/mallfoundry.git
$ cd mallfoundry
$ mvn compile
```

项目的默认启动入口在 `spring-boot` 项目中：

```
$ cd components/launch-modules/spring-boot
$ cd src/main/java/org/mallfoundry
$ cat StandaloneSpringBootApplication.java
```

## REST API Docs

我们提供了一个部分 REST API 接口的文档：

[http://mallfoundry.gitee.io/mallfoundry-api-reference](http://mallfoundry.gitee.io/mallfoundry-api-reference)

## 前端界面（UI）

整个 Mallfoundry 采用前后端分离的架构，后端服务不包含前端界面，需要另行编译安装前端。

* 商家后台：[https://gitee.com/mallfoundry/mallfoundry-store](https://gitee.com/mallfoundry/mallfoundry-store)
* 前台 H5：[https://gitee.com/mallfoundry/mall-h5-vue](https://gitee.com/mallfoundry/mall-h5-vue)

### 商家后台

![store-list](https://gitee.com/mallfoundry/mallfoundry/raw/master/docs/screenshots/store-list.png)

![store-dashboard](https://gitee.com/mallfoundry/mallfoundry/raw/master/docs/screenshots/store-dashboard.png)

![store-product-list](https://gitee.com/mallfoundry/mallfoundry/raw/master/docs/screenshots/store-product-list.png)

![store-product](https://gitee.com/mallfoundry/mallfoundry/raw/master/docs/screenshots/store-product.png)

![store-order-list](https://gitee.com/mallfoundry/mallfoundry/raw/master/docs/screenshots/store-order-list.png)

![store-order](https://gitee.com/mallfoundry/mallfoundry/raw/master/docs/screenshots/store-order.png)

![store-coupon-list](https://gitee.com/mallfoundry/mallfoundry/raw/master/docs/screenshots/store-coupon-list.png)

![store-balance](https://gitee.com/mallfoundry/mallfoundry/raw/master/docs/screenshots/store-balance.png)

### 前台（H5）

持续更新中...

## 参与共建

获得 Mallfoundry 的最新动态，可以关注公众号「不够具体」。

![qrcode-for-mp](https://gitee.com/mallfoundry/mallfoundry/raw/master/docs/images/qrcode-for-mp.jpg)

有关领域驱动设计（DDD）的讨论，可以添加 QQ 群，和优秀的小伙伴们共同进步。

![qrcode-for-qq-group](https://gitee.com/mallfoundry/mallfoundry/raw/master/docs/images/qrcode-for-qq-group.png)

## 许可证（License）

Mallfoundry 使用 GPLv2 许可证。有关详细信息，请参阅许可文件：

<https://www.gnu.org/licenses/old-licenses/gpl-2.0.txt>
