<template>
    <div class="login_wrap">
        <div class="form_wrap">
            <el-form ref="formRef" :model="loginData" :rules="rules" class="demo-dynamic">
                <div class="content">
                    <h1 style=" font-size: 30px;">前台借还书管理系统</h1>
                    <h1>账号登录</h1>
                    <h3>账号(手机号)</h3>
                    <el-form-item prop="userid" label="">
                        <el-input v-model="loginData.userid" />
                    </el-form-item>
                    <h3>密码</h3>
                    <el-form-item prop="password" label="">
                        <el-input type="password" v-model="loginData.password" />
                    </el-form-item>
                    <el-form-item>
                        <el-button class="login_btn" @click="resetForm">重置</el-button>
                        <el-button class="login_btn" @click="handleLogin">进入</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
    </div>
</template>
<script>
import axios from 'axios'
import { ElMessage } from 'element-plus'
export default {
    data() {
        return {
            // 登录数据，数据绑定对象
            loginData: {
                userid: '',
                password: ''
            },
            // 表单的验证规则
            rules: {
                // 验证账号是否合法
                userid: [
                    { required: true, message: "请填写账号", trigger: "blur" },
                    { pattern: /^1[3|5|7|8|9]\d{9}$/, message: "请输入正确的手机号码", trigger: "blur" }
                ],
                // 验证密码是否合法
                password: [
                    { required: true, message: "请填写密码", trigger: "blur" },
                    { min: 5, max: 15, message: "长度在5到15个字符之间", trigger: "blur" }
                ],
            }
        }
    },
    methods: {
        // 点击重置按钮，重置登录表单
        resetForm() {
            this.$refs.formRef.resetFields();
        },
        // 触发登录
        handleLogin() {
            // 登录前的表单预验证，保证输入合法
            this.$refs.formRef.validate((valid) => {
                // console.log(valid);
                if (!valid) return;
                axios({
                    method: 'post',
                    params: {
                        userid: this.loginData.userid,
                        password: this.loginData.password
                    },
                    url: '/user/login',//Loginwithusernameandpassword,signin
                }).then((res) => {
                    console.log('/a1', res)
                    console.log(res.data)
                    if (res.data.information=="wrong password") {
                        ElMessage({
                            message: '密码错误！',
                            type: 'warning',
                        })
                        console.log('登录失败！')
                    }
                    else if(res.data.information=="unregistered")  {
                        ElMessage({
                            message: '该账号尚未注册！',
                            type: 'warning',
                        })
                        console.log('登录失败！')
                    }
                    else if(res.data.information=="administrator")
                    {
                        ElMessage({
                            message: '登录成功！',
                            type: 'success',
                        })
                        console.log('登录成功！')
                        // console.log(res.config.params.userid);
                        window.sessionStorage.setItem('userid', res.config.params.userid)
                        this.$router.push('/Reserve')

                        localStorage.setItem("token",res.data.token)	//保存token
                        localStorage.setItem("userid",res.config.params.userid)	//保存userid
                    }
                    else{
                        ElMessage({
                            message: '该账号无权限！',
                            type: 'warning',
                        })
                        console.log('登录失败！')
                    }
                }, error => {
                    ElMessage({
                        message: '登录失败！',
                        type: 'warning',
                    })
                    console.log('登录失败！')
                    console.log('错误', error.message)
                })
            })
        }
    }

}
</script>

<style lang="less" scoped>
* {
    padding: 0;
    margin: 0;
    box-sizing: border-box;
}

/* 消除小点 */
li {
    list-style: none;
}

/* 消除下划线 */
a {
    text-decoration: none;
}

/* flex布局类名 */
.flex-float {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.flex {
    display: flex;
    align-items: center;
}

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
    padding: 20px 0;
}

.login_wrap {
    width: 100%;
    height: 100vh;
    position: reactive;
    display: flex;
    justify-content: center;
    align-items: center;
    background-image: url(../../assets/lbg.jpg);
    background-size: 100%;
}

.form_wrap {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background: #fff;
    /* padding: 30px 50px; */

    width: 900px;
    height: 560px;
    display: flex;
    border-radius: 20px;
    overflow: hidden;
    box-shadow: 0 25px 45px rgba(0, 0, 0, 0.3);
}

.demo-dynamic {
    width: 900px;
    height: 560px;
    background: rgba(255, 255, 255, 0.9);
    display: flex;
    align-items: center;
    justify-content: center;
}

.demo-dynamic .content {
    width: 70%;
    /* height: 421.2px; */
    display: flex;
    flex-direction: column;
    text-align: center;
}

.first-login {
    text-align: center;
    font-size: 16px;
    font-weight: 400;
    color: #55855a;
    padding: 20px 0;
}

.login_btn {
    width: 48%;
    height: 40px;
    border-radius: 20px;
    border: none;
    // color: rgb(18, 17, 17);
    color: rgb(51, 85, 97);
    cursor: pointer;
    margin-top: 50px;
    background-image: url(../../assets/lbutton.jpg);
    background-size: 250%;
}
</style>