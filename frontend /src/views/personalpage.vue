<template>

  <el-container class="selfPage_container">
    <!--头部区域-->
    
      <!--侧边栏-->
      <el-aside :width="iscollapse ? '64px' : '200px'">
        <div class="toggle-button" @click="toggleCollapse">|||</div>
        <el-menu background-color="rgb(59,95,113)" text-color="#fff" active-text-color="#ffd04b" :router="true"
          :collapse="iscollapse" :collapse-transition="false">
          <el-menu-item index="myInf">
            <el-icon>
              <Management />
            </el-icon>
            <template v-slot:title> <span>我的信息</span></template>
          </el-menu-item>
          <el-menu-item index="post">
            <el-icon><EditPen /></el-icon>
            <template v-slot:title> <span>我的发布</span></template>
          </el-menu-item>
           <el-menu-item index="review">
            <el-icon>
              <Opportunity />
            </el-icon>
            <template v-slot:title> <span>我的评论</span></template>
          </el-menu-item>
          <el-menu-item index="myBorrow">
            <el-icon>
              <UserFilled />
            </el-icon>
            <template v-slot:title> <span>我的借阅</span></template>
          </el-menu-item>
          <el-menu-item index="myCheck">
            <el-icon>
              <Clock />
            </el-icon>
            <template v-slot:title> <span>我的预留</span></template>
          </el-menu-item>
          <el-menu-item index="/payFor">
            <el-icon>
              <HelpFilled />
            </el-icon>
            <template v-slot:title> <span>我的赔书</span></template>
          </el-menu-item>

          <el-menu-item index="/placeAppointed">
            <el-icon>
              <Odometer />
            </el-icon>
            <template v-slot:title> <span>场地预约</span></template>
          </el-menu-item>

        </el-menu>
      </el-aside>
      <!--右侧内容主体-->
      <el-main>
        <!--路由占位符-->
        <router-view></router-view>
      </el-main>
    </el-container>
</template>

<script>
export default {
  name: "selfInfo",
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
.selfPage_container {
  height: 100%;
  //   position: absolute;
  //   top: 0px;
  //   left: 0px;
  //   width: 100%;
}

.el-aside {
  background-color: rgb(59, 95, 113);
}

.toggle-button {
  color: white;
  text-align: center;
  line-height: 34px;
  letter-spacing: 0.2em;
  cursor: pointer;
}

.el-header {
  // background-color: #55626b;
  background-color: #004E90;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: #fff;
  font-size: 20px;

  >div {
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

.el-main {
  height: 100%;
  background-color: #eaedf1;
}

.navi {
  width: 500px;
  padding-right: 10%;
}

a {
  text-decoration: none;
  color: #fff;
  font-size: 15px;
}
</style>
