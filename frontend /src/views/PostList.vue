<template>
    <el-container class="home-container">
        <div class="plate_show">
            <div style="padding-bottom:2%">
                <img style="width: 100%" src="../assets/lbutton.jpg" alt="" />

                <div style="padding:2%">
                    <center>
                        <button class="button" @click="dialogFormVisible = true">加入讨论</button>
                    </center>
                </div>
                <div style="padding-bottom:5%">
                    <el-col v-for="(item) in tabledata" :key="item.classId" class="hands">
                        <div>
                            <el-card :body-style="{ padding: '2%' }" shadow="hover" @click="test(item)">
                                <header>
                                    <img src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                                        width="30">
                                    <span
                                        style="display: inline-block; overflow: hidden; height: 23px; padding-left: 10px;">{{
                                                item.nickName
                                        }}</span>
                                    <span
                                        style="color:grey; font-size: 13px; display: inline-block; overflow: hidden; height: 21px; padding-left: 15px;">{{
                                                item.time
                                        }}</span>
                                </header>
                                <div style="color:black; font-size: 20px; padding: 10px;">
                                    {{ item.postname }}
                                </div>
                                <div style="color:grey; font-size: 13px; height: 40px; display: flex;">
                                    <span class="overNone" style="white-space: pre-wrap;">{{ item.content }}</span>
                                    
                                </div>

                            </el-card>
                        </div>
                    </el-col>
                </div>

                <el-dialog v-model="dialogFormVisible" title="发帖">
                    <el-form :model="form">
                        <el-form-item label="标题" :label-width="formLabelWidth">
                            <el-input v-model="form.name" autocomplete="off" />
                        </el-form-item>
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

                <center>
                    <span style="color:grey; font-size: 17px;">已经到底了</span>
                </center>

            </div>
        </div>
    </el-container>


</template>

<script>
import axios from "axios"
import { ElMessage } from 'element-plus'
export default {
    inject:['reload'],
    data() {
        return {
            tabledata: [
                // {
                //     id:"1",
                //     postname: "三体真好看",
                //     content: "《三体》：当代史的科幻书写 评论刘慈欣的《三体》是件困难的事情，因为可以选择的角度太多。 反复思量过后挑了这个角度，在我看来，写一部好的科幻小说，或者一部适合评论家们的科幻小说都不是难事，但是当作家的心思并不是用在这些上面，而是捎带着完成了这两件事的时候，就并不是那么容易了。版权归作者所有，任何形式转载请联系作者。用“宇宙史诗”来形容刘慈欣的《三体》系列我想并不过分——它对宇宙的起源、生命与环境间的相互作用、文明的演变、人类的整合，甚至宇宙的终极走向，都给出了自圆其说的明确猜想及推论；这一庞大的体系通过一些相关而分离的片段如冰山一角般呈现出了一个具有高度美感的思维体系，描述了永恒之初直至宇宙尽头的维度之旅；它确立了微观与宏观彼此渗透交错的依存关系，而贯穿其中的则是人类的情感，有激情的壮丽澎湃，有爱情的温柔微妙，也有绝情的冷酷黑暗。这一切的丰富片段、结构、内涵极其跨越的时间广度，我觉得也只能用“史诗”来进行总结，否则便不足以准确表达《三体》的气魄。",
                //     time: "2022-11-27 08:23:45",
                //     nickname: "climo"
                // },
                // {
                //     id:"2",
                //     postname: "放飞理想的土地",
                //     content: "95年左右我在高中同学家见到第一本《科幻世界》。看到的第一篇是王晋康的《生死平衡》。当时我就震惊了。 科幻小说原来可以不是那个样子，原来可以是这个样子。在这之前，我对科幻小说的概念建立在小学时读过的一本中国科幻作品集的基础之上。",
                //     time: "2022-11-27 08:23:45",
                //     nickname: "summer _q"
                // },
                // {
                //     id:"3",
                //     postname: "因了他的思想，让我看到更远处！",
                //     content: "《三体》：当代史的科幻书写 评论刘慈欣的《三体》是件困难的事情，因为可以选择的角度太多。 反复思量过后挑了这个角度，在我看来，写一部好的科幻小说，或者一部适合评论家们的科幻小说都不是难事，但是当作家的心思并不是用在这些上面，而是捎带着完成了这两件事的时候，就并不是那么容易了。",
                //     time: "2022-11-27 08:23:45",
                //     nickname: "climo"
                // },
                // {
                //     id:"4",
                //     postname: "放飞理想的土地",
                //     content: "《三体》：当代史的科幻书写 评论刘慈欣的《三体》是件困难的事情。",
                //     time: "2022-11-27 08:23:45",
                //     nickname: "climo"
                // }
            ],
            dialogFormVisible: false,
            form: {
                name: '',
                text: '',
            },
            nickname:'',
            userid: window.sessionStorage.getItem('userid'),
            fullscreenLoading:false,
        }
    },
    mounted() {
        axios.get('/posts/getValid').then((res) => {
            console.log('posts:', res)
            this.$data.tabledata = res.data
        })
        axios({
                method: 'post',
                params: {
                    userid:this.userid,
                },
                url: '/user/myInf',
            }).then((res) => {
                this.$data.nickname=res.data[0].nickName;
                console.log(this.$data.nickname);
            }, error => {
                console.log('错误', error.message)
            })

    },
    methods: {
        test(item) {
            console.log(item);
            this.$router.push({ path: "/PostDetail", query: { id: item.postId, postname: item.postname, content: item.content, time: item.time, nickname: item.nickName } });
        },
        Commit() {
            this.dialogFormVisible = false;
            console.log(this.form);
            this.fullscreenLoading = true;
            axios({
                method: 'post',
                data: {
                    postname: this.form.name,
                    content: this.form.text,
                    userId:this.userid,
                    nickName:this.nickname,
                },
                url: '/posts/save',
            }).then((res) => {
                console.log("res",res)
              if(res.data == true)
                {
                  ElMessage({
                    message: '发布成功',
                    type: 'success',
                  })
                }
              else if(res.data == false){
                ElMessage.error('发布失败,请至个人主页查看失败信息')
              }
                this.reload();
                this.fullscreenLoading = false;
            }, error => {
                console.log('错误', error.message)
            })
        }

    },
}
</script>

<style lang="less" scoped>
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

.overNone {

    overflow: hidden;
    text-overflow: ellipsis;
    //溢出省略号
    display: -webkit-box;
    -webkit-line-clamp: 2;
    line-clamp: 2;
    -webkit-box-orient: vertical;
}

.hands{
  cursor: pointer;  /*鼠标悬停变小手*/
}

</style>