<template>
  <div>
    <h1 style="font-size: 20px; padding:1%"> 通知公告管理 </h1>
    <div class="tabs">
      <el-table :data="tableData.filter(data => !search || data.title.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%;" height="440" :default-sort="{ prop: 'releaseDate', order: 'descending' }">
        <el-table-column label="公告名称" prop="title" width="600px"></el-table-column>
        <el-table-column label="发布时间" prop="releaseDate"></el-table-column>
        <el-table-column label="发布人员id" prop="userId"></el-table-column>
        <el-table-column align="right">
          <template #header>
            <el-input v-model="search" style="width: 80%;" size="mini" placeholder="输入公告关键字搜索" />
          </template>
          <template #default="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">信息修改
            </el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除公告
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div align="right" style="padding:1%">
        <el-button type="primary" @click="dialogFormVisible = true">新增公告
        </el-button>
      </div>

      <!-- 嵌套表单（发布） -->
      <el-dialog v-model="dialogFormVisible" title="发布公告">
        <el-form :model="form">
          <el-form-item label="名称" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off" />
          </el-form-item>
          <el-form-item label="内容" :label-width="formLabelWidth">
            <el-input v-model="form.text" :rows="8" type="textarea" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click="CommitNotice">发布</el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 嵌套表单（修改） -->
      <el-dialog v-model="dialogNoticeVisible" title="发布公告">
        <el-form :model="Notice">
          <el-form-item label="名称" :label-width="formLabelWidth">
            <el-input v-model="Notice.name" autocomplete="off" />
          </el-form-item>
          <el-form-item label="内容" :label-width="formLabelWidth">
            <el-input v-model="Notice.text" :rows="8" type="textarea" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogNoticeVisible = false">取消</el-button>
            <el-button type="primary" @click="EditNotice">发布</el-button>
          </span>
        </template>
      </el-dialog>

    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { h } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  inject:['reload'],
  data() {
    return {
      tableData: [],
      search: '',
      dialogFormVisible: false,
      form: {
        name: '',
        text: '',
      },
      dialogNoticeVisible: false,
      Notice: {
        id: '',
        name: '',
        text: '',
      },
      formLabelWidth: '50px',
      userid:window.sessionStorage.getItem('userid'),
    }
  },
  methods: {
    handleEdit(index, row) {
      this.Notice.name = row.title;
      this.Notice.text = row.text;
      this.Notice.id = row.noticeId;
      this.dialogNoticeVisible = true;
    },
    handleDelete(index, row) {
      console.log(index, row);
      this.$confirm('确认删除该公告?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 返回id给后端删除
        axios({
          method: 'post',
          params: {
            id: row.noticeId,
          },
          url: '/notice/DeleteNotice',
        }).then((res) => {
          console.log('delete', res)
        }, error => {
          console.log('错误', error.message)
        })
        this.$message({
          type: 'success',
          message: '删除成功!'
        });
        this.reload();
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },

    handleClick(index, row) {
      console.log(index, row);
    },

    CommitNotice() {
      this.dialogFormVisible = false;
      console.log(this.form);
      axios({
        method: 'post',
        params: {
          title: this.form.name,
          text: this.form.text,
          userid:this.userid,
        },
        url: '/notice/AddNotice',
      }).then((res) => {
        console.log('add', res)
        this.reload();
      }, error => {
        console.log('错误', error.message)
      })
    },

    EditNotice() {
      this.dialogNoticeVisible = false;
      console.log(this.Notice);
      axios({
        method: 'post',
        params: {
          id:this.Notice.id,
          title: this.Notice.name,
          text: this.Notice.text,
          userid:this.userid,
        },
        url: '/notice/EditNotice',
      }).then((res) => {
        console.log('edit', res)
        if (res.data == true) {
          this.$message({
            message: '修改公告成功',
            type: 'success'
          });
          this.reload();
        }
        else{
          this.$message({
            message: '修改公告失败'
          });
        }
      }, error => {
        console.log('错误', error.message)
      })
    },
  },
  mounted() {
    axios.get('/notice/GetNotice').then((res) => {
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