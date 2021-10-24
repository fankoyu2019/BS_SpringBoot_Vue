<template>
  <div>
    <h3>Welcome</h3>
    <el-row type="flex" justify="space-around" :gutter="10">
      <el-col :span="6">
        <el-card class="box-card">
          目前住院人数：
          <div class="numberClass">
           {{ nowPatientsNumber }}
         </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          今日新增人数：
          <div class="numberClass">
            {{ newPatientsInNumber }}
         </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="box-card">
          今日出院人数：
          <div class="numberClass">
            {{ newPatientsOutNumber }}
         </div>

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
        // 该医师医院内当前患者总数
        nowPatientsNumber: 0,
        // 该医生今日新增患者数量
        newPatientsInNumber: 0,
        // 该医生今日出院患者数量
        newPatientsOutNumber: 0,
      }
    },
    created() {
      this.getPatientsNumber()
    },
    methods:{
      getPatientsNumber(){
        this.$http.post('api/patient/getPatientsNumber/'+window.sessionStorage.getItem("userId")).then(res=>{
          this.nowPatientsNumber=res.data.nowPatientsNumber;
          this.newPatientsInNumber=res.data.inNumber;
          this.newPatientsOutNumber=res.data.outNumber;
        }).catch(()=>{
          this.$message.warning("获取失败")
        })
      },
    }
  }
</script>

<style scoped>
  .box-card {
    height: 220px;
  }
  .numberClass{
    text-align: center;
    font-size: 80px;
    color: #fafafa;
    letter-spacing: 0;
    text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666, 0px 5px 0px #555, 0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135;
  }
</style>
