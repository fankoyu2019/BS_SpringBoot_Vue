<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }" @click.native="saveNavState('/home')">首页</el-breadcrumb-item>
      <el-breadcrumb-item>个人信息</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row :gutter="10">
     <el-col :span="8">
       <el-card>
       <el-form ref="form" :model="doctor" >
         <span>姓名:   {{doctor.dcName}}</span><br>
         <span>性别:   {{doctor.dcSex}}</span><br>
         <span>年龄:   {{doctor.dcAge}}  岁</span><br>
         <span>职位:   {{doctor.dcPosition}}</span><br>
         <span>科室:   {{doctor.dcDepartment}}</span><br>
         <span>电话:   {{doctor.dcPhone}}</span>
       </el-form>
     </el-card>
     </el-col>
    </el-row>

  </div>
</template>

<script>
  export default {
    name: "",
    data() {
      return {
        doctor: {},
        //获取医生信息的参数对象
        queryInfo: {
          queryId: '',
        }
      }
    },
    created() {
      this.queryInfo.queryId = window.sessionStorage.getItem("userId")
      this.getDoctor()
    },

    methods: {
      saveNavState(activePath) {
        window.sessionStorage.setItem('activePath', activePath)
      },
      async getDoctor() {
        await this.$http.post("api/doctor/getDoctor", this.queryInfo).then(res => {
          this.doctor = res.data.doctor
        })
      }
    }
  }
</script>

<style >
  .item .el-form-item__label{
    font-size: 15px;
  }


</style>
