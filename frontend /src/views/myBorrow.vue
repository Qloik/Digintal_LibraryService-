<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb :separator-icon="ArrowRight">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>个人主页</el-breadcrumb-item>
      <el-breadcrumb-item>我的借阅</el-breadcrumb-item>
    </el-breadcrumb>

    <!--卡片视图区域-->
    <el-card :data="filterTableData" class="box-card">
      <!--搜索与添加区域-->
      <!--搜索与添加区域-->
      <el-row :gutter="20">
        <!--第一列-->
        <el-col :span="10">
          <!-- eslint-disable-next-line vue/no-parsing-error-->
          <el-input v-model="search" placeholder="请输入借阅的书籍名称" clearable @clear="searchBtn">
            <template #append>
              <el-button @click="searchBtn">搜</el-button>
            </template>
          </el-input>
        </el-col>
      </el-row>
    </el-card>

    <!--列表区域-->
    <el-card class="box-card2">
      <el-table ref="filterTable" :data="tableData" style="width: 100%" max-height="430">
        <el-table-column label="图书名称" prop="bookName">
        </el-table-column>
        <el-table-column label="馆藏编号" prop="bookId">
          <template #default="scope">
            <el-tag disable-transitions> {{ scope.row.bookId }}</el-tag>
          </template>
        </el-table-column>

        <!-- <el-table-column label="出版社" prop="publisher"> </el-table-column> -->
        <el-table-column label="借书时间" prop="borrowTime"> </el-table-column>
        <el-table-column label="应还时间" prop="returnTime"> </el-table-column>
        <el-table-column label="归还时间" prop="backTime"></el-table-column>
        <el-table-column label="状态" prop="mode">
          <template #default="scope">
            <el-tag disable-transitions effect="dark" :type="buttonType(scope.row.borrowStatus)">
              {{ scope.row.borrowStatus }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="right">
          <template #default="scope">
            <el-button   size="mini" @click="handleReBorrow(scope.$index, scope.row)"
              :disabled="scope.row.borrowStatus != '已取书' ? true : false">续借</el-button>
            
           
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>


<script>
import { computed, ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { h } from "vue";
import axios from "axios";
const search = ref("");

export default {
  inject: ["reload"],
  data() {
    return {
      search: "",
      tableData: [],
      choosedate: "",
      userid: window.sessionStorage.getItem("userid"),
    };
  },
  computed: {
    tableData() {
      let search = this.search;
      let list = this.tableData.filter(
        (data) =>
          !search ||
          data.bookName.toLowerCase().includes(search.toLowerCase()) ||
          data.bookName.toLowerCase().includes(search.toLowerCase())
      );
      return list;
    },
  },

  created() {
    this.getBorrowList();
    this.getdate();
  },
  methods: {
    getdate() {
      var date = new Date();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      this.choosedate = year + "-" + month + "-" + strDate;
    },
    getBorrowList() {
      axios({
        method: "post",
        params: {
          userid: this.userid,
        },
        url: "/borrowed-book/getborrowedbyUserid",
      }).then((res) => {
        console.log(res.data);
        this.$data.tableData = res.data;
      });
    },
    showConfirmDialog(index, row) {
      this.$confirm("确认归还本图书吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.handleReturn(index, row);
        })
        .catch(() => {
          // 用户取消归还操作
        });
    },
    handleReturn(index, row) {
      if (row.borrowStatus == "已取书") {
        axios({
          method: "post",
          params: {
            borrowId: row.borrowId,
          },
          url: "/borrowed-book/returnborrowed",
        }).then((res) => {

          if (res.data) {
            ElMessage({
              message: "归还成功",
              type: "success",
            });
          }
          this.reload();
        });
      }
    },
    handleReBorrow(index, row) {

      {
        ElMessageBox({
          title: "确认续借",
          message: h("p", null, [
            h("div", null, "续借图书：", row.bookName),
            h("div", null, "续借时长：", "30天"),
          ]),
          showCancelButton: true,
          confirmButtonText: "确认续借",
          cancelButtonText: "取消",

          beforeClose: (action, instance, done) => {
            if (action === "confirm") {
              axios({
                method: "post",
                params: {
                  bookid: row.bookId,
                  returnTime: row.returnTime,
                },
                url: "/borrowed-book/postponeborrowed",
              }).then((res) => {
                console.log(res);
              });
              ElMessage({
                type: "success",
                message: "续借成功",
              });
              done();
              this.reload();
            } else {
              ElMessage({
                message: "已取消续借",
              });
              done();
            }
          },
        })

      }

    },
    //监听pagesize_3改变的事件
    handleSizeChange(newSize) {
      console.log(newSize);
    },
    // 监听页码值改变的事件
    handleCurrentChange(newPage) {
      console.log(newPage);
    },
    buttonType(modeId) {
      // 选中的button更改颜色，其余的都更改为灰色
      if (modeId === "已借出") {
        return "success";
      } else if (modeId === "已归还") {
        return "danger";
      } else if(modeId==="已取书"){
        return "success";
      }
      else {
        return "info";
      }
    },
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
  font-size: 18px;
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