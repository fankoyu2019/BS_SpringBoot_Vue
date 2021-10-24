<template>
  <div class="login_container">
    <div class="login_box">
      <!--      头像区域-->
      <div class="avatar_box">
        <img src="./assets/bgi1911.jpg" alt="">
      </div>
      <!--      表单区域-->
      <el-form ref="form" :model="user" :rules="formRules" class="demo-ruleForm">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="user.username" prefix-icon="el-icon-user-solid"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="user.password" prefix-icon="el-icon-key" show-password></el-input>
        </el-form-item>
        <!--        <el-form-item label="确认密码" prop="password2">
                  <el-input v-model="user.password2" show-password></el-input>
                </el-form-item>-->
        <el-form-item align="left" class="btns">
          <el-button type="primary" :loading="submitLoading" @click="handleSubmit('form')">登录</el-button>
          <el-button type="info" @click="resetForm('form')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  export default {
    name: "",
    data() {
      let checkPwd2 = (rule, value, callback) => {
        if (value.trim().length == 0) {
          callback(new Error("确认密码不能为空!"));
        } else if (value != this.user.password) {
          callback(new Error("两次密码不一致!"));
        } else {
          callback();
        }
      }

      return {
        user: {
          username: "",
          password: "",
          password2: "",
        },
        submitLoading: false,
        formRules: {
          username: [
            {required: true, message: "用户名不能为空", trigger: "blur"},
            {min: 3, max: 10, message: "用户名长度在3-10个字符", trigger: "blur"}
          ],
          password: [
            {required: true, message: "密码不能为空", trigger: "blur"},
            {min: 6, max: 15, message: "用户名长度在6-15个字符", trigger: "blur"}
          ],
          password2: [
            {validator: checkPwd2, trigger: "blur"}
          ]
        }
      }
    },
    methods: {

      handleSubmit(formName) {
        //将按钮状态该为加载中
        this.submitLoading = !this.submitLoading
        //发起登录请求前再次进行表单校验
        //先获取表单对象，然后执行校验方法
        this.$refs["form"].validate(async (valid) => {
          //valid校验结果 true 成功 false 失败
          if (!valid) {
            this.submitLoading = !this.submitLoading
            return
          }

          this.$http.post("/api/user/login", this.user).then(res => {
            // console.log(res.status);
            // console.log("登录成功");
            console.log(res)
            // console.log(res.data.token)
            this.$message.success('登录成功!')
            window.sessionStorage.setItem("userId",res.data.userId);
            window.sessionStorage.setItem("token",res.data.token);
            this.submitLoading = !this.submitLoading
            this.$router.push('/home')
          }).catch((error)=>{
            this.submitLoading = !this.submitLoading
            this.$message.error('登录失败!')
          })


          /*this.axios.post("http://localhost:8009/user/login", this.user).then(res => {
            console.log(res.code);
            console.log(res.headers);
            console.log(res);
            const jwt = res.headers['authorization'];
            const userInfo = res.data.data;
            console.log(userInfo);

            //把数据共享出去
            _this.$store.commit("SET_TOKEN", jwt)
            _this.$store.commit("SET_USERINFO", userInfo)

            //获取
            console.log(_this.$store.getters.getUser())

            _this.$router.push("/")
          })*/
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        // this.$refs.form.resetFields();
      }
    }
  }
</script>

<style scoped>
  .demo-ruleForm {
    padding: 50px 0 0 0;
    max-width: 400px;
    margin: auto;
  }

  .login_container {
    background-image: linear-gradient(to top, #5ee7df 0%, #b490ca 100%);
    height: 100%;
  }

  .login_box {
    padding: 15px;
    width: 450px;

    background-color: #f9fafc;
    border-radius: 15px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);


  }

  .avatar_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eeeeee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #f9fafc;
  }

  img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-color: #eee;
  }

  .btns {
    display: flex;
    justify-content: flex-end;
  }
</style>
