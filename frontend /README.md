# JAVA图书馆-前端

## Vue相关

This project build for Vue3, Vue-router, Vuex, ViewUIPlus and Vue-cli.

### Install
```shell
npm install
```
### Run
```shell
npm run serve
```
### Build
#### Build for Production
```shell
npm run build
```
#### Build for Staging
```shell
npm run build:staging
```
### Run Eslint
#### Run without Fixing

- This operation will also run before git-commit.
```shell
npm run lint
```
Run with Fixing

```shell
npm run lint:fix
```
### .env Description

- This project exposes environment variables on `process.env` object.
- Different modes (development/staging/production) correspond to different environment files (.env.*).
- .env file is always included, duplicate variables are overwritten by the specific mode file (.env.*).



## 部分文件功能说明

**src：**源文件

- **assets：**使用的图片

- **components：**自己写的可以重用的vue组件

  - 目前没有因为全按页面来写的，之后有时间可以梳理一下把重复的部分拿出来

- **mock：**模拟数据接口

  - index.js：mock封装提供get的数据，具体使用里面有注释
  - user.js：应该是mock自带的示例还没用到

- **network：**跟网络有关的东西

  - request.js：创建axios实例，跨域指定后端路由，还有一些登录相关的还不知道怎么用

- **store：**vue自带的还没用上

- **views：**所有的页面

  - double.vue：双人研习室预约
  - help.vue：预约系统帮助
  - Home.vue：预约系统整体布局
  - multi.vue：多人研习室预约
  - single.vue：单人研习室预约
  - Welcome.vue：预约系统首页
  - HomePage.vue:图书馆主页

- router.js：路由

  

## 其他补充

- 在vue.config.js里配置好了跨域，等于现在前端访问‘http://139.196.161.12/’直接用'/api'代替就行了（例如接口是‘http://139.196.161.12/API/number’ 前端axios就用'/api/API/number')
- 拉取下来npm install 一下然后run serve应该就行了（注意要在library目录下），预约系统在域名后面加/home进入



