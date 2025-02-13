<template>
    <div>
        <h1 style="font-size: 20px; padding:1%">馆内情况总览</h1>
        <el-row :gutter="5">
            <el-col :span="12">
                <div class="grid-content bg">
                    <el-row :gutter="5">
                        <el-col :span="10">
                            <div class="grid-content-small bg">
                                <!-- 环形进度条显示上座率 -->
                                <div>
                                    <el-progress type="dashboard" :percentage=percentage width=200 stroke-width=15
                                        :color="ProgressColor">
                                        <template #default="{ percentage }">
                                            <span class="percentage-value">{{ percentage }}%</span>
                                            <span class="percentage-label">Attendance</span>
                                        </template>
                                    </el-progress>
                                </div>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div class="grid-content-small bg">
                                <h1 style="font-size: 20px; padding:1%">剩余预约名额</h1>
                                <span class="big-number">
                                    {{ 500 - CurrentPNum }}
                                </span>
                                <div class="wrap" style="padding-top:10%">
                                    <button class="button" @click="submit()">预约入馆</button>
                                    <button class="button" @click="cancel()" style="float:right">取消预约</button>
                                </div>
                            </div>
                        </el-col>
                    </el-row>
                    <el-divider></el-divider>

                    <!-- 走马灯 -->
                    <div class="block">
                        <el-carousel height="230px">
                            <el-carousel-item v-for="item in imgList" :key="item.id">
                                <img :src="item.src" class="image">
                            </el-carousel-item>
                        </el-carousel>
                    </div>

                </div>
            </el-col>
            <el-col :span="10" :offset="1">
                <div class="grid-content2 bg">
                    <h1 style="font-size: 20px; padding:3%">通知公告</h1>
                    <el-table class="hands" :data="tableData" style="width: 100%" max-height="170"
                        @row-click="clickNotice" :default-sort = "{prop: 'releaseDate', order: 'descending'}">
                        <el-table-column label="通知公告名称" prop="title" width="330">
                            <template #default="scope">
                                <a target="_blank">
                                    {{ scope.row.title }}
                                </a>
                            </template>
                        </el-table-column>
                        <el-table-column label="发布日期" prop="releaseDate" align="right">
                        </el-table-column>
                    </el-table>
                </div>
                <el-divider></el-divider>
                <div class="grid-content2 bg">
                    <h1 style="font-size: 20px; padding:3%">我的预约</h1>
                    <el-table :data="ReserveData" :show-header="true" style="width: 100%" height="170">
                        <el-table-column label="研习室类型" prop="roomType" width="100"></el-table-column>
                        <el-table-column label="房间号" prop="roomNumber"></el-table-column>
                        <el-table-column label="时间段" prop="reserveTime" width="120"></el-table-column>
                        <el-table-column label="日期" prop="reserveDate" width="120"></el-table-column>

                        <el-table-column align="right">
                            <template #default="scope">
                                <el-button type="danger" size="mini" @click="handleEdit(scope.$index, scope.row)">取消
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>

            </el-col>
        </el-row>
    </div>
</template>

