<template>


  <el-container class="home-container">
    <!--      头部区域-->
    <el-header>
      <div class="logo_div">
        <img src="../assets/logo.png" alt="" class="logo_img">
        <span class="logo_text">医院数据分析管理系统</span>
      </div>
      <el-button type="info" @click="logout" style="float: right">退出</el-button>
    </el-header>
    <!--      页面主体区域-->
    <el-container>
      <!--        侧边栏-->
      <el-aside :width="isCollapse?'64px': '170px'">
        <div class="toggle-button" @click="toggleCollapse">|||</div>
        <!--        <ul>
                  <li v-for="link in links" :key="link.path">
                    <router-link :to="link.path">{{link.name}}</router-link>
                  </li>
                </ul>-->
        <!--        侧边栏菜单区域-->
        <el-menu
          background-color='rgba(0,0,0,0)'
          text-color="#fff"
          active-text-color="#409EFF"
          unique-opened
          :collapse="isCollapse"
          :collapse-transition="false"
          router
          :default-active="$route.path"
        >

          <el-menu-item :index="oneLink.path" v-for="oneLink in oneLinks"
                        :key="oneLink.path"
                        @click="saveNavState(oneLink.path)">

              <i :class="oneLink.icon"></i>
              <span slot="title">{{ oneLink.name }}</span>

          </el-menu-item>
        </el-menu>
      </el-aside>
      <!--        右侧内容主体-->
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
    <el-footer>footer</el-footer>
  </el-container>


</template>

<script>
  export default {
    name: "Home",
    data() {
      return {
        oneLinks: [
          {name: "个人信息", path: "/setting", icon: "el-icon-s-tools"},
          {name: "患者管理", path: "/patients", icon: "el-icon-s-data"},
          {name: "心脏病诊断预测", path: "/heartPredict", icon: "el-icon-s-marketing"},

        ],
        //是否折叠
        isCollapse: false,
        //被激活的链接地址
        activePath: ''
      }
    },
    created() {

      this.activePath = window.sessionStorage.getItem('activePath')
      // console.log(this.activePath)
    },

    methods: {
      logout() {
        window.sessionStorage.clear();
        this.$router.push("/login");
      },
      //点击按钮，切换菜单的折叠与展开
      toggleCollapse() {
        this.isCollapse = !this.isCollapse
      },

      saveNavState(activePath) {
        window.sessionStorage.setItem('activePath', activePath)
        this.activePath = activePath
      }
    }
  }
</script>

<style scoped>
  .logo_div {
    display: flex;
    height: 50px;
    align-items: center;
  }

  .logo_text {
    margin-left: 15px;
  }

  .logo_img {

    height: 100%;
  }

  .home-container {
    height: 100%;
  }

  .el-container {
  }

  .el-header {

    background-color: #3d3d3b;
    display: flex;
    justify-content: space-between;
    padding-left: 0;
    align-items: center;
    color: #fff;
    font-size: 20px;

  }

  .el-aside {
    background: #2b5876; /* fallback for old browsers */
    background: -webkit-linear-gradient(to right, #4e4376, #2b5876); /* Chrome 10-25, Safari 5.1-6 */
    background: linear-gradient(to right, #4e4376, #2b5876); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */

  }

  .el-main {
    background-color: #ebedec;
  }

  .el-footer {
    background-color: #a2a9af;
  }

  .el-menu {
    border: 0;

  }
  .el-menu-item{

  }
  .toggle-button {

    font-size: 10px;
    line-height: 24px;
    color: #f9fafc;
    text-align: center;
    letter-spacing: 0.2em;
    cursor: pointer;

  }
</style>
