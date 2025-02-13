<template>

  <el-container style="height:100%">

    <div>
      <el-menu id="webAside" text-color="#fff" active-text-color="#ffd04b" :router="true" :collapse="iscollapse"
        :collapse-transition="false">
        <el-menu-item index="/Siping">
          <el-icon>
            <Management />
          </el-icon>
          <template v-slot:title> <span>四平路校区馆藏指引</span></template>
        </el-menu-item>
        <el-menu-item index="/Jiading">
          <el-icon>
            <Management />
          </el-icon>
          <template v-slot:title> <span>嘉定校区馆藏指引</span></template>
        </el-menu-item>
        <el-menu-item index="/bookCheck">
          <el-icon>
            <Management />
          </el-icon>
          <template v-slot:title> <span>馆藏查阅</span></template>
        </el-menu-item>
      </el-menu>
    </div>

    <body id="poster">
      <el-main>
        <center>
          <h1 style="font-size: 30px; padding:3%">四平路校区图书馆馆藏分布概览</h1>
        </center>
        <table border="1" width="100%" height="100%">
          <tr align="center" valign="middle" height="30px">
            <td>楼层</td>
            <td>馆藏及区域名称</td>
            <td>相关服务</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>一楼裙楼南</td>
            <td>电子阅览室，报告厅</td>
            <td rowspan="2">可阅览，可预约座位</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>二楼裙楼南</td>
            <td>期刊阅览室</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>一楼裙楼北</td>
            <td>新书阅览室</td>
            <td>可阅览，开架可出借</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>二楼裙楼北</td>
            <td>马克思主义专题阅览室</td>
            <td>可阅览，可预约座位</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>一楼书库</td>
            <td rowspan="2">非常用书库</td>
            <td rowspan="3">可阅览</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>二楼书库</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>三楼书库</td>
            <td>科技图书阅览室</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>一楼阅览楼</td>
            <td rowspan="2">科技图书阅览室</td>
            <td rowspan="2">可阅览，可预约座位</td>
          </tr>

          <tr align="center" valign="middle" height="60px">
            <td>二楼阅览楼</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>五楼主楼南</td>
            <td>馆长室、情报分析与研究部办公室、学科与知识产权服务部</td>
            <td rowspan="2">相关咨询</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>五楼主楼北</td>
            <td>系统管理部、平台与数据服务部、信息素养教研部</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>六楼主楼南</td>
            <td>社科外文阅览室</td>
            <td rowspan="2">可预约，可阅览</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>六楼主楼北</td>
            <td>生物医学图书阅览室</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>七-九楼</td>
            <td>社科图书阅览室</td>
            <td>可预约，可阅览</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>十楼主楼南</td>
            <td>闻学堂</td>
            <td>特殊用途</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>十楼主楼北</td>
            <td>特藏部、古籍与特藏文献研究室</td>
            <td>特殊人员开放</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>十一楼主楼南</td>
            <td rowspan="2">研习室</td>
            <td rowspan="2">可预约</td>
          </tr>
          <tr align="center" valign="middle" height="60px">
            <td>十一楼主楼北</td>
          </tr>
        </table>
      </el-main>
    </body>
  </el-container>
</template>

<style lang="less" scoped>

// .el-menu-vertical-demo:not(.el-menu--collapse) {
//   margin: 0;
//   padding: 0;
//   border: 0;
//   background: linear-gradient(#004e99, white);
//   width: 250px;
//   height: 1000px;
//   min-height: 100%;
// }
.bg1 {
  background-image: url(../../assets/bg3.png);
}
.el-main {

  padding-right: 5%;
  padding-left: 5%;
  padding-bottom: 5%;

}

.el-header {
  background-color: #004e90;
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
  }
}

#poster {
  background: url("../../assets/notice-bg.jpg");
  background-size:cover;
  margin: 0;
  padding: 0;
  border: 0;
  width: 100%;
  height: 100%;
}

// .el-menu-vertical-demo:not(.el-menu--collapse) {
//   width: 250px;
//   min-height: 350px;
// }
a {
  text-decoration: none;
}

#webAside {
  height: 100%;
  background: rgb(59, 96, 113);
}

.router-link-active {
  text-decoration: none;
}
</style>