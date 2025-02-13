<template>
  <!-- 头部 -->
  <div id="Hder" class="header open">

    <div class="hdt" style="opacity:0.4;height:30px;">
      <!-- <script language="javascript" src="JavaScript/openlink.js"></script> -->
      <img src="../images/school-head.png" alt="" style="height:26px">
      <a href="https://www.tongji.edu.cn/" target="_blank">同济大学</a>
      <!-- <a href="" target="_blank" onclick="" class="lang">English</a> -->
    </div>

    <div class="hdb">
      <!-- logo部分 -->
      <div class="logo">
        <!-- 网站logo图片地址请在本组件"内容配置-网站logo"处填写 -->
        <img src="../assets/TongjiLogo.png" style="height:40px">
        <!-- width="320px" height="70px" -->
      </div>

      <!-- 开馆时间 -->
      <div class="date-num">
        <!-- 补充时间跳转 -->
        <!-- <a class="date" href="" target="_blank" title="开馆时间" >7:00 ~ 22:00 </a> -->
        <!-- 开馆时间 -->
        <p class="date">7:00 ~ 22:00</p>

        <!-- 补充座位数量 -->
        <!-- 这里座位数量应该链接到座位分布待填入 -->
        <router-link to="/home">
          <div class="label"><img src="../images/chair.png" />&nbsp;&nbsp;-</div>
          <!-- 这里要添加一个座位已用量 -->
          <div class="num">
            <i title="已使用座位量">{{chairsUsed}} </i> / <span title="座位总量">500</span>
          </div>
        </router-link>
      </div>

      <!-- 快捷部分 -->
      <div class="hdr">
        <!-- <a href="../../public/index.html" class="a1 link1" style="" >首页</a> -->
        <!-- 这里要转到图书排行 -->
        <a href="/Siping" class="a1 link2" style="">图书</a>
        <!-- 转到个人主页 -->
        <a href="/personalpage" class="a1 link3" onclick="" style="">个人</a>

        <!-- 搜索框 -->
        <router-link to="/bookCheck">
        <div class="so">
          <div class="btn"></div>
        </div>
      </router-link>
      </div>
    </div>
  </div>
  <!-- <div class="mb-menu"></div> -->

  <el-container>


    <el-aside :width="iscollapse ? '64px' : '200px'" style="background-color:#fff;padding:1px;box-shadow:0 0 30px rgb(51, 85, 97);
    overflow: hidden;position: fixed;z-index: 99;height: 100%;">
      <!-- 侧边栏菜单 -->
      <div class="toggle-button" @click="toggleCollapse" style="color:rgb(51, 85, 97); padding-top: 200px">|||</div>

      <el-menu background-color="white" text-color="rgb(51, 85, 97)" active-text-color="rgb(51, 85, 97)" :router="true"
        :collapse="iscollapse" :collapse-transition="false">
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
      <!-- 导航栏 -->
    </el-aside>

    <el-main class="main-page-pad">
      <!-- 活动页 -->
      <div class="index-flash banner swiper-container-horizontal swiper-container-fade" style="width: 100%">
        <ul class="swiper-wrapper" style="transition-duration: 0ms">
          <li id="div1" class="swiper-slide swiper-slide-prev" style="width:100%;opacity: 1;
          transform: translate3d(0px, 0px, 0px);transition-duration: 0ms;">
            <div class="img pc-show">
              <img src="../assets/lbg.jpg" alt="" style="width: 100%;" />
            </div>
          </li>
        </ul>
        <div class="arrow"></div>

      </div>

      <!-- 搜索栏 -->
      <div class="wp2">
        <ul class="so-list fix" style="display: flex;
            justify-content: center;
            align-items: center;">

          <div class="con" style="">
            <!-- 表单这一部分要post搜索内容到服务器 -->
            <form  @keyup.enter="searchBooks(searchStr)" @submit.native.prevent>
              <!-- title -->
              <div class="tit">
                <span style="color:rgb(38,94,109)">图书搜索</span> 
              </div>
              <!-- 搜索框 -->
              <div class="inp">
                <input class="text" type="text" v-model="searchStr">
                <input class="btn" type="button" @click="searchBooks(searchStr)"/>
              </div>
            </form>
            <!-- 搜索栏底部 -->
            <!-- <div class="tp">
              <a class="a1" href="#" target="_blank">按类型查</a>
              <a class="a1" href="#" target="_blank">按热度查</a>
              <a class="a1" href="#" target="_blank">按评分查</a>
              <a class="a1" href="#" target="_blank">新书通报</a>
            </div> -->
          </div>

        </ul>

      </div>

      <!-- 通知公告 -->
      <div class="main" style="margin-top:20% ">
        <div class="row-a2">
          <div class="bg"></div>
          <div class="wp">


               <!-- 图书top榜单 -->
            <div class="col-l">
              <div class="m-tit1">
                <h3 class="tit">借阅热榜</h3>
                <!-- <a class="more" href="zydt.htm">更多<em>&gt;</em></a> -->
                <div class="con-r"></div>
              </div>
              <ul class="p-list1" style="min-height: 430px; overflow: hidden">
                <li v-for="(item,index) in topbook.slice(0,4) " :key="item.bookName">
                  <div class="date detail-pad"><span class="top-num">{{index+1}}</span>Top</div>
                  <h4 class="tit" @click="toBook(item.isbn)">
                    <img :src="item.bookImg" alt="" style="width: 80px">
                    
                    <div class="introduction">
                      <div>
                        <img src="../images/bookicon.png" alt="">
                        <div class="detail-book">{{item.bookName}}</div>
                      </div>
                      <div style="margin-top:13px">
                        <img src="../images/authoricon.png" alt="">
                         <div class="detail-book">{{item.bookAuthor}}</div>
                      </div>
                      <div style="margin-top:13px">
                        <img src="../images/publishericon.png" alt="">
                        <div class="detail-book">{{item.bookPublisher}}</div>
                      </div>
                    </div>

                  </h4>
                </li>
              </ul>
            </div>


            <!-- 通知 -->
            <div class="col-c">
              <div class="m-tit1">
                <h3 class="tit">通知公告</h3>
                <!-- <a class="more" href="tzgg.htm">更多<em>&gt;</em></a> -->
                <div class="con-r">
                </div>
              </div>
              <ul class="p-list1 hands" style="height: 560px">
                <li>
                  <div class="date"><span>{{date[0]}}</span>{{yearMonth[0]}}</div>
                  <h4 class="tit" @click="clickNotice(notices[0].title,notices[0].releaseDate,notices[0].text)">
                      <span class="tz-label color1">通知</span>{{notices[0].title}}
                  </h4>
                </li>

                <li>
                  <div class="date"><span>{{date[1]}}</span>{{yearMonth[0]}}</div>
                  <h4 class="tit">
                  <h4 class="tit" @click="clickNotice(notices[1].title,notices[1].releaseDate,notices[1].text)">
                      <span class="tz-label color1">通知</span>{{notices[1].title}}
                  </h4>
                  </h4>
                </li>

                <li>
                  <div class="date"><span>{{date[2]}}</span>{{yearMonth[0]}}</div>
                  <h4 class="tit">
                  <h4 class="tit" @click="clickNotice(notices[2].title,notices[2].releaseDate,notices[2].text)">
                      <span class="tz-label color1">通知</span>{{notices[2].title}}
                  </h4>
                  </h4>
                </li>

                <li>
                  <div class="date"><span>{{date[3]}}</span>{{yearMonth[0]}}</div>
                  <h4 class="tit">
                  <h4 class="tit" @click="clickNotice(notices[3].title,notices[3].releaseDate,notices[3].text)">
                      <span class="tz-label color1">通知</span>{{notices[3].title}}
                  </h4>
                  </h4>
                </li>
                <li>
                  <div class="date"><span>{{date[4]}}</span>{{yearMonth[4]}}</div>
                  <h4 class="tit">
                  <h4 class="tit" @click="clickNotice(notices[4].title,notices[4].releaseDate,notices[4].text)">
                      <span class="tz-label color1">通知</span>{{notices[4].title}}
                  </h4>
                  </h4>
                </li>
                 <li>
                  <div class="date"><span>{{date[5]}}</span>{{yearMonth[5]}}</div>
                  <h4 class="tit">
                  <h4 class="tit" @click="clickNotice(notices[5].title,notices[5].releaseDate,notices[5].text)">
                      <span class="tz-label color1">通知</span>{{notices[5].title}}
                  </h4>
                  </h4>
                </li>
              </ul>
            </div>
         

            <!-- 图书top榜单 -->
            <div class="col-r">
              <div id="main1012297">
              <div class="m-tit1">
                <h3 class="tit">图书推荐</h3>
                <!-- <a class="more" href="zydt.htm">更多<em>&gt;</em></a> -->
                <div class="con-r"></div>
              </div>
              <ul class="p-list1" style="min-height: 430px; overflow: hidden">
                <ul class="p-list1 grid-container" style="min-height: 430px; overflow: hidden">
                <li v-for="(item,index) in topbook.slice(0, 4) " :key="item.bookName" class="grid-item">
      
                  <h4 class="tit" @click="toBook(item.isbn)">
                   
                    <img :src="item.bookImg" alt="" style="width: 130px">
                  
                    <div class="introduction1">
                      <div>
                     
                        <div class="detail-book1">{{item.bookName}}</div>
                      </div>
                      <!-- <div style="margin-top:13px">
                        <img src="../images/authoricon.png" alt="">
                         <div class="detail-book">{{item.bookAuthor}}</div>
                      </div>                -->
                    </div>

                  </h4>
                </li>
                </ul>
              </ul>
              </div>
            </div>

            <!-- 活动日历 -->
            <!-- <div class="col-r">
              <div id="main1012297">
                <div class="m-tit1">
                  <h3 class="tit">活动日历</h3>
                  <div class="con-r">
                    <p class="btn-calendar"></p>
                  </div>
                </div>
                <el-calendar v-model="value" />
                <div class="activity-block">
                  <h3 class="title">活动</h3>
                  <router-link class="content" to="/Notice">{{calendarActivity}}</router-link>
                  <div class="con-r">
                
                  </div>
                </div>
              </div>
            </div> -->
          </div>
        </div>
      </div>

      <!-- 底栏 -->
      <div class="footer fix" style="position: static;background-color:rgb(38, 94, 109)">
        <div class="wp">
          <div class="ftl">
            <div class="lg">
              <a href="index.htm" onclick="">
                <img src="../assets/TongjiLogo.png" alt="" />
              </a>
            </div>
            <div class="addr">
              <p style="display: inline">
                <span>中国</span>&nbsp;<span>上海</span>&nbsp;<span>嘉定区</span>&nbsp;<span>同济大学图书馆</span>&nbsp;<span>电话：65982734</span>&nbsp;<span>邮箱：tongjilib@tongji.edu.cn</span>
              </p>
            </div>
            <div class="lk">
              <a href="https://www.tongji.edu.cn/" target="_blank">同济大学</a>
            </div>
            <div class="lk2">
              <a href="https://www.lib.tongji.edu.cn/" target="_blank">同济大学图书馆(官方)</a>
            </div>
          </div>
          <div class="ftr">
            <div class="qr">
              <div class="pic" onclick="">
                <img src="../images/code.png" alt="">
              </div>
              <p style="color: #fff">图书馆微信公众号</p>
            </div>
            <div class="c"></div>
            <div class="copy">
              <!-- 版权内容请在本组件"内容配置-版权"处填写 -->
              <p>
                <span>版权所有：</span><a href="https://www.lib.tongji.edu.cn/" target="_blank">同济大学图书馆</a>　版权声明
              </p>
            </div>
          </div>
        </div>
      </div>

    </el-main>
  </el-container>
