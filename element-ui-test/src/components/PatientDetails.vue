<template>
  <div>
    <!--    面包屑区域-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item :to="{path:'/patients'}">患者管理</el-breadcrumb-item>
      <el-breadcrumb-item>患者详情</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="box-card" v-model="patient">
      <div slot="header" class="clearfix">
        <el-button style="float: right; padding: 3px 0" type="text" @click="backToPatientList">返回</el-button>
        <el-row class="patientRow">
          <el-col :span="3"><span>姓名:{{ patient.patName }}</span></el-col>
          <el-col :span="3"><span>年龄:{{ patient.patAge }}</span></el-col>
          <el-col :span="3"><span>性别:{{ patient.patSex }}</span></el-col>
          <el-col :span="4"><span>病床号:{{ patient.patBed }}</span></el-col>
          <el-col :span="5"><span>手机号:{{ patient.patPhone }}</span></el-col>
        </el-row>
        <el-row class="patientRow">
          <el-col>医嘱:{{ patient.patRemark }}</el-col>
        </el-row>
      </div>

      <el-select v-model="property" placeholder="请选择" style="float: right" @change="updateChart">
        <el-option
          v-for="item in properties"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
      <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
      <div id="main" style="width: 80%; height:370px;"></div>


    </el-card>
  </div>
</template>

<script>
  import * as echarts from 'echarts'

  export default {
    name: "",
    data() {
      return {
        myChart: '',
        patientDailyRecordList: [],
        patient: [],
        pdrTemperatures: [],
        property: 0,
        properties: [{
          value: 0,
          label: '体温 °C'
        }, {
          value: 1,
          label: '脉搏 次/min'
        }, {
          value: 2,
          label: '呼吸 次/min'
        }, {
          value: 3,
          label: '收缩压 mmHg'
        }, {
          value: 4,
          label: '舒张压 mmHg'
        }],
        // 指定图表的配置项和数据
        option: {
          title: {
            text: '患者情况图例'
          },
          tooltip: {},
          legend: {
            data: ['体温 °C']
          },
          xAxis: {
            data: []
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              formatter: '{value}(t)'
            },
          },
          series: [{
            name: '体温 °C',
            type: 'bar',
            data: []
          }]
        },

      }
    },
    created() {
      this.getPatient()
    },
    mounted() {


      // 基于准备好的dom，初始化echarts实例
      this.myChart = echarts.init(document.getElementById('main'))

      this.$http.post('/api/patient/getDailyRecord/' + this.$route.params.patId, this.$route.params.patId).then(res => {
        console.log(res.data.patientDailyRecordList)
        this.patientDailyRecordList = res.data.patientDailyRecordList

        this.option.legend.data[0] = '体温 °C'
        this.option.series[0].name = '体温 °C'
        this.option.yAxis.axisLabel.formatter = '{value}(°C)'
        this.option.yAxis.name = '体温 °C'
        this.option.yAxis.min = 35
        res.data.patientDailyRecordList.forEach((value, index) => {
          if (value.pdrTemperature >= 37.5) {
            this.option.series[0].data[index] = {value: Object.values(value)[2], itemStyle: {color: '#ff8c00'}}
          }
          else if(value.pdrTemperature <= 36){
            this.option.series[0].data[index] = {value: Object.values(value)[2], itemStyle: {color: '#90ee90'}}
          }
          else {
            this.option.series[0].data[index] = Object.values(value)[2]
          }
          this.option.xAxis.data[index] = value.pdrRecordingTime
        })
        console.log(this.option.series[0].data)
        // 使用刚指定的配置项和数据显示图表
        this.myChart.setOption(this.option)
      }).catch(() => {

      })
    },
    methods: {
      // 更新图表
      updateChart(val) {
        let unitSymbol = [' °C', ' 次/min', ' 次/min', ' mmHg', ' mmHg']
        let unitName = ['体温', '脉搏', '呼吸', '收缩压', '舒张压']
        let lowerLimit = [35, 40, 6, 70, 40]
        let upThreshold = [37.5, 85, 20, 140, 90]
        let downThreshold = [36,65,10,90,60]
        this.option.legend.data[0] = unitName[val] + unitSymbol[val]
        this.option.series[0].name = unitName[val] + unitSymbol[val]
        this.option.yAxis.axisLabel.formatter = '{value}' + unitSymbol[val]
        this.option.yAxis.name = unitName[val] + unitSymbol[val]
        this.option.yAxis.min = lowerLimit[val]

        this.patientDailyRecordList.forEach((value, index) => {
          if (Object.values(value)[val + 2] >= upThreshold[val]) {
            this.option.series[0].data[index] = {value: Object.values(value)[val + 2], itemStyle: {color: '#ff8c00'}}
          }
          else if(Object.values(value)[val + 2]<=downThreshold[val]){
            this.option.series[0].data[index] = {value: Object.values(value)[val + 2], itemStyle: {color: '#90ee90'}}
          }
          else {
            this.option.series[0].data[index] = Object.values(value)[val + 2]
          }
          this.option.xAxis.data[index] = value.pdrRecordingTime
        })

        console.log(this.option.series[0].data)
        // 使用刚指定的配置项和数据显示图表
        this.myChart.setOption(this.option)
      },

      // 获取患者信息
      getPatient() {
        this.$http.post('/api/patient/findPatientByPatId/' + this.$route.params.patId).then(res => {
          this.patient = res.data.patient
        }).catch(() => {
          this.$message.warning('获取患者信息失败!')
        })
      },
      // 返回到患者列表
      backToPatientList() {
        this.$router.push('/patients')
      }
    }
  }
</script>

<style scoped>
  .patientRow {
    width: 80%;
  }
</style>
