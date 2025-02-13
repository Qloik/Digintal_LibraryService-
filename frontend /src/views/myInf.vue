<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb :separator-icon="ArrowRight">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>个人主页</el-breadcrumb-item>
      <el-breadcrumb-item>我的信息</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 第一行的个人信息 -->
    <el-row :gutter="40">
      <!-- 第一列的头像信息和数据统计 -->
      <el-col :span="15">
        <el-row :gutter="40">
          <el-card class="box-head" style="width: 100%" shadow="hover">
            <el-card class="box-head1" style="width: 100%" shadow="hover">
              <el-col :span="20">
                <div class="first">
                  <!-- <el-avatar :size="100" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" /> -->

                  <el-avatar :size="100" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                    @click="UploadVisible = true" />

                  <!-- <el-dialog title="修改头像" v-model="UploadVisible" width="50%">
                    <el-form ref="userInfoForm" :model="userInfoForm" class="user-form" label-width="100px">

                      <el-form-item prop="avatar">
                        <el-upload class="avatar-uploader"
                          action="https://jsonplaceholder.typicode.com/posts/" :show-file-list="false"
                          :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                          <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                          <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                          </el-icon>
                        </el-upload>
                      </el-form-item>

                    </el-form>
                    <template #footer>
                      <span class="dialog-footer">
                        <el-button @click="UploadVisible = false">取消</el-button>
                        <el-button type="primary" @click="Commit">修改</el-button>
                      </span>
                    </template>
                  </el-dialog> -->


                </div>
              </el-col>
              <el-col :span="10">
                <div class="ava1">
                  <span style="font-weight: bold">
                    用户名：<span class="big-number">
                      {{ tableData2[0].username }}
                    </span> </span><br /><br />
                  <span style="font-weight: bold">
                    登录状态:
                    <span class="big-number">
                      <el-button type="success" round>在线</el-button>
                    </span></span>
                </div>
              </el-col>
              <el-col :span="20">
                <div class="ava2">
                  <el-progress type="dashboard" :percentage="thebooknum" width="100" stroke-width="15">
                    <template #default="{ thebooknum }">
                      <span class="percentage-value">{{ thebooknum }}</span>
                      <span class="percentage-label">借阅图书</span>
                    </template>
                  </el-progress>
                </div>
                <div class="ava3">
                  <el-progress type="dashboard" :percentage="thestatenum" width="100" stroke-width="15">
                    <template #default="{ thestatenum }">
                      <span class="percentage-value">{{ thestatenum }}</span>
                      <span class="percentage-label">场地预约</span>
                    </template>
                  </el-progress>
                </div>
              </el-col>
            </el-card>
          </el-card>
        </el-row>

        <el-row :gutter="40">
          <el-card class="box-data" style="width: 100%" shadow="hover">
            <template #header>
              <div class="card-header">
                <span style="font-weight: bold">最新动态</span>
              </div>
            </template>

            <el-table class="hands" :data="tableData" :show-header="false" style="width: 100%" max-height="170"
              @row-click="clickNotice">
              <el-table-column prop="title" width="350">
                <template #default="scope">
                  <a target="_blank">
                    {{ scope.row.title }}
                  </a>
                </template>
              </el-table-column>
              <el-table-column prop="releaseDate" align="right">
              </el-table-column>
            </el-table>
          </el-card>
        </el-row>
      </el-col>

      <!-- 第二列的个人信息详情 -->
      <el-col :span="8">
        <el-card class="box-infor" shadow="hover">
          <template #header>
            <div class="card-header">
              <span style="font-weight: bold">个人信息</span>
            </div>
          </template>
          <div class="infor" :data="tableData2">
            用户昵称： {{ tableData2[0].username }}<br />
            用户 ID ： {{ userid }}<br />
            性别 ： {{ tableData2[0].sex }}<br />
            年龄： {{ tableData2[0].age }}<br />
            登录身份： {{ tableData2[0].nickName }}<br />
            电子邮箱： {{ tableData2[0].usermail }}<br />

            <el-button class="code0" v-if="
              tableData2[0].ifAdministrator == 1   //判断是否是管理员
            " type="primary" round text @click="handleShift">管理员入口</el-button>

            <!-- 修改密码弹出框 -->
            <el-button
              class="code"
              type="primary"
              :before-close="handleClose"
              round
              text
              @click="dialogVisible2 = true"
              >修改密码</el-button
            >
           
            <el-dialog
              v-model="dialogVisible2"
              title="修改密码"
              v-bind="dialogVisible2"
            >
              <el-form
                ref="form"
                :model="user"
                :rules="rules"
                label-width="80px"
              >
                <!-- 输入内容 -->
                <el-form-item label="账号" prop="userid">
                  <el-input v-model="user.userid" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="旧密码" prop="password">
                  <el-input v-model="user.password" placeholder="请输入旧密码" type="password" />
                </el-form-item>
                <el-form-item label="新密码" prop="passwordupdated">
                  <el-input v-model="user.passwordupdated" placeholder="请输入新密码" type="password" autocomplete="off">
                  </el-input>
                </el-form-item>
              </el-form>

              <template #footer>
                <span class="dialog-footer">
                  <el-button @click="dialogVisible2 = false">取 消</el-button>
                  <el-button type="primary" size="mini" @click="submit">保存</el-button>
                </span>
              </template>
            </el-dialog>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios"
import { ElMessageBox } from "element-plus"