</template>

<script>
import { reactive, ref } from 'vue'
import axios from 'axios'
// import qs from 'qs'

const value = ref(new Date())
export default {
  name: "HomePage",
  data() {
    return {
      // 是否折叠
      iscollapse: true,
      seachStr: '',
      notices: [
        {
          ID: '',
          releaseDate: '',
          text: '',
          title: '',
        },
        {
          ID: '',
          releaseDate: '',
          text: '',
          title: '',
        },
        {
          ID: '',
          releaseDate: '',
          text: '',
          title: '',
        },
        {
          ID: '',
          releaseDate: '',
          text: '',
          title: '',
        },
        {
          ID: '',
          releaseDate: '',
          text: '',
          title: '',
        },
        {
          ID: '',
          releaseDate: '',
          text: '',
          title: '',
        },
      ],
      calendarActivity:'无',
      date: [],
      yearMonth: [],
      chairsUsed: 0,
      booknames: [],

      topbook:[
        {
          bookName:'',
          bookAuthor:'',
          bookPublisher:'',
          bookImg:'',
          isbn:'',
        }
      ],    
    }
  },

  methods: {
    toggleCollapse() {
      this.iscollapse = !this.iscollapse;
      var elmt = document.querySelector(".main-page-pad");
      if (this.iscollapse == false) {
        document.querySelector(".header.open").style.left = "162px";
        elmt.style = "padding-left:200px!important";
        // window.alert(e.className);
      
      } else {
        document.querySelector(".header.open").style.left = "26px";
        elmt.style = "padding-left:0px!important";
      }
    },
    clickNotice(title, time, text) {
      this.$router.push({ path: "/notice", query: { title: title, time: time, text: text } });
    },
    getBookInfo() {
      axios({
        method: 'get',
        url:'/top-books/get'
      }).then(response => {

        this.$data.topbook = response.data;
        console.log(this.$data.topbook);
      })
    },
    searchBooks(bookstr) {
      // axios({
      //   method: 'post',
      //   url: '/api/Book/querybookandsearch',
      //   params: {
      //     searchstr: bookstr
      //   }
      // })
      //   .then(res => {
      //     console.log(res)
      //   })
      //   .catch(err => {
      //     console.error(err);
      //   })
      // 跳转到搜索页
      
      this.$router.push({
        path: '/bookCheck',
        query: { keyword: bookstr }
      });


    },
    toBook(isbn) {
      this.$router.push({
        path: '/book',
        query: { isbn: isbn }
      })
    }
  },

  mounted() {
    let nts = [];
    axios({
      method: 'get',
      url: '/notice/GetNotice',
      // timeout: '500'
    }).then(response => {
      var length = response.data.length;
      // this.$data.notices = response.data;
      for (let i = 0; i < 6; i++) {
        this.$data.notices[i] = response.data[length - i - 1];
        this.$data.date[i] = this.$data.notices[i].releaseDate.substring(8);
        this.$data.yearMonth[i] = this.$data.notices[i].releaseDate.substring(0, 4) + '.' +
          this.$data.notices[i].releaseDate.substring(5, 7);
      }
      // console.log(this.$data.notices)
      // console.log(this.$data.yearMonth);
      var d = new Date();
      var today = d.getFullYear() + '-' + d.getMonth() + '-' + d.getDate();
      if (today == this.$data.notices[0].releaseDate)
        this.$data.calendarActivity = this.$data.notices[0].title;
      // console.log(this.$data.notices[4].RELEASE_DATE);
    }).catch(error => {
      console.log('@', error)
    });

    axios({
      method: 'get',
      url: '/entry-appointment/getNumber',
    }).then(response => {
      this.$data.chairsUsed = response.data;
    }).catch(error => {
      console.log('@', error)
    });

    

    this.getBookInfo();
    // console.log('@', this.$data.book_img);
  }
 
}


