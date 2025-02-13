<template>
  <el-container class="home-container">
    <el-backtop :visibility-height="200" :right="60" :bottom="60">
      <div>
        <el-icon><ArrowUpBold /></el-icon>
      </div>
      
    </el-backtop>
    <!-- body -->
    <el-container>

   <!--if-->   
      <!-- aside -->

      <el-aside :width="iscollapse ? '64px' : '200px'">
        <!-- 侧边栏菜单 -->
        <div class="toggle-button" @click="toggleCollapse">|||</div>

        <el-menu
          background-color="rgb(59,96,113)"
          text-color="#fff"
          active-text-color="#ffd04b"
          :router="true"
          :collapse="iscollapse"
          :collapse-transition="false"
        >
           <el-menu-item index="/forum">
          <el-icon>
            <Management />
          </el-icon>
          <template v-slot:title> <span>论坛中心</span></template>
        </el-menu-item>
        <el-menu-item index="/home">
          <el-icon>
            <Opportunity />
          </el-icon>
          <template v-slot:title> <span>场地预约</span></template>
        </el-menu-item>
        <el-menu-item index="/Siping">
          <el-icon>
            <HelpFilled />
          </el-icon>
          <template v-slot:title> <span>馆藏分布</span></template>
        </el-menu-item>
        <el-menu-item index="/myInf">

          <el-icon>
            <UserFilled />
          </el-icon>
          <template v-slot:title> <span>个人主页</span></template>
        </el-menu-item>
        </el-menu>
      </el-aside>

      <!-- main -->

      <el-main>
        <div class="content">
          <h1 style="color: rgba(164, 58, 58, 0.688); font-size: 30px">
            &nbsp;&nbsp;&nbsp; 图书详情
          </h1>

          <!-- 第1页 -->
          <div class="page">
            <div>
              <img id="bookimg" src="" class="picture1" />
            </div>

            <div class="booktext" style="padding-left: 100px">
              <br><br><br>
              <p class="title">
                <b><i>{{bookname}} </i></b>
              </p>
              <br />
              <div class="border"></div>
              <br>

              <p style="font-size: 17px; color: rgb(67, 74, 102)">
                <b>{{author}} 著</b>
                <br />
                <b> {{trans}} </b>
                <br />
                <b>{{publisher}}</b>
                <br /><br />
              </p>

              <p class="grey">{{book_repre}}</p>
              <br />
              <p style="font-size: 17px">
                <b>ISBN: {{isbn}}</b>

                <br /><br />

                <el-rate
                  v-model="value"
                  disabled
                  show-score
                  text-color="#ff9900"
                  score-template="{value}"
                >
                </el-rate>
                <br /><br />
              </p>
 
              
              <el-button type="primary"
              :before-close="handleClose"
              round
              text
              @click="borrowSuccess = true">
                立即借阅
              </el-button>

          
               <!-- 新增借阅图书按钮和对话框，按照校区借书 -->
               <el-dialog
                v-model="borrowSuccess"
                title="借阅图书"
                v-bind="borrowSuccess"
                width="30%" draggable
                >

              <el-table :data="gridData">
                  <el-table-column property="place" label="馆藏地址" width="150" />         
                  <el-table-column property="num" label="余量" ></el-table-column>
                  <el-table-column align="right">    
                    <template #default="scope">                               
                      <el-button v-if="scope.row.num > 0" type="primary" prop="num" @click="submit(scope.row)" >借阅</el-button>
                      <el-button v-else  type="primary" prop="num" >预约</el-button>
                    </template>                   
                  </el-table-column>
            
                </el-table>
        
            </el-dialog>
         
       
            

            </div>
          </div>

          <br /><br /><br /><br /><br />

          <div class="border"></div>

          <br /><br /><br /><br />

          <h1 style="color: rgba(164, 58, 58, 0.688); font-size: 30px">
            <br />
            &nbsp;&nbsp;&nbsp; 图书详情
          </h1>

          <br /><br />
          <p style="font-size: 23px">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <b>内容简介</b>
          </p>
          <!-- 第2页 -->
          <div class="page">
            <div
              class="booktext"
              style="padding-top: 60px; padding-right: 40px; line-height: 40px"
            >
              <p style="font-size: 17px; text-align: left">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                {{book_text}}
              </p>
            </div>

            <div>
              <img id="bookimg2" src="" class="picture2" />
            </div>
          </div>

          <h1 style="color: rgba(164, 58, 58, 0.688); font-size: 30px">
            <br />
            &nbsp;&nbsp;&nbsp; 图书详情
          </h1>

          <!-- 第3页 -->
          <div class="page">
            <div>
              <img id="authorimg" src="" class="picture3"  />
            </div>

            <div class="booktext">
              <p style="font-size: 23px">
                <b>作者简介</b>
              </p>

              <p style="font-size: 16px; text-align: left; line-height: 40px">
                &nbsp;&nbsp;&nbsp;&nbsp; {{author_text}}
              </p>
            </div>
          </div>

          <br /><br /><br />

          <div class="border"></div>
          <br />
          <h1 style="color: rgba(164, 58, 58, 0.688); font-size: 30px">
            &nbsp;&nbsp;&nbsp; 评价与反馈
          </h1>
          <br /><br />

          <!-- 尾页 -->
          <div
            style="display: inline-block; font-size: 18px; padding-left: 80px"
          >
            <div class="block">
              <span class="demonstration">请选择您对本书的评价：</span>
              <br /><br />
              <el-rate v-model="value1" @change="upd" show-text=""></el-rate>
            </div>

            <br /><br /><br />


            <br /><br /><br />
          </div>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
