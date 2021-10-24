<template>
  <div>
    <!--    面包屑区域-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }" @click.native="saveNavState('/home')">首页</el-breadcrumb-item>
      <el-breadcrumb-item>患者管理</el-breadcrumb-item>
    </el-breadcrumb>

    <!--    卡片区域-->
    <el-card>
      <!--      搜索与添加区域-->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入患者姓名" v-model="queryInfo.query" clearable @clear="getPatientsList">
            <el-button slot="append" icon="el-icon-search" @click="getPatientsList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible=true">添加患者</el-button>
        </el-col>
      </el-row>

      <!--患者列表区域-->
      <el-table
        :data="patientsList"
        height="360px"
        style="width: 100%"
        border
        stripe
        v-loading="loading"

      >
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="住院日期" sortable width="200px">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.patInTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="住院天数" width="80px">
          <template slot-scope="scope">
            {{ countInDays(scope.row.patInTime) }}
          </template>
        </el-table-column>
        <el-table-column label="姓名">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.patName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="patPhone" label="手机号"></el-table-column>
        <el-table-column prop="patBed" label="病床号"></el-table-column>
        <el-table-column prop="patRemark" label="医嘱"></el-table-column>
        <!--
                <el-table-column label="状态">
                  <template slot-scope="scope">
                    <el-switch
                      v-model="scope.row.mg_state"
                      active-color="#13ce66"
                      inactive-color="#ff4949"
                      @change="patientStateChanged(scope.row)"
                    >
                    </el-switch>
                  </template>
                </el-table-column>
                -->
        <el-table-column label="操作" fixed="right" width="190px">
          <template slot-scope="scope">
            <el-tooltip effect="dark" content="患者详情" placement="top">
              <el-button type="primary" icon="el-icon-more" size="mini" @click="inPatientDetails(scope.row.patId)"></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="修改信息" placement="top">
              <el-button type="info" icon="el-icon-edit" size="mini"
                         @click="showEditDialog(scope.row.patId)"></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="患者出院" placement="top">
              <el-button type="success" icon="el-icon-check" size="mini"
              @click="leaveHospital(scope.row)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!--分页区域-->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNum"
        :page-sizes="queryInfo.pageSizes"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>

    <!--    添加病人的对话框-->
    <el-dialog
      title="添加患者"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClosed"
    >
      <!--      内容主题部分-->
      <el-form ref="addFormRef" :model="addPatient" :rules="patientRules" label-width="80px">
        <el-form-item label="患者姓名" prop="patName">
          <el-input v-model="addPatient.patName" placeholder="请输入患者姓名"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="patAge">
          <el-input-number v-model="addPatient.patAge" :min="1" :max="100"></el-input-number>
        </el-form-item>
        <el-form-item label="性别" prop="patSex">
          <el-radio-group v-model="addPatient.patSex">
            <el-radio-button label="男">男</el-radio-button>
            <el-radio-button label="女">女</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" prop="patPhone">
          <el-input v-model="addPatient.patPhone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="病床号" prop="patBed">
          <el-input v-model="addPatient.patBed" placeholder="请输入患者床号"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="patRemark">
          <el-input type="textarea" v-model="addPatient.patRemark" maxlength="100"
                    show-word-limit :autosize="{ minRows: 2, maxRows: 4}"></el-input>
        </el-form-item>
      </el-form>


      <!--      底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="addPatientClick">确 定</el-button>
      </span>
    </el-dialog>

    <!--    修改病人信息的对话框-->
    <el-dialog
      title="修改信息"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <!--      内容主题部分-->
      <el-form ref="editFormRef" :model="editForm" :rules="patientRules" label-width="80px">
        <el-form-item label="患者姓名" prop="patName">
          <el-input v-model="editForm.patName" disabled></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="patAge">
          <el-input-number v-model="editForm.patAge" :min="1" :max="100"></el-input-number>
        </el-form-item>
        <el-form-item label="性别" prop="patSex">
          <el-radio-group v-model="editForm.patSex">
            <el-radio-button label="男">男</el-radio-button>
            <el-radio-button label="女">女</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机号" prop="patPhone">
          <el-input v-model="editForm.patPhone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="病床号" prop="patBed">
          <el-input v-model="editForm.patBed" placeholder="请输入患者床号"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="patRemark">
          <el-input type="textarea" v-model="editForm.patRemark" maxlength="100"
                    show-word-limit :autosize="{ minRows: 2, maxRows: 4}"></el-input>
        </el-form-item>
      </el-form>

      <!--      底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="editPatientClick">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "",
    data() {
      //验证手机号的规则
      let checkPhone = (rule, value, callback) => {
        // 验证手机号的正则表达式
        const regMobile = /^(0|86|17951)?(13[0-9]|15[0123456789]|17[678]|18[0-9]|14[57])[0-9]{8}$/

        if (regMobile.test(value) || value.trim().length == 0) {
          return callback()
        }
        callback(new Error('请输入合法的手机号'))
      }

      return {
        //表格加载标志
        loading:true,
        //患者列表数据 {}会导致table添加height属性后卡死 应换成[]
        patientsList: [],
        //患者修改对话框的信息
        editForm: [],
        //获取患者列表的请求数据
        queryInfo: {
          //医生id
          queryId: '',
          //搜索框内容
          query: '',
          // 当前的页数
          pageNum: 1,
          // 当前每页显示多少条数据
          pageSize: 5,
          // 每页显示多少数据 下拉选择菜单
          pageSizes: [5, 10, 20, 50, 100]
        },
        //当前列表总条数
        total: 0,
        //控制添加病人对话框的显示与隐藏
        addDialogVisible: false,
        //控制修改病人信息对话框的显示与隐藏
        editDialogVisible: false,
        //提交按钮改变状态 加载中
        submitLoading: false,
        //添加患者的表单数据
        addPatient: {
          patName: '',
          patAge: '',
          patSex: '',
          patPhone: '',
          patBed: '',
          patRemark: '',
        },
        //患者表单验证规则对象
        patientRules: {
          patName: [
            {required: true, message: "请输入患者姓名", trigger: "blur"},
          ],
          patAge: [],
          patSex: [
            {required: true, message: '请选择患者性别', trigger: 'change'}
          ],
          patPhone: [
            {validator: checkPhone, trigger: 'blur'}
          ],
          patBed: [],
          patRemark: [],
        }
      }
    },
    created() {
      this.queryInfo.queryId = window.sessionStorage.getItem("userId")
      this.getPatientsList()
    },
    methods: {
      async getPatientsList() {
        this.loading=true
        await this.$http.post('/api/doctor/getPatientsList', this.queryInfo).then(res => {
          // console.log(res.data.patientsList)
          this.patientsList = res.data.patientsList
          this.total = res.data.total
          this.queryInfo.pageNum = res.data.pageNum
          this.loading=false
        })
      },
      saveNavState(activePath) {
        window.sessionStorage.setItem('activePath', activePath)
      },
      //  监听 pageSize 改变的事件
      handleSizeChange(newSize) {
        this.queryInfo.pageSize = newSize
        this.getPatientsList()
      },
      //  监听 页码值 改变的事件
      handleCurrentChange(newPage) {
        // console.log(newPage)
        this.queryInfo.pageNum = newPage
        this.getPatientsList()
      },
      //监听 switch 开关状态的改变
      patientStateChanged(userInfo) {
        // console.log(userInfo)
      },
      // 住院天数
      countInDays(InTime) {
        let date1 = new Date(InTime)
        let date2 = new Date()
        date1 = new Date(date1.getFullYear(), date1.getMonth(), date1.getDate());
        date2 = new Date(date2.getFullYear(), date2.getMonth(), date2.getDate());
        const diff = date2.getTime() - date1.getTime(); //目标时间减去当前时间
        const diffDate = diff / (24 * 60 * 60 * 1000);  //计算当前时间与结束时间之间相差天数
        return diffDate
      },
      // 监听添加患者对话框的关闭事件
      addDialogClosed() {
        this.$refs.addFormRef.resetFields()
      },
      // 监听修改患者对话框的关闭事件
      editDialogClosed() {
        this.$refs.editFormRef.resetFields()
      },
      // 点击按钮，添加新患者
      addPatientClick() {
        //改变按钮状态为加载态
        this.submitLoading = !this.submitLoading
        this.$refs.addFormRef.validate(valid => {
          if (!valid) {
            //改变按钮状态为初始态
            this.submitLoading = !this.submitLoading
            return
          }

          // 可以发起添加患者的网络的请求
          this.$http.post("/api/doctor/savePatient/" + window.sessionStorage.getItem("userId"), this.addPatient).then(res => {
            this.$message.success('添加患者成功！')
            //隐藏添加患者的对话框
            this.addDialogVisible = false
            //改变按钮状态为初始态
            this.submitLoading = !this.submitLoading
            // 重置获取患者列表数据
            this.getPatientsList()
          }).catch(error => {
            this.$message.error('添加患者失败！')
            //改变按钮状态为初始态
            this.submitLoading = !this.submitLoading
          })
        })
      },
      // 显示修改患者信息对话框
      async showEditDialog(patId) {

        //获取患者信息
        await this.$http.post("/api/patient/findPatientByPatId/" + patId).then(res => {
          this.editForm = res.data.patient
          this.editDialogVisible = true
        }).catch(error => {
          this.$message.warning('打开对话框出现错误!')
        })


      },
      // 修改患者信息并提交
      editPatientClick() {
        //改变按钮状态为加载态
        this.submitLoading = !this.submitLoading
        this.$refs.editFormRef.validate(async valid => {
          if (!valid) {
            //改变按钮状态为初始态
            this.submitLoading = !this.submitLoading
            return
          }
          // 可以发起修改患者信息的网络请求
          await this.$http.post("/api/patient/updatePatient/"
            +this.editForm.patId,this.editForm).then(res=>{

            this.$message.success('修改患者信息成功！')
            //隐藏添加患者的对话框
            this.editDialogVisible = false
            //改变按钮状态为初始态
            this.submitLoading = !this.submitLoading
            // 重置获取患者列表数据
            this.getPatientsList()
          }).catch(error=>{
            this.$message.error('修改患者信息失败！')
            //改变按钮状态为初始态
            this.submitLoading = !this.submitLoading
          })

        })
      },

      //患者出院
      leaveHospital(patient){
        this.$confirm('确认该患者出院?','提示',{
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type:'warning',
          center:true
        }).then(()=>{
          this.$http.post("/api/patient/leaveHospital",patient).then(res=>{
            this.$message.success('出院成功！')
            this.getPatientsList()
          }).catch(()=>{
            this.$message.warning('出院失败！')
          })
        })

      },
      // 进入患者详情界面
      inPatientDetails(patId){
        this.$router.push(`/patientDetails/${patId}`)
      },

    }
  }
</script>

<style scoped>
  .el-table {
    margin-top: 15px;
    font-size: 13px;
  }

  .el-pagination {
    margin-top: 15px;
  }
</style>
