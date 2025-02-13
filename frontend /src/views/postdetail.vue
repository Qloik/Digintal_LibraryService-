<template>
  <el-container class="home-container">
    <div class="plate_show">
      <div class="backgroup">
        <img style="width: 100%" src="../assets/lbutton.jpg" alt="" />
      </div>
      <el-divider></el-divider>

      <el-card :body-style="{ padding: '3%' }" shadow="never">
        <div style="font-size: 27px; padding-bottom: 10px; font-weight: bold; color:#494949;">
          {{ postname }}
        </div>
        <header>
          <img src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" width="30">
          <span style="display: inline-block; overflow: hidden; height: 23px; padding-left: 10px;">{{
            nickname
          }}</span>
          <span
            style="color:grey; font-size: 13px; display: inline-block; overflow: hidden; height: 21px; padding-left: 15px;">{{
              time
            }}</span>
        </header>

        <div style="font-size: 17px; padding-top: 1%;line-height:33px; padding-bottom: 3%;">
          <span style="white-space: pre-wrap;">{{ content }}</span>
        </div>

        <span style="color:grey; font-size: 14px;">
          © 本文版权归作者 {{ nickname }} 所有，任何形式转载请联系作者。
        </span>
      </el-card>


      <el-divider></el-divider>
      <div style="padding-bottom:2%">
        <span style="font-size: 25px; font-weight: bold; color:#494949; ">评论 ({{ count }})</span>
      </div>

      <el-col v-for="(item) in tabledata" :key="item.classId">
        <div>
          <el-card :body-style="{ padding: '3%' }" shadow="hover" @click="test(item)">
            <div id="left" style="float:left ; width:8%; height:100%;padding-right: 1%;">
              <img src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" width="100">
            </div>

            <div id="right" style="float:right ; width:90%; min-height:140px; padding-bottom: 2%;">
              <header style="background-color: #E7F2F4;">
                <span
                  style="font-size: 20px; display: inline-block; overflow: hidden; height: 25px; padding-left: 10px;">{{
                    item.nickName
                  }}</span>
                <span
                  style="color:grey; font-size: 15px; display: inline-block; overflow: hidden; height: 20px; padding-left: 20px;">{{
                    item.time
                  }}</span>
              </header>
              <div style="font-size: 17px;display: flex; padding-top: 1%;line-height:33px;">
                <span style="white-space: pre-wrap;">{{ item.content }}</span>
              </div>
            </div>
          </el-card>
        </div>
      </el-col>

      <el-divider></el-divider>

      <div style="padding-bottom:10%">
        <center>
          <button class="button" @click="dialogFormVisible = true">发布评论</button>
        </center>
      </div>

      <el-dialog v-model="dialogFormVisible" title="评论">
        <el-form :model="form">
          <el-form-item label="内容" :label-width="formLabelWidth">
            <el-input v-model="form.text" :rows="8" type="textarea" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click="Commit" v-loading.fullscreen.lock="fullscreenLoading">发布</el-button>
          </span>
        </template>
      </el-dialog>

    </div>
  </el-container>
</template>

<script>
import axios from "axios"
import { ElMessage } from 'element-plus'
export default {
  inject: ['reload'],
  data() {
    return {
      id: '',
      postname: '',
      content: '',
      time: '',
      nickname: '',

      count: 0,

      tabledata: [
        // {
        //   content: "顺着讨论一下。其实作家的写作都有一种一以贯之的思维逻辑，或者说哲学思考。所以早期后期区分可能不明显，因为他的哲学没变。",
        //   time: "2022-11-27 08:23:45",
        //   nickname: "climo"
        // },
        // {
        //   content: "95年左右我在高中同学家见到第一本《科幻世界》。看到的第一篇是王晋康的《生死平衡》。当时我就震惊了。 科幻小说原来可以不是那个样子，原来可以是这个样子。在这之前，我对科幻小说的概念建立在小学时读过的一本中国科幻作品集的基础之上。",
        //   time: "2022-11-27 08:23:45",
        //   nickname: "climo"
        // },
        // {
        //   content: "浩淼的宇宙是渊薮，对于一个文科生，看的很慢。咱不说政治可能更加轻松，快乐承受不了那么些制度条条框框，憋死人的会。在宇宙生还是亡面前，你活过，就行。诚实的说我这文盲硬科幻看得很艰难，但依旧滋滋有味，需要发明多少宇宙形容词，算得上是初识特斯拉得感觉~一个人对历史进程的影响哪怕再大，也不能以一己之力成为左右人类文明的奇点，这背后的经济力量、政治力量、科技水准等才是发展与改变的真正基石。",
        //   time: "2022-11-27 08:23:45",
        //   nickname: "jacky"
        // },
      ],
      dialogFormVisible: false,
      form: {
        text: '',
      },
      userid: window.sessionStorage.getItem('userid'),
      mynickname: '',
      fullscreenLoading: false,
    }
  },
  mounted() {
    this.$data.id = this.$route.query.id;
    this.$data.postname = this.$route.query.postname;
    this.$data.content = this.$route.query.content;
    this.$data.time = this.$route.query.time;
    this.$data.nickname = this.$route.query.nickname;

    axios({
      method: 'post',
      params: {
        postId: this.id,
      },
      url: '/review/queryByPostId',
    }).then((res) => {
      console.log('add', res)
      this.tabledata = res.data;
    }, error => {
      console.log('错误', error.message)
    })

    axios({
      method: 'post',
      params: {
        userid: this.userid,
      },
      url: '/user/myInf',
    }).then((res) => {
      this.$data.mynickname = res.data[0].nickName;
      console.log(this.$data.nickname);
    }, error => {
      console.log('错误', error.message)
    })

    axios({
      method: 'post',
      params: {
        postId: this.id,
      },
      url: '/posts/count',
    }).then((res) => {
      this.$data.count = res.data;
      console.log(this.$data.count);
    }, error => {
      console.log('错误', error.message)
    })

  },
  methods: {
    Commit() {
      this.dialogFormVisible = false;
      console.log(this.id);
      this.fullscreenLoading = true;
      axios({
        method: 'post',
        data: {
          content: this.form.text,
          userId: this.userid,
          postId: this.id,
          nickName: this.mynickname
        },
        url: '/review/save',
      }).then((res) => {
        console.log("res", res)
        if (res.data == true) {
          ElMessage({
            message: '发布成功',
            type: 'success',
          })
        }
        else if (res.data == false) {
          ElMessage.error('发布失败,请至个人主页查看失败信息')
        }
        this.reload();
        this.fullscreenLoading = false;
      }, error => {
        console.log('错误', error.message)
      })
    }
  }
}
</script>

<style scoped>
.home-container {
  background-color: #f4f4f4;
  height: 100%;
}

.plate_show {
  width: 95%;
  margin: 20px auto;
}

.button {
  width: 125px;
  height: 45px;
  font-size: 15px;
  letter-spacing: 2.5px;
  font-weight: 530;
  color: #000;
  background-color: #fff;
  border: none;
  border-radius: 45px;
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
}

.button:hover {
  background-color: #B4D8E0;
  box-shadow: 0px 15px 20px rgba(148, 188, 201, 0.4);
  color: #fff;
  transform: translateY(-3px);
}
</style>