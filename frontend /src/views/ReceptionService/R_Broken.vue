<template>
    <div style="padding-top:30px">
        <h1 style="font-size: 20px; padding:1%"> 损坏图书管理 </h1>
        <div class="tabs" min-height="500">
            <el-table
                :data="tableData.filter(data => !search || data.bookName.toLowerCase().includes(search.toLowerCase()))"
                style="width: 100%;" height="440">
                <el-table-column label="书名" prop="bookName"></el-table-column>
                <el-table-column label="馆藏编号" prop="bookId"></el-table-column>
                <el-table-column label="损坏时间" prop="damageTime"></el-table-column>
                <el-table-column label="赔偿人员" prop="userId"></el-table-column>
                <el-table-column align="right">
                    <template #header>
                        <el-input v-model="search" style="width: 100%;" size="mini" placeholder="输入书名关键字搜索" />
                    </template>
                    <template #default="scope">
                        <el-button size="mini" type="danger" @click="handleDelete(scope.row)">已处理
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div align="right" style="padding:1%">
                <el-button type="primary" @click="handleNew()">新增损坏图书
                </el-button>
            </div>

            <!-- 嵌套表单（新增损坏图书） -->
            <el-dialog v-model="dialogFormVisible" title="新增损坏图书" width="30%">
                <el-form :model="form" :local-position="labelPosition" :label-position="labelPosition" label-width="100px">
                    <el-form-item label="馆藏编号" :label-width="formLabelWidth">
                        <el-input v-model="form.bookId" autocomplete="off" />
                    </el-form-item>
                    <el-form-item label="赔偿人员id" :label-width="formLabelWidth">
                        <el-input v-model="form.userId" autocomplete="off" />
                    </el-form-item>
                    <el-form-item>
                        <p>
                            <el-icon>
                                <InfoFilled />
                            </el-icon> 补充：如果书籍自然损坏赔偿人员填无即可
                        </p>
                    </el-form-item>
                </el-form>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dialogFormVisible = false">取消</el-button>
                        <el-button type="primary" @click="Commit">确认新增</el-button>
                    </span>
                </template>
            </el-dialog>
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
        axios.get('/damaged-book/getdamaged').then((res) => {
            console.log('Damaged:', res)
            this.$data.tableData = res.data
        })
    },
    methods: {
        handleNew() {
            this.dialogFormVisible = true;
        },
        Commit() {
            // 返回id
            axios({
                method: 'post',
                params: {
                    bookId: this.form.bookId,
                    userId: this.form.userId,
                },
                url: '/damaged-book/adddamagedbook',
            }).then((res) => {
                console.log('edit', res)
                if (res.data == true) {
                    this.$message({
                        message: '添加损坏图书成功',
                        type: 'success'
                    });
                    this.reload();
                }
                else {
                    this.$message({
                        message: '添加损坏图书失败',
                        type: 'warning'
                    });
                }
            }, error => {
                console.log('错误', error.message)
            })
            this.dialogFormVisible = false;
        },
        handleDelete(row) {
            console.log(row);
            ElMessageBox({
                title: '损坏图书处理确认',
                message: h('p', null, [
                    h('div', null, '书名：', row.bookName),
                    h('div', null, '馆藏编号：', row.bookId),
                    h('div', null, '损坏时间：', row.damageTime),
                    h('div', null, '赔偿人员：', row.userId)
                ]),
                showCancelButton: true,
                confirmButtonText: '确认处理',
                cancelButtonText: '取消',
            }).then((action) => {
                if (action === 'confirm') {
                    console.log('commit');
                    //向后端传输信息
                    axios({
                        method: 'post',
                        params: {
                            bookId: row.bookId,
                            damageTime: row.damageTime,
                        },
                        url: '/damaged-book/repairdamagedbook',
                    }).then((res) => {
                        console.log('edit', res)
                        if (res.data == true) {
                            this.$message({
                                message: '更改损坏图书状态成功',
                                type: 'success'
                            });
                            this.reload();
                        }
                        else {
                            this.$message({
                                message: '更改损坏图书状态失败'
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