const axios = require("axios");
import { ElMessage } from "element-plus";

export default 
{
  inject: ["reload"],
  name: "app",

  data() {
    return {
      activeIndex: "1",
      activeIndex2: "1",
      // 是否折叠
      iscollapse: true,

      // 本书评分
      value: 5,
      value1: -5,
      counts: 0,

      textarea2: "",

      gridData: 
      [
      {place:"四平路校区",
      num:0,
      },
      {place:"嘉定校区",
      num:0,
      }],

      // 抽屉
      // drawer: false,

      form: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: "",
      },
      formLabelWidth: "80px",
      timer: null,
      dialog: false,

      //书籍详情
      bookname: ' ',
      isbn:1,
      author:1,
      trans:1,
      publisher:1,
      book_repre:1,
      book_id:1,
      book_text:1,
      author_text:1,
      book_img:1,
      author_img:1,
      
     userid: window.sessionStorage.getItem('userid'),
     choosedate:'',
     borrowSuccess: false,
   
    };
  },

  created() {
    
  },

  methods: {

    submit(row) {

          axios({
            method: "post",
            params: {
              isbn: this.isbn,
              userid: this.userid,
              place: row.place,
            },
            url: "/borrowed-book/borrow",
          }).then(
            (res) => {
              console.log("submit", res);
              if (res.data == true) {
                this.$message({
                  message: "借阅成功！",
                  type: "success",
                });
                this.reload();
              } else if (res.data == false) {
                this.$message({
                  message: "图书已被借阅或损坏，借阅失败！",
                  type: "warning",
                });
              
              }
            },
            (error) => {
              console.log("错误", error.message);
            }
          );
          
        
      ;
    },
        //接收馆藏发来的书名
    getParams() {
      const routerParams = this.$route.query.bookName;
      this.bookname = routerParams; //这里更改bookname
      const routerParams2 = this.$route.query.isbn;
      this.isbn = routerParams2; //这里更改bookname
    },
    watch: {
      $route: "getParams",
    },

    //触发change事件时启动，根据后台评分情况更新当前分数
    upd() {
      console.log(this.value);
      console.log(this.value1);
      console.log(this.counts);

      this.counts = Number(this.counts)+1;
      console.log('test',this.counts);
      this.value = (this.value1 + this.value * (this.counts-1)) / this.counts;
      this.value = this.value.toFixed(2);

      //axios post方式
      axios({
        method: "post",
        params: {
          isbn: this.isbn,
          rate: this.value,
          count: this.counts,
        },
        url: '/book-info/updateScore',
      }).then((res) => {
        console.log('/a1', res)
      }, error => {
        console.log('错误', error.message)
      })
    },
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
           this.choosedate = year + "." + month + "." + strDate;
           
        },
    // borrow() {
      
    //   axios({
    //     method: "post",
    //     params: {
    //       bookid: this.book_id,
    //       loan_people_id: this.userid,
    //     },
    //     url: 'api/LoanBooks/INSERTBOOKLOAN',
    //   }).then((res) => {
    //     console.log('/a1', res)
    //     if(res.data){
    //       ElMessage({
    //     message: "借阅成功！",
    //     type: "success",
    //   });
    //     }
    //     else {
    //        ElMessage({
    //     message: "图书已被借阅或损坏，借阅失败！",
    //     type: "fail",
    //   });
    //     }
        
    //   }, error => {
    //     console.log(this.$data.book_id)
    //     console.log('错误', error.message)
    //   })

    // },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
    // 点击按钮，切换菜单折叠
    toggleCollapse() {
      this.iscollapse = !this.iscollapse;
    },
    update() {},
  },
 
   mounted() 
   {
    this.getParams(); //在页面渲染完毕时接收馆藏发来的书名参数
    this.getdate();//页面建立时获取当前时间
        // 页面建立时获取本书评分
        // axios.get('http://localhost:8080/score').then((res) => {
        //     console.log(res)
        //     this.$data.value = res.data.currentScore
        //     this.$data.counts=res.data.num
        // })
        
        //获取书籍详情
        axios({
        method: 'post',
        params: {
          isbn:this.isbn
        },
        url: '/book-info/queryByIsbn',
      }).then((res) => {
        console.log(res)

        this.$data.bookname = res.data.bookName
        this.$data.isbn = res.data.isbn
        this.$data.author = res.data.bookAuthor
        this.$data.trans = res.data.bookTranslation
        this.$data.publisher = res.data.bookPublisher
        this.$data.book_repre = res.data.bookRepre
        this.$data.book_text = res.data.bookText
        this.$data.author_text = res.data.bookAuthorabout
        //获取评分
        this.$data.value = res.data.score
        this.$data.counts=res.data.scorePeopleNumber

        //获取图片
        this.$data.book_img=res.data.bookImg
        this.$data.author_img=res.data.authorImg

        document.getElementById("bookimg").src = this.book_img
        document.getElementById("bookimg2").src = this.book_img
        document.getElementById("authorimg").src = this.author_img
        console.log(this.book_img)

        
      }, error => {
        console.log('错误', error.message)
      }),


       axios({
        method: 'post',
        params: {
          isbn:this.isbn,
          place:"四平路校区",
        },
        url: '/borrowed-book/countisbnplace',
      }).then((res) => {
        console.log(res)
        this.$data.gridData[0].num = res.data
        
      }, error => {
        console.log('错误', error.message) 
      }),
      
      axios({
        method: 'post',
        params: {
          isbn:this.isbn,
          place:"嘉定校区",
        },
        url: '/borrowed-book/countisbnplace',
      }).then((res) => {
        console.log(res)
        this.$data.gridData[1].num = res.data
        
      }, error => {
        console.log('错误', error.message) 
      })


    },
};
</script>

