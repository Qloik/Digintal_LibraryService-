<template>
  <!-- 头部区域 -->
  <el-container class="home-container">
    <!-- 页面主体区域 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="iscollapse ? '64px' : '200px'">
        <!-- 侧边栏菜单 -->
        <div class="toggle-button" @click="toggleCollapse">|||</div>

        <el-menu background-color="rgb(59,95,113)" text-color="#fff" active-text-color="#ffd04b" :router="true"
          :collapse="iscollapse" :collapse-transition="false">
          <el-menu-item index="/BookManage">
            <el-icon>
              <Management />
            </el-icon>
            <template v-slot:title> <span>图书管理</span></template>
          </el-menu-item>
          <el-menu-item index="/NoticeManage">
            <el-icon>
              <BellFilled />
            </el-icon>
            <template v-slot:title> <span>通知公告管理</span></template>
          </el-menu-item>
          <el-menu-item index="/PostManage">
            <el-icon>
              <Opportunity />
            </el-icon>
            <template v-slot:title> <span>帖子审核管理</span></template>
          </el-menu-item>
          <el-menu-item index="/ReviewManage">
            <el-icon>
              <UserFilled />
            </el-icon>
            <template v-slot:title> <span>评论审核管理</span></template>
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
  setup() { },
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
  height: 100%;
}

.el-aside {
  background-color: rgb(59, 96, 113);
}

.el-main {
  background-color: #e5e6e8;
  padding-top: 0%;
  padding-left: 3%;
  padding-right: 3%;
  padding-bottom: 3%;
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
