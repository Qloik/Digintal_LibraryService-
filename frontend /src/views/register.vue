<template>
    <div class="register">
        <div class="contain">
            <div class="big-box">
                <div class="big-contain" key="bigContainRegister">
                    <!-- <div class="btitle">创建账户</div> -->
                    <h1>创建账户</h1>
                    <el-form ref="formRef" :model="registerData" :rules="rules" class="demo-dynamic">
                        <div class="content">
                            <h3>昵称</h3>
                            <el-form-item prop="nickname" label="">
                                <el-input v-model="registerData.nickname" />
                            </el-form-item>
                            <h3>邮箱</h3>
                            <el-form-item prop="usermail" label="">
                                <el-input v-model="registerData.usermail" />
                            </el-form-item>
                            <h3>手机号</h3>
                            <el-form-item prop="userid" label="">
                                <el-input v-model="registerData.userid" />
                            </el-form-item>
                            <h3>密码</h3>
                            <el-form-item prop="password" label="">
                                <el-input type="password" v-model="registerData.password" />
                            </el-form-item>
                            <h3>确认密码</h3>
                            <el-form-item prop="check" label="">
                                <el-input type="password" v-model="registerData.check" />
                            </el-form-item>
                            <!-- <input type="email" placeholder="邮箱" v-model="form.useremail">
                        <input type="password" placeholder="密码" v-model="form.userpassword"> -->
                        </div>
                    </el-form>
                    <!-- <button class="bbutton" @click="handleRegister">注册</button> -->
                    <div>
                        <el-button class="bbutton" @click="handleRegister">注册</el-button>
                        <el-button class="bbutton" @click="returnLogin">返回登录</el-button>
                    </div>
                </div>
            </div>

            <div class="small-box">

                <div class="small-contain" key="smallContainRegister">
                    <br><br><br><br>
                    <div class="stitle">Hello，同学!</div>
                    <p class="scontent"> <b>进入图书馆，和我们一起学习</b> </p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
export default {
    data() {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.registerData.check !== '') {
                    this.$refs.formRef.validateField('check');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.registerData.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            // 登录数据，数据绑定对象
            registerData: {
                nickname: '',
                usermail: '',
                userid: '',
                password: '',
                check: '',

                // nickname: 'xiaoming',
                // usermail: 'xiaoming@qq.com',
                // userid: '13609876543',
                // password: '123456',
                // check: '123456',
            },
            // 表单的验证规则
            rules: {
                // 验证用户名是否合法
                nickname: [
                    { required: true, message: "请填写昵称", trigger: "blur" },
                    { min: 2, max: 15, message: "长度在2到15个字符之间", trigger: "blur" }
                ],
                // 验证邮箱是否合法
                usermail: [
                    { required: true, message: "请填写邮箱", trigger: "blur" },
                    { type: 'email', message: "请填写正确的邮箱格式", trigger: "blur" }
                ],
                //验证手机号
                userid: [
                    { required: true, message: "请填写手机号", trigger: "blur" },
                    { pattern: /^1[3|5|7|8|9]\d{9}$/, message: "请输入正确的号码格式", trigger: "blur" }
                ],
                // 验证密码是否合法
                password: [
                    { validator: validatePass, trigger: "blur" },
                    { min: 5, max: 16, message: "长度在5到16个字符之间", trigger: "blur" }
                ],
                check: [
                    { validator: validatePass2, trigger: "blur" },
                ],

            }
        }
    },
    methods: {
        returnLogin() {
            this.$router.push('/login')
        },
        handleRegister() {
            // console.log('start')
            // 登录前的表单预验证，保证输入合法
            this.$refs.formRef.validate((valid) => {
                // console.log(valid);
                // console.log('start')
                if (!valid) return;
                ElMessageBox.confirm(
                    '确认您的信息正确？',
                    'Warning',
                    {
                        confirmButtonText: '确认',
                        cancelButtonText: '取消',
                        type: 'warning',
                    }
                ).then(() => {
                    axios({
                        method: 'post',
                        params: {
                            userid: this.registerData.userid,
                            nickname: this.registerData.nickname,
                            usermail: this.registerData.usermail,
                            password: this.registerData.password,
                        },
                        url: '/user/register',
                    }).then((res) => {
                        console.log('/a1', res);
                        console.log(res.data)
                        if (res.data==1) {
                            ElMessage({
                            type: 'success',
                            message: `注册成功！`,
                            });
                            console.log('注册成功！') 
                        }
                        else{
                            ElMessage({
                            type: 'warning',
                            message: `注册失败！`,
                            });
                        }
                        
                    })
                }).catch(() => {
                    ElMessage({
                        type: 'info',
                        message: '已取消注册',
                    });
                });
            })
        }

    }

}
</script>
<style scoped="scoped">
h1 {
    font-size: 26px;
    color: #70b4e3;
    font-weight: 400;
    padding-bottom: 10px;
}

