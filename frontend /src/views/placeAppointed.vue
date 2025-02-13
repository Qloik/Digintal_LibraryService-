<template>
    <div>
      <!--面包屑导航区域-->
      <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>个人主页</el-breadcrumb-item>
        <el-breadcrumb-item>场地预约</el-breadcrumb-item>
      </el-breadcrumb>

      <!--卡片视图区域-->
      <el-card class="box-card">
        <!--搜索与添加区域-->
        <el-row :gutter="20">
          <!--第一列-->
          <el-col :span="10">
             <!-- eslint-disable-next-line vue/no-parsing-error-->
              <el-input v-model="search" placeholder="请输入要查找的场地信息" clearable @clear="searchBtn">
               <template #append>
                 <el-button @click="searchBtn">搜</el-button>
               </template>
             </el-input>
          </el-col>
        </el-row>
      </el-card>

      <!--列表区域-->
      <el-card class="box-card2">
      <el-table :data="ReserveData" :header-cell-style="{textAlign: 'center'}" :cell-style="{ textAlign: 'center' }" border style="width: 100%"  stripe="true" >
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="roomType" label="场地信息"></el-table-column>
        <el-table-column prop="roomNumber" label="房间号" width="160"></el-table-column>      
        <el-table-column prop="reserveDate" label="日期"></el-table-column>
        <el-table-column prop="reserveTime" label="时间段"></el-table-column>
         <el-table-column prop="reserveId" label="预约编号"  width="80"></el-table-column>
      </el-table>

  

      </el-card>

    </div>
</template>

<script>
import axios from 'axios'
export default ({
  data () {
    return{
        search: '',
        ReserveData: [{
          mode:'ok',
        }],
         userid: window.sessionStorage.getItem('userid'),
      }
  },
  computed:{
    ReserveData() {
      let search = this.search;
      let list =this.ReserveData.filter(data => !search || data.roomType.toLowerCase().includes(search.toLowerCase())|| data.roomType.toLowerCase().includes(search.toLowerCase()));
      return list
    }
  },
  created () {
    this.placeList()
  },
  mounted () {
   axios({
            method: 'post',
            params: {
                userid: this.userid,
            },
            url: '/room-appointment/GetAppointByUser',
        }).then((res) => {
            console.log('111', res)
            this.$data.ReserveData = res.data
        }, error => {
            console.log('错误', error.message)
        })

  } ,
  methods: {
    async placeList () {
      const { data: res } = await this.$http.get('place', {
        params: this.placeInfo
      })
      if (res.meta.status !== 200) return this.$message.error('获取用户列表失败')
      this.placelist = res.data.place
      this.total = res.data.total
      console.log(res)
    },
    // 监听pagesize_2改变的事件
    handleSizeChange (newSize) {
      console.log(newSize)
    },
    // 监听页码值改变的事件
    handleCurrentChange (newPage) {
      console.log(newPage)
    },buttonType(modeId){
// 选中的button更改颜色，其余的都更改为灰色  
          return "success"
      },
  }
})
</script>

<style lang="less" scoped>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
.box-card{
  margin-top: 20px;
  margin-bottom: 10px;
  font-size: 20px;
  margin-left: 40px;
  margin-right: 60px;
}
.box-card2{
  margin-top: 20px;
  margin-bottom: 10px;
  font-size: 20px;
  margin-left: 40px;
  margin-right: 60px;
}
</style>