<style  scoped>
.home-container {
  height: 700px;
}
.bo{
  height: 200px;
  width: 20px;

}

.bg1 {
  background-image: url(../assets/bg4.jpg);
}

.el-header {
  background-color: #303133;

  display: flex;
  justify-content: space-between;
  align-items: center;
  color: aliceblue;
  font-size: 25px;
}

.el-aside {
  background-color: rgb(59, 96, 113);
}

.el-main {
  background-color: rgb(248, 243, 243);
}

.el-menu {
  border: none;
}
.code {
  margin-top: 15px;
  margin-left: 160px;
  font-size: 17px;
}

.toggle-button {
  color: white;
  text-align: center;
  line-height: 34px;
  letter-spacing: 0.2em;
  cursor: pointer;
}

.title {
  color: rgb(50, 76, 126);
  font-size: 35px;
}

.grey {
  color: grey;
  font-size: 18px;
}

a:hover {
  color: red;
}

.content {
  /* background-color: rgb(248, 243, 243); */

  width: 1500px;
  padding: 30px;
}

.booktext {
  display: inline-block;
  text-align: center;
  padding: 40px;
  height: 800px;
  width: 500px;
  float: left;
}

.border {
  height: 2px;

  background-color: brown;
}

.picture1 {
  height: 600px;
  width: 500px;
  padding-top: 60px;
  float: left;
}

.picture2 {
  height: 450px;
  width: 450px;
  padding-left: 50px;
  float: left;
}

.picture3 {
  height: 450px;
  width: 400px;
  padding: 50px;
  float: left;
  object-fit:cover;
}

.page {
  width: 1100px;
  height: 900px;
  padding-left: 100px;
}
</style>