h3 {
    text-align: left;
    font-size: 16px;
    font-weight: 400;
    color: #70b4e3;
    padding: 2px 0;
}

.register {
    width: 100vw;
    height: 100vh;
    box-sizing: border-box;
    /* background-image: linear-gradient(to right top, #d16ba5, #c777b9, #ba83ca,
            #aa8fd8, #9a9ae1, #8aa7ec, #79b3f4, #69bff8, #52cffe, #41dfff, #46eefa, #5ffbf1); */
    background-image: url(../assets/lbg.jpg);
    background-size: 100%;
}

.demo-dynamic {
    width: 900px;
    height: 380px;
    background: rgba(255, 255, 255, 0.9);
    display: flex;
    align-items: center;
    justify-content: center;
}

.demo-dynamic .content {
    width: 60%;
    /* height: 421.2px; */
    display: flex;
    flex-direction: column;
    text-align: center;
}

.contain {
    width: 80%;
    height: 75%;
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    border-radius: 20px;
    overflow: hidden;
    /* box-shadow: 0 0 3px #f0f0f0,
        0 0 6px #f0f0f0; */
    box-shadow: 0 25px 45px rgba(0, 0, 0, 0.3);

}

.big-box {
    width: 70%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 30%;
    transform: translateX(0%);
    transition: all 1s;
}

.big-contain {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}


.bform {
    width: 80%;
    height: 40%;
    padding: 2em 0;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    /* align-items: center; */
}

.bform .errTips {
    display: block;
    width: 50%;
    text-align: left;
    color: red;
    font-size: 0.7em;
    margin-left: 1em;
}

.bform input {
    width: 50%;
    height: 30px;
    border: none;
    outline: none;
    border-radius: 10px;
    padding-left: 2em;
    background-color: #f0f0f0;
}

.bbutton {
    margin-top: 5px;
    width: 45%;
    height: 40px;
    border-radius: 24px;
    border: none;
    outline: none;
    /* background-image: linear-gradient(120deg, #76daec, 0%, #c5a8de 100%); */
    background-image: url(../assets/lbutton.jpg);
    background-size: 800%;
    /* color: #fff; */
    color: rgb(51, 85, 97);
    font-size: 0.9em;
    cursor: pointer;
}

.small-box {
    width: 30%;
    height: 100%;
    background: linear-gradient(135deg, rgb(44, 134, 142), rgb(55, 154, 124));
    position: absolute;
    top: 0;
    left: 0;
    transform: translateX(0%);
    transition: all 1s;
    border-top-left-radius: inherit;
    border-bottom-left-radius: inherit;
}

.small-contain {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    /* justify-content: center; */
    align-items: center;
    background-image: url(../assets/lbg.jpg);
    background-size: 295%;
}

.stitle {
    font-size: 1.5em;
    font-weight: bold;
    color: #fff;
    color: rgb(51, 85, 97);
}

.scontent {
    font-size: 1.0em;
    /* color: black; */
    color: #fff;
    color: rgb(51, 85, 97);
    text-align: center;
    padding: 2em 4em;
    line-height: 1.7em;
}

.sbutton {
    width: 60%;
    height: 40px;
    border-radius: 24px;
    border: 1px solid #fff;
    outline: none;
    background-color: transparent;
    color: #fff;
    font-size: 0.9em;
    cursor: pointer;
}

.big-box.active {
    left: 0;
    transition: all 0.5s;
}

.small-box.active {
    left: 100%;
    border-top-left-radius: 0;
    border-bottom-left-radius: 0;
    border-top-right-radius: inherit;
    border-bottom-right-radius: inherit;
    transform: translateX(-100%);
    transition: all 1s;
}
</style>