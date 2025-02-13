<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb :separator-icon="ArrowRight">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>个人主页</el-breadcrumb-item>
      <el-breadcrumb-item>我的评论</el-breadcrumb-item>
    </el-breadcrumb>

    <!--卡片视图区域-->
    <el-card class="box-card">
      <!--搜索与添加区域-->
      <el-row :gutter="20">
        <!--第一列-->
        <el-col :span="10">
          <!-- eslint-disable-next-line vue/no-parsing-error-->
          <el-input
            v-model="search"
            placeholder="请输入评论内容"
            clearable
            @clear="searchBtn"
          >
            <template #append>
              <el-button @click="searchBtn">搜</el-button>
            </template>
          </el-input>
        </el-col>
      </el-row>
    </el-card>

    <!--列表区域-->
    <el-card class="box-card2">
      <el-table
        :data="tableData"
        :header-cell-style="{ textAlign: 'center' }"
        :cell-style="{ textAlign: 'center' }"
        border
        style="width: 100%"
        stripe="true"
      >
        <el-table-column type="index"></el-table-column>
        <el-table-column
          prop="time"
          label="评论时间"
          width="200"
        ></el-table-column>
        <el-table-column prop="status" label="评论状态" width="120">
          <template #default="scope">
            <el-tag disable-transitions effect="dark" :type="buttonType(scope.row.status)">
              {{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="详情内容"></el-table-column>

        <el-table-column align="right" width="100" label="修改">
          <template #default="scope">
            <el-button
              size="mini"
              type="info"
              @click="showchangeConfirmDialog(scope.$index, scope.row)"
              v-loading.fullscreen.lock="fullscreenLoading"
            >
              修改</el-button
            >
          </template>
        </el-table-column>
        <el-table-column align="right" width="100">
          <template #default="scope">
            <el-button
              size="mini"
              type="danger"
              @click="showConfirmDialog(scope.$index, scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { computed, ref } from "vue";
import axios from "axios";
import { ElMessage, ElMessageBox } from "element-plus";

const search = ref("");
export default {
  inject: ["reload"],
  data() {
    return {
      search: "",
      tableData: [{}],
      postchangedialogFormVisible: false,
      userid: window.sessionStorage.getItem("userid"),
      content_new: "",
      fullscreenLoading:false,
    };
  },
  computed: {
    tableData() {
      let search = this.search;
      let list = this.tableData.filter(
        (data) =>
          !search ||
          data.content.toLowerCase().includes(search.toLowerCase()) ||
          data.content.toLowerCase().includes(search.toLowerCase()) 
      );
      return list;
    },
  },
  
  methods: {
    async getUserList() {
      const { data: res } = await this.$http.get("users", {
        params: this.queryInfo,
      });
      if (res.meta.status !== 200)
        return this.$message.error("获取用户列表失败");
      this.userlist = res.data.users;
      this.total = res.data.total;
      console.log(res);
    },
    // 监听pagesize改变的事件
    handleSizeChange(newSize) {
      console.log(newSize);
    },
    // 监听页码值改变的事件
    handleCurrentChange(newPage) {
      console.log(newPage);
    },
    showchangeConfirmDialog(index, row) {
      
      ElMessageBox.prompt("请修改您的评论", "评论修改", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputValue: row.content,
        showInput: true,
        inputType: "textarea",
        inputRows: 1,
     
      })
        .then(({ value }) => {
          console.log("value", value);
          this.content_new=value;

          this.changepost(index, row);
        })
        .catch(() => {
          // 用户取消删除操作
        });
    },
    showConfirmDialog(index, row) {
      this.$confirm("确认删除此项内容吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.handlereview(index, row);
        })
        .catch(() => {
          // 用户取消删除操作
        });
    },
    handlereview(index, row) {
      console.log(row);
      axios({
        method: "post",
        params: {
          userId: this.userid,
          reviewId: row.reviewId,
        },
        url: "/review/deleteOneReview",
      }).then(
        (res) => {
          console.log("edit", res);
          if (res.data) {
            this.$message({
              message: "删除评论成功",
              type: "success",
            });
            this.reload();
          } else {
            this.$message({
              message: "删除评论失败",
              type: "warning",
            });
            this.reload();
          }
        },
        (error) => {
          console.log("错误", error.message);
        }
      );
    },
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
    changepost(index, row) {
       console.log(row);
       this.fullscreenLoading = true;
      axios({
        method: "post",
        params: {
          reviewId: row.reviewId,
          content: this.content_new,
      
        },
        url: "/review/changeOneReview",
      }).then(
        (res) => {
          console.log("change", res);
          this.fullscreenLoading = false;
          if (res.data) {
            this.$message({
              message: "修改发布成功",
              type: "success",
              
            });
            this.reload();
          } else {
            this.$message({
              message: "修改发布失败",
              type: "warning",
            });
            this.reload();
          }
        
        },
        (error) => {
          console.log("错误", error.message);
        }
      );
    },
  },

  mounted() {
    axios({
      method: "post",
      params: {
        userId: this.userid,
      },
      url: "/review/queryByUserId",
    }).then(
      (res) => {
        console.log("add", res);
        this.tableData = res.data;
      },
      (error) => {
        console.log("错误", error.message);
      }
    );
  },
};
</script>

<style lang="less" scoped>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
.box-card {
  margin-top: 20px;
  margin-bottom: 10px;
  font-size: 20px;
  margin-left: 40px;
  margin-right: 60px;
}
.box-card2 {
  margin-top: 20px;
  margin-bottom: 10px;
  font-size: 20px;
  margin-left: 40px;
  margin-right: 60px;
}
</style>
