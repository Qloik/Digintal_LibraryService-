<template>
  <!-- 头部区域 -->
  <el-container class="home-container">
    <!-- 页面主体区域 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="iscollapse ? '64px' : '200px'">
        <!-- 侧边栏菜单区域 -->
        <!-- <el-menu background-color="#333744" text-color="#fff" active-text-color="#409eff" router="true">
          <el-menu-item index="/home">主页</el-menu-item>
          <el-menu-item index="/help">使用帮助</el-menu-item>
          <p style="color:grey;font-size: 15px;text-align: center;">资源列表</p>
          <el-menu-item index="/single">单人研习室</el-menu-item>
          <el-menu-item index="/double">双人研习室</el-menu-item>
          <el-menu-item index="/multi">多人研习室</el-menu-item>

          <el-menu-item index="/book">图书详情页面演示</el-menu-item>
        </el-menu> -->
        <!-- <el-aside :width="iscollapse ? '64px' : '200px'"> -->
        <!-- 侧边栏菜单 -->
        <div class="toggle-button" @click="toggleCollapse">|||</div>

        <el-menu
          background-color="rgb(59,95,113)"
          text-color="#fff"
          active-text-color="#ffd04b"
          :router="true"
          :collapse="iscollapse"
          :collapse-transition="false"
        >         
          <el-menu-item index="/home">
            <el-icon><Management /></el-icon>
            <template v-slot:title> <span>主页</span></template>
          </el-menu-item>
          <el-menu-item index="/help">
            <el-icon><Opportunity /></el-icon>
            <template v-slot:title> <span>使用帮助</span></template>
          </el-menu-item>
            <el-menu-item index="/single">
            <el-icon><UserFilled /></el-icon>
            <template v-slot:title> <span>个人研习室</span></template>
          </el-menu-item>
            <el-menu-item index="/double">
        <el-icon><HelpFilled /></el-icon>
            <template v-slot:title> <span>双人研习室</span></template>
          </el-menu-item>
            <el-menu-item index="/multi">
            <el-icon><Share /></el-icon>
            <template v-slot:title> <span>多人研习室</span></template>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <!-- </el-aside> -->
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
  name: "Home",
  setup() {},
  data() {
    return {
      activeIndex: "1",
      activeIndex2: "1",
      // 是否折叠
      iscollapse: true,
    }
  },
  methods: {
    toggleCollapse() {
      this.iscollapse = !this.iscollapse;
    },
  },
};
</script>

<style lang="less" scoped>
.home-container {
  height: 700px;
}

.el-header {
  // background-color: #55626b;
  background-color: #004e90;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #fff;
  font-size: 20px;

  > div {
    display: flex;
    align-items: center;

    span {
      margin-left: 15px;
    }
  }

  .reshape_img {
    height: 40px;
    // width: 20px;
  }
}

.el-aside {
  background-color:rgb(59,96,113);
}

.el-main {
  background-color: #e5e6e8;
  padding-top: 0%;
  padding-left: 3%;
}
.navi {
  width: 500px;
  padding-right: 10%;
}
.toggle-button {
  color: white;
  text-align: center;
  line-height: 34px;
  letter-spacing: 0.2em;
  cursor: pointer;
}
a {
  text-decoration: none;
  color: #fff;
  font-size: 15px;
}
</style>