export default {
  inject: ["reload"],
  data() {
    return {
      //修改密码的user
      user: {
        userid: "",
        password: "",
        passwordupdated: "",
      },
      // 表单的验证规则
      rules: {
        userid: [
          { required: true, message: "请填写账号", trigger: "blur" },
          {
            min: 5,
            max: 15,
            message: "长度在5到15个字符之间",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入原密码", trigger: "blur" },
          {
            min: 6,
            max: 15,
            message: "长度在6到15个字符之间",
            trigger: "blur",
          },
        ],
        newPassword: [
          { required: true, message: "请输入新密码", trigger: "blur" },
        ],
      },
      //通知公告
      tableData: [],
      //个人信息
      tableData2: [{}],
      //通知显示
      dialogVisible: false,
      //修改密码页面显示
      dialogVisible2: false,
      userid: window.sessionStorage.getItem("userid"),
      //用户预定场馆总数
      thestatenum: "",
      //用户借阅图书总数
      thebooknum: "",

      UploadVisible: false,
      imageUrl: '',
    };
  },
  //页面初始化自动处理
  mounted() {
    //获取通知公告内容
    axios.get("/notice/GetNotice").then((res) => {
      console.log("notice:", res);
      this.$data.tableData = res.data;
    }),
      //获取用户个人信息
      axios({
        method: "post",
        params: {
          userid: this.userid,
        },
        url: "/user/myInf",
      }).then(
        (res) => {
          console.log("/user/myInf", res);
          this.$data.tableData2 = res.data;
        },
        (error) => {
          console.log("错误", error.message);
        }
      );

    //获取用户借阅书籍数量
    axios({
      method: "post",
      params: {
        userid: this.userid,
      },
      url: "/borrowed-book/countborrowed",
    }).then(
      (res) => {
        console.log("inf", res);
        this.$data.thebooknum = res.data;
        if (res.data == true) {
          // this.$message({
          //   message: "成功",
          //   type: "success",
          // });
        } else if (res.data == false) {

        }
      },
      (error) => {
        console.log("错误", error.message);
      }
    );

    //获取用户预约场馆数量
    axios({
      method: "post",
      params: {
        userid: this.userid,
      },
      url: "/room-appointment/theroomnum",
    }).then(
      (res) => {
        console.log("inf", res);
        this.$data.thestatenum = res.data;
        if (res.data == true) {
          // this.$message({
          //   message: "成功",
          //   type: "success",
          // });
        } else if (res.data == false) {

        }
      },
      (error) => {
        console.log("错误", error.message);
      }
    );
  },
  //响应事件处理
  methods: {
    //post修改密码
    submit() {
      console.log(this.user);
      this.$refs["form"].validate((valid) => {
        if (valid) {
          axios({
            method: "post",
            params: {
              userid: this.userid,
              password: this.user.password,
              passwordupdated: this.user.passwordupdated,
            },
            url: "/user/changepassword",
          }).then(
            (res) => {
              console.log("submit", res);
              if (res.data == true) {
                this.$message({
                  message: "修改成功！",
                  type: "success",
                });
              } else if (res.data == false) {
                this.$message({
                  message: "密码错误！",
                  type: "warning",
                });

              }
            },
            (error) => {
              console.log("错误", error.message);
            }
          );
          this.reload();
        }
      });
    },
    handleClick() {
      console.log("预约入馆");
    },
    // 触发进入管理员界面
    handleShift() {
      this.$router.push('/Manager')
    },


    clickNotice(row) {
      let thisRowData = this;
      thisRowData = row;
      console.log(row, 444);
      this.$router.push({
        path: "/Notice",
        query: { title: row.title, time: row.releaseDate, text: row.text },
      });
    },
    clickManager(row) {
      let thisRowData0 = this;
      thisRowData0 = row;
      console.log(row, 444);
      this.$router.push({
        path: "/manager",
        query: { name: row.title, time: row.releaseDate, text: row.text },
      });
    },

    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    }


  },
};
</script>

<style lang="less" scoped>
.dialog-footer button:first-child {
  margin-right: 10px;
}

.box-head {
 
  margin-top: 20px;
  margin-bottom: 10px;
  font-size: 20px;
  margin-left: 70px;
}

.box-head1 {
 
  margin-top: 20px;
  margin-bottom: 10px;
  font-size: 20px;
  height: 200px;
}

.box-data {
  position: relative;

  margin-top: 20px;
  margin-bottom: 10px;
  margin-left: 70px;
}

.box-infor {
 
 
  margin-top: 25px;
  margin-bottom: 10px;
  margin-left: 40px;
  margin-right: 10px;
}

.box-dynamic {
  margin-top: 20px;
  margin-bottom: 10px;
  margin-left: 40px;
  margin-right: 20px;
}

.card-header {
  font-size: 20px;
}

.box-head .span {
  margin-left: 20px;
}

.text {
  font-size: 14px;
}

.content {
  font-size: 15px;
  margin-left: 70px;
}

.item {
  padding: 18px;
}

.box-card {
  width: 480px;
}

.first {
  position: relative;
  left: 12px;
  top: 20px;
}

.ava1 {
  position: relative;
  top: -65px;
  left: 150px;
  margin-bottom: 40px;
}

.ava2 {
  position: relative;
  top: -200px;
  left: 380px;
  margin-bottom: 40px;
}

.ava3 {
  position: relative;
  top: -345px;
  left: 530px;
  margin-bottom: 40px;
}

.infor {
  margin-top: 20px;
  margin-bottom: 20px;
  margin-left: 30px;
  line-height: 3.5;
  font-size: 17px;
}

.code0 {
  margin-top: 15px;
  margin-left: 0px;
  font-size: 17px;
}

.code {
  margin-top: 15px;
  margin-left: 160px;
  font-size: 17px;
}

.data {
  margin-left: 20px;
  line-height: 1.5;
  font-size: 17px;
}

.big-number {
  margin-left: 20px;
  font-weight: bold;
  color: #3d3f41;
}

a {
  text-decoration: none;
  color: #004e90;
}



.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.hands{
  cursor: pointer;  /*鼠标悬停变小手*/
}
</style>