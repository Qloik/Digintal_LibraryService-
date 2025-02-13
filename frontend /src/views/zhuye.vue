<template>
  <!-- 头部区域 -->
  <el-container class="home-container">
    <el-backtop :visibility-height="200" :right="60" :bottom="60">
      <div>
        <el-icon>
          <ArrowUpBold />
        </el-icon>
      </div>
    </el-backtop>
    <!-- head -->
    <el-header class="bg1">
      <div>
        <img src="" alt="" />
        <h2 style="font-family: KaiTi">

          <!-- <el-icon :size="size" :color="color">
            <Edit />
          </el-icon> -->
          <img src="../assets/TongjiLogo.png" alt="" class="reshape_img">
        </h2>
        axios
      </div>
      <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect"
        background-color="rgba(0,0,0,0)" text-color="#fff" active-text-color="#fff" router="true">

        <el-menu-item index="/HomePage">图书馆首页</el-menu-item>
        <el-menu-item index="/forum">论坛中心</el-menu-item>
        <el-menu-item index="/home">场地预约</el-menu-item>
        <el-menu-item index="/Siping">馆藏分布</el-menu-item>
      </el-menu>
      <el-dropdown>
        <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="selfInfo">个人主页</el-dropdown-item>
            <el-dropdown-item @click="logout">退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-header>

    <!-- 页面主体区域 -->
    <el-container>
      <!-- 侧边栏 -->

      <!-- 右侧内容主体 -->
      <el-main>
        <!-- 路由占位 -->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "zhuye",
  setup() { },
  methods: {
    selfInfo() {
      this.$router.push('/personalpage')
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    // 清除会话存储，返回登录页面
    logout() {
      window.sessionStorage.clear()
      window.localStorage.clear()
      this.$router.push('/login')
      ElMessage({
        message: '退出成功！',
        type: 'success',
      })
    }
  }
};

</script>

<style lang="less"  scoped>
.home-container {
  height: 100%;
}

.bg1 {
  background-image: url(../assets/bg7.jpg);
}

.reshape_img {
  margin-top: 10px;
  height: 40px;

}

.el-menu-demo {
  width: 600px;
}

.el-header {
  background-color: #303133;

  display: flex;
  justify-content: space-between;
  align-items: center;
  color: aliceblue;
  font-size: 25px;
}

.el-aside {
  background-color: #333744;
}

.el-main {
  background-color: rgb(248, 243, 243);
  padding: 0px;
}

.el-menu {
  border: none;

}

.toggle-button {
  color: white;
  text-align: center;
  line-height: 34px;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
