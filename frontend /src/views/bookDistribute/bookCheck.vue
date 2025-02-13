<template slot-scope="scope">
  <meta http-equiv="Access-Control-Allow-Origin" content="*">
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
    <el-main>
      
      <div class="tabs">
        <div align="center">
          <el-input v-model="this.keyword" placeholder="输入书名或作者关键字搜索" @keydown.enter.native="search" style="width:40%">
            <template #append>
              <el-button @click="search">
                <el-icon><Search /></el-icon>
              </el-button>
            </template>
          </el-input>
        </div>
        <el-divider></el-divider>
        <el-table :data="tableData" style="width: 100%" :row-class-name="tableRowClassName">
          <el-table-column label="书名" prop="bookName">
          </el-table-column>
          <el-table-column label="作者" prop="bookAuthor">
          </el-table-column>
          <!-- <el-table-column label="地点" prop="BOOK_PLACE">
          </el-table-column>
          <el-table-column label="状态" prop="BOOK_STATE">
            <template #default="scope">
              <div
                v-if="this.tableData[scope.$index].BOOK_STATE == '已借出' || this.tableData[scope.$index].BOOK_STATE == '无法借阅'">
                <div style="color:red;">无法借阅</div>
              </div>
              <div v-else>
                <div style="color:green;">可借阅</div>
              </div>
            </template>

          </el-table-column> -->

          <el-table-column label="更多" width="80">
            <template #default="scope">
              <el-button size="mini" type="text" @click="jumpURL(scope.$index, scope.row)">详情</el-button>
            </template>
          </el-table-column>
        </el-table>

        <div style="padding-top:1%">
          <span class="demonstration"></span>
          <el-pagination @current-change="changePage" :current-page="currentPage4" :page-size="11"
            layout="total, prev, pager, next, jumper" :total="total">
          </el-pagination>
        </div>
      </div>
    </el-main>
  </el-container>

</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      //请求信息，向后端请求某一页数的一表图书信息
      curPageSize: 11,
      curPage: 1,
      bookName: "",
      bookid: "",
      isbn:"",
      index: 0,
      tableData: [], //存放图书信息的列表
      allData: [], //所有图书信息的表
      total: 0, //列表里图书的数量
      keyword: "",
    };
  },
  created() {
    this.getParams();
    this.getBookList(); //在页面创建时的函数，向后端请求图书列表
  },

  methods: {
    //接收主页搜索框的关键词
    getParams() {
      const routerParams = this.$route.query.keyword;
      this.keyword = routerParams; //更改本地关键词
    },
    watch: {
      $route: "getParams",
    },

    //点击跳转相应图书页面
    jumpURL(index, row) {
      this.index = index;
      // this.bookName = this.tableData[this.index].BOOK_NAME;
      // this.bookid = this.tableData[this.index].BOOK_ID;
      this.isbn = this.tableData[this.index].isbn;

      this.$router.push({
        path: "/book",
        query: {
          // bookName: this.bookName,
          // book_id: this.bookid,
          isbn:this.isbn
        },
      });
    },

    //实现检索加上实时渲染
    retrieve() {
      for (var i = 0; i < this.allData.length; i++) {
        var flag = true;
        var s1 = this.allData[i].bookName + this.allData[i].bookAuthor;
        var s2 = this.keyword;
        var flag = false;

        for (var j = 0; j < s1.length - s2.length + 1; j++) {
          if (s1[j] == s2[0]) {
            var left = j;
            var right = j + s2.length - 1;
            while (right > left) {
              if (s1[left] != s2[left - j] || s1[right] != s2[right - j]) break;
              else {
                left++;
                right--;
              }
            }
            if (left >= right) {
              flag = true;
              break;
            }
          }
        }
        if (flag === false) {
          this.allData.splice(i, 1);
          i = i - 1;
        }
      }
      this.total = this.allData.length;
      //更新渲染数组tableData
      while (true) {
        if (this.tableData.length === 0) {
          break;
        } else {
          this.tableData.pop();
        }
      }
      var up;
      if (this.allData.length < 11) up = this.allData.length;
      else up = 11;
      for (var i = 0; i < up; i++) {
        this.tableData.push(this.allData[i]);
      }
    },

    //搜索
    search() {
      console.log("successful");
      //默认空字符串显示所有书籍

      if (this.keyword == "") {
        this.getBookList();
      } else {
        //每次重新获取新的图书列表
        axios({
          method: "get",
          url: "/book-info/get",
        }).then(({ data }) => {
          console.log(data);
          if (data.length === 0) {
            alert("获取图书列表失败");
            return;
          } else {
            // 把数据存进allData里面，更新total
            this.allData = data;
          }
        });
        // 字符串匹配，检索书名和作者名
        this.retrieve();
      }
    },
    //换页
    changePage(newPage) {
      this.curPage = newPage;
      //清空数组
      if (this.tableData.length !== 0) {
        var size = this.tableData.length;
        for (var i = 0; i < size; i++) {
          this.tableData.pop();
        }
      }
      //找到下一个页面对应的前后下标，更新tableData
      var allSize = this.allData.length;
      var up;
      if (allSize < this.curPage * this.curPageSize) {
        up = allSize;
      } else {
        up = this.curPage * this.curPageSize;
      }
      for (var i = (this.curPage - 1) * this.curPageSize; i < up; i++) {
        this.tableData.push(this.allData[i]);
      }
    },
    // 向后端发送axios请求获取图书数据并更新列表
    getBookList() {
      //清空数组
      if (this.tableData.length !== 0) {
        for (var i = 0; i < this.tableData.length; i++) {
          this.tableData.pop();
        }
      }
      axios({
        method: "get",
        url: "/book-info/get",
      }).then(({ data }) => {
        console.log(data);
        if (data.length === 0) {
          alert("获取图书列表失败");
          return;
        } else {
          // 把数据存进allData里面，更新total
          this.allData = data;
          this.total = this.allData.length;

          //判断是否接收到首页传来的keyword
          if (typeof (this.keyword) === 'undefined')
            this.keyword = ""

          //判断是否需要检索
          if (this.keyword.length !== 0) {
            this.retrieve();
          } else {
            //更新渲染数组tableData
            while (true) {
              if (this.tableData.length === 0) {
                break;
              } else {
                this.tableData.pop();
              }
            }
            for (var i = 0; i < 11; i++) {
              this.tableData.push(this.allData[i]);
            }
          }
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>

#webAside {
  height: 100%;
  //height: 650px; //这里100%可能会在图书列表较少时使得页面显示不全，故写死
  background: rgb(59, 96, 113);
}
.el-main {

  padding: 3%;

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

a {
  text-decoration: none;
}

.router-link-active {
  text-decoration: none;
}

.block {
  position: fixed;
  bottom: 0;
  height: 40px;
  width: 100%;
  text-align: center;
}
.tabs {
    height: 100%;
    width: 100%;
    padding: 3%;
    background-color: white;
}
</style>