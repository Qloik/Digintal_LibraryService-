<template>
    <div>
        <!-- <h3>双人研习室预约</h3>  -->
        <h1 style="font-size: 20px; padding:1%"><span> TODAY </span>{{ getdate() }}</h1>
        <el-tabs class="tabs" v-model="activeName" type="border-card" @tab-click="handleClick" width="100%">
            <el-tab-pane v-for="(item, index) in weekday" :key="index" :label="item.label" :name="item.name">
                <p></p>
                <el-table ref="filterTable" :data="tableData" style="width: 100%" height="430">
                    <el-table-column label="研习室类型" prop="roomType">
                    </el-table-column>
                    <el-table-column label="研习室房间号" prop="roomNumber"
                        :filters="[{ text: '101', value: '101' }, { text: '102', value: '102' }]"
                        :filter-method="filterTag" filter-placement="bottom-end">
                        <template #default="scope">
                            <el-tag disable-transitions>
                                {{ scope.row.roomNumber }}</el-tag>
                        </template>
                    </el-table-column>

                    <el-table-column label="时间段" prop="reserveTime">
                    </el-table-column>

                    <el-table-column label="状态" prop="roomStatus"
                        :filters="[{ text: '已预约', value: '已预约' }, { text: '未预约', value: '未预约' }]"
                        :filter-method="filterMode" filter-placement="bottom-end">
                        <template #default="scope">
                            <el-tag disable-transitions effect="dark"
                                :type="scope.row.roomStatus === '已预约' ? 'danger' : 'success'">
                                {{ scope.row.roomStatus }}</el-tag>
                        </template>
                    </el-table-column>

                    <el-table-column align="right">
                        <template #default="scope">
                            <el-button type="primary" size="mini" @click="handleEdit(scope.$index, scope.row)"
                                :disabled="scope.row.roomStatus == '已预约' ? true : false">预约
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
import axios from 'axios'
import { toRaw } from '@vue/reactivity';
const weekArr = ["SUN", "MON", "TUE", "WED", "THUR", "FRI", "SAT"]
import { h } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
    inject:['reload'],
    data() {
        return {
            activeName: 'first',
            tableData: [],
            weekday: [
                {
                    label: '',
                    name: 'first',
                    date: ''
                },
                {
                    label: '',
                    name: 'second',
                    date: ''
                },
                {
                    label: '',
                    name: 'third',
                    date: ''
                }
            ],
            choosedate: '',
            userid: window.sessionStorage.getItem('userid'),
        };
    },
    mounted() {
            this.choosedate = this.weekday[0].date
            axios({
                method: 'post',
                params: {
                    type: '双人研习室',
                    date: this.choosedate,
                },
                url: '/room-appointment/GetRoom',
            }).then((res) => {
                console.log('single', res)
                this.$data.tableData = res.data
            }, error => {
                console.log('错误', error.message)
            })
    },
    methods: {
        getdate() {
            // 获取当前年月日和星期几
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            var getWeek = date.getDay()

            //明天
            var day1 = new Date()
            day1.setTime(day1.getTime() + 24 * 60 * 60 * 1000)
            var month2 = day1.getMonth() + 1;
            var strDate2 = day1.getDate();
            if (month2 >= 1 && month2 <= 9) {
                month2 = "0" + month2;
            }
            if (strDate2 >= 0 && strDate2 <= 9) {
                strDate2 = "0" + strDate2;
            }
            this.$data.weekday[0].label = (day1.getMonth() + 1) + "." + day1.getDate() + " " + weekArr[day1.getDay()];
            this.$data.weekday[0].date = year+"-"+ month2 + "-" + strDate2;

            // 后天
            day1.setTime(day1.getTime() + 24 * 60 * 60 * 1000)
            month2 = day1.getMonth() + 1;
            strDate2 = day1.getDate();
            if (month2 >= 1 && month2 <= 9) {
                month2 = "0" + month2;
            }
            if (strDate2 >= 0 && strDate2 <= 9) {
                strDate2 = "0" + strDate2;
            }
            this.$data.weekday[1].label = (day1.getMonth() + 1) + "." + day1.getDate() + " " + weekArr[day1.getDay()];
            this.$data.weekday[1].date = year+"-"+ month2 + "-" + strDate2;

            // 大后天
            day1.setTime(day1.getTime() + 24 * 60 * 60 * 1000)
            month2 = day1.getMonth() + 1;
            strDate2 = day1.getDate();
            if (month2 >= 1 && month2 <= 9) {
                month2 = "0" + month2;
            }
            if (strDate2 >= 0 && strDate2 <= 9) {
                strDate2 = "0" + strDate2;
            }
            this.$data.weekday[2].label = (day1.getMonth() + 1) + "." + day1.getDate() + " " + weekArr[day1.getDay()];
            this.$data.weekday[2].date = year+"-"+ month2 + "-" + strDate2;

            var currentdate = year + "." + month + "." + strDate + " " + weekArr[getWeek];
            return currentdate;
        },
        handleClick(tab, event) {
            //获取target值的方式，通过json序列化之后可获取值
            var list = JSON.parse(JSON.stringify(tab))
            console.log(list.paneName);
            if (list.paneName == "first") {
                this.choosedate = this.weekday[0].date
            }
            else if (list.paneName == "second") {
                this.choosedate = this.weekday[1].date
            }
            else if (list.paneName == "third") {
                this.choosedate = this.weekday[2].date
            }
            axios({
                method: 'post',
                params: {
                    type: '双人研习室',
                    date: this.choosedate,
                },
                url: '/room-appointment/GetRoom',
            }).then((res) => {
                console.log('single', res)
                this.$data.tableData = res.data
            }, error => {
                console.log('错误', error.message)
            })
            console.log(this.choosedate)
        },

        handleEdit(index, row) {
            console.log(index, row);
            console.log('chose',this.choosedate);
            ElMessageBox({
                title: '预约信息确认',
                message: h('p', null, [
                    h('div', null, '研习室类型：', row.roomType),
                    h('div', null, '研习室房间号：', row.roomNumber),
                    h('div', null, '日期：', this.choosedate),
                    h('div', null, '时间段：', row.reserveTime),
                ]),
                showCancelButton: true,
                confirmButtonText: '确认预约',
                cancelButtonText: '取消',
                beforeClose: (action, instance, done) => {
                    if (action === 'confirm') {
                        ElMessage({
                            type: 'success',
                            message: '预约成功',
                        })
                        done()
                    } else {
                        ElMessage({
                            message: '已取消预约',
                        })
                        done()
                    }
                },
            }).then((action) => {
                if (action === 'confirm') {
                    console.log('commit');
                    //向后端传输预约信息
                    axios({
                        method: 'post',
                        params: {
                            userid: this.userid,
                            reserve_id:row.reserveId,
                        },
                        url: '/room-appointment/AppointById',
                    })
                    this.reload()
                }
            })
        },
        filterTag(value, row) {
            return row.roomNumber === value;
        },
        filterMode(value, row) {
            return row.roomStatus === value;
        }
    },
}

</script>

<style lang="less" scoped>
.tabs {
    height: 100%;
    width: 99%;
    padding: 3%;
}
</style>