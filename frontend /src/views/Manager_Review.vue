<template>
  <div>
    <h1 style="font-size: 20px; padding:1%"> 评论审核管理 </h1>
    <div class="tabs">
      <el-table :data="tableData.filter(data => !search || data.title.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%;" height="440" :default-sort="{ prop: 'releaseDate', order: 'descending' }">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="评论时间" prop="time" width="200px"></el-table-column>
        <el-table-column label="评论人员id" prop="userId" width="150px"></el-table-column>
        <el-table-column prop="content" label="详情内容" width="500px"></el-table-column>
         <el-table-column prop="status" label="评论状态" width="120">
          <template #default="scope">
            <el-tag
              disable-transitions
              effect="dark"
              :type="buttonType(scope.row.status)"
            >
              {{ scope.row.status }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column align="right">
          <template #header>
            <el-input v-model="search" style="width: 80%;" size="mini" placeholder="输入评论关键字搜索" />
          </template>
          <template #default="scope">
            <el-button size="mini" type="success" @click="statuschange(scope.$index, scope.row)">评论状态修改
            </el-button>
          </template>
        </el-table-column>
      </el-table>     


    </div>
  </div>
</template>

<script>
import axios from 'axios'


export default {
  inject:['reload'],
  data() {
    return {
      tableData: [],
      search: '',
      dialogFormVisible: false,
      formLabelWidth: '50px',
      userid:window.sessionStorage.getItem('userid'),
    }
  },
  methods: {
     buttonType(modeId) {
      // 选中的button更改颜色，其余的都更改为灰色
      if (modeId === "发布成功") {
        return "success";
      } else if (modeId === "未通过审核") {
        return "danger";
      } else {
        return "info";
      }
    },
  
    handleClick(index, row) {
      console.log(index, row);
    },

   statuschange(index, row) {

      console.log(row.status);
      axios({
        method: 'post',
        params: {
          reviewId: row.reviewId,
        },
        url: '/review/changeStatus',
      }).then((res) => {
        console.log('edit', res)
        if (res.data == true) {
          this.$message({
            message: '修改评论发布状态成功',
            type: 'success'
          });
          this.reload();
        }
        else{
          this.$message({
            message: '修改评论发布状态失败'
          });
        }
      }, error => {
        console.log('错误', error.message)
      })
    },
  },
  mounted() {
    axios.get('/review/getallreview').then((res) => {
      console.log('notice:', res)
      this.$data.tableData = res.data
    })
  },
}

</script>

<style lang="less" scoped>
.tabs {
  height: 99%;
  width: 99%;
  padding: 3%;
  background-color: white;
}

.el-input {
  width: 300px;
}
</style>