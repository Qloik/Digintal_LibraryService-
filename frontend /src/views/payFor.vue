<template>
    <div>
      <!--面包屑导航区域-->
      <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>个人主页</el-breadcrumb-item>
        <el-breadcrumb-item>我的赔书</el-breadcrumb-item>
      </el-breadcrumb>

      <!--卡片视图区域-->
      <el-card class="box-card">
        <!--搜索与添加区域-->
        <el-row :gutter="20">
          <!--第一列-->
          <el-col :span="10">
             <!-- eslint-disable-next-line vue/no-parsing-error-->
             <el-input v-model="search" placeholder="请输入书籍名称" clearable @clear="searchBtn">
               <template #append>
                 <el-button @click="searchBtn">搜</el-button>
               </template>
             </el-input>
             
          </el-col>
        </el-row>
      </el-card>

      <!--列表区域-->
      <el-card class="box-card2">
    <el-table :data="tableData_4" :header-cell-style="{textAlign: 'center'}" :cell-style="{ textAlign: 'center' }" border style="width: 100%"  stripe="true" >
        <el-table-column type="index"></el-table-column>
        <!-- <el-table-column label="ISBN" prop="ISBN">
        <template #default="scope">
          <el-tag disable-transitions> {{ scope.row.ISBN }}</el-tag>
        </template>
      </el-table-column> -->
        <el-table-column prop="bookName" label="图书名称"></el-table-column>
        <el-table-column prop="bookId" label="图书ID"></el-table-column>
        <el-table-column prop="damageTime" label="损坏时间"></el-table-column>
        <el-table-column prop="repairTime" label="维修时间"></el-table-column>
      
       
      </el-table>


      </el-card>

    </div>
</template>

<script>
import { computed, ref } from 'vue'
import axios from 'axios'
const search = ref('')

export default ({
  data () {
			return{
        search: '',
        userid: window.sessionStorage.getItem("userid"),
        tableData_4: [
        ],
      }
  },
  computed:{
    //搜索功能
    tableData_4() {
      let search = this.search;
      let list =this.tableData_4.filter(data => !search || data.bookName.toLowerCase().includes(search.toLowerCase())|| data.bookName.toLowerCase().includes(search.toLowerCase()));
      return list
    }
  },
  mounted () {
    //获取用户赔书信息
   axios({
        method:'post',
        params:{
          userid:this.userid
        },
        url:'/damaged-book/getdamagedbyUserid'
      }).then((res)=>{
        console.log(res)
        this.$data.tableData_4=res.data
      })
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
