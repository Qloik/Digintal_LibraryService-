<template>
    <div style="padding-top:30px">
        <h1 style="font-size: 20px; padding:1%"> 黑名单管理 </h1>
        <div class="tabs" min-height="500">
            <el-table
                :data="tableData.filter(data => !search || data.userId.toLowerCase().includes(search.toLowerCase()))"
                style="width: 100%;" height="440">
                <el-table-column label="人员昵称" prop="nickName"></el-table-column>
                <el-table-column label="人员姓名" prop="username"></el-table-column>
                <el-table-column label="手机号码" prop="userId"></el-table-column>
                <el-table-column label="邮箱" prop="usermail"></el-table-column>
                <el-table-column align="right">
                    <template #header>
                        <el-input v-model="search" style="width:90%;" size="mini" placeholder="输入人员编号（手机号）" />
                    </template>
                    <template #default="scope">
                        <el-button size="mini" type="danger" @click="handleDelete(scope.row)">移出黑名单
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import { h } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
    inject: ['reload'],
    data() {
        return {
            tableData: [],
            search: '',
            dialogFormVisible: false,
            form: {
                bookId: '',
                userId: '',
            },
            labelPosition: 'right',
        }
    },
    mounted() {
        axios.get('/user/getBlockedUsers').then((res) => {
            console.log('blacklist:', res)
            this.$data.tableData = res.data
        })
    },
    methods: {
        handleDelete(row) {
            console.log(row);
            ElMessageBox({
                title: '移出黑名单确认',
                message: h('p', null, [
                    h('div', null, '人员编号：', row.userId),
                    h('div', null, '人员姓名：', row.username)
                ]),
                showCancelButton: true,
                confirmButtonText: '确认移出',
                cancelButtonText: '取消',
                beforeClose: (action, instance, done) => {
                    if (action === 'confirm') {
                        done()
                    } else {
                        ElMessage({
                            message: '已取消移出黑名单',
                        })
                        done()
                    }
                },
            }).then((action) => {
                if (action === 'confirm') {
                    console.log('commit');
                    axios({
                        method: 'post',
                        params: {
                            userid: row.userId,
                        },
                        url: '/user/unblockuser',
                    }).then((res) => {
                        console.log('delete', res)
                        if (res.data == true) {
                            this.$message({
                                message: '移出黑名单成功',
                                type: 'success'
                            });
                            this.reload();
                        }
                        else {
                            this.$message({
                                message: '移出黑名单失败'
                            });
                        }
                    }, error => {
                        console.log('错误', error.message)
                    })
                }
            })
        }
    },
}

</script>

<style lang="less" scoped>
.tabs {
    height: 99%;
    width: 99%;
    padding: 3%;
    background-color: white;
}

.el-input {
    width: 300px;
}
</style>