</script>

<style>

@import "../StyleSheet/style.css";
@import "../StyleSheet/index.vsb.css";
@import "../StyleSheet/robot.css";
@import "../StyleSheet/slick.css";
@import "../StyleSheet/swiper.css";

/* .calendar-activity {
  height:50px;
  padding:"12px 10px"
} */

/*

.m-tit1 .more em {
    color: #949494;
    font-size: 16px;
    margin-left: 8px
}
*/
.detail-pad{
  margin-right: 5px!important;
}

.top-num {
  background: #4b7e8b;
  color: #f0f2f4;
  border-radius: 20px;
  width: 35px;
}

.top-num-other {
  width: 35px;
  font-size: 13px!important;
}

.wp .col-l .p-list1 .tit img{
  height:80px;
  float:left;
}

.introduction {
  float:left;
}

.wp .col-l .p-list1 .tit .introduction img{
  height:20px;
}

.detail-book {
  width: 145px;
  height: 20px;
  font-size: 10px;
  letter-spacing: 2.5px;
  font-weight: 530;
  color: #000;
  background-color: #dbdfe4;
  border: none;
  border-radius: 45px;
  -webkit-box-shadow: 0px 8px 15px rgb(0 0 0 / 10%);
  box-shadow: 0px 8px 15px rgb(0 0 0 / 10%);
  -webkit-transition: all 0.3s ease 0s;
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
  margin-left: 30px;
  position: relative;
  /* padding-left: 20px; */
  line-height: initial;
  text-align: center;
}
.detail-book1 {
  width: 125px;
  height: 25px;
  font-size: 10px;
  letter-spacing: 2.5px;
  font-weight: 530;
  color: #000;
  background-color: #dbdfe4;
  border: none;
  border-radius: 45px;
  -webkit-box-shadow: 0px 8px 15px rgb(0 0 0 / 10%);
  box-shadow: 0px 8px 15px rgb(0 0 0 / 10%);
  -webkit-transition: all 0.3s ease 0s;
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
  margin-left: 10px;
  position: relative;
  /* padding-left: 20px; */
  line-height: initial;
  text-align: center;
}

