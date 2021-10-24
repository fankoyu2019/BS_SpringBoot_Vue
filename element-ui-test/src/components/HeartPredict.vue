<template>
  <div>
    <!--    面包屑区域-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }" @click.native="saveNavState('/home')">首页</el-breadcrumb-item>
      <el-breadcrumb-item>个人信息</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>

      <el-form ref="heartPredictFormRef" :model="heartPredictInfo" :rules="heartRules" label-width="80px">

        <span class="leftForm">
          <el-form-item label="年龄">
            <el-input-number v-model="heartPredictInfo.age" :min="1" :max="100"></el-input-number>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="heartPredictInfo.sex">
              <el-radio-button :label="1">男</el-radio-button>
              <el-radio-button :label="0">女</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="胸痛类型">
            <el-select v-model="heartPredictInfo.cp">
              <el-option
                v-for="item in cpProperties"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="静息血压" prop="trestbps">
            <el-input v-model="heartPredictInfo.trestbps" placeholder="请输入静息血压">
              <template slot="append">mmHg</template>
            </el-input>
          </el-form-item>
          <el-form-item label="胆固醇" prop="chol">
            <el-input v-model="heartPredictInfo.chol" placeholder="请输入胆固醇含量">
              <template slot="append">mg/dl</template>
            </el-input>
          </el-form-item>
          <el-form-item label="空腹血糖" prop="fbs">
            <el-input v-model="heartPredictInfo.fbs" placeholder="请输入空腹血糖含量">
              <template slot="append">mg/dl</template>
            </el-input>
          </el-form-item>
          <el-form-item label="静息心电图结果" label-width="120px">
            <el-select v-model="heartPredictInfo.restecg">
              <el-option
                v-for="item in restecgProperties"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </span>
        <span class="rightForm">
          <el-form-item label="最大心率" prop="thalach" label-width="145px">
            <el-input v-model="heartPredictInfo.thalach" placeholder="请输入最大心率值">
              <template slot="append">次/min</template>
            </el-input>
          </el-form-item>
          <el-form-item label="运动诱发心绞痛" label-width="145px">
            <el-radio-group v-model="heartPredictInfo.exang">
              <el-radio-button :label="0">无</el-radio-button>
              <el-radio-button :label="1">有</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="运动引起的ST抑郁相对于休息值" label-width="145px">
            <br>
            <el-input-number  :precision="2" :step="0.1" :max="20" :min="0" v-model="heartPredictInfo.oldpeak" >
            </el-input-number>
          </el-form-item>
          <el-form-item label="运动峰值ST段的斜率" prop="slope" label-width="145px">
            <el-select v-model="heartPredictInfo.slope">
              <el-option
                v-for="item in slopeProperties"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="萤光显色的主要血管的数量(0-3)" label-width="145px">
            <el-input-number :max="3" :min="0" v-model="heartPredictInfo.ca" >
            </el-input-number>
          </el-form-item>
          <el-form-item label="海洋性贫血" prop="thal" label-width="145px">
            <el-select v-model="heartPredictInfo.thal">
              <el-option
                v-for="item in thProperties"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </span>

      </el-form>
      <el-button type="primary" :loading="submitLoading" @click="heartPredictEvent">开始预测</el-button>
      <div>
        <p>预测结果:</p>
        <el-tag v-if="boolPredict===0"> 初步判断为无 </el-tag>
        <el-tag v-if="boolPredict===1" type="danger"> 有心脏病可能！ </el-tag>
      </div>
    </el-card>

  </div>
</template>

<script>
  export default {
    name: "",
    data() {
      return {
        //预测结果
        boolPredict:'',
        //按钮是否为加载态
        submitLoading:false,
        //预测信息表单
        heartPredictInfo: {
          age: 20, sex: 1, cp: 3, trestbps: '', chol: '', fbs: '', restecg: 0,
          thalach: '', exang: 1, oldpeak: 1, slope: '', ca: 0, thal: '',
        },
        //表单验证规则
        heartRules:{
          trestbps:[{required: true, message: "请输入静息血压", trigger: "blur"}],
          chol:[{required: true, message: "请输入胆固醇含量", trigger: "blur"}],
          fbs:[{required: true, message: "请输入空腹血糖含量", trigger: "blur"}],
          thalach:[{required: true, message: "请输入最大心率值", trigger: "blur"}],
          slope:[{required: true, message: "请选择坡度类型", trigger: "blur"}],
          thal:[{required: true, message: "请选择thal类型", trigger: "blur"}],
        },
        cpProperties: [
          {value: 0, label: '典型心绞痛'},
          {value: 1, label: '非典型心绞痛'},
          {value: 2, label: '非心绞痛性疼痛'},
          {value: 3, label: '无症状'}
        ],
        restecgProperties: [
          {value: 0, label: '正常'},
          {value: 1, label: 'ST-T波异常(T波倒置及/或ST抬高或压低 > 0.05 mV)'},
          {value: 2, label: '根据Estes标准显示可能左心室肥厚'},
        ],
        slopeProperties:[
          {value: 0, label: '向上倾斜'},
          {value: 1, label: '平'},
          {value: 2, label: '向下倾斜'},
        ],
        thProperties:[
          {value: 0, label: '正常'},
          {value: 1, label: '固定缺陷'},
          {value: 2, label: '可逆转缺陷'},
        ],
      }
    },
    methods:{
      //点击按钮触发心脏病预测功能
      heartPredictEvent(){
        //改变按钮状态为加载态
        this.submitLoading = !this.submitLoading
        this.$refs.heartPredictFormRef.validate(valid=>{
          if(!valid){
            //改变按钮状态为初始态
            this.submitLoading = !this.submitLoading
            return
          }

          this.$http.post('/api/DM/heartPredict',this.heartPredictInfo).then(res=>{
            console.log(res.data.heartIndex)
            this.$message.success('预测成功！')
            console.log(res.data.heartIndex.condition)
            this.boolPredict = res.data.heartIndex.condition

            //改变按钮状态为初始态
            this.submitLoading = !this.submitLoading
          }).catch(()=>{
            this.$message.warning('预测出现错误！')

            //改变按钮状态为初始态
            this.submitLoading = !this.submitLoading
          })
        })
      }
    }
  }
</script>

<style scoped>
  .el-form {
    border-right: solid 1px lightblue;
    padding-right: 20px;
    margin:0 30px 30px 0;
    width: 780px;
    float: left;
  }
  .el-form-item{
    max-width: 360px;
  }
  .leftForm{
    float: left;
  }
  .rightForm{
    float: right;
  }

</style>