<script>
import axios from 'axios'
import { h } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
    inject:['reload'],
    data() {
        return {
            tableData: [],
            ReserveData: [],
            imgList: [
                { id: 0, src: require('../assets/lib1.jpg') },
                { id: 1, src: require('../assets/lib2.jpg') },
                { id: 2, src: require('../assets/lib3.jpg') },
                { id: 3, src: require('../assets/lib4.jpg') },
            ],
            value1: '',
            dynamicValidateForm: {
                date: ''
            },
            dialogTableVisible: false,
            CurrentPNum: 0,
            percentage: 0,
            userid: window.sessionStorage.getItem('userid'),
            ProgressColor: '#86AAB6',
        }
    },
    mounted() {
        //页面建立时获取当前在馆人数并显示
        //页面建立时获取通知公告
        //页面建立时获取我的预约信息

        axios.get('/entry-appointment/getNumber').then((res) => {
            this.$data.CurrentPNum = res.data
            this.$data.percentage = (this.$data.CurrentPNum / 5).toFixed(1)
        })

        axios.get('/notice/GetNotice').then((res) => {
            console.log('notice:', res)
            this.$data.tableData = res.data
        })

        //id传到后端检查是否未预约，返回true预约成功，false显示今日已预约
        //预约成功后剩余名额减少

        axios({
            method: 'post',
            params: {
                userid: this.userid,
            },
            url: '/room-appointment/GetAppointByUser',
        }).then((res) => {
            console.log('111', res)
            this.$data.ReserveData = res.data
        }, error => {
            console.log('错误', error.message)
        })

    },
    methods: {
        submit() {

            axios({
                method: 'post',
                params: {
                    userid: this.userid,
                },
                url: '/entry-appointment/reserve',
            }).then((res) => {
                console.log('submit', res)
                if (res.data == true) {
                    this.$message({
                        message: '预约入馆成功',
                        type: 'success'
                    });
                    this.reload();
                } else if (res.data == false) {
                    this.$message.error('预约入馆失败，账号已存在预约，请先取消预约');
                }
            }, error => {
                console.log('错误', error.message)
            })
        },
        cancel() {

            axios({
                method: 'post',
                params: {
                    userid: this.userid,
                },
                url: '/entry-appointment/cancel',
            }).then((res) => {
                console.log('cancel', res)
                if (res.data == true) {
                    this.$message({
                        message: '取消入馆预约成功',
                        type: 'success'
                    });
                    this.reload();
                } else if (res.data == false) {
                    this.$message.error('取消入馆预约失败，请先进行预约');
                }
            }, error => {
                console.log('错误', error.message)
            })
        },
        handleEdit(index, row) {
            console.log(index, row);
            ElMessageBox({
                title: '取消预约确认',
                message: h('p', null, [
                    h('div', null, '研习室类型：', row.roomType),
                    h('div', null, '研习室房间号：', row.roomNumber),
                    h('div', null, '日期：', row.reserveDate),
                    h('div', null, '时间段：', row.reserveTime),
                ]),
                showCancelButton: true,
                confirmButtonText: '确认取消',
                cancelButtonText: '退出',
                beforeClose: (action, instance, done) => {
                    if (action === 'confirm') {
                        ElMessage({
                            type: 'success',
                            message: '取消预约成功',
                        })
                        done()
                    } else {
                        ElMessage({
                            message: '未取消预约',
                        })
                        done()
                    }
                },
            }).then((action) => {
                if (action === 'confirm') {
                    console.log('commit');
                    //向后端传输取消预约信息
                    axios({
                        method: 'post',
                        params: {
                            reserve_id:row.reserveId
                        },
                        url: '/room-appointment/CancelAppoint',
                    })
                    this.reload();
                }
            })
        },
        disabledDate(time) {
            return time.getTime() <= Date.now();
        },
        clickNotice(row) {
            let thisRowData = this;
            thisRowData = row;
            console.log(row.title, 444);
            this.$router.push({ path: "/notice", query: { title: row.title, time: row.releaseDate, text: row.text } });
        },
    }

}
</script>

<style lang="less" scoped>

.el-row {
    margin-bottom: 20px;
    &:last-child {
        margin-bottom: 0;
    }
}

.el-col {
    border-radius: 20px;
}

.bg {
    //background: #e5e6e8;
    background: #fff;
}

.bg2 {
    background: #a4aab6;
}

.big-number {
    font-size: 50px;
    font-weight: bold;
    color: #99a9bf;
}

.grid-content {
    border-radius: 20px;
    min-height: 450px;
    padding: 6%;
    margin-left: 10px;
}

.grid-content-small {
    border-radius: 20px;
    min-height: 100%;
    min-width: 100px;
    padding: 3%;
}

.grid-content2 {
    border-radius: 20px;
    padding: 1px 30px;
    padding-bottom: 5%;
    min-height: 220px;
}

.row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
}

a {
    text-decoration: none;
    color: #004E90;
}

.percentage-value {
    display: block;
    margin-top: 10px;
    font-size: 35px;
}

.percentage-label {
    display: block;
    margin-top: 10px;
    font-size: 12px;
}


.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}

img {
    max-width: 100%;
    height: auto;
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

.hands{
  cursor: pointer;  /*鼠标悬停变小手*/
}
</style>