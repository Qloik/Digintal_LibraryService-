<template>
    <div style="padding-top:30px">
        <h1 style="font-size: 20px; padding:1%"> 还书核销 </h1>
        <div class="tabs" min-height="500">
            <el-table :data="tableData.filter(data => !search || data.userId.toLowerCase().includes(search.toLowerCase()))"
                style="width: 100%;" height="440">
                <el-table-column label="书名" prop="bookName"></el-table-column>
                <el-table-column label="馆藏编号" prop="bookId"></el-table-column>
                <el-table-column label="截止时间" prop="returnTime"></el-table-column>
                <el-table-column label="还书人员" prop="userId"></el-table-column>
                <el-table-column label="状态" prop="borrowStatus">
                    <template #default="scope">
                        <el-tag disable-transitions effect="dark"
                            :type="scope.row.borrowStatus === '已取书' ? 'success' : 'danger'">
                            {{ scope.row.borrowStatus }}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column align="right">
                    <template #header>
                        <el-input v-model="search" style="width: 100%;" size="mini" placeholder="输入人员编号搜索" />
                    </template>
                    <template #default="scope">
                        <el-button size="mini" type="danger" @click="handleDelete(scope.row)">核销
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
        // 获取当前还书请求
        axios.get('/borrowed-book/getReceived').then((res) => {
            console.log('receive:', res)
            this.$data.tableData = res.data
        })
    },
    methods: {
        handleDelete(row) {
            // 核销还书请求
            console.log(row);
            ElMessageBox({
                title: '还书信息确认',
                message: h('p', null, [
                    h('div', null, '书名：', row.bookName),
                    h('div', null, '馆藏编号：', row.bookId),
                    h('div', null, '还书人员：', row.userId)
                ]),
                showCancelButton: true,
                confirmButtonText: '确认还书成功',
                cancelButtonText: '取消',
            }).then((action) => {
                if (action === 'confirm') {
                    console.log('commit');
                    //向后端传输信息
                    axios({
                        method: 'post',
                        params: {
                            borrowId: row.borrowId
                        },
                        url: '/borrowed-book/returnborrowed',
                    }).then((res) => {
                        console.log('edit', res)
                        if (res.status == 200) {
                            this.$message({
                                message: '还书成功',
                                type: 'success'
                            });
                            this.reload();
                        }
                        else {
                            this.$message({
                                message: '还书失败'
                            });
                        }
                    }, error => {
                        console.log('错误', error.message)
                    })
                    this.reload()
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