.detail-last-top {
  width: 210px!important;
}

.activity-block {
  overflow: hidden;
  height: 50px;
  margin-right: 20px;
}

.activity-block .title {
  float: left;
  font-size: 16px;
  color: #29293a;
  font-weight: 700;
  margin-right: 10px;
  margin-left: 20px
}

.activity-block .content {
  height: 40px;
  width:72%;
  float: left;
  border-left: 4px solid #265e6d;
  font-size: 14px;
  color: #000000;
  padding-left: 10px;
  margin-right: 10px;
}

.activity-block .content:hover {
  color: #b80308
}

.el-calendar-day {
  height: 40px!important;
}
.grid-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* 每行两列 */
  grid-gap: 20px; /* 列之间的间距 */
}


.main-page-pad {
  padding:0%!important;
  overflow: hidden!important;
  padding-top: 0%!important;
  padding-right: 0%!important;
  padding-bottom: 0%!important;
  padding-left:0px!important;
}

.toggle-button {
  color: white;
  text-align: center;
  line-height: 34px;
  letter-spacing: 0.2em;
  cursor: pointer;

}

.el-aside {
  background-color: rgb(59, 96, 113);
}

.link1 {
  background-image: url(../images/icons05.png)
}

.link2 {
  background-image: url(../images/icons07.png)
}

.link3 {
  background-image: url(../images/icons08.png)
}

.col-l:hover {
  box-shadow: -2px 2px 10px rgb(126, 126, 126);
}

.col-c:hover {
  box-shadow: -2px 2px 10px rgb(126, 126, 126);
}

.col-r:hover {
  box-shadow: -2px 2px 10px rgb(126, 126, 126);
}
.hands{
  cursor: pointer;  /*鼠标悬停变小手*/
}
</style>