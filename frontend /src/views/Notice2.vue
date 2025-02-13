<template>
    <!-- 头部区域 -->
    <el-container class="home-container">
        <el-main>
            <div class="content">
                <center>
                    <tr>
                        <div style="color:black; font-size: 25px; font-weight: bold;">                       
                            {{title}}
                        </div>
                    </tr>
                    <p></p>
                    <tr>
                        <div style="color:grey; font-size: 13px">                           
                            {{time}}
                        </div>
                    </tr>
                    <el-divider></el-divider>
                </center>
                <span style="line-height:35px; white-space: pre-wrap">
                    {{ text }}
                </span>
            </div>

        </el-main>

        <el-footer></el-footer>
    </el-container>
</template>

<script>
import axios from 'axios'
export default {
    data() {
    return {
      text:'',
      title:'',
      time:'',
    }
  },
  methods: {
  },
    mounted() {
        console.log(this.$route.query.id, 7777);
        // axios({
        //     method: 'post',
        //     data: this.$route.query.id,
        //     url: 'http://localhost:8080/getNotice2',
        // }).then((res) => {
        //     console.log(res)
        //     this.$data.text = res.data.text
        //     this.$data.title = res.data.title
        //     this.$data.time = res.data.time
        // }, error => {
        //     console.log('错误', error.message)
        // })
        this.$data.text = this.$route.query.text
        this.$data.title = this.$route.query.name
        this.$data.time = this.$route.query.time
        
    },
}
</script>


<style lang="less" scoped>
.home-container {
    min-height: 100%;
}

.el-header {
    // background-color: #55626b;
    background-color: #004E90;
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
        // width: 20px;
    }
}

.el-main {
    //background: #d3dce6;
    background-image: url("../assets/notice-bg.jpg");
    // background-size: cover;
    padding-top: 2%;
    padding-bottom: 2%;
    background-position: 50% 50%;
}

.el-footer {
    background-color: rgb(59, 96, 113);
    align-items: center;
}

.content {
    width: 800px;
    min-height: 450px;
    padding: 4%;
    padding-top: 2%;

    left: 0;
    right: 0;
    margin: auto;

    background: #fff;
}

.navi {
    width: 500px;
    padding-right: 10%;
}

a {
    text-decoration: none;
    color: #fff;
    font-size: 15px;
}
</style>