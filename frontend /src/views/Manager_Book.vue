<template>
  <div>
    <h1 style="font-size: 20px; padding:1%"> 馆藏图书管理 </h1>
    <div class="tabs">
      <el-table :data="tableData.filter(data => !search || data.bookName.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%;" height="440">
        <el-table-column label="书名" prop="bookName"></el-table-column>
        <el-table-column label="ISBN" prop="isbn"></el-table-column>
        <el-table-column label="嘉定校区库存" prop="countJiading"></el-table-column>
        <el-table-column label="四平路校区库存" prop="countSiping"></el-table-column>
        <el-table-column align="right">
          <template #header>
            <el-input v-model="search" style="width: 80%;" size="mini" placeholder="输入书名关键字搜索" />
          </template>
          <template #default="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">信息修改
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <div align="right" style="padding:1%">
        <el-button @click="handleAdd">增加图书库存
        </el-button>
        <el-button type="primary" @click="handleNew">新增图书信息
        </el-button>
      </div>

      <!-- 嵌套表单（新增图书） -->
      <el-dialog v-model="dialogFormVisible" title="新增图书信息" width="40%">
        <el-form :model="form" :local-position="labelPosition" :label-position="labelPosition" label-width="120px">
          <el-form-item label="名称" :label-width="formLabelWidth">
            <el-input v-model="form.bookName" autocomplete="off" />
          </el-form-item>
          <el-form-item label="作者" :label-width="formLabelWidth">
            <el-input v-model="form.bookAuthor" autocomplete="off" />
          </el-form-item>
          <el-form-item label="翻译" :label-width="formLabelWidth">
            <el-input v-model="form.bookTranslation" autocomplete="off" />
          </el-form-item>
          <el-form-item label="代表句" :label-width="formLabelWidth">
            <el-input v-model="form.bookRepre" autocomplete="off" />
          </el-form-item>
          <el-form-item label="出版商" :label-width="formLabelWidth">
            <el-input v-model="form.bookPublisher" autocomplete="off" />
          </el-form-item>
          <el-form-item label="isbn" :label-width="formLabelWidth">
            <el-input v-model="form.isbn" autocomplete="off" />
          </el-form-item>
          <el-form-item label="内容简介" :label-width="formLabelWidth">
            <el-input v-model="form.bookText" :rows="3" type="textarea" />
          </el-form-item>
          <el-form-item label="作者简介" :label-width="formLabelWidth">
            <el-input v-model="form.bookAuthorabout" :rows="3" type="textarea" />
          </el-form-item>
          <el-form-item label="书本图片链接" :label-width="formLabelWidth">
            <el-input v-model="form.bookImg" autocomplete="off" />
          </el-form-item>
          <el-form-item label="作者图片链接" :label-width="formLabelWidth">
            <el-input v-model="form.authorImg" autocomplete="off" />
          </el-form-item>
          <el-form-item>
            <p>
              <el-icon>
                <InfoFilled />
              </el-icon> 补充：中文书籍翻译填 Chinese 汉语 即可
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

      <!-- 嵌套表单（修改图书信息） -->
      <el-dialog v-model="dialogMulVisible" title="修改图书信息" width="40%">
        <el-form :model="Mulform" :local-position="labelPosition" :label-position="labelPosition" label-width="120px">
          <el-form-item label="名称" :label-width="formLabelWidth">
            <el-input v-model="Mulform.bookName" autocomplete="off" />
          </el-form-item>
          <el-form-item label="作者" :label-width="formLabelWidth">
            <el-input v-model="Mulform.bookAuthor" autocomplete="off" />
          </el-form-item>
          <el-form-item label="翻译(默认中文)" :label-width="formLabelWidth">
            <el-input v-model="Mulform.bookTranslation" autocomplete="off" />
          </el-form-item>
          <el-form-item label="代表句" :label-width="formLabelWidth">
            <el-input v-model="Mulform.bookRepre" autocomplete="off" />
          </el-form-item>
          <el-form-item label="出版商" :label-width="formLabelWidth">
            <el-input v-model="Mulform.bookPublisher" autocomplete="off" />
          </el-form-item>
          <el-form-item label="isbn" :label-width="formLabelWidth">
            <p>{{Mulform.isbn}}</p>
          </el-form-item>
          <el-form-item label="内容简介" :label-width="formLabelWidth">
            <el-input v-model="Mulform.bookText" :rows="3" type="textarea" />
          </el-form-item>
          <el-form-item label="作者简介" :label-width="formLabelWidth">
            <el-input v-model="Mulform.bookAuthorabout" :rows="3" type="textarea" />
          </el-form-item>
          <el-form-item label="书本图片链接" :label-width="formLabelWidth">
            <el-input v-model="Mulform.bookImg" autocomplete="off" />
          </el-form-item>
          <el-form-item label="作者图片链接" :label-width="formLabelWidth">
            <el-input v-model="Mulform.authorImg" autocomplete="off" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogMulVisible = false">取消</el-button>
            <el-button type="primary" @click="MulCommit">确认修改</el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 嵌套表单（新增图书库存） -->
      <el-dialog v-model="dialogForm2Visible" title="新增图书库存" width="40%">
        <el-form :model="form2" :local-position="labelPosition" :label-position="labelPosition" label-width="200px">
          <el-form-item label="isbn" :label-width="formLabelWidth">
            <el-input v-model="form2.isbn" autocomplete="off" />
          </el-form-item>
          <el-form-item label="四平路校区新增库存量" :label-width="formLabelWidth">
            <el-input v-model="form2.count_Siping" autocomplete="off" />
          </el-form-item>
          <el-form-item label="嘉定校区新增库存量" :label-width="formLabelWidth">
            <el-input v-model="form2.count_Jiading" autocomplete="off" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取消</el-button>
            <el-button type="primary" @click="AddCommit">确认新增</el-button>
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
      dialogForm2Visible: false,
      dialogMulVisible: false,
      labelPosition: 'right',
      form: {
        bookName: '',
        bookAuthor: '',
        bookTranslation: '',
        bookRepre: '',
        bookPublisher: '',
        isbn: '',
        bookText: '',
        bookAuthorabout: '',
        bookImg: '',
        authorImg: '',
      },
      Mulform: {
        bookName: '',
        bookAuthor: '',
        bookTranslation: '',
        bookRepre: '',
        bookPublisher: '',
        isbn: '',
        bookText: '',
        bookAuthorabout: '',
        bookImg: '',
        authorImg: '',
      },
      form2:{
        isbn:'',
        count_Siping:'0',
        count_Jiading:'0',
      }
    }
  },
  methods: {
    handleEdit(row) {
      console.log(row);
        axios({
            method: 'post',
            params: {
                isbn: row.isbn,
            },
            url: '/book-info/queryByIsbn',
        }).then((res) => {
            this.$data.Mulform = res.data;
            this.dialogMulVisible = true;

        }, error => {
            console.log('错误', error.message)
        })
    },
    handleNew() {
      this.dialogFormVisible = true;
    },
    handleAdd(){
      this.dialogForm2Visible = true;
    },
    Commit() {
      console.log(this.form);
      // 返回id
      axios({
        method: 'post',
        data: {
          bookName:this.form.bookName,
          bookAuthor: this.form.bookAuthor,
          bookTranslation: this.form.bookTranslation,
          bookRepre: this.form.bookRepre,
          bookPublisher: this.form.bookPublisher,
          isbn:this.form.isbn,
          bookText: this.form.bookText,
          bookAuthorabout: this.form.bookAuthorabout,
          bookImg: this.form.bookImg,
          authorImg: this.form.authorImg,
        },
        url: '/book-info/saveOrMod',
      }).then((res) => {
        console.log('edit', res)
        if (res.data == true) {
          this.$message({
            message: '新增图书成功',
            type: 'success'
          });
          this.reload();
        }
        else {
          this.$message({
            message: '新增图书失败'
          });
        }
      }, error => {
        console.log('错误', error.message)
      })
      this.dialogFormVisible = false;
    },

    MulCommit() {
      console.log(this.Mulform);
      // 返回id
      axios({
        method: 'post',
        data: {
          bookName:this.Mulform.bookName,
          bookAuthor: this.Mulform.bookAuthor,
          bookTranslation: this.Mulform.bookTranslation,
          bookRepre: this.Mulform.bookRepre,
          bookPublisher: this.Mulform.bookPublisher,
          isbn:this.Mulform.isbn,
          booktext: this.Mulform.bookText,
          bookAuthorabout: this.Mulform.bookAuthorabout,
          bookImg: this.Mulform.bookImg,
          authorImg: this.Mulform.authorImg,
        },
        url: '/book-info/saveOrMod',
      }).then((res) => {
        console.log('edit', res)
        if (res.data == true) {
          this.$message({
            message: '修改图书信息成功',
            type: 'success'
          });
        }
        else {
          this.$message({
            message: '修改图书信息失败'
          });
        }
      }, error => {
        console.log('错误', error.message)
      })
      this.reload();

      this.dialogFormVisible = false;
    },
    AddCommit(){
      console.log(this.form2);
      axios({
        method: 'post',
        params: {
          isbn:this.form2.isbn,
          countJiading:this.form2.count_Jiading,
          countSiping:this.form2.count_Siping,
        },
        url: '/book/save',
      }).then((res) => {
        console.log('edit', res)
        if (res.data == true) {
          this.$message({
            message: '新增图书库存成功',
            type: 'success'
          });
          this.reload();
        }
        else {
          this.$message({
            message: '新增图书库存失败'
          });
        }
      }, error => {
        console.log('错误', error.message)
      })
      this.dialogFormVisible = false;
    },
  },
  mounted() {
    axios.get('/book-count-campus-all/get').then((res) => {
      console.log('book:', res)
      this.$data.tableData = res.data
    })
    // axios({
    //     method: 'post',
    //     params: {
    //         isbn: row.isbn,
    //         place: "四平路校区",
    //     },
    //     url: '/book/Count',
    //   }).then((res) => {
    //     console.log('countSiping:', res)
        
    // })
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
  width: 350px;
}
